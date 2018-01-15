import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Flight } from './FlightDO'
import { Ng2MessagePopupComponent, Ng2PopupComponent} from 'ng2-popup'
import { FlightSharedService } from '../flight-edit/FlightSharedService'
import { Router } from '@angular/router'
@Component({
  selector: 'app-flight',
  templateUrl: './flight.component.html',
  styleUrls: ['./flight.component.css']
})
export class FlightComponent implements OnInit {

  @ViewChild(Ng2PopupComponent) popup: Ng2PopupComponent;
  @ViewChild('loader') loader: ElementRef;
  flights: Flight;

  constructor(private http: HttpClient, private router: Router, private flightSS: FlightSharedService) { }

  ngOnInit() {
      this.getFlights();
  }

  getFlights(): void{
    this.startLoader();
    this.http.get<Flight>('/flight/get').subscribe(flightGet => {
      this.flights = flightGet;
      this.stopLoader();
    });
  }

  deleteFlight(flight): void{
    this.startLoader();
    this.http.post('/flight/delete',flight).toPromise().then(res =>{
      this.getFlights();
      console.log(res)
    }).catch(error =>{
      this.stopLoader();
      this.popup.open(Ng2MessagePopupComponent, {
        title: 'Operation denied',
        message: 'You want to remove object that is connected to other objects'
      });
    });
  }

  editFlight(flight): void{
    this.flightSS.flight = flight;
    this.router.navigate(['flightEdit'])
  }

  stopLoader():void{
    this.loader.nativeElement.style.display="none";
  }

  startLoader():void{
    this.loader.nativeElement.style.display="block";
  }

}
