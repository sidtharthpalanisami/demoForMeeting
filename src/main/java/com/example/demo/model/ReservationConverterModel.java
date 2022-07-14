package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class ReservationConverterModel {
    private String id;
    private String meetingId;
    private LocalDateTime from;
    private LocalDateTime to;
    private String bookedBy;
    private String status;
}
