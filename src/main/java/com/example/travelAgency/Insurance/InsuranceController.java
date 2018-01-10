package com.example.travelAgency.Insurance;

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
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/insurance")
public class InsuranceController {

    @Autowired
    TravelJDBCTemplate travelJDBCTemplate;

    /**
     * return insurances for reservation
     * @param id reservation id
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<Insurance> getInsurancesForReservation(@RequestParam("id") int id){
        System.out.println("Downloading insurances for reservation from dataBase...");
        return travelJDBCTemplate.getInsurances(id);
    }

    /**
     * return all available insurances for creating reservation
     * @param
     * @return
     */
    @RequestMapping(value = "/getAvailableForReservation", method = RequestMethod.GET)
    public List<Insurance> getAvailableInsurancesForReservation(@RequestParam("departureDate") String departureDateString,
                                                  @RequestParam("returnDate") String returnDateString, @RequestParam("peopleNumber") int peopleNumber){
        System.out.println("Downloading available insurances for reservation from dataBase...");
        List<AvailableInsurance> availableInsurances = travelJDBCTemplate.getAvailableInsurances();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date departureDate = new Date();
        Date returnDate = new Date();
        try{
            departureDate = formatter.parse(departureDateString);
            returnDate = formatter.parse(returnDateString);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        List<Insurance> insurances = new ArrayList<>();
        for(AvailableInsurance availableInsurance : availableInsurances){
            Insurance insurance = new Insurance(availableInsurance.getPrice() *
                    TimeUnit.DAYS.convert(returnDate.getTime()-departureDate.getTime(),TimeUnit.MILLISECONDS) * peopleNumber,
                    departureDate, returnDate, availableInsurance.getType(), peopleNumber);
            insurances.add(insurance);
        }
        return insurances;
    }

    @RequestMapping(value = "/getAvailable", method = RequestMethod.GET)
    public List<AvailableInsurance> getAvailableInsurance(){
        System.out.println("Downloading available insurances from dataBase...");
        return travelJDBCTemplate.getAvailableInsurances();
    }

    @RequestMapping(value = "/deleteAvailable", method = RequestMethod.POST)
    public ResponseEntity<Insurance> deleteAvailableInsurance(@RequestBody Insurance insurance){
        System.out.println("Deleting available insurance from dataBase...");
        travelJDBCTemplate.deleteAvailableInsurance(insurance);
        return new ResponseEntity<Insurance>(insurance, HttpStatus.OK);
    }


    @RequestMapping(value = "/addAvailable", method = RequestMethod.POST)
    public ResponseEntity<Insurance> addAvailableInsurance(@RequestBody Insurance insurance){
        System.out.println("Adding available insurance to dataBase...");
        travelJDBCTemplate.addAvailableInsurance(insurance);
        return new ResponseEntity<Insurance>(insurance, HttpStatus.OK);
    }

}
