package com.example.demo.service;

import com.example.demo.Repo.ItemRepo;
import com.example.demo.exception.BasicException;
import com.example.demo.model.Meeting;
import com.example.demo.model.Reservation;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetingService {

    @Autowired
    ItemRepo itemRepo;
    @Autowired
    MongoOperations mongoOperation;

    public Boolean checkReservationOverlapping(Meeting meeting,LocalDateTime fromDate,LocalDateTime toDate){
        return meeting.getReservations().stream().filter(reservation -> compareDate(reservation,fromDate,toDate)).count()>0;
    }
    public Boolean compareDate(Reservation reservation, LocalDateTime from, LocalDateTime to){
        Boolean boo = (from.isBefore(reservation.getTo())
                ||from.isEqual(reservation.getTo()))
                && (to.isAfter(reservation.getFrom())||to.isEqual(reservation.getFrom()));
        return boo;
    }

    public Meeting getAndCheckMeeting(String id) throws BasicException {
        Meeting meeting = itemRepo.findById(id).orElseThrow(() -> new BasicException("Meeting not exists"));
        return meeting;
    }


    public void getFromBoth(){
        LookupOperation lookupOperation = LookupOperation.newLookup().
                from("reservation").
                localField("reservations.rid").
                foreignField("_id").
                as("meeting_reservation");

        AggregationOperation match = Aggregation.match(Criteria.where("meeting_reservation").size(1));

        Aggregation aggregation = Aggregation.newAggregation(lookupOperation, match);

        List<BasicDBObject> results = mongoOperation.aggregate(aggregation, "meeting", BasicDBObject.class).getMappedResults();

    }

}
