package com.example.travelAgency.Trip;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TripReservationMapper implements RowMapper<Trip> {

public Trip mapRow(ResultSet rs, int rowNum) throws SQLException {
        Trip trip = new Trip();
        trip.setId(rs.getInt("id_wyc"));
        trip.setDestination(rs.getString("cel"));
        trip.setPeopleNumber(rs.getInt("ilosc_osob"));
        trip.setPrice(rs.getFloat("cena"));
        trip.setCountedPrice(rs.getFloat("cena_suma"));
        return trip;
        }
}
