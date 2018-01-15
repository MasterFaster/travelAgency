import { Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import { Airport } from './AirportDO'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Ng2MessagePopupComponent, Ng2PopupComponent} from 'ng2-popup'
@Component({
  selector: 'app-airport',
  templateUrl: './airport.component.html',
  styleUrls: ['./airport.component.css']
})
export class AirportComponent implements OnInit {

  @ViewChild(Ng2PopupComponent) popup: Ng2PopupComponent;
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

  deleteAirport(airport): void{
    this.startLoader();
    this.http.post('/airport/delete',airport).toPromise().then(res =>{
      this.stopLoader();
      this.getAirports();
    }).catch(error =>{
      this.stopLoader();
      this.popup.open(Ng2MessagePopupComponent, {
        title: 'Operation denied',
        message: 'You want to remove object that is connected to other objects'
      });
  });
  }


  stopLoader():void{
    this.loader.nativeElement.style.display="none";
  }

  startLoader():void{
    this.loader.nativeElement.style.display="block";
  }

}
