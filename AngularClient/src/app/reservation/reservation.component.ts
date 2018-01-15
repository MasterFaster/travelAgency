import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import {Client} from '../client/ClientDO';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
import { Hotel } from '../hotel/HotelDO'
import { HotelRoom } from '../hotel-room/HotelRoomDO'
import { NgForm } from '@angular/forms'
import {Reservation} from './ReservationDO'
import { URLSearchParams, RequestOptions } from '@angular/http'
import { ReservationDetailsSharedService } from '../reservation-details/reservationDetailsSharedService'
import { Insurance } from '../insurance/InsuranceDO'
@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

  @ViewChild('loader') loader: ElementRef;
  reservations: Reservation;
  //+1 when startLoader, -1 when stopLoader, stop when loaderCounter == 0
  loaderCounter: number; 

  constructor(private http: HttpClient,
              private reservationDetailsSS: ReservationDetailsSharedService) { }

  ngOnInit() {
    this.loaderCounter = 0;
    this.getReservations();
  }


  getReservations(): void{
    this.startLoader();
    this.http.get<Reservation>('/reservation/get').subscribe(reservationGet => {
      this.reservations = reservationGet;
      this.stopLoader();
    });
  }

  findReservation(firstName, secondName, hotelName): void{
    this.startLoader();
    console.log("finding specified reservation")
    let params = new HttpParams().set("firstName", firstName)
    .set("secondName",secondName).set("hotelName",hotelName);
    this.http.get<Reservation>('/reservation/getSpecified',{params: params}).subscribe(reservationGet => {
      this.reservations = reservationGet;
      this.stopLoader();
    })
  }

  deleteReservation(reservation): void{
    this.startLoader();
    this.http.post('/reservation/delete', reservation).toPromise().then(res =>{
      this.getReservations();
      this.stopLoader();
      console.log(res)
    }).catch(error =>{
        console.log(error);
    });
  }

  setReservationSharedService(reservation): void{
      this.reservationDetailsSS.reservation = reservation;
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
