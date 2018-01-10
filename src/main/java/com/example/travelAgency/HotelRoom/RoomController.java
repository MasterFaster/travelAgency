package com.example.travelAgency.HotelRoom;

import com.example.travelAgency.DataBase.TravelJDBCTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    TravelJDBCTemplate travelJDBCTemplate;

    /**
     * returns list of rooms, where id_hotelu = id
     * @param id
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<Room> getRooms(@RequestParam(  "id") int id){
        System.out.println("Downloading rooms list...");
        return travelJDBCTemplate.getRooms(id);
    }

    /**
     * return list of rooms which are available between departure and return dates and where id_hotelu = id
     * @param id
     * @param departureDateString
     * @param returnDateString
     * @return
     */
    @RequestMapping(value = "/getAvailable", method = RequestMethod.GET)
    public List<Room> getAvailableRooms(@RequestParam(  "id") int id,@RequestParam(  "departureDate") String departureDateString,
                                        @RequestParam(  "returnDate") String returnDateString){
        System.out.println("Downloading available rooms list...");
        try{
            return travelJDBCTemplate.getAvailableRooms(id, departureDateString, returnDateString);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ArrayList<Room>();
    }

    /**
     * return list of rooms for reservations with id
     * @param id reservation id
     * @return
     */
    @RequestMapping(value = "/getReservation", method = RequestMethod.GET)
    public List<Room> getReservationRooms(@RequestParam("id") int id){
        System.out.println("Downloading rooms from reservation...");
        return travelJDBCTemplate.getReservationRooms(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Room> addHotel(@RequestBody Room room){
        System.out.println("Adding room to dataBase...");
        travelJDBCTemplate.addRoom(room);
        return new ResponseEntity<Room>(room, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Room> deleteClient(@RequestBody Room room){
        System.out.println("Deleting room from dataBase");
        System.out.println(room.getId());
        travelJDBCTemplate.deleteRoom(room);
        return new ResponseEntity<Room>(room, HttpStatus.OK);
    }

}
