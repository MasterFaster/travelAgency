package com.example.travelAgency.Reservation;

import com.example.travelAgency.DataBase.TravelJDBCTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {


    @Autowired
    TravelJDBCTemplate travelJDBCTemplate;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<Reservation> getReservations(){
        System.out.println("Downloading reservations from dataBase...");
        return travelJDBCTemplate.getReservations();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation){
        System.out.println("Adding reservation to dataBase...");
        travelJDBCTemplate.addReservation(reservation);
        System.out.println("Updating price of reservation with procedure...");
        travelJDBCTemplate.updateReservationPrice(reservation.getId());
        return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Reservation> deleteReservation(@RequestBody Reservation reservation){
        System.out.println("Deleting reservation from dataBase");
        travelJDBCTemplate.deleteReservation(reservation);
        return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
    }

    @RequestMapping(value = "/getSingle", method = RequestMethod.GET)
    public Reservation getSingleReservation(@RequestParam("id") int id){
        System.out.println("Downloading single reservation");
        return travelJDBCTemplate.getSingleReservation(id);
    }

    @RequestMapping(value = "getPrices", method = RequestMethod.GET)
    public float getAllPrices(){
        System.out.println("Downloading and counting all reservations prices");
        return travelJDBCTemplate.getAllReservationsPrices();
    }

    @RequestMapping(value = "/getSpecified", method = RequestMethod.GET)
    public List<Reservation> getSpecifiedReservations(@RequestParam("firstName") String firstName,
                                                      @RequestParam("secondName") String secondName,
                                                      @RequestParam("hotelName") String hotelName){
        System.out.println("Downloading reservetion with filters");
        return travelJDBCTemplate.getReservationsSpecified(firstName, secondName,hotelName);
    }

}
