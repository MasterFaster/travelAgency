import { Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import { Airport } from './AirportDO'
import { HttpClient, HttpHeaders } from '@angular/common/http'
@Component({
  selector: 'app-airport',
  templateUrl: './airport.component.html',
  styleUrls: ['./airport.component.css']
})
export class AirportComponent implements OnInit {

  @ViewChild('loader') loader: ElementRef;
  airports: Airport;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.getAirports();
  }

  getAirports():void{
    this.startLoader();
    this.http.get<Airport>('/airport/get').subscribe(airportsGet => {
      this.airports = airportsGet;
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
