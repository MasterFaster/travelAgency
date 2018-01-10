package com.example.travelAgency.Flight;

import com.example.travelAgency.DataBase.TravelJDBCTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flight")
public class FlightController {

    @Autowired
    TravelJDBCTemplate travelJDBCTemplate;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<Flight> getFlights(){
        System.out.println("Downloading flights list...");
        return travelJDBCTemplate.getFlights();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight){
        System.out.println("Adding flight to dataBase...");
        System.out.println(flight.getDepartureDate());
        System.out.println(flight.getArrivalDate());
        travelJDBCTemplate.addFlight(flight);
        return new ResponseEntity<Flight>(flight, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Flight> deleteClient(@RequestBody Flight flight){
        System.out.println("Deleting flight from dataBase");
        travelJDBCTemplate.deleteFlight(flight);
        return new ResponseEntity<Flight>(flight, HttpStatus.OK);
    }
}
