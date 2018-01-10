import {Injectable, EventEmitter} from "@angular/core";    

@Injectable()
export class HotelRoomSharedService {
    changedEmitter = new EventEmitter<any>()
    id? : number;
    name: string;
}