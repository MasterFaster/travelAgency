import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import {Client} from './ClientDO'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { ClientSharedService } from '../client-edit/ClientSharedService'
import { Router } from '@angular/router'
import { Ng2MessagePopupComponent, Ng2PopupComponent} from 'ng2-popup'
@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {

  @ViewChild(Ng2PopupComponent) popup: Ng2PopupComponent;
  @ViewChild('loader') loader: ElementRef;
  clients: Client;

  constructor(private http: HttpClient,
              private clientSS: ClientSharedService,
              private router: Router) { }

  ngOnInit() {
    this.getClients();
  }

  getClients():void{
    this.startLoader();
    this.http.get<Client>('/client/get').subscribe(clientsGet => {
      this.clients = clientsGet;
      this.stopLoader();
    });
  }

  

  deleteClient(client): void{
    this.startLoader();
    this.http.post('/client/delete',client).toPromise().then(res =>{
      this.getClients();
    }).catch(error =>{
      this.stopLoader();
      this.popup.open(Ng2MessagePopupComponent, {
        title: 'Operation denied',
        message: `You want to remove object that is connected to other objects.
        You should remove all reservations connected to this client` 
      });
    });
  }

  editClient(client): void{
    this.clientSS.client = client;
    this.router.navigate(['/clientEdit']);
  }

  stopLoader():void{
    this.loader.nativeElement.style.display="none";
  }

  startLoader():void{
    this.loader.nativeElement.style.display="block";
  }

}
