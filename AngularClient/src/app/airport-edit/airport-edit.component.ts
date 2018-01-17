import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Airport } from '../airport/AirportDO'
import { FormGroup, FormBuilder, Validators , FormControl} from '@angular/forms';
import { Router } from '@angular/router'
import { AirportSharedService } from './AirportSharedService'
import { Ng2MessagePopupComponent, Ng2PopupComponent} from 'ng2-popup'
@Component({
  selector: 'app-airport-edit',
  templateUrl: './airport-edit.component.html',
  styleUrls: ['./airport-edit.component.css']
})
export class AirportEditComponent implements OnInit {
  complexForm: FormGroup;
  @ViewChild('loader') loader: ElementRef;
  @ViewChild(Ng2PopupComponent) popup: Ng2PopupComponent;

  constructor(private http: HttpClient, private router: Router, fb: FormBuilder,
  private airportSS: AirportSharedService) {
    this.complexForm = fb.group({
      'nameControl' : new FormControl('', Validators.required),
      'symbolControl' : new FormControl('',  Validators.required),
      'countryControl' : new FormControl('', Validators.required),
      'cityControl' : new FormControl('', Validators.required),
      'streetControl' : new FormControl('', Validators.required),
      'houseNrControl' : new FormControl('', Validators.required)
    }); 
              
  
  }

  ngOnInit() {
    this.stopLoader();
  }

  editAirport(name, symbol, country, city, street, houseNr):void{
    this.startLoader()
    let airport : Airport = {};  //oneClient is created to set values and insert into dataBase
    airport.name = name;
    airport.symbol = symbol;
    airport.country = country;
    airport.city = city;
    airport.street = street;
    airport.houseNumber = houseNr;
    airport.id = this.airportSS.airport.id;
    this.http.post('/airport/edit', airport).toPromise().then(res =>{
      this.stopLoader();
      this.router.navigate(['/airportManagement']);
      console.log(res)
    }).catch(error =>{
      this.stopLoader();
      this.popup.open(Ng2MessagePopupComponent, {
        title: 'Operation denied',
        message: 'Something went wrong. Probably input values are out of range'
      });
    });
    //this.getClients();
  }

  stopLoader():void{
    this.loader.nativeElement.style.display="none";
  }

  startLoader():void{
    this.loader.nativeElement.style.display="block";
  }
}
