import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators , FormControl} from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Router } from '@angular/router'
import { Payment } from './Payment'
import { ReservationDetailsSharedService } from '../reservation-details/reservationDetailsSharedService'
@Component({
  selector: 'app-payment-add',
  templateUrl: './payment-add.component.html',
  styleUrls: ['./payment-add.component.css']
})
export class PaymentAddComponent implements OnInit {

  complexForm: FormGroup;
  @ViewChild('loader') loader: ElementRef;

  constructor(private http: HttpClient, private router: Router, fb: FormBuilder,
    private reservationDetailsSS: ReservationDetailsSharedService) {
    this.complexForm = fb.group({
      'paymentTypeControl' : new FormControl('', Validators.required),
      'amountControl' : new FormControl('', [Validators.required, Validators.pattern("[0-9]+([.][0-9]?[0-9]?)?")]),
      'dateControl' : new FormControl('', Validators.required)
    });
   }

  ngOnInit() {
    this.stopLoader();
  }

  addPayment(paymentType, paymentDate, amount): void{
    this.startLoader();
    let payment: Payment = {};
    payment.paymentType = paymentType;
    payment.date = paymentDate;
    amount = amount.replace(",",".");
    payment.amount = amount;
    payment.reservationId = this.reservationDetailsSS.reservation.id;
    this.http.post('/payment/add', payment).toPromise().then( res =>{
        this.stopLoader();
        this.router.navigate(['/reservationDetailsManagement']);
    })
  }

  stopLoader():void{
    this.loader.nativeElement.style.display="none";
  }

  startLoader():void{
    this.loader.nativeElement.style.display="block";
  }
}
