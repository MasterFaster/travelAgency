import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HotelRoomSharedService } from '../hotel/hotelRoomSharedService'
import { HotelRoom } from './HotelRoomDO'
import { FormGroup, FormBuilder, Validators , FormControl} from '@angular/forms';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
import { URLSearchParams } from '@angular/http'
@Component({
  selector: 'app-hotel-room',
  templateUrl: './hotel-room.component.html',
  styleUrls: ['./hotel-room.component.css']
})
export class HotelRoomComponent implements OnInit {

  complexForm: FormGroup;
  @ViewChild('loader') loader: ElementRef;
  rooms: HotelRoom;
  id: number;

  constructor(private http: HttpClient,
    private hotelRoomService: HotelRoomSharedService,
    fb: FormBuilder) {
    this.hotelRoomService.changedEmitter.subscribe(
      (data: any) => {
        this.id = data;
        this.getRooms(this.id);
      }
    )

    this.complexForm = fb.group({
      'roomNumberControl' : new FormControl('', [Validators.required, Validators.pattern("[0-9]+")]),
      'peopleNumberControl' : new FormControl('',  [Validators.required, Validators.pattern("[0-9]+")]),
      'priceControl' : new FormControl('', [Validators.required, Validators.pattern("[0-9]+([.][0-9]?[0-9]?)?")])
    });
   }

  
  ngOnInit() {
    this.id = this.hotelRoomService.id;
    this.getRooms(this.id);
  }

  getRooms(id): void{ 
    this.startLoader();
    let params = new HttpParams().set('id',id);
    this.http.get<HotelRoom>('/room/get',{params: params}).subscribe(hotelRoomGet => {
      this.rooms = hotelRoomGet;
      this.stopLoader();
    });
  }

  addRoom(roomNumber, peopleNumber, price):void{
    this.startLoader();
      let room: HotelRoom = {};
      room.roomNumber = roomNumber;
      room.peopleNumber = peopleNumber;
      price = price.replace(",",".");
      room.price = price;
      room.hotelId = this.id;
      this.http.post('/room/add', room).toPromise().then(res =>{
        this.getRooms(this.id);
        console.log(res)
      }).catch(error =>{
          console.log(error);
      });
  }

  deleteRoom(room): void{
    this.startLoader();
    this.http.post('/room/delete',room).toPromise().then(res =>{
      this.getRooms(this.id);
      console.log(res)
    }).catch(error =>{
        console.log(error);
    });
  }

  stopLoader():void{
    this.loader.nativeElement.style.display="none";
  }

  startLoader():void{
    this.loader.nativeElement.style.display="block";
  }

}
