package com.example.demo.service;

import com.example.demo.Repo.MeetingRepo;
import com.example.demo.model.Meeting;
import com.example.demo.model.MeetingAndReservationModel;
import com.example.demo.model.Reservation;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduleTask {

    @Autowired
    MeetingRepo itemRepo;

    @Autowired
    MeetingService meetingService;

//    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}" ,initialDelay = 10000)
//    public void updateStatus(){
//        LocalDateTime currentTime = LocalDateTime.now();
//        List<Meeting> allMeetings = itemRepo.findAll();
//        allMeetings.stream().forEach(
//                m->m.setStatus(m.getReservations().stream().anyMatch(
//                        r -> (currentTime.isAfter(r.getFrom()) || currentTime.isEqual(r.getFrom())) &&
//                                (currentTime.isBefore(r.getTo()) || currentTime.isEqual(r.getTo()))
//                ) ? "O" : "U")
//        );
//        System.out.println("Scheduling run on "+LocalDateTime.now());
//        itemRepo.saveAll(allMeetings);
//    }

    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}" ,initialDelay = 10000)
    public void updateStatus(){
        LocalDateTime currentTime = LocalDateTime.now();
        List<MeetingAndReservationModel> fromBoth = meetingService.getFromBoth();
        fromBoth.stream().forEach(
                m->m.setStatus(m.getReservationList().stream().anyMatch(
                        r -> (currentTime.isAfter(r.getFrom()) || currentTime.isEqual(r.getFrom())) &&
                                (currentTime.isBefore(r.getTo()) || currentTime.isEqual(r.getTo()))
                ) ? "O" : "U")
        );
        System.out.println("Scheduling run on "+LocalDateTime.now());
        List<Meeting> meetingList = new ArrayList<>();
        fromBoth.stream().forEach(list->assignAndAdd(meetingList,list));
        itemRepo.saveAll(meetingList);
    }

    private void assignAndAdd(List<Meeting> meetingList, MeetingAndReservationModel list) {
        Meeting meeting = new Meeting(list.getId(), list.getName(), list.getCapacity(),list.getDescription(), list.getStatus());
        meetingList.add(meeting);
    }


}
