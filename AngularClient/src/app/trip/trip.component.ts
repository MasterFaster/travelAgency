import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Trip } from './TripDO'
import { TripDetailsSharedService } from '../trip-details/tripDetailsSharedService'
import { Ng2MessagePopupComponent, Ng2PopupComponent} from 'ng2-popup'
@Component({
  selector: 'app-trip',
  templateUrl: './trip.component.html',
  styleUrls: ['./trip.component.css']
})
export class TripComponent implements OnInit {

  @ViewChild(Ng2PopupComponent) popup: Ng2PopupComponent;
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
      this.stopLoader();
      this.popup.open(Ng2MessagePopupComponent, {
        title: 'Operation denied',
        message: 'You want to remove object that is connected to other objects'
      });
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
