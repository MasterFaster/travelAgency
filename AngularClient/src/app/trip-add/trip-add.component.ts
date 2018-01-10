import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Router } from '@angular/router'
import { FormGroup, FormBuilder, Validators , FormControl} from '@angular/forms';
import { Trip } from '../trip/TripDO'
import { Hotel } from '../hotel/HotelDO'
@Component({
  selector: 'app-trip-add',
  templateUrl: './trip-add.component.html',
  styleUrls: ['./trip-add.component.css']
})
export class TripAddComponent implements OnInit {

  complexForm: FormGroup;
  @ViewChild('loader') loader: ElementRef;
  hotels: Hotel;
  selectedHotels: Array<Hotel> = [];

  constructor(private http: HttpClient, private router: Router, fb: FormBuilder) {
    this.complexForm = fb.group({
      'destinationControl' : new FormControl('', Validators.required),
      'priceControl' : new FormControl('', [Validators.required, Validators.pattern("[0-9]+([.][0-9]?[0-9]?)?")])
    });
   }

  ngOnInit() {
    this.stopLoader();
    this.getHotels();
  }

  addTrip(destination, price):void{
    this.startLoader();
    let trip: Trip = {};
    trip.destination = destination;
    trip.price = price;
    trip.hotels = this.selectedHotels;
    this.http.post('/trip/addAvailable', trip).toPromise().then(res =>{
      this.stopLoader();
      this.router.navigate(['/tripManagement']);
      console.log(res)
    }).catch(error =>{
        console.log(error);
    });
  }

  getHotels():void{
      this.startLoader();
      this.http.get<Hotel>('/hotel/get').subscribe(hotelGet => {
        this.hotels = hotelGet;
        this.stopLoader();
      });
  }

  selectedHotelChanged(hotel): void{
    console.log(hotel.id);
    var index;
    index = this.selectedHotels.indexOf(hotel); 
    if(index != -1){
        this.selectedHotels.splice(index,1);
    }else{
      this.selectedHotels.push(hotel);
    }
    console.log(this.selectedHotels);
  }

  stopLoader():void{
    this.loader.nativeElement.style.display="none";
  }

  startLoader():void{
    this.loader.nativeElement.style.display="block";
  }

}
