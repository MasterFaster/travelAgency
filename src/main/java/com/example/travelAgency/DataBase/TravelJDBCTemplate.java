package com.example.travelAgency.DataBase;

import com.example.travelAgency.Airport.Airport;
import com.example.travelAgency.Airport.AirportMapper;
import com.example.travelAgency.Client.Client;
import com.example.travelAgency.Client.ClientMapper;
import com.example.travelAgency.Flight.Flight;
import com.example.travelAgency.Flight.FlightMapper;
import com.example.travelAgency.Flight.FlightMapperSimple;
import com.example.travelAgency.FlightMain.FlightMain;
import com.example.travelAgency.FlightMain.FlightMainMapper;
import com.example.travelAgency.Hotel.Hotel;
import com.example.travelAgency.Hotel.HotelMapper;
import com.example.travelAgency.HotelRoom.Room;
import com.example.travelAgency.HotelRoom.RoomMapper;
import com.example.travelAgency.Insurance.AvailableInsurance;
import com.example.travelAgency.Insurance.AvailableInsuranceMapper;
import com.example.travelAgency.Insurance.Insurance;
import com.example.travelAgency.Insurance.InsuranceMapper;
import com.example.travelAgency.Payment.Payment;
import com.example.travelAgency.Payment.PaymentMapper;
import com.example.travelAgency.Reservation.Reservation;
import com.example.travelAgency.Reservation.ReservationMapper;
import com.example.travelAgency.Trip.Trip;
import com.example.travelAgency.Trip.TripMapper;
import com.example.travelAgency.Trip.TripReservationMapper;
import oracle.jdbc.OracleDatabaseException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.object.GenericStoredProcedure;
import org.springframework.jdbc.object.StoredProcedure;

import javax.sql.DataSource;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

//@ComponentScan
public class TravelJDBCTemplate {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;


    public TravelJDBCTemplate(){
        System.out.println("empty constructor");
    }

    public TravelJDBCTemplate(DataSource dataSource){
        System.out.println("Constructor with data source");
        this.dataSource = dataSource;//???
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Client> getClients(){
        String sql = "Select * from klienci";
        List<Client> clients = jdbcTemplate.query(sql, new ClientMapper());
        List l =jdbcTemplate.queryForList(sql);
        System.out.println(l.size());
        return clients;
    }

    public Client getSingleClient(int id){
        String sql = "Select * from klienci where id_klienta = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{id}, new ClientMapper());
    }

    public void addClient(Client client){
        String sql = "INSERT INTO KLIENCI " +
                "(IMIE, NAZWISKO, PESEL, TELEFON, EMAIL, KRAJ, MIASTO, ULICA, NUMER_DOMU) VALUES (?, ?, ?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,new Object[]{client.getFirstName(), client.getSecondName(),
        client.getPesel(), client.getPhoneNumber(), client.getEmail(), client.getCountry(), client.getCity(), client.getStreet(),
        client.getHouseNumber()});
    }

    public void deleteClient(Client client) throws OracleDatabaseException{
        String sql = "DELETE FROM KLIENCI WHERE ID_KLIENTA = ?";
        jdbcTemplate.update(sql, new Object[]{client.getId()});
    }

    public void updateClient(Client client){
        String sql = "UPDATE klienci SET imie = ?, nazwisko = ?, pesel = ?, telefon = ?, email = ?, kraj = ?, miasto = ?, " +
                "ulica = ?, numer_domu = ? WHERE id_klienta = ?";
        jdbcTemplate.update(sql, new Object[]{client.getFirstName(),client.getSecondName(), client.getPesel(), client.getPhoneNumber(),
        client.getEmail(), client.getCountry(), client.getCity(), client.getStreet(), client.getHouseNumber(), client.getId()});
    }

    public double getClientLoan(int id){
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource).withFunctionName("kwotaDoZaplaty");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_klienta_input", id);
        Map<String, Object> loan = simpleJdbcCall.execute(in);
        return (double)loan.get("return");
    }

    public List<Hotel> getHotels(){
        String sql = "Select * from hotele";
        List<Hotel> hotels = jdbcTemplate.query(sql, new HotelMapper());
        //List l =jdbcTemplate.queryForList(sql);
        //System.out.println(l.size());
        return hotels;
    }

    public Hotel getSingleHotel(int id){
        String sql = "SELECT * from hotele WHERE id_hotelu = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new HotelMapper());
    }

    public void deleteHotel(Hotel hotel) throws OracleDatabaseException{
        String sqlDelRoom = "DELETE FROM POKOJE WHERE id_hotelu = ?";
        jdbcTemplate.update(sqlDelRoom, new Object[]{hotel.getId()});
        String sqlDelHotel = "DELETE FROM HOTELE WHERE ID_HOTELU = ?";
        jdbcTemplate.update(sqlDelHotel, new Object[]{hotel.getId()});
    }

    public void addHotel(Hotel hotel){
        String sql = "INSERT INTO HOTELE " +
                "(NAZWA, GWIAZDKI, KRAJ, MIASTO, ULICA, NUMER_DOMU) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(sql,new Object[]{hotel.getName(), hotel.getStars(),hotel.getCountry(),hotel.getCity(),
                hotel.getStreet(),hotel.getHouseNumber()});
    }

    public void updateHotel(Hotel hotel){
        String sql = "UPDATE hotele SET nazwa = ?, gwiazdki = ?, kraj = ?, miasto = ?, ulica = ?, numer_domu = ? " +
                "WHERE id_hotelu = ?";
        jdbcTemplate.update(sql, new Object[]{hotel.getName(), hotel.getStars(), hotel.getCountry(), hotel.getCity(),
        hotel.getStreet(), hotel.getHouseNumber(), hotel.getId()});
    }

    public List<Room> getRooms(int id){
        String sql = "Select * from pokoje where id_hotelu = "+Integer.toString(id);
        List<Room> rooms = jdbcTemplate.query(sql, new RoomMapper());
        List l =jdbcTemplate.queryForList(sql);
        return rooms;
    }

    public List<Room> getAvailableRooms(int id, String departureDateString, String returnDateString) throws Exception{
        //List<Room> availableRooms = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date departureDate = new Date();
        Date returnDate = new Date();
        departureDate = formatter.parse(departureDateString);
        returnDate = formatter.parse(returnDateString);
        /*String sql = "SELECT * FROM POKOJE WHERE id_hotelu = " + Integer.toString(id);
        List<Room> roomsInHotel = jdbcTemplate.query(sql, new RoomMapper());
        Iterator<Room> iterator = roomsInHotel.iterator();
        while(iterator.hasNext()){
            Room room = iterator.next();
            sql = "SELECT * FROM rezerwacje left JOIN rezerwacje_pokoje pok USING(id_rez) join hotele USING(id_hotelu) " +
                    "join klienci USING(id_klienta) WHERE pok.id_pokoju = ?";
            List<Reservation> reservations = jdbcTemplate.query(sql,new Object[]{room.getId()}, new ReservationMapper());
            for (Reservation oldReservation : reservations) {
                if (oldReservation.getDepartureDate().before(returnDate) && departureDate.before(oldReservation.getReturnDate())) {
                    iterator.remove();
                    break;
                }
            }
        }
        */
       String sql = "SELECT * FROM pokoje WHERE id_pokoju not IN (SELECT id_pokoju from pokoje " +
               "JOIN rezerwacje_pokoje USING(id_pokoju) JOIN rezerwacje USING(id_rez) " +
               "WHERE pokoje.id_hotelu = ? AND TO_DATE(od,'DD.MM.YY') < ? AND ? <  TO_DATE(do,'DD.MM.YY')) " +
               "AND id_hotelu = ?";
       List<Room> roomsInHotel = jdbcTemplate.query(sql, new Object[]{id, returnDate, departureDate,id}, new RoomMapper());


        return roomsInHotel;
    }

    public List<Room> getReservationRooms(int id){
        String sql = "SELECT * FROM pokoje JOIN rezerwacje_pokoje USING(id_pokoju) WHERE id_rez = ?";
        return jdbcTemplate.query(sql,  new Object[]{id},new RoomMapper());
    }

    public void addRoom(Room room){
        String sql = "INSERT INTO POKOJE " +
                "(NUMER_POKOJU, ILOSC_OSOB, ID_HOTELU, CENA_ZA_DOBE) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql,new Object[]{room.getRoomNumber(), room.getPeopleNumber(), room.getHotelId(), room.getPrice()});
    }

    public void deleteRoom(Room room) throws OracleDatabaseException {
        String sql = "DELETE FROM POKOJE WHERE ID_POKOJU = ?";
        jdbcTemplate.update(sql, new Object[]{room.getId()});
        System.out.println("Room deleting ended");
    }

    public List<Reservation> getReservations(){
        String sql = "Select * from rezerwacje JOIN hotele USING(id_hotelu) JOIN klienci USING(id_klienta)";
        List<Reservation> reservations = jdbcTemplate.query(sql, new ReservationMapper());
        /*for(Reservation reservation : reservations){
            sql = "Select id_pokoju, numer_pokoju, ilosc_osob, id_hotelu, cena_za_dobe" +
                    " from pokoje JOIN rezerwacje_pokoje USING(id_pokoju) WHERE id_rez = " + Integer.toString(reservation.getId());
            List<Room> rooms = jdbcTemplate.query(sql, new RoomMapper());
            //System.out.println(rooms);
            //System.out.println(sql);
            //reservation.setRooms(new ArrayList<Room>(rooms));
        }*/
        return reservations;
    }

    public void addReservation(Reservation reservation){
        int index = jdbcTemplate.queryForObject("select last_number from user_sequences where sequence_name='REZERWACJESEQ'", Integer.class);
        reservation.setId(index);
        String sql = "INSERT INTO rezerwacje " +
                "(od, do, ID_HOTELU, id_klienta) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql,new Object[]{reservation.getDepartureDate(), reservation.getReturnDate(),
                reservation.getHotelId(), reservation.getClientId()});
        sql = "INSERT INTO rezerwacje_pokoje(id_rez,id_pokoju) VALUES(?,?)";
        for(int room : reservation.getRooms()){
            jdbcTemplate.update(sql, new Object[]{index, room});
        }
        sql = "INSERT INTO ubezpieczenia(id_rez, cena, od, do, typ, ilosc_osob) VALUES (?,?,?,?,?,?)";
        for(Insurance insurance : reservation.getInsurances()){
            jdbcTemplate.update(sql, new Object[]{index, insurance.getPrice(), insurance.getDepartureDate(), insurance.getReturnDate(),
            insurance.getType(), insurance.getPeopleNumber()});
        }
        sql = "INSERT INTO wycieczki_rezerwacje(id_rez, id_wyc, ilosc_osob, cena) VALUES (?,?,?,?)";
        for(Trip trip : reservation.getTrips()){
            jdbcTemplate.update(sql, new Object[]{index, trip.getId(), trip.getPeopleNumber(), trip.getCountedPrice()});
        }
    }

    public Reservation getSingleReservation(int id){
        String sql = "Select * from rezerwacje JOIN hotele USING(id_hotelu) JOIN klienci USING(id_klienta) WHERE id_rez = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ReservationMapper());
    }

    public List<Reservation> getReservationsSpecified(String firstName, String secondName, String hotelName){
        if(!firstName.contains("%") && !firstName.contains("_"))
            firstName = firstName + "%";
        if(!secondName.contains("%") && !secondName.contains("_"))
            secondName = secondName + "%";
        if(!hotelName.contains("%") && !hotelName.contains("_"))
            hotelName = hotelName + "%";
        System.out.println("FirstName: *" + firstName + "* secondName: *" + secondName + "* hotelName: *" + hotelName +"*");
        String sql = "Select * from rezerwacje JOIN hotele USING(id_hotelu) JOIN klienci USING(id_klienta) " +
                "WHERE imie like ? AND nazwisko like ? AND nazwa like ?";
        return jdbcTemplate.query(sql, new Object[]{firstName,secondName,hotelName}, new ReservationMapper());
    }

    public void updateReservationPrice(int id){
        System.out.println("Updating in stored procedure = " + id);
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("obliczSumCene");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_rezerwacji", id);
        simpleJdbcCall.execute(in);
    }

    public float getAllReservationsPrices(){
        String sql = "SELECT SUM(cena_suma) from rezerwacje";
        return jdbcTemplate.queryForObject(sql, Float.class);
    }

    /**
     * trigger is created in sql
     * removing reservation cause removing from ubezpieczenia, platnosci, wycieczki_rezerwacje,
     * rezerwacje_pokoje, loty_przeloty, przeloty
     * @param reservation
     */
    public void deleteReservation(Reservation reservation){
        String sql = "DELETE rezerwacje WHERE id_rez = ?";
        jdbcTemplate.update(sql, new Object[]{reservation.getId()});
    }

    /**
     * return list of insurances for reservation
     * @param id reservation id
     * @return
     */
    public List<Insurance> getInsurances(int id){
        String sql = "Select * from ubezpieczenia WHERE id_rez = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new InsuranceMapper());
    }

    public void addInsurance(Insurance insurance){
        String sql = "INSERT INTO ubezpieczenia(id_ub,cena,od,do,id_rez,typ,ilosc_osob) VALUES(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{insurance.getId(), insurance.getPrice(), insurance.getDepartureDate(),
                insurance.getReturnDate(), insurance.getReservationId(), insurance.getType(), insurance.getPeopleNumber()});
    }

    public List<AvailableInsurance> getAvailableInsurances(){
        String sql = "SELECT * FROM rodzaj_ubezpieczenia";
        return jdbcTemplate.query(sql,new AvailableInsuranceMapper());
    }

    public void addAvailableInsurance(Insurance insurance){
        String sql = "INSERT INTO rodzaj_ubezpieczenia(typ, cena) VALUES(?,?)";
        jdbcTemplate.update(sql, new Object[]{insurance.getType(), insurance.getPrice()});
    }


    public void deleteAvailableInsurance(Insurance insurance){
        String sql = "DELETE FROM rodzaj_ubezpieczenia WHERE id_rodz_ub = ?";
        jdbcTemplate.update(sql, new Object[]{insurance.getId()});
    }

    public List<Airport> getAirports(){
        String sql = "SELECT * from lotniska";
        return jdbcTemplate.query(sql, new AirportMapper());
    }

    public void addAirport(Airport airport){
        String sql = "INSERT INTO lotniska(nazwa, symbol, kraj, miasto, ulica, numer_domu) VALUES(?,?,?,?,?,?)";
        jdbcTemplate.update(sql,new Object[]{airport.getName(), airport.getSymbol(), airport.getCountry(), airport.getCity(),
                            airport.getStreet(), airport.getHouseNumber()});
    }

    public void deleteAirport(Airport airport) throws OracleDatabaseException {
        String sql = "DELETE from lotniska WHERE id_lotniska = ?";
        jdbcTemplate.update(sql, new Object[]{airport.getId()});
    }

    public List<Flight> getFlights(){
        /*String sql = "SELECT * FROM loty";
        List<Flight> flights = jdbcTemplate.query(sql,new FlightMapper());
        System.out.println(flights.size());
        for(Flight flight : flights){
            sql = "Select * from lotniska WHERE id_lotniska = ?";
            flight.setDepartureAirport(jdbcTemplate.queryForObject(sql,new Object[]{flight.getDepartureAirportId()}, new AirportMapper()));
            flight.setArrivalAirport(jdbcTemplate.queryForObject(sql,new Object[]{flight.getArrivalAirportId()}, new AirportMapper()));
        }
        return flights;*/
        String sql = " SELECT id_lotu, cena, data_wylotu, data_przylotu,id_lotniska_odlot, id_lotniska_przylot, ilosc_osob, " +
                "lotnOdl.id_lotniska AS lotnOdl_id_lotniska, lotnOdl.nazwa AS lotnOdl_nazwa, lotnOdl.symbol AS lotnOdl_symbol, lotnOdl.kraj AS lotnOdl_kraj, lotnOdl.miasto AS lotnOdl_miasto, lotnOdl.ulica AS lotnOdl_ulica, lotnOdl.numer_domu AS lotnOdl_numer_domu, " +
                "lotnPrz.id_lotniska AS lotnPrz_id_lotniska, lotnPrz.nazwa AS lotnPrz_nazwa, lotnPrz.symbol AS lotnPrz_symbol, lotnPrz.kraj AS lotnPrz_kraj, lotnPrz.miasto AS lotnPrz_miasto, lotnPrz.ulica AS lotnPrz_ulica, lotnPrz.numer_domu AS lotnPrz_numer_domu " +
                "from loty JOIN lotniska lotnOdl ON loty.id_lotniska_odlot = lotnOdl.id_lotniska " +
                "JOIN lotniska lotnPrz ON loty.id_lotniska_przylot = lotnPrz.id_lotniska";
        return jdbcTemplate.query(sql, new FlightMapper());
    }

    public void getFlightInformation(Flight flight){
        String sql = " SELECT id_lotu, cena, data_wylotu, data_przylotu,id_lotniska_odlot, id_lotniska_przylot, ilosc_osob, " +
                "lotnOdl.id_lotniska AS lotnOdl_id_lotniska, lotnOdl.nazwa AS lotnOdl_nazwa, lotnOdl.symbol AS lotnOdl_symbol, lotnOdl.kraj AS lotnOdl_kraj, lotnOdl.miasto AS lotnOdl_miasto, lotnOdl.ulica AS lotnOdl_ulica, lotnOdl.numer_domu AS lotnOdl_numer_domu, " +
                "lotnPrz.id_lotniska AS lotnPrz_id_lotniska, lotnPrz.nazwa AS lotnPrz_nazwa, lotnPrz.symbol AS lotnPrz_symbol, lotnPrz.kraj AS lotnPrz_kraj, lotnPrz.miasto AS lotnPrz_miasto, lotnPrz.ulica AS lotnPrz_ulica, lotnPrz.numer_domu AS lotnPrz_numer_domu " +
                "from loty JOIN lotniska lotnOdl ON loty.id_lotniska_odlot = lotnOdl.id_lotniska " +
                "JOIN lotniska lotnPrz ON loty.id_lotniska_przylot = lotnPrz.id_lotniska WHERE id_lotu = ?";
        Flight newFlight = jdbcTemplate.queryForObject(sql, new Object[]{flight.getId()}, new FlightMapper());
        flight.setDepartureAirport(newFlight.getDepartureAirport());
        flight.setArrivalAirport(newFlight.getArrivalAirport());
    }

    public void addFlight(Flight flight){
        String sql = "INSERT INTO loty(cena, data_wylotu, data_przylotu, id_lotniska_odlot, id_lotniska_przylot, ilosc_osob) " +
                "VALUES(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{flight.getPrice(), flight.getDepartureDate(), flight.getArrivalDate(),
                flight.getDepartureAirportId(), flight.getArrivalAirportId(), flight.getPeopleNumber()});
    }

    public void deleteFlight(Flight flight) throws OracleDatabaseException{
        String sql = "DELETE FROM loty WHERE id_lotu = ?";
        jdbcTemplate.update(sql, new Object[]{flight.getId()});
    }

    //***********************************************************************************************************************************************

    /**
     * String sql = "SELECT * FROM loty lotyGl WHERE TO_DATE(data_przylotu,'DD.MM.YY') = ?  AND lotyGl.ilosc_osob >" +
     "(SELECT  nvl(SUM(NVL(przel.ilosc_osob,0)),0) from przeloty przel JOIN loty_przeloty lot_przel ON " +
     "przel.id_przelotu = lot_przel.id_przelotu WHERE lot_przel.id_lotu = lotyGl.id_lotu)";
     * @param departureDate
     * @param peopleNumber
     * @param departureAirportId
     * @param arrivalAirportId
     * @return
     */
    public FlightMain getAvailableFlightMain(Date departureDate, int peopleNumber, int departureAirportId, int arrivalAirportId){
        System.out.println(departureDate);
        //TO_DATE(?,'DD.MM.YY')
        String sql = "SELECT * FROM loty lotyGl WHERE TO_DATE(data_przylotu,'DD.MM.YY') = ?  AND lotyGl.ilosc_osob -" +
                "(SELECT  nvl(SUM(NVL(przel.ilosc_osob,0)),0) from przeloty przel JOIN loty_przeloty lot_przel ON " +
                "przel.id_przelotu = lot_przel.id_przelotu WHERE lot_przel.id_lotu = lotyGl.id_lotu) >= ?";

        List<Flight> flights = jdbcTemplate.query(sql, new Object[]{departureDate, peopleNumber}, new FlightMapperSimple());
        List<Flight> resultFlights = new ArrayList<>();
        FlightMain flightMain = new FlightMain();

        resultFlights = flights.stream()
                .filter(flight -> flight.getDepartureAirportId() == departureAirportId && flight.getArrivalAirportId() == arrivalAirportId)
                .sorted(Comparator.comparing(Flight::getPrice))
                .limit(1)
                .collect(Collectors.toList());
        if(resultFlights.size() != 0) {
            flightMain.setFlights(resultFlights);
            return flightMain;
        }

        List<Flight> firstFlights = flights.stream()
                .filter(flight -> flight.getDepartureAirportId() == departureAirportId)
                .collect(Collectors.toList());

        List<List<Flight>> firstSecondFlights =  new ArrayList<>();

        for(Flight firstFlight : firstFlights){
            List<Flight> firstSecondFlight = new ArrayList<>();
            firstSecondFlight.add(firstFlight);
            firstSecondFlight.addAll(flights.stream()
                            .filter(flight -> flight.getDepartureAirportId() == firstFlight.getArrivalAirportId() &&
                                    flight.getArrivalAirportId() == arrivalAirportId &&
                                    flight.getDepartureDate().after(firstFlight.getArrivalDate()))
                            .sorted(Comparator.comparing(Flight::getPrice))
                            .limit(1)
                            .collect(Collectors.toList())
                    );
            if(firstSecondFlight.size() == 2) {
                firstSecondFlights.add(firstSecondFlight);
            }
        }
        Collections.sort(firstSecondFlights, new Comparator<List<Flight>>(){
                    public int compare(List<Flight> leftFlights, List<Flight> rightFlights){
                        int sumleft = 0, sumright = 0;
                        for(Flight flight: leftFlights){
                            sumleft+= flight.getPrice();
                        }
                        for(Flight flight: rightFlights){
                            sumright+= flight.getPrice();
                        }
                        if(sumleft < sumright)
                            return -1;
                        else
                            return 1;
                    }
                });
        if(firstSecondFlights.size() > 0)
            flightMain.setFlights(firstSecondFlights.get(0));
        return flightMain;
    }

    public void addFlightMain(FlightMain flightMain){
        int index = jdbcTemplate.queryForObject("select last_number from user_sequences where sequence_name='PRZELOTSEQ'", Integer.class);
        System.out.println("check: " + flightMain.getDepartureAirportId() + " " +flightMain.getArrivalAirportId() + " res: " + flightMain.getReservationId());
        String sql = "INSERT INTO przeloty(id_rez,data_wylotu, data_przylotu, id_lotniska_start, id_lotniska_koniec, cena, ilosc_osob) VALUES(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{flightMain.getReservationId(), flightMain.getDepartureDate(), flightMain.getArrivalDate(),
        flightMain.getDepartureAirportId(), flightMain.getArrivalAirportId(), flightMain.getPrice(), flightMain.getPeopleNumber()});
        sql = "INSERT INTO loty_przeloty(id_lotu,id_przelotu) VALUES(?,?)";
        for(Flight flight : flightMain.getFlights()){
            jdbcTemplate.update(sql, new Object[]{flight.getId(),index});
        }
    }

    public List<FlightMain> getReservationFlightMain(int id){
        String sql = "SELECT id_przelotu, id_rez, data_wylotu, data_przylotu, id_lotniska_start, id_lotniska_koniec, cena, ilosc_osob, " +
                "lotnOdl.id_lotniska AS lotnOdl_id_lotniska, lotnOdl.nazwa AS lotnOdl_nazwa, lotnOdl.symbol AS lotnOdl_symbol, lotnOdl.kraj AS lotnOdl_kraj, lotnOdl.miasto AS lotnOdl_miasto, lotnOdl.ulica AS lotnOdl_ulica, lotnOdl.numer_domu AS lotnOdl_numer_domu, " +
                "lotnPrz.id_lotniska AS lotnPrz_id_lotniska, lotnPrz.nazwa AS lotnPrz_nazwa, lotnPrz.symbol AS lotnPrz_symbol, lotnPrz.kraj AS lotnPrz_kraj, lotnPrz.miasto AS lotnPrz_miasto, lotnPrz.ulica AS lotnPrz_ulica, lotnPrz.numer_domu AS lotnPrz_numer_domu " +
                "FROM przeloty JOIN lotniska lotnOdl ON przeloty.id_lotniska_start = lotnOdl.id_lotniska " +
                "JOIN lotniska lotnPrz ON przeloty.id_lotniska_koniec = lotnPrz.id_lotniska  WHERE id_rez = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new FlightMainMapper());
    }

    public List<Flight> getFlightMainFlights(int id){
        String sql = "SELECT id_lotu, data_wylotu, data_przylotu, id_lotniska_odlot, id_lotniska_przylot, cena, ilosc_osob, " +
                "lotnOdl.id_lotniska AS lotnOdl_id_lotniska, lotnOdl.nazwa AS lotnOdl_nazwa, lotnOdl.symbol AS lotnOdl_symbol, lotnOdl.kraj AS lotnOdl_kraj, lotnOdl.miasto AS lotnOdl_miasto, lotnOdl.ulica AS lotnOdl_ulica, lotnOdl.numer_domu AS lotnOdl_numer_domu, " +
                "lotnPrz.id_lotniska AS lotnPrz_id_lotniska, lotnPrz.nazwa AS lotnPrz_nazwa, lotnPrz.symbol AS lotnPrz_symbol, lotnPrz.kraj AS lotnPrz_kraj, lotnPrz.miasto AS lotnPrz_miasto, lotnPrz.ulica AS lotnPrz_ulica, lotnPrz.numer_domu AS lotnPrz_numer_domu " +
                "FROM loty_przeloty JOIN loty USING (id_lotu) JOIN lotniska lotnOdl ON " +
                "loty.id_lotniska_odlot = lotnOdl.id_lotniska JOIN lotniska lotnPrz ON loty.id_lotniska_przylot = lotnPrz.id_lotniska " +
                "WHERE id_przelotu = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new FlightMapper());
    }

    public void deleteFlightMain(FlightMain flightMain){
        String sql = "DELETE FROM loty_przeloty WHERE id_przelotu = ?";
        jdbcTemplate.update(sql, new Object[]{flightMain.getId()});
        sql = "DELETE FROM przeloty WHERE id_przelotu = ?";
        jdbcTemplate.update(sql, new Object[]{flightMain.getId()});
    }

    public List<Trip> getAvailableTrips(){
        String sql = "SELECT * FROM wycieczki";
        return jdbcTemplate.query(sql, new TripMapper());
    }

    public void deleteAvailableTrip(Trip trip) throws OracleDatabaseException{
        String sql = "DELETE FROM wycieczki_hotele WHERE id_wyc = ?";
        jdbcTemplate.update(sql, new Object[]{trip.getId()});
        sql = "DELETE FROM wycieczki WHERE id_wyc = ?";
        jdbcTemplate.update(sql, new Object[]{trip.getId()});
    }

    public List<Trip> getReservationTrips(int id){
        String sql = "SELECT id_wyc, wyc.cel, wyc_rez.ilosc_osob, wyc.cena, wyc_rez.cena AS cena_suma FROM " +
                "wycieczki_rezerwacje wyc_rez JOIN wycieczki wyc USING (id_wyc) WHERE wyc_rez.id_rez = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new TripReservationMapper());
    }

    public void addAvailableTrip(Trip trip){
        int index = jdbcTemplate.queryForObject("select last_number from user_sequences where sequence_name='WYCIECZKISEQ'", Integer.class);
        String sql = "INSERT INTO wycieczki(cel, cena) VALUES(?,?)";
        jdbcTemplate.update(sql,new Object[]{trip.getDestination(), trip.getPrice()});
        sql = "INSERT INTO wycieczki_hotele(id_wyc, id_hotelu) VALUES(?,?)";
        for(Hotel hotel : trip.getHotels()){
            jdbcTemplate.update(sql, new Object[]{index, hotel.getId()});
        }
    }

    public List<Hotel> getTripHotels(int id){
        String sql = "SELECT id_hotelu, nazwa, gwiazdki, kraj, miasto, ulica, numer_domu FROM HOTELE JOIN wycieczki_hotele USING (id_hotelu) WHERE id_wyc = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new HotelMapper());
    }

    /**
     * download available trips for hotel with id
     * @param id
     * @return
     */
    public List<Trip> getHotelTrips(int id){
        String sql = "SELECT * FROM wycieczki JOIN wycieczki_hotele USING (id_wyc) WHERE id_hotelu = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new TripMapper());
    }

    public void addPayment(Payment payment){
        String sql = "INSERT INTO platnosci(id_rez, sposob_zaplaty, data, kwota) VALUES(?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{payment.getReservationId(), payment.getPaymentType(),
                payment.getDate(), payment.getAmount()});
    }

    public List<Payment> getPayments(int id){
        String sql = "SELECT * FROM platnosci WHERE id_rez = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new PaymentMapper());
    }

    public void deletePayment(Payment payment){
        String sql = "DELETE FROM platnosci WHERE id_zaplaty = ?";
        jdbcTemplate.update(sql, new Object[]{payment.getId()});
    }

    public float getPaymentsCash(){
        String sql = "SELECT SUM(kwota) from platnosci";
        return jdbcTemplate.queryForObject(sql, Float.class);
    }
}
//2017-11-14T13:02
//INSERT INTO loty(cena, data_wylotu, data_przylotu, id_lotniska_odlot, id_lotniska_przylot) VALUES(380, TO_DATE('2018/01/01 12:35:00', 'yyyy/mm/dd hh24:mi:ss'),
 //       TO_DATE('2018/01/01 12:56:00', 'yyyy/mm/dd hh24:mi:ss'), 3,4);
