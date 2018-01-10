import { ClientComponent } from './app/client/client.component';
import { HotelComponent } from './app/hotel/hotel.component'
import { HotelRoomComponent } from './app/hotel-room/hotel-room.component'
import { ReservationComponent } from './app/reservation/reservation.component'
import { ReservationDetailsComponent } from './app/reservation-details/reservation-details.component'
import {ClientAddComponent} from './app/client-add/client-add.component'
import { HotelAddComponent } from './app/hotel-add/hotel-add.component'
import { AirportComponent } from './app/airport/airport.component'
import { AirportAddComponent } from './app/airport-add/airport-add.component'
import { FlightComponent } from './app/flight/flight.component'
import { FlightAddComponent } from './app/flight-add/flight-add.component'
import { InsuranceComponent } from './app/insurance/insurance.component'
import { InsuranceAddComponent } from './app/insurance-add/insurance-add.component'
import { TripComponent } from './app/trip/trip.component' 
import { TripAddComponent } from './app/trip-add/trip-add.component'
import { TripDetailsComponent } from './app/trip-details/trip-details.component'
import { ReservationAddComponent } from './app/reservation-add/reservation-add.component'
import { ReservationAddFlightComponent } from './app/reservation-add-flight/reservation-add-flight.component'
import { ReservationFlightMainDetailsComponent } from './app/reservation-flight-main-details/reservation-flight-main-details.component'
import { PaymentAddComponent } from './app/payment-add/payment-add.component'
import { CashStatsComponent } from './app/cash-stats/cash-stats.component'
export const AppRoutes: any = [
    { path: "clientManagement", component: ClientComponent},
    { path: "hotelManagement", component: HotelComponent, children: [
        { path: "hotelRoomManagement", component: HotelRoomComponent, outlet: 'roomProperties'}
    ]},
    { path: "reservationManagement", component: ReservationComponent },
    { path: "reservationDetailsManagement", component: ReservationDetailsComponent },
    { path: "addClient", component: ClientAddComponent },
    { path: "addHotel", component: HotelAddComponent },
    { path: "airportManagement", component: AirportComponent },
    { path: "addAirport", component: AirportAddComponent },
    { path: "flightManagement", component: FlightComponent },
    { path: "addFlight", component: FlightAddComponent },
    { path: "insuranceManagement", component: InsuranceComponent },
    { path: "addAvailableInsurance", component: InsuranceAddComponent },
    { path: "tripManagement", component: TripComponent },
    { path: "addTrip", component: TripAddComponent },
    { path: "tripDetailsManagement", component: TripDetailsComponent },
    { path: "addReservation", component: ReservationAddComponent },
    { path: "addReservationFlight", component: ReservationAddFlightComponent },
    { path: "flightMainDetails", component: ReservationFlightMainDetailsComponent },
    { path: "addPayment", component: PaymentAddComponent },
    { path: "cashStats", component: CashStatsComponent }
];

export const AppComponents: any = [
    ClientComponent,
    HotelComponent,
    HotelRoomComponent
];