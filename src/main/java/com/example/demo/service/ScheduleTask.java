package com.example.demo.service;

import com.example.demo.Repo.ItemRepo;
import com.example.demo.model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScheduleTask {

    @Autowired
    ItemRepo itemRepo;

    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}" ,initialDelay = 10000)
    public void updateStatus(){
        LocalDateTime currentTime = LocalDateTime.now();
        List<Meeting> allMeetings = itemRepo.findAll();
        allMeetings.stream().forEach(
                m->m.setStatus(m.getReservations().stream().anyMatch(
                        r -> (currentTime.isAfter(r.getFrom()) || currentTime.isEqual(r.getFrom())) &&
                                (currentTime.isBefore(r.getTo()) || currentTime.isEqual(r.getTo()))
                ) ? "O" : "U")
        );
        System.out.println("Scheduling run on "+LocalDateTime.now());
        itemRepo.saveAll(allMeetings);
    }


}
