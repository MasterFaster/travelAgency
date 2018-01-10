package com.example.travelAgency.DataBase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


import javax.sql.DataSource;


@Configuration
public class JDBCTemplateConfig {

    private final String driverClassName ="oracle.jdbc.driver.OracleDriver";
    private final String url = "jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl";
    private final String userName= "inf127290";
    private final String password = "roman69";

    @Bean
    public TravelJDBCTemplate travelJDBCTemplate(){
        return new TravelJDBCTemplate(dataSource());
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        //dataSource.setDriverClassName("jdbc:oracle:thin");
        //dataSource.setUrl("admlab2-main.cs.put.poznan.pl:1521/dblab01.cs.put.poznan.pl");
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

}
