import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
import { TripDetailsSharedService } from './tripDetailsSharedService'
import { Trip } from '../trip/TripDO'
import { Hotel } from '../hotel/HotelDO'
@Component({
  selector: 'app-trip-details',
  templateUrl: './trip-details.component.html',
  styleUrls: ['./trip-details.component.css']
})
export class TripDetailsComponent implements OnInit {

  @ViewChild('loader') loader: ElementRef;
  hotels: Hotel;

  constructor(private http: HttpClient,
              private tripDetailsSS: TripDetailsSharedService) { }

  ngOnInit() {
    this.getHotels();
  }

  getHotels(): void{
    this.startLoader();
    let params = new HttpParams().set('id',this.tripDetailsSS.trip.id.toString());
    this.http.get<Hotel>('/trip/getHotels',{params: params}).subscribe(hotelGet => {
      this.hotels = hotelGet;
      this.stopLoader();
    });
  }

  stopLoader():void{
    this.loader.nativeElement.style.display="none";
  }

  startLoader():void{
    this.loader.nativeElement.style.display="block";
  }

}
