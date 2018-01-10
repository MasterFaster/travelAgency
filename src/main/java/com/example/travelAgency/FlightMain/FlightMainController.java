package com.example.travelAgency.FlightMain;

import com.example.travelAgency.DataBase.TravelJDBCTemplate;
import com.example.travelAgency.Flight.Flight;
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
@RequestMapping("/flightMain")
public class FlightMainController {

    @Autowired
    TravelJDBCTemplate travelJDBCTemplate;

    @RequestMapping(value = "/getAvailable", method = RequestMethod.GET)
    public FlightMain getAvailableFlightMains(@RequestParam(  "departureDate") String departureDateString,
                                                    @RequestParam(  "peopleNumber") int peopleNumber,
                                                    @RequestParam(  "departureAirportId") int departureAirportId,
                                                    @RequestParam(  "arrivalAirportId") int arrivalAirportId){
        System.out.println("Downloading available FlightMain list...");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date departureDate = new Date();
        try {
            departureDate = formatter.parse(departureDateString);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        FlightMain flightMain = travelJDBCTemplate.getAvailableFlightMain(departureDate, peopleNumber, departureAirportId, arrivalAirportId);
        flightMain.setDepartureAirportId(departureAirportId);
        flightMain.setArrivalAirportId(arrivalAirportId);
        for(Flight flight : flightMain.getFlights()){
            travelJDBCTemplate.getFlightInformation(flight);
            flightMain.setPrice(flightMain.getPrice()+(flight.getPrice()*peopleNumber));
        }
        flightMain.calculateDates();
        flightMain.setPeopleNumber(peopleNumber);
        return flightMain;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<FlightMain> addFlightMain(@RequestBody FlightMain flightMain){
        System.out.println("Adding FlightMain to dataBase...");
        travelJDBCTemplate.addFlightMain(flightMain);
        System.out.println("Updating price of reservation with procedure...");
        travelJDBCTemplate.updateReservationPrice(flightMain.getReservationId());
        return new ResponseEntity<FlightMain>(flightMain, HttpStatus.OK);
    }

    @RequestMapping(value = "/getReservation", method = RequestMethod.GET)
    public List<FlightMain> getReservationFlightMain(@RequestParam("id") int id){
        System.out.println("Downloading FlightMains for reservation");
        return travelJDBCTemplate.getReservationFlightMain(id);
    }

    @RequestMapping(value = "/getFlights", method = RequestMethod.GET)
    public List<Flight> getFlightMainFlights(@RequestParam("id") int id){
        System.out.println("Downloading flights for FlightMain...");
        return travelJDBCTemplate.getFlightMainFlights(id);
    }

    @RequestMapping(value = "/delete", method =  RequestMethod.POST)
    public ResponseEntity<FlightMain> deleteFlightMain(@RequestBody FlightMain flightMain){
        System.out.println("Deleting flightMain from reservation");
        travelJDBCTemplate.deleteFlightMain(flightMain);
        travelJDBCTemplate.updateReservationPrice(flightMain.getReservationId());
        return new ResponseEntity<FlightMain>(flightMain, HttpStatus.OK);
    }
}
