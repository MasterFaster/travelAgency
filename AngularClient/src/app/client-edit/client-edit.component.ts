import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators , FormControl} from '@angular/forms';
import { Client } from '../client/ClientDO'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Router } from '@angular/router'
import { ClientSharedService } from './ClientSharedService'
import { Ng2MessagePopupComponent, Ng2PopupComponent} from 'ng2-popup'
@Component({
  selector: 'app-client-edit',
  templateUrl: './client-edit.component.html',
  styleUrls: ['./client-edit.component.css']
})
export class ClientEditComponent  implements OnInit {
  
    complexForm: FormGroup;
    @ViewChild('loader') loader: ElementRef;
    @ViewChild(Ng2PopupComponent) popup: Ng2PopupComponent;

    constructor(private http: HttpClient, private router: Router, fb:FormBuilder,
    private clientSS: ClientSharedService) {
      this.complexForm = fb.group({
        'firstNameControl' : new FormControl('', Validators.required),
        'secondNameControl' : new FormControl('', Validators.required),
        'peselControl' : new FormControl('', [Validators.required, Validators.pattern("[0-9]+")]),
        'phoneNrControl' : new FormControl('', Validators.pattern('[0-9]+'))
      });
      
      
    }

    editClient(firstName, secondName, pesel, phoneNr, email, country, city, street, houseNr): void{
        this.startLoader();
        let client = new Client;
        client.firstName = firstName;
        client.secondName = secondName;
        client.pesel = pesel;
        client.phoneNumber = phoneNr;
        client.email = email;
        client.country = country;
        client.city = city;
        client.street = street;
        client.houseNumber = houseNr;
        client.id = this.clientSS.client.id;
        this.http.post('/client/edit', client).toPromise().then(res =>{
          this.stopLoader();
          console.log(res)
          this.router.navigate(['/clientManagement']);
        }).catch(error =>{
          this.stopLoader();
          this.popup.open(Ng2MessagePopupComponent, {
            title: 'Operation denied',
            message: 'Something went wrong. Probably input values are out of range'
          });
        });
    }
    test(): void{
      console.log(this.complexForm.status)
    }
    
      ngOnInit() {
        this.stopLoader();
      }
    
      stopLoader():void{
        this.loader.nativeElement.style.display="none";
      }
    
      startLoader():void{
        this.loader.nativeElement.style.display="block";
      }
  }
  