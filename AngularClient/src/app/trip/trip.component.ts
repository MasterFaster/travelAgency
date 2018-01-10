import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Trip } from './TripDO'
import { TripDetailsSharedService } from '../trip-details/tripDetailsSharedService'
@Component({
  selector: 'app-trip',
  templateUrl: './trip.component.html',
  styleUrls: ['./trip.component.css']
})
export class TripComponent implements OnInit {

  @ViewChild('loader') loader: ElementRef;
  trips: Trip;

  constructor(private http: HttpClient,
              private tripDetailsSS: TripDetailsSharedService) { }

  ngOnInit() {
    this.getTrips();
  }

  getTrips():void{
    this.startLoader();
    this.http.get<Trip>('/trip/getAvailable').subscribe(tripGet => {
      this.trips = tripGet;
      this.stopLoader();
    });
  }


  deleteTrip(trip): void{
    this.startLoader();
    this.http.post('/trip/deleteAvailable',trip).toPromise().then(res =>{
      this.getTrips();
      console.log(res)
    }).catch(error =>{
        console.log(error);
    });
  }

  setTripSharedService(trip): void{
      this.tripDetailsSS.trip = trip;
  }

  stopLoader():void{
    this.loader.nativeElement.style.display="none";
  }

  startLoader():void{
    this.loader.nativeElement.style.display="block";
  }

}
