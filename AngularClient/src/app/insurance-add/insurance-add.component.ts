import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Insurance } from '../insurance/InsuranceDO'
import { FormGroup, FormBuilder, Validators , FormControl} from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Router } from '@angular/router'
@Component({
  selector: 'app-insurance-add',
  templateUrl: './insurance-add.component.html',
  styleUrls: ['./insurance-add.component.css']
})
export class InsuranceAddComponent implements OnInit {

  complexForm: FormGroup;
  @ViewChild('loader') loader: ElementRef;
  
  constructor(private http: HttpClient, private router: Router, fb: FormBuilder) {
    this.complexForm = fb.group({
      'typeControl' : new FormControl('', Validators.required),
      'priceControl' : new FormControl('', [Validators.required, Validators.pattern("[0-9]+([.][0-9]?[0-9]?)?")])
    });
   }

  ngOnInit() {
    this.stopLoader();
  }

  addInsurance(type, price):void{
    this.startLoader();
    let insurance: Insurance = {};
    insurance.type = type;
    insurance.price = price;
    this.http.post('/insurance/addAvailable', insurance).toPromise().then(res =>{
      this.stopLoader();
      this.router.navigate(['/insuranceManagement']);
      console.log(res)
    }).catch(error =>{
        console.log(error);
    });
    //this.getHotels();
  }

  stopLoader():void{
    this.loader.nativeElement.style.display="none";
  }

  startLoader():void{
    this.loader.nativeElement.style.display="block";
  }

}
