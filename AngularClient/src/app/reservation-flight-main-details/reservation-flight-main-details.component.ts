import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FlightMainSharedService } from './FlightMainSharedService'
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
import { Flight } from '../flight/FlightDO' 
@Component({
  selector: 'app-reservation-flight-main-details',
  templateUrl: './reservation-flight-main-details.component.html',
  styleUrls: ['./reservation-flight-main-details.component.css']
})
export class ReservationFlightMainDetailsComponent implements OnInit {

  @ViewChild('loader') loader: ElementRef;
  flights: Flight;

  constructor(private http: HttpClient,
               private flightMainSharedService: FlightMainSharedService) {

  }

  ngOnInit() {
    this.getFlights();
  }

  getFlights(): void{
    this.startLoader();
    let params =new HttpParams().set('id',this.flightMainSharedService.id.toString());
    this.http.get<Flight>('/flightMain/getFlights',{params: params}).subscribe(flightsGet => {
      this.flights = flightsGet;
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
