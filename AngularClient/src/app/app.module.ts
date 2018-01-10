import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {RouterModule} from '@angular/router'
import { HttpClientModule } from '@angular/common/http'
import { AppComponent } from './app.component';
import { ClientComponent } from './client/client.component';
import { AppComponents, AppRoutes} from '../app.routing';
import { HotelComponent } from './hotel/hotel.component';
import { HotelRoomComponent } from './hotel-room/hotel-room.component';
import { HotelRoomSharedService } from './hotel/hotelRoomSharedService';
import { ReservationComponent } from './reservation/reservation.component';
import { FormsModule } from '@angular/forms';
import { ReservationDetailsComponent } from './reservation-details/reservation-details.component';
import { ReservationDetailsSharedService } from './reservation-details/reservationDetailsSharedService';
import { ClientAddComponent } from './client-add/client-add.component'
import { HotelAddComponent} from './hotel-add/hotel-add.component';
import { AirportComponent } from './airport/airport.component';
import { AirportAddComponent } from './airport-add/airport-add.component';
import { FlightComponent } from './flight/flight.component';
import { FlightAddComponent } from './flight-add/flight-add.component'
import { AlertModule } from 'ngx-bootstrap';
import { InsuranceComponent } from './insurance/insurance.component';
import { InsuranceAddComponent } from './insurance-add/insurance-add.component';
import { TripComponent } from './trip/trip.component';
import { TripAddComponent } from './trip-add/trip-add.component';
import { TripDetailsComponent } from './trip-details/trip-details.component';
import { TripDetailsSharedService } from './trip-details/tripDetailsSharedService';
import { ReservationAddComponent } from './reservation-add/reservation-add.component'
import { FormBuilder,ReactiveFormsModule } from '@angular/forms';
import { ReservationAddFlightComponent } from './reservation-add-flight/reservation-add-flight.component';
import { ReservationFlightMainDetailsComponent } from './reservation-flight-main-details/reservation-flight-main-details.component';
import { FlightMainSharedService } from './reservation-flight-main-details/FlightMainSharedService';
import { PaymentAddComponent } from './payment-add/payment-add.component';
import { CashStatsComponent } from './cash-stats/cash-stats.component'
@NgModule({
  declarations: [
    AppComponent,
    ClientComponent,
    HotelComponent,
    HotelRoomComponent,
    ReservationComponent,
    ReservationDetailsComponent,
    ClientAddComponent,
    HotelAddComponent,
    AirportComponent,
    AirportAddComponent,
    FlightComponent,
    FlightAddComponent,
    InsuranceComponent,
    InsuranceAddComponent,
    TripComponent,
    TripAddComponent,
    TripDetailsComponent,
    ReservationAddComponent,
    ReservationAddFlightComponent,
    ReservationFlightMainDetailsComponent,
    PaymentAddComponent,
    CashStatsComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    RouterModule.forRoot(AppRoutes),
    HttpClientModule,
    FormsModule,
    AlertModule.forRoot(),
    ReactiveFormsModule
  ],
  providers: [HotelRoomSharedService, ReservationDetailsSharedService, TripDetailsSharedService, FormBuilder, FlightMainSharedService],
  bootstrap: [AppComponent]
})
export class AppModule { }