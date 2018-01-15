import { Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import { Airport } from './AirportDO'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Ng2MessagePopupComponent, Ng2PopupComponent} from 'ng2-popup'
import { AirportSharedService } from '../airport-edit/AirportSharedService'
import { Router } from '@angular/router'
@Component({
  selector: 'app-airport',
  templateUrl: './airport.component.html',
  styleUrls: ['./airport.component.css']
})
export class AirportComponent implements OnInit {

  @ViewChild(Ng2PopupComponent) popup: Ng2PopupComponent;
  @ViewChild('loader') loader: ElementRef;
  airports: Airport;

  constructor(private http: HttpClient, private airportSS: AirportSharedService,
  private router: Router) { }

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

  editAirport(airport): void{
    this.airportSS.airport = airport;
    this.router.navigate(['/airportEdit'])
  }


  stopLoader():void{
    this.loader.nativeElement.style.display="none";
  }

  startLoader():void{
    this.loader.nativeElement.style.display="block";
  }

}
