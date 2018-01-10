import { Airport } from '../airport/AirportDO'
export class Flight{
    id?: number;
    price?: number;
    departureDate?: Date;
    arrivalDate?: Date;
    departureAirportId?: number;
    arrivalAirportId?: number;
    departureAirport?: Airport;
    arrivalAirport?: Airport;
    peopleNumber?: number;
}