package com.example.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
@Document
public class Reservation {

    @Id
    private String id;
    private LocalDateTime from;
    @Indexed(name = "expire_after" ,expireAfterSeconds = 3600)
    private LocalDateTime to;
    private String bookedBy;
    private String status;
}
