package com.example.travelAgency.Insurance;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AvailableInsuranceMapper implements RowMapper<AvailableInsurance> {

        public AvailableInsurance mapRow(ResultSet rs, int rowNum) throws SQLException {
            AvailableInsurance insurance = new AvailableInsurance();
            insurance.setId(rs.getInt("id_rodz_ub"));
            insurance.setType(rs.getString("typ"));
            insurance.setPrice(rs.getFloat("cena"));
            return insurance;
        }
}
