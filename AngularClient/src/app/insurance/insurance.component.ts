import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Insurance } from './InsuranceDO'
@Component({
  selector: 'app-insurance',
  templateUrl: './insurance.component.html',
  styleUrls: ['./insurance.component.css']
})
export class InsuranceComponent implements OnInit {

  @ViewChild('loader') loader: ElementRef;
  insurances: Insurance;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.getAvailableInsurances();
  }

  getAvailableInsurances():void{
    this.startLoader();
    this.http.get<Insurance>('/insurance/getAvailable').subscribe(insuranceGet => {
      this.insurances = insuranceGet;
      this.stopLoader();
    });
  } 


  deleteInsurance(insurance):void{
      this.startLoader();
      this.http.post('/insurance/deleteAvailable', insurance).toPromise().then(res =>{
        this.getAvailableInsurances();
        console.log(res)
      }).catch(error =>{
          console.log(error);
      });
  }


  stopLoader():void{
    this.loader.nativeElement.style.display="none";
  }

  startLoader():void{
    this.loader.nativeElement.style.display="block";
  }

}
