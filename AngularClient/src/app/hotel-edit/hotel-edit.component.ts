import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Hotel } from '../hotel/HotelDO'
import { FormGroup, FormBuilder, Validators , FormControl} from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Router } from '@angular/router'
import { HotelSharedService } from './HotelSharedService'
import { Ng2MessagePopupComponent, Ng2PopupComponent} from 'ng2-popup'
@Component({
  selector: 'app-hotel-edit',
  templateUrl: './hotel-edit.component.html',
  styleUrls: ['./hotel-edit.component.css']
})
export class HotelEditComponent implements OnInit {
  
    complexForm: FormGroup;
    @ViewChild('loader') loader: ElementRef;
    @ViewChild(Ng2PopupComponent) popup: Ng2PopupComponent;

    constructor(private http: HttpClient, private router: Router, fb: FormBuilder,
    private hotelSS: HotelSharedService) { 
      this.complexForm = fb.group({
        'nameControl' : new FormControl('', Validators.required),
        'starsControl' : new FormControl('',  Validators.pattern("[0-9]+")),
        'countryControl' : new FormControl('', Validators.required),
        'cityControl' : new FormControl('', Validators.required),
        'streetControl' : new FormControl('', Validators.required),
        'houseNrControl' : new FormControl('', Validators.required)
      });
           
    }
    
      ngOnInit() {
        this.stopLoader();
      }
    
    
      editHotel(name, stars, country, city, street, houseNr):void{
        this.startLoader();
        let hotel: Hotel = {};
        hotel.name = name;
        hotel.stars =  stars;
        hotel.country = country;
        hotel.city = city;
        hotel.street = street;
        hotel.houseNumber = houseNr;
        hotel.id = this.hotelSS.hotel.id;
        this.http.post('/hotel/edit', hotel).toPromise().then(res =>{
          //this.getHotels();
          this.stopLoader();
          this.router.navigate(['/hotelManagement']);
          console.log(res)
        }).catch(error =>{
          this.stopLoader();
          this.popup.open(Ng2MessagePopupComponent, {
            title: 'Operation denied',
            message: 'Something went wrong. Probably input values are out of range'
          });
        });
        //this.getHotels();
      }
  
      stopLoader():void{
        this.loader.nativeElement.style.display="none";
      }
    
      startLoader():void{
        this.loader.nativeElement.style.display="block";
      }
  }
  