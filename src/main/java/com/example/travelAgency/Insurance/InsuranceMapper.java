package com.example.travelAgency.Insurance;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InsuranceMapper implements RowMapper<Insurance> {
    public Insurance mapRow(ResultSet rs, int rowNum) throws SQLException {
        Insurance insurance = new Insurance();
        insurance.setId(rs.getInt("id_ub"));
        insurance.setDepartureDate(rs.getDate("od"));
        insurance.setReturnDate(rs.getDate("do"));
        insurance.setPrice(rs.getFloat("cena"));
        insurance.setPeopleNumber(rs.getInt("ilosc_osob"));
        insurance.setType(rs.getString("typ"));
        return insurance;
    }
}
