import {Injectable} from "@angular/core";    
import { Reservation } from '../reservation/ReservationDO'
@Injectable()
export class ReservationDetailsSharedService {
    reservation: Reservation;
}