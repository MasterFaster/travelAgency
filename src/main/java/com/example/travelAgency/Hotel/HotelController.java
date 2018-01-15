package com.example.travelAgency.Hotel;

import com.example.travelAgency.DataBase.TravelJDBCTemplate;
import oracle.jdbc.OracleDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    TravelJDBCTemplate travelJDBCTemplate;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<Hotel> getHotels(){
        System.out.println("Downloading hotels list...");
        return travelJDBCTemplate.getHotels();
    }

    @RequestMapping(value = "/getSingle", method = RequestMethod.GET)
    public Hotel getSingleHotel(@RequestParam(  "id") int id){
        System.out.println("Downloading single hotel from dataBase...");
        return travelJDBCTemplate.getSingleHotel(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel){
        System.out.println("Adding hotel to dataBase...");
        travelJDBCTemplate.addHotel(hotel);
        //getHotels();
        return new ResponseEntity<Hotel>(hotel, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Hotel> deleteHotel(@RequestBody Hotel hotel){
        System.out.println("Deleting hotel from dataBase");
        try {
            travelJDBCTemplate.deleteHotel(hotel);
        }catch(OracleDatabaseException ex){
            ex.printStackTrace();
            return new ResponseEntity<Hotel>(hotel, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Hotel>(hotel, HttpStatus.OK);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Hotel> editHotel(@RequestBody Hotel hotel){
        System.out.println("Updating hotel details");
        travelJDBCTemplate.updateHotel(hotel);
        return new ResponseEntity<Hotel>(hotel, HttpStatus.OK);
    }

}
