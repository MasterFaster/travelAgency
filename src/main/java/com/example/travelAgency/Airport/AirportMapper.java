package com.example.travelAgency.Airport;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AirportMapper implements RowMapper<Airport> {

    public Airport mapRow(ResultSet rs, int rowNum) throws SQLException {
        Airport airport = new Airport();
        airport.setId(rs.getInt("id_lotniska"));
        airport.setName(rs.getString("nazwa"));
        airport.setSymbol(rs.getString("symbol"));
        airport.setCountry(rs.getString("kraj"));
        airport.setCity(rs.getString("miasto"));
        airport.setStreet(rs.getString("ulica"));
        airport.setHouseNumber(rs.getString("numer_domu"));
        return airport;
    }
}
