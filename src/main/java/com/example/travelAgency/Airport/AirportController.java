package com.example.travelAgency.Airport;

import com.example.travelAgency.DataBase.TravelJDBCTemplate;
import oracle.jdbc.OracleDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/airport")
public class AirportController {

    @Autowired
    TravelJDBCTemplate travelJDBCTemplate;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<Airport> getAirports(){
        System.out.println("Downloading airports from dataBase...");
        return travelJDBCTemplate.getAirports();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Airport> addAirport(@RequestBody Airport airport){
        System.out.println("Adding client to dataBase...");
        travelJDBCTemplate.addAirport(airport);
        return new ResponseEntity<Airport>(airport, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Airport> deleteAirport(@RequestBody Airport airport){
        System.out.println("Deleteing airport from dataBase...");
        try{
            travelJDBCTemplate.deleteAirport(airport);
        }catch(OracleDatabaseException ex){
            ex.printStackTrace();
            return new ResponseEntity<Airport>(airport, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Airport>(airport, HttpStatus.OK);
    }

}
