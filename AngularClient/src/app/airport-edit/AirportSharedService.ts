import {Injectable} from "@angular/core"; 
import { Airport } from '../airport/AirportDO'
@Injectable()
export class AirportSharedService{
    airport?: Airport;
}