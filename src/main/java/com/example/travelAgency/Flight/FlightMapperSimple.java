package com.example.travelAgency.Flight;

import com.example.travelAgency.Airport.Airport;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class FlightMapperSimple  implements RowMapper<Flight> {

    public Flight mapRow(ResultSet rs, int rowNum) throws SQLException {
        Flight flight = new Flight();
        flight.setId(rs.getInt("id_lotu"));
        flight.setPrice(rs.getFloat("cena"));
        //flight.setDepartureDate(new Date(rs.getDate("data_wylotu").getTime() - (3600*1000)));
        //flight.setArrivalDate(new Date(rs.getDate("data_przylotu").getTime() - (3600*1000)));
        flight.setDepartureDate(new Date(rs.getTimestamp("data_wylotu").getTime()));
        flight.setArrivalDate(rs.getTimestamp("data_przylotu"));
        flight.setDepartureAirportId(rs.getInt("id_lotniska_odlot"));
        flight.setArrivalAirportId(rs.getInt("id_lotniska_przylot"));
        flight.setPeopleNumber(rs.getInt("ilosc_osob"));
        return flight;
    }
}
