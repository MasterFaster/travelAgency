import { Airport } from '../airport/AirportDO'
import { Flight } from '../flight/FlightDO'
export class FlightMain{
    id?: number;
    reservationId?: number;
    departureDate?: Date;
    arrivalDate?: Date;
    departureAirportId?: number;
    arrivalAirportId?: number;
    departureAirport?: Airport;
    arrivalAirport?: Airport;
    peopleNumber?: number;
    price?: number;
    flights?: Array<Flight>;
}