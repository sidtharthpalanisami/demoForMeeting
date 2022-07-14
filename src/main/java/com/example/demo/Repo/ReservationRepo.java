package com.example.demo.Repo;

import com.example.demo.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends MongoRepository<Reservation,String> {

    @Query(value = "{meetingid:'?0'}")
    public List<Reservation> findByMeetingId(String meetingId);
}
