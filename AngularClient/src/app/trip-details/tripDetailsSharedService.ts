import {Injectable} from "@angular/core";    
import { Trip } from '../trip/TripDO'
@Injectable()
export class TripDetailsSharedService{
    trip?: Trip;
}