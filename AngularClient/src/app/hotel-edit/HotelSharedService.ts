import {Injectable} from "@angular/core";   
import { Hotel } from '../hotel/HotelDO' 
@Injectable()
export class HotelSharedService{
    hotel?: Hotel;
}