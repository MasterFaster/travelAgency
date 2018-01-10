import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
import { ReservationDetailsSharedService } from './reservationDetailsSharedService'
import {Client} from '../client/ClientDO'
import { Hotel } from '../hotel/HotelDO'
import { HotelRoom } from '../hotel-room/HotelRoomDO'
import { Insurance } from '../Insurance/InsuranceDO'
import { Trip } from '../trip/TripDO'
import { FlightMain } from '../reservation-add-flight/FlightMainDO'
import { FlightMainSharedService } from '../reservation-flight-main-details/FlightMainSharedService'
import { Payment } from '../payment-add/Payment'
import { Reservation } from '../reservation/ReservationDO'
@Component({
  selector: 'app-reservation-details',
  templateUrl: './reservation-details.component.html',
  styleUrls: ['./reservation-details.component.css']
})
export class ReservationDetailsComponent implements OnInit {

  @ViewChild('loader') loader: ElementRef;
  client: Client;
  hotel: Hotel;
  rooms: HotelRoom;
  insurances: Insurance;
  trips: Trip;
  flightMains: FlightMain;
  payments: Payment;
  isClientAvailable: boolean = false;
  isHotelAvailable: boolean = false;
  //+1 when startLoader, -1 when stopLoader, stop when loaderCounter == 0
  loaderCounter: number = 0; 

  constructor(private http: HttpClient,
                private reservationDetailsSS: ReservationDetailsSharedService,
                private flightMainSharedService: FlightMainSharedService) { }
  
  ngOnInit() {
      //console.log(this.reservationDetailsSS.reservation.clientId)
      this.updateReservationInfo();
      //this.getSignleClient();
      //this.getSingleHotel();
      this.getRooms();
      this.getInsurances();
      this.getTrips();
      this.getFlightMain();
      this.getPayments(); 
      this.isClientAvailable = true;
      this.isHotelAvailable = true;
  }

  updateReservationInfo(): void{
    this.startLoader();
    let params = new HttpParams().set('id', this.reservationDetailsSS.reservation.id.toString());
    this.http.get<Reservation>('/reservation/getSingle',{params: params}).subscribe(reservationGet => {
      this.reservationDetailsSS.reservation = reservationGet;
      this.stopLoader();
    })
  }

  getSignleClient(): void{
    this.startLoader();
    console.log(this.reservationDetailsSS.reservation.clientId.toString() )
    let params =new HttpParams().set('id',this.reservationDetailsSS.reservation.clientId.toString());
    this.http.get<Client>('/client/getSingle',{params: params}).subscribe(clientsGet => {
      this.client = clientsGet;
      this.stopLoader();
      this.isClientAvailable = true;
    });
  }

  getSingleHotel(): void{
    this.startLoader();
    let params = new HttpParams().set('id', this.reservationDetailsSS.reservation.hotelId.toString())
    this.http.get<Hotel>('/hotel/getSingle',{params: params}).subscribe(hotelGet => {
      this.hotel = hotelGet;
      this.stopLoader();
      this.isHotelAvailable = true;
    });
  }

  getRooms(): void{ 
    this.startLoader();
    let params = new HttpParams().set('id',this.reservationDetailsSS.reservation.id.toString());
    this.http.get<HotelRoom>('/room/getReservation',{params: params}).subscribe(hotelRoomGet => {
      this.rooms = hotelRoomGet;
      this.stopLoader();
    });
  }

  getInsurances(): void{
    this.startLoader();
    let params = new HttpParams().set('id',this.reservationDetailsSS.reservation.id.toString());
    this.http.get<HotelRoom>('/insurance/get',{params: params}).subscribe(insurancesGet => {
      this.insurances = insurancesGet;
      this.stopLoader();
    });
  }

  getTrips(): void{
    this.startLoader();
    let params = new HttpParams().set('id', this.reservationDetailsSS.reservation.id.toString());
    this.http.get<Trip>('/trip/getReservation',{params: params}).subscribe(tripsGet => {
      this.trips = tripsGet;
      this.stopLoader();
    });
  }

  getFlightMain(): void{
    this.startLoader();
    let params = new HttpParams().set('id', this.reservationDetailsSS.reservation.id.toString());
    this.http.get<FlightMain>('/flightMain/getReservation',{params: params}).subscribe(flightMainGet =>{
      this.flightMains = flightMainGet;
      this.stopLoader();
    })
  }

  setFlightMainSharedService(id): void{
    this.flightMainSharedService.id = id;
  }

  deleteFlightMain(flightMain): void{
    this.loaderCounter = 0;
    this.startLoader();
    this.http.post('/flightMain/delete', flightMain).toPromise().then(res =>{
      this.updateReservationInfo();
      this.getFlightMain();
      this.stopLoader();
    })
  }

  getPayments(): void{
    this.startLoader();
    let params = new HttpParams().set('id', this.reservationDetailsSS.reservation.id.toString());
    this.http.get<Payment>('/payment/getReservation',{params: params}).subscribe(paymentGet =>{
      this.payments = paymentGet;
      this.stopLoader();
    })
  }

  deletePayment(payment): void{
    this.loaderCounter = 0;
    this.startLoader();
    this.http.post('payment/delete', payment).toPromise().then(res =>{
      this.updateReservationInfo();
      this.getPayments();
      this.stopLoader();
    })
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
