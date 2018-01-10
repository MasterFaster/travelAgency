import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Flight } from './FlightDO'
@Component({
  selector: 'app-flight',
  templateUrl: './flight.component.html',
  styleUrls: ['./flight.component.css']
})
export class FlightComponent implements OnInit {

  @ViewChild('loader') loader: ElementRef;
  flights: Flight;

  constructor(private http: HttpClient) { }

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
