import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Hotel } from './HotelDO'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { HotelRoomSharedService } from '../hotel/hotelRoomSharedService'
import { Router } from '@angular/router'
import { HotelSharedService } from '../hotel-edit/HotelSharedService'
import { Ng2MessagePopupComponent, Ng2PopupComponent} from 'ng2-popup'
@Component({
  selector: 'app-hotel',
  templateUrl: './hotel.component.html',
  styleUrls: ['./hotel.component.css']
})
export class HotelComponent implements OnInit {

  @ViewChild(Ng2PopupComponent) popup: Ng2PopupComponent;
  @ViewChild('loader') loader: ElementRef;
  hotels: Hotel;
  debugger: string;

  constructor(private http: HttpClient, private router: Router,
              private hotelRoomService: HotelRoomSharedService, 
              private hotelSS: HotelSharedService) { }

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
        this.stopLoader();
        this.popup.open(Ng2MessagePopupComponent, {
          title: 'Operation denied',
          message: 'You want to remove object that is connected to other objects'
        });
      });
  }

  editHotel(hotel): void{
    this.hotelSS.hotel = hotel;
    this.router.navigate(['/hotelEdit']);
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
