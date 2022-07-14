package com.example.demo.service;

import com.example.demo.Repo.MeetingRepo;
import com.example.demo.Repo.ReservationRepo;
import com.example.demo.exception.BasicException;
import com.example.demo.model.Meeting;
import com.example.demo.model.MeetingAndReservationModel;
import com.example.demo.model.Reservation;
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
    ReservationRepo reservationRepo;
    @Autowired
    MeetingRepo itemRepo;
    @Autowired
    MongoOperations mongoOperation;

    public Reservation UpdateReservation(Reservation reservation) throws BasicException {
        Reservation reser = reservationRepo.findById(reservation.getId()).orElseThrow(()->new BasicException("Invalid Reservation Id"));
        reser.setFrom(reservation.getFrom());
        reser.setTo(reservation.getTo());
        reser.setBookedBy(reservation.getBookedBy());
        return saveReservation(reser);
    }

    public Reservation saveReservation(Reservation reser) throws BasicException {
        if (reser !=null) {
            Meeting meeting = getAndCheckMeeting(reser.getMeetingId());
            List<Reservation> reservationList = reservationRepo.findByMeetingId(meeting.getId());
            if (reservationList.stream().anyMatch(r -> compareDate(r, reser.getFrom(), reser.getTo()))) {
                throw new BasicException("Date Overlapping , Please give a new date");
            } else {
                return reservationRepo.save(reser);
            }
        } else
            throw new BasicException("Invalid Reservation ID");
    }

//    public Boolean checkReservationOverlapping(Meeting meeting,LocalDateTime fromDate,LocalDateTime toDate){
//        return meeting.getReservations().stream().filter(reservation -> compareDate(reservation,fromDate,toDate)).count()>0;
//    }
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


    public List<MeetingAndReservationModel> getFromBoth(){
        LookupOperation lookupOperation = LookupOperation.newLookup().
                from("reservation").
                localField("_id").
                foreignField("meetingId").
                as("reservationList");

//        AggregationOperation match = Aggregation.match(Criteria.where("meeting_reservation"));

        Aggregation aggregation = Aggregation.newAggregation(lookupOperation);

        List<MeetingAndReservationModel> results = mongoOperation.aggregate(aggregation, "meeting", MeetingAndReservationModel.class).getMappedResults();

        return results;
    }

}
