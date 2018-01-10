package com.example.travelAgency.Trip;

import com.example.travelAgency.DataBase.TravelJDBCTemplate;
import com.example.travelAgency.Hotel.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trip")
public class TripController {

    @Autowired
    TravelJDBCTemplate travelJDBCTemplate;

    /**
     *
     * @return all possible trips
     */
    @RequestMapping(value = "/getAvailable", method = RequestMethod.GET)
    public List<Trip> getAvailableTrips(){
        System.out.println("Downloading available trips from dataBase...");
        return travelJDBCTemplate.getAvailableTrips();
    }

    /**
     *
     * @return list of hotels with trip(id) available
     */
    @RequestMapping(value = "/getHotels", method = RequestMethod.GET)
    public List<Hotel> getTripHotels(@RequestParam ("id") int id){
        System.out.println("Downloading hotels for trip with id = " + Integer.toString(id) + " from dataBase...");
        return travelJDBCTemplate.getTripHotels(id);
    }

    @RequestMapping(value = "/deleteAvailable", method = RequestMethod.POST)
    public ResponseEntity<Trip> deleteAvailableTrip(@RequestBody Trip trip){
        System.out.println("Deleting available trip from dataBase...");
        travelJDBCTemplate.deleteAvailableTrip(trip);
        return new ResponseEntity<Trip>(trip, HttpStatus.OK);
    }


    @RequestMapping(value = "/addAvailable", method = RequestMethod.POST)
    public ResponseEntity<Trip> addAvailableTrip(@RequestBody Trip trip){
        System.out.println("Adding available trip to dataBase...");
        travelJDBCTemplate.addAvailableTrip(trip);
        return new ResponseEntity<Trip>(trip, HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @return trips for specific hotel with id_hotelu = id
     */
    @RequestMapping(value = "/getHotelAvailable", method = RequestMethod.GET)
    public List<Trip> getHotelAvailableTrips(@RequestParam ("id") int id){
        System.out.println("Downloading trips available for hotel with id = " + Integer.toString(id) + " from dataBase...");
        return travelJDBCTemplate.getHotelTrips(id);
    }


    /**
     *
     * @param id
     * @return trips for specific reservation id_rezerwacji = id
     */
    @RequestMapping(value = "/getReservation", method = RequestMethod.GET)
    public List<Trip> getReservationTrips(@RequestParam ("id") int id){
        System.out.println("Downloading trips contained in reservation with id = " + Integer.toString(id) + " from dataBase...");
        return travelJDBCTemplate.getReservationTrips(id);
    }
}
