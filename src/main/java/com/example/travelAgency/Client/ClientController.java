package com.example.travelAgency.Client;

import com.example.travelAgency.DataBase.TravelJDBCTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    TravelJDBCTemplate travelJDBCTemplate;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<Client> getClients(){
        System.out.println("Downloading clients from dataBase...");
        return travelJDBCTemplate.getClients();
    }

    @RequestMapping(value = "/getSingle", method = RequestMethod.GET)
    public Client getSignleClient(@RequestParam(  "id") int id){
        System.out.println("Downloading single client from dataBase...");
        return travelJDBCTemplate.getSingleClient(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Client> addClient(@RequestBody Client client){
        System.out.println("Adding client to dataBase...");
        travelJDBCTemplate.addClient(client);
        //getClients();
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Client> editClient(@RequestBody Client client){
        System.out.println("Updating client personal details");
        travelJDBCTemplate.updateClient(client);
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Client> deleteClient(@RequestBody Client client){
        System.out.println("Deleting client from dataBase");
        travelJDBCTemplate.deleteClient(client);
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }


    @RequestMapping(value = "/getLoan", method = RequestMethod.GET)
    public double getLoan(@RequestParam("id") int id){
        System.out.println("Downloading loan for client");
        return travelJDBCTemplate.getClientLoan(id);
    }

}
