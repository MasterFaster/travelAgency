package com.example.travelAgency.Flight;

import com.example.travelAgency.Airport.Airport;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class FlightMapper implements RowMapper<Flight> {

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

        Airport departureAirport = new Airport();
        departureAirport.setId(rs.getInt("lotnOdl_id_lotniska"));
        departureAirport.setName(rs.getString("lotnOdl_nazwa"));
        departureAirport.setSymbol(rs.getString("lotnOdl_symbol"));
        departureAirport.setCountry(rs.getString("lotnOdl_kraj"));
        departureAirport.setCity(rs.getString("lotnOdl_miasto"));
        departureAirport.setStreet(rs.getString("lotnOdl_ulica"));
        departureAirport.setHouseNumber(rs.getString("lotnOdl_numer_domu"));
        flight.setDepartureAirport(departureAirport);

        Airport arrivalAirport = new Airport();
        arrivalAirport.setId(rs.getInt("lotnPrz_id_lotniska"));
        arrivalAirport.setName(rs.getString("lotnPrz_nazwa"));
        arrivalAirport.setSymbol(rs.getString("lotnPrz_symbol"));
        arrivalAirport.setCountry(rs.getString("lotnPrz_kraj"));
        arrivalAirport.setCity(rs.getString("lotnPrz_miasto"));
        arrivalAirport.setStreet(rs.getString("lotnPrz_ulica"));
        arrivalAirport.setHouseNumber(rs.getString("lotnPrz_numer_domu"));
        flight.setArrivalAirport(arrivalAirport);
        return flight;
    }
}
