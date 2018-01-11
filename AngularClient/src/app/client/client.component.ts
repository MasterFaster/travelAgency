import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import {Client} from './ClientDO'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { ClientSharedService } from '../client-edit/ClientSharedService'
import { Router } from '@angular/router'
@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {

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
      console.log(res)
    }).catch(error =>{
        console.log(error);
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
