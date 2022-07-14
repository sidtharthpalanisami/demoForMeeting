package com.example.demo.controller;

import com.example.demo.Repo.ReservationRepo;
import com.example.demo.exception.BasicException;
import com.example.demo.model.Reservation;
import com.example.demo.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    ReservationRepo reservationRepo;


    @Autowired
    MeetingService meetingService;

    @GetMapping("/getAllReservation")
    public List<Reservation> getAll(){
        return reservationRepo.findAll();
    }

    @GetMapping("/getReservation/{id}")
    public Reservation getOne(@PathVariable String id) throws BasicException {
        return reservationRepo.findById(id).orElseThrow(()->new BasicException("Reservation id is invalid"));
    }

    @PutMapping("/UpdateReservation")
    public Reservation updateOrCreateReservation(@RequestBody Reservation reservation) throws BasicException {
        return meetingService.UpdateReservation(reservation);
    }

    @PostMapping("/createReservation")
    public Reservation createReservation(@RequestBody Reservation reservation) throws BasicException {
       return meetingService.saveReservation(reservation);
    }

    @DeleteMapping("/deleteReservation/{id}")
    public String deleteReservation(@PathVariable String id){
        if(reservationRepo.existsById(id)) {
            reservationRepo.deleteById(id);
            return "Reservation deleted";
        }
        else
            return "Reservation is not available";
    }

}
