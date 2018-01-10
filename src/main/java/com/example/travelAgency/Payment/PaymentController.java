package com.example.travelAgency.Payment;

import com.example.travelAgency.DataBase.TravelJDBCTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    TravelJDBCTemplate travelJDBCTemplate;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Payment> addPayment(@RequestBody Payment payment){
        System.out.println("Adding payment to dataBase");
        System.out.println("Kwota platnosci: " + payment.getAmount());
        travelJDBCTemplate.addPayment(payment);
        return new ResponseEntity<Payment>(payment, HttpStatus.OK);
    }

    @RequestMapping(value = "/getReservation", method = RequestMethod.GET)
    public List<Payment> getPayments(@RequestParam("id") int id){
        System.out.println("Downloading payments for reservation");
        return travelJDBCTemplate.getPayments(id);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<Payment> deletePayment(@RequestBody Payment payment){
        System.out.println("Deleting payment from dataBase");
        travelJDBCTemplate.deletePayment(payment);
        return new ResponseEntity<Payment>(payment,HttpStatus.OK);
    }

    @RequestMapping(value = "/getCash", method = RequestMethod.GET)
    public float getCashFromPayments(){
        System.out.println("Downloading and counting cash from all payments");
        return travelJDBCTemplate.getPaymentsCash();
    }
}
