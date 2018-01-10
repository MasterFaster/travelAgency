import { Hotel } from '../hotel/HotelDO'
export class Trip{
    id?: number;
    destination?: string;
    price?: number; //for one person
    peopleNumber?: number;
    hotels?: Array<Hotel>;

    countedPrice?: number;  //for many people
}