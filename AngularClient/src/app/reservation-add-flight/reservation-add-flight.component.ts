import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
import { Airport } from '../airport/AirportDO'
import { FormGroup, FormBuilder, Validators , FormControl} from '@angular/forms';
import { Router } from '@angular/router'
import { ReservationDetailsSharedService } from '../reservation-details/reservationDetailsSharedService'
import { FlightMain } from './FlightMainDO'
import { Ng2MessagePopupComponent, Ng2PopupComponent} from 'ng2-popup'
@Component({
  selector: 'app-reservation-add-flight',
  templateUrl: './reservation-add-flight.component.html',
  styleUrls: ['./reservation-add-flight.component.css']
})
export class ReservationAddFlightComponent implements OnInit {

  complexForm: FormGroup;
  @ViewChild('loader') loader: ElementRef;
  @ViewChild(Ng2PopupComponent) popup: Ng2PopupComponent;
  airports: Airport;
  flightMains: FlightMain;
  flightMainsAvailable:boolean = false;
  flightMainsListEmpty: boolean = false;

  constructor(private http: HttpClient, private router: Router, fb: FormBuilder,
  private reservationDetailsSS: ReservationDetailsSharedService) {
    this.complexForm = fb.group({
      'peopleNumberControl' : new FormControl('', [Validators.required, Validators.pattern("[0-9]+")]),
      'departureAirportControl' : new FormControl('', Validators.required),
      'arrivalAirportControl' : new FormControl('', Validators.required),
      'departureDateControl' : new FormControl('', Validators.required)
    });
   }

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

  showAvailableFlights(departureDate, departureAirport, arrivalAirport, peopleNumber): void{
    this.startLoader();
   
    let params = new HttpParams().set('departureDate', departureDate.value)
      .set('peopleNumber', peopleNumber).set('departureAirportId', departureAirport)
      .set('arrivalAirportId', arrivalAirport);

    this.http.get<FlightMain>('/flightMain/getAvailable',{params: params}).subscribe(flightMainGet => {
      this.flightMains = flightMainGet;
      if(this.flightMains.flights.length == 0){
        this.flightMainsAvailable = false;
        this.flightMainsListEmpty = true;
      }else{
        this.flightMainsAvailable = true;
      }
      this.stopLoader();
      
    });
  }

  addFlights(): void{
    this.startLoader();
    this.flightMains.reservationId = this.reservationDetailsSS.reservation.id;
    this.http.post('/flightMain/add', this.flightMains).toPromise().then(res =>{
      this.stopLoader();
      console.log(res)
      this.router.navigate(['/reservationDetailsManagement']);
    }).catch(error =>{
      this.stopLoader();
      this.popup.open(Ng2MessagePopupComponent, {
        title: 'Operation denied',
        message: 'Something went wrong. Probably input values are out of range'
      });
    });
  }

  reset():void{
    this.flightMains = null;
    this.flightMainsAvailable = false;
    this.flightMainsListEmpty = false;
  }

  stopLoader():void{
    this.loader.nativeElement.style.display="none";
  }

  startLoader():void{
    this.loader.nativeElement.style.display="block";
  }

}
