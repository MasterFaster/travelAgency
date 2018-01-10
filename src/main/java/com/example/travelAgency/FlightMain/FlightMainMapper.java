package com.example.travelAgency.FlightMain;

import com.example.travelAgency.Airport.Airport;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class FlightMainMapper implements RowMapper<FlightMain> {

    public FlightMain mapRow(ResultSet rs, int rowNum) throws SQLException {
        FlightMain flightMain = new FlightMain();
        flightMain.setId(rs.getInt("id_przelotu"));
        flightMain.setReservationId(rs.getInt("id_rez"));
        flightMain.setDepartureDate(rs.getTimestamp("data_wylotu"));
        flightMain.setArrivalDate(rs.getTimestamp("data_przylotu"));
        flightMain.setDepartureAirportId(rs.getInt("id_lotniska_start"));
        flightMain.setArrivalAirportId(rs.getInt("id_lotniska_koniec"));
        flightMain.setPrice(rs.getFloat("cena"));
        flightMain.setPeopleNumber(rs.getInt("ilosc_osob"));

        Airport departureAirport = new Airport();
        departureAirport.setId(rs.getInt("lotnOdl_id_lotniska"));
        departureAirport.setName(rs.getString("lotnOdl_nazwa"));
        departureAirport.setSymbol(rs.getString("lotnOdl_symbol"));
        departureAirport.setCountry(rs.getString("lotnOdl_kraj"));
        departureAirport.setCity(rs.getString("lotnOdl_miasto"));
        departureAirport.setStreet(rs.getString("lotnOdl_ulica"));
        departureAirport.setHouseNumber(rs.getString("lotnOdl_numer_domu"));
        flightMain.setDepartureAirport(departureAirport);

        Airport arrivalAirport = new Airport();
        arrivalAirport.setId(rs.getInt("lotnPrz_id_lotniska"));
        arrivalAirport.setName(rs.getString("lotnPrz_nazwa"));
        arrivalAirport.setSymbol(rs.getString("lotnPrz_symbol"));
        arrivalAirport.setCountry(rs.getString("lotnPrz_kraj"));
        arrivalAirport.setCity(rs.getString("lotnPrz_miasto"));
        arrivalAirport.setStreet(rs.getString("lotnPrz_ulica"));
        arrivalAirport.setHouseNumber(rs.getString("lotnPrz_numer_domu"));
        flightMain.setArrivalAirport(arrivalAirport);
        return flightMain;
    }
}
