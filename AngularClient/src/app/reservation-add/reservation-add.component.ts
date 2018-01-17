import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import {Client} from '../client/ClientDO';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
import { URLSearchParams, RequestOptions } from '@angular/http'
import { FormGroup, FormBuilder, Validators , FormControl} from '@angular/forms';
import { Hotel } from '../hotel/HotelDO'
import {Reservation} from '../reservation/ReservationDO'
import { HotelRoom } from '../hotel-room/HotelRoomDO'
import { Insurance } from '../insurance/InsuranceDO'
import { Router } from '@angular/router'
import { Trip } from '../trip/TripDO'
import { Ng2MessagePopupComponent, Ng2PopupComponent} from 'ng2-popup'
@Component({
  selector: 'app-reservation-add',
  templateUrl: './reservation-add.component.html',
  styleUrls: ['./reservation-add.component.css']
})
export class ReservationAddComponent implements OnInit {

  complexForm: FormGroup;
  checkDateValid: boolean;
  @ViewChild('loader') loader: ElementRef;
  @ViewChild(Ng2PopupComponent) popup: Ng2PopupComponent;
  clients: Client;
  hotels: Hotel;
  rooms: HotelRoom;
  trips: Trip;
  selectedTrips: Array<Trip> = [];
  selectedRooms: Array<number>[] = [];
  insurances: Insurance;
  selectedInsurances: Array<Insurance> = [];
  ifNoRooms: boolean = false;
  //+1 when startLoader, -1 when stopLoader, stop when loaderCounter == 0
  loaderCounter: number; 
  
  constructor(private http: HttpClient, private router: Router, fb: FormBuilder) {
    this.complexForm = fb.group({
      'clientControl' : new FormControl('', Validators.required),
      'hotelControl' : new FormControl('', Validators.required),
      'peopleNumberControl' : new FormControl('', [Validators.required, Validators.pattern('[0-9]+'), Validators.max(500)])
    });
   }

  ngOnInit() {
    this.loaderCounter = 0;
    this.getClients();
    this.getHotels();
  }

  datesValidation(departureDate, arrivalDate): void{
    if(new Date(arrivalDate).getTime()-new Date(departureDate).getTime() > 0){
      this.checkDateValid = true;
    }else{
      this.checkDateValid = false;
    }
    //console.log(this.checkDateValid)
    //console.log(new Date(arrivalDate).getTime()-new Date(departureDate).getTime());
  }

  getClients():void{
    this.startLoader();
    this.http.get<Client>('/client/get').subscribe(clientsGet => {
      this.clients = clientsGet;
      //this.getClients();
      this.stopLoader();
    });
  }

  getHotels():void{
    this.startLoader();
    this.http.get<Hotel>('/hotel/get').subscribe(hotelGet => {
      this.hotels = hotelGet;
      this.stopLoader();
    });
  }

  selectedRoomChanged(id): void{
    console.log(id);
    var index;
    index = this.selectedRooms.indexOf(id); 
    if(index != -1){
        this.selectedRooms.splice(index,1);
    }else{
      this.selectedRooms.push(id);
    }
    console.log(this.selectedRooms  );
  }

  
  showAvailableRooms(selectedHotel, departureDate, returnDate,peopleNumber):void{
    this.getAvailableInsurances(departureDate,returnDate,peopleNumber);
    this.getAvailableTrips(selectedHotel);
    this.startLoader();
    console.log(departureDate.value);
    let params = new HttpParams().set('id',selectedHotel).set('departureDate', departureDate.value).set('returnDate', returnDate.value);
    this.http.get<HotelRoom>('/room/getAvailable', {params: params}).subscribe(hotelRoomGet => {
      this.rooms = hotelRoomGet;
      if(hotelRoomGet == false){
        this.ifNoRooms = true;
      }else{
        this.ifNoRooms = false;
      }
      this.stopLoader();
    });
  }

  //download available trips for hotel with id-selectedHotel
  getAvailableTrips(selectedHotel): void{
      this.startLoader();
      let params = new HttpParams().set('id',selectedHotel);
      this.http.get<HotelRoom>('/trip/getHotelAvailable', {params: params}).subscribe(tripGet => {
        this.trips = tripGet;
        this.stopLoader();
      });
  }

  tripPeopleNumberChanged(trip, peopleNumber){
    trip.peopleNumber = peopleNumber;
    var index;
    index = this.selectedTrips.indexOf(trip); 
    if(index != -1){
        this.selectedTrips[index].peopleNumber = peopleNumber;
        this.selectedTrips[index].countedPrice = trip.price * peopleNumber;
        //this.selectedTrips.splice(index,1);
    }else{
      trip.countedPrice = trip.price * peopleNumber;
      trip.peopleNumber = peopleNumber;
    }
    console.log(this.selectedTrips);
  }

  selectedTripChanged(trip, peopleNumber): void{
    console.log(trip);
    var index;
    index = this.selectedTrips.indexOf(trip); 
    if(index != -1){
        this.selectedTrips.splice(index,1);
    }else{
      trip.countedPrice = trip.price * peopleNumber;
      this.selectedTrips.push(trip);
    }
    console.log(this.selectedTrips);
  }

  reset(): void{
    this.rooms = null;
    this.ifNoRooms = false;
    this.selectedRooms = [];
    this.insurances = null;
    this.selectedInsurances = [];
    this.trips = null;
    this.selectedTrips=[];
  }

  getAvailableInsurances(departureDate, returnDate, peopleNumber): void{
    this.startLoader();
    let params = new HttpParams().set('departureDate', departureDate.value).set('returnDate', returnDate.value).set('peopleNumber',peopleNumber);
    this.http.get<Insurance>('/insurance/getAvailableForReservation', {params: params}).subscribe(insuranceGet => {
      this.insurances = insuranceGet;
      this.stopLoader();
    });
  }

  selectedInsuranceChanged(insurance): void{
      console.log(insurance);
      var index;
      index = this.selectedInsurances.indexOf(insurance); 
      if(index != -1){
          this.selectedInsurances.splice(index,1);
      }else{
        this.selectedInsurances.push(insurance);
      }
      console.log(this.selectedInsurances  );
  }

  addReservation(clientId, hotelId, departureDate, returnDate): void{
    this.startLoader();
    let reservation: Reservation = {};
    reservation.clientId = clientId;
    reservation.hotelId = hotelId;
    reservation.departureDate = departureDate.value;
    reservation.returnDate = returnDate.value;
    reservation.rooms = this.selectedRooms;
    reservation.insurances = this.selectedInsurances;
    reservation.trips = this.selectedTrips;
    this.http.post('/reservation/add', reservation).toPromise().then(res =>{
      console.log(res)
      this.router.navigate(['/reservationManagement']);
    }).catch(error =>{
      this.stopLoader();
      this.popup.open(Ng2MessagePopupComponent, {
        title: 'Operation denied',
        message: 'Something went wrong.'
      });
    });
    console.log(departureDate.value)
    console.log(clientId)
    console.log(hotelId)
    console.log()
    
    //this.stopLoader();
  }

  stopLoader():void{
    this.loaderCounter -= 1;
    if(this.loaderCounter == 0){
      this.loader.nativeElement.style.display="none";
    }
  }

  startLoader():void{
    this.loaderCounter +=1 ;
    this.loader.nativeElement.style.display="block";
  }

}
