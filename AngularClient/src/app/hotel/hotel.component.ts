import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Hotel } from './HotelDO'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { HotelRoomSharedService } from '../hotel/hotelRoomSharedService'
@Component({
  selector: 'app-hotel',
  templateUrl: './hotel.component.html',
  styleUrls: ['./hotel.component.css']
})
export class HotelComponent implements OnInit {

  @ViewChild('loader') loader: ElementRef;
  hotels: Hotel;
  debugger: string;

  constructor(private http: HttpClient,
              private hotelRoomService: HotelRoomSharedService) { }

  ngOnInit() {
    this.getHotels();
    this.debugger = "";
  }

  getHotels():void{
    this.startLoader();
    this.http.get<Hotel>('/hotel/get').subscribe(hotelGet => {
      this.hotels = hotelGet;
      this.stopLoader();
    });
  } 

  deleteHotel(hotel):void{
      this.startLoader();
      this.http.post('/hotel/delete', hotel).toPromise().then(res =>{
        this.getHotels();
        console.log(res)
      }).catch(error =>{
          console.log(error);
      });
  }

  showRooms(id, name):void{
      this.debugger = id;
      this.hotelRoomService.id = id;
      this.hotelRoomService.name = name;
      this.hotelRoomService.changedEmitter.emit(id);
  }

  stopLoader():void{
    this.loader.nativeElement.style.display="none";
  }

  startLoader():void{
    this.loader.nativeElement.style.display="block";
  }
}
