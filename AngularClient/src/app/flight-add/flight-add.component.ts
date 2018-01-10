import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
import { Airport } from '../airport/AirportDO'
import { FormGroup, FormBuilder, Validators , FormControl} from '@angular/forms';
import { Flight } from '../flight/FlightDO'
import { Router } from '@angular/router'
@Component({
  selector: 'app-flight-add',
  templateUrl: './flight-add.component.html',
  styleUrls: ['./flight-add.component.css']
})
export class FlightAddComponent implements OnInit {

  complexForm: FormGroup;
  @ViewChild('loader') loader: ElementRef;
  checkDateValid: boolean;
  airports: Airport;

  constructor(private http: HttpClient, private router: Router, fb: FormBuilder) {
    this.complexForm = fb.group({
      'priceControl' : new FormControl('', [Validators.required, Validators.pattern("[0-9]+([.][0-9]?[0-9]?)?")]),
      'peopleNumberControl' : new FormControl('', [Validators.required, Validators.pattern("[0-9]+")]),
      'departureAirportControl' : new FormControl('', Validators.required),
      'arrivalAirportControl' : new FormControl('', Validators.required)
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

  datesValidation(departureDateTime, arrivalDateTime): void{
    if(new Date(arrivalDateTime).getTime()-new Date(departureDateTime).getTime() > 0){
      this.checkDateValid = true;
    }else{
      this.checkDateValid = false;
    }
    console.log(this.checkDateValid)
  }

  addFlight(departureAirport, arrivalAirport, departureDateTime, arrivalDateTime, price, peopleNumber){
      this.startLoader();
      let flight: Flight = {};
      flight.departureAirportId = departureAirport;
      flight.arrivalAirportId = arrivalAirport;
      flight.departureDate = departureDateTime.value;
      flight.arrivalDate = arrivalDateTime.value;
      flight.price = price;
      flight.peopleNumber = peopleNumber;
       this.http.post('/flight/add', flight).toPromise().then(res =>{
        this.stopLoader();
        this.router.navigate(['/flightManagement']);
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
