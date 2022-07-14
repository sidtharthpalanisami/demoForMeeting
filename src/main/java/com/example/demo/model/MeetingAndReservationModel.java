package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MeetingAndReservationModel {
    private String id;
    private String name;
    private int capacity;
    private String description;
    private String status;
    private List<Reservation> reservationList;
}

