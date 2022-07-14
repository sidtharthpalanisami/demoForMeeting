package com.example.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document("meeting")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Meeting {

    @Id
    private String id;
    @Indexed(name = "named_index")
    private String name;
    private int capacity;
    private String description;
    private String status;
    private List<Reservation> reservations;

}
