package com.example.travelAgency;

import com.example.travelAgency.DataBase.TravelJDBCTemplate;
import com.example.travelAgency.Flight.Flight;
import com.example.travelAgency.FlightMain.FlightMain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TravelAgencyApplicationTests {

	@Autowired
	TravelJDBCTemplate travelJDBCTemplate;

	@Test
	public void contextLoads() throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
		FlightMain flightMain =  travelJDBCTemplate.getAvailableFlightMain(dateFormat.parse("28.12.17"),10,3,5);
		for(Flight flight : flightMain.getFlights()){
			System.out.println(flight.getId());
		}
	}

	@Test
	public void testFunction(){
		System.out.println(travelJDBCTemplate.getClientLoan(21));
	}

}
