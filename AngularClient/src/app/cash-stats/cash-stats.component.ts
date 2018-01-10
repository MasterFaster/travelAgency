import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ClientStats } from  './ClientStatsDO'
import { Client } from '../client/ClientDO'
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
@Component({
  selector: 'app-cash-stats',
  templateUrl: './cash-stats.component.html',
  styleUrls: ['./cash-stats.component.css']
})
export class CashStatsComponent implements OnInit {

  @ViewChild('loader') loader: ElementRef;
  clients: Client;
  clientsStats: Array<ClientStats> = [];
  paymentsCash: number = 0;
  reservationsPrice: number = 0;
  liczba: number = 10;
  loaderCounter: number; 
  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.loaderCounter = 0;
    this.getClients();
    this.getPayments();
    this.getDebtSum();
  }

  getClients():void{
    this.startLoader();
    this.http.get<Client>('/client/get').subscribe(clientsGet => {
      this.clients = clientsGet;
      //this.clientsStats = this.calculateLoan(clientsGet.);
      this.stopLoader();
      Object.keys(this.clients).forEach(key =>{
        this.calculateLoan(this.clients[key]);
        console.log(this.clients[key]);
      })
    });
  }

  calculateLoan(client): void{
        this.startLoader();
        let clientStat: ClientStats = {};
        clientStat.client = client;
        let params = new HttpParams().set('id', client.id.toString());
        this.http.get<number>('/client/getLoan',{params: params}).subscribe(loanGet =>{
          this.stopLoader();
          if(loanGet == null){
            loanGet = 0;
          }
          clientStat.debt = loanGet;
          console.log(loanGet);
        })
        this.clientsStats.push(clientStat);
  }

  getPayments(): void{
    this.startLoader();
    this.http.get<number>('/payment/getCash').subscribe(cashGet => {
      this.paymentsCash = cashGet;
      this.stopLoader();
    })
  }

  getDebtSum(): void{
    this.startLoader();
    this.http.get<number>('/reservation/getPrices').subscribe(reservationsPriceGet => {
      this.reservationsPrice = reservationsPriceGet;
      this.stopLoader();
    })
  }

  stopLoader():void{
    this.loaderCounter -= 1;
    if(this.loaderCounter == 0){
      this.loader.nativeElement.style.display="none";
    }
  }

  startLoader():void{
    this.loaderCounter +=1 ;
    this.loader.nativeElement.style.display="block";
  }

}
