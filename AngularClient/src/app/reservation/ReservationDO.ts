import { Client } from '../client/ClientDO'
import { Hotel } from '../hotel/HotelDO'
import { HotelRoom } from '../hotel-room/HotelRoomDO'
import { Insurance } from '../insurance/InsuranceDO'
import { Trip } from '../trip/TripDO'
export class Reservation{
    id?: number;
    departureDate?: Date;
    returnDate?: Date;
    hotelId?: number;
    clientId?: number;
    price?: number;
    rooms?: Array<number>[]

    client?: Client;
    hotel?: Hotel;
    
    //when adding reservation with insurances
    insurances?: Array<Insurance>;
    trips?: Array<Trip>;
}