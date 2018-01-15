import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
import { FormGroup, FormBuilder, Validators , FormControl} from '@angular/forms';
import { Flight } from '../flight/FlightDO'
import { Router } from '@angular/router'
import { FlightSharedService } from './FlightSharedService'
@Component({
  selector: 'app-flight-edit',
  templateUrl: './flight-edit.component.html',
  styleUrls: ['./flight-edit.component.css']
})
export class FlightEditComponent implements OnInit {
  
    complexForm: FormGroup;
    @ViewChild('loader') loader: ElementRef;
    checkDateValid: boolean;
  
    constructor(private http: HttpClient, private router: Router, fb: FormBuilder,
    private flightSS: FlightSharedService) {
      this.complexForm = fb.group({
        'priceControl' : new FormControl('', [Validators.required, Validators.pattern("[0-9]+([.][0-9]?[0-9]?)?")]),
        'peopleNumberControl' : new FormControl('', [Validators.required, Validators.pattern("[0-9]+")]),
      });
     }
  
    ngOnInit() {
      this.stopLoader();
    }
  
    
    editFlight(price, peopleNumber){
        this.startLoader();
        let flight: Flight = {};
        flight = this.flightSS.flight;
        flight.price = price;
        flight.peopleNumber = peopleNumber;
         this.http.post('/flight/edit', flight).toPromise().then(res =>{
          this.stopLoader();
          this.router.navigate(['/flightManagement']);
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
  