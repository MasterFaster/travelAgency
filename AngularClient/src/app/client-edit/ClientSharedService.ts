import {Injectable} from "@angular/core";   
import { Client } from '../client/ClientDO' 
@Injectable()
export class ClientSharedService{
    client?: Client;
}