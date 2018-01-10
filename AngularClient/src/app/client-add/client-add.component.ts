import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators , FormControl} from '@angular/forms';
import { Client } from '../client/ClientDO'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Router } from '@angular/router'
@Component({
  selector: 'app-client-add',
  templateUrl: './client-add.component.html',
  styleUrls: ['./client-add.component.css']
})
export class ClientAddComponent implements OnInit {

  complexForm: FormGroup;
  @ViewChild('loader') loader: ElementRef;

  constructor(private http: HttpClient, private router: Router, fb:FormBuilder) {
    this.complexForm = fb.group({
      'firstNameControl' : new FormControl('', Validators.required),
      'secondNameControl' : new FormControl('', Validators.required),
      'peselControl' : new FormControl('', [Validators.required, Validators.pattern("[0-9]+")]),
      'phoneNrControl' : new FormControl('', Validators.pattern('[0-9]+'))
    });

  }
  
    ngOnInit() {
      this.stopLoader();
    }
  
    addClient(firstName, secondName, pesel, phoneNr, email, country, city, street, houseNr):void{
      this.startLoader();
      let client : Client = {};  //oneClient is created to set values and insert into dataBase
      client.firstName = firstName;
      client.secondName = secondName;
      client.pesel = pesel;
      client.phoneNumber = phoneNr;
      client.email = email;
      client.country = country;
      client.city = city;
      client.street = street;
      client.houseNumber = houseNr;
      this.http.post('/client/add', client).toPromise().then(res =>{
        this.stopLoader();
        this.router.navigate(['/clientManagement']);
        console.log(res)
      }).catch(error =>{
          console.log(error);
      });
      //this.getClients();
    }
  
    stopLoader():void{
      this.loader.nativeElement.style.display="none";
    }
  
    startLoader():void{
      this.loader.nativeElement.style.display="block";
    }
}
