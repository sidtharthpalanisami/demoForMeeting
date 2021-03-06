package com.example.demo.controller;

import com.example.demo.Repo.MeetingRepo;
import com.example.demo.exception.MeetingUnavailableException;
import com.example.demo.model.Meeting;
import com.example.demo.model.MeetingAndReservationModel;
import com.example.demo.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/meeting/")
public class MeetingController {

    @Autowired
    MeetingRepo itemRepo;

    @Autowired
    MeetingService meetingService;

    @GetMapping("/getAllData")
    public List<MeetingAndReservationModel> getAllMeetingRoom(){
        return meetingService.getFromBoth();
    }

    @PostMapping("/createMeetingRoom")
    public String createMeeting(@RequestBody Meeting meeting){
        System.out.println("creating");
        try{
        itemRepo.insert(meeting);}
        catch(Exception e){
            return "Error in creating meeting";
        }
        return "Meeting Room Created";
    }

    @PutMapping("/updateMeetingRoom/{id}")
    public Meeting updateMeeting(@PathVariable String id,@RequestBody Meeting meeting) throws MeetingUnavailableException {
        Meeting saved = null;
        if(itemRepo.existsById(id)){
            saved = itemRepo.save(meeting);
        }
        else{
            throw new MeetingUnavailableException("Invalid meeting room");
        }
        return saved;
    }

    @DeleteMapping("/deleteMeeting/{id}")
    public String deleteMeeting(@PathVariable String id){
        if(itemRepo.existsById(id)){
            itemRepo.deleteById(id);
            return "Meeting is deleted";
        }
        else
            return "Invaid meeting Id";
    }

    @GetMapping("/getAvailableMeetingRooms")
    public List<Meeting> getAvailableMeeting(){
        List<Meeting> meetingList = itemRepo.findByStatus("U");
        if(meetingList.isEmpty())
            return new ArrayList<Meeting>();
        return meetingList;
    }

//        @PutMapping("/createReservation/{id}")
//        public String createNewReservation(@PathVariable String id, @RequestBody Reservation reservation) throws BasicException, MeetingUnavailableException {
//            List<Meeting> meetingList = getAvailableMeeting();
//            Meeting meeting = meetingService.getAndCheckMeeting(id);
//               if(meetingList.isEmpty() || !meetingList.stream().anyMatch(m->m.getId().equalsIgnoreCase(meeting.getId())))
//                   throw new MeetingUnavailableException("Meeting room is not available");
//               else{
//                   if(meetingService.checkReservationOverlapping(meeting,reservation.getFrom(),reservation.getTo())){
//                       throw new BasicException("Meeting already available for the data period");
//                   }
//               }
//                meeting.getReservations().add(reservation);
//                itemRepo.save(meeting);
//            return "Reservation made";
//        }

//        @GetMapping("/checkAvailability/{id}/{fromDate}/{toDate}")
//        public String getAvailability(@PathVariable String id, @PathVariable String fromDate, @PathVariable String toDate) throws BasicException {
//                Meeting meeting = meetingService.getAndCheckMeeting(id);
//                if(meetingService.checkReservationOverlapping(meeting,LocalDateTime.parse(fromDate),LocalDateTime.parse(toDate))){
//                    throw new BasicException("Meeting already available for the data period");
//                }
//            return "Meeting Room is available";
//    }
//    @PutMapping("/deleteReservation/{id}/reservationId/{rId}")
//    public String deleteReservation(@PathVariable String id,@PathVariable String rId) throws BasicException {
//           Meeting meeting = meetingService.getAndCheckMeeting(id);
//            meeting.getReservations().removeIf(reserve -> reserve.getId().equalsIgnoreCase(rId));
//            itemRepo.save(meeting);
//            return "Reservation is deleted";
//    }

//    @PutMapping("/updateReservation/{id}/{rId}")
//    public String updateReservation(@PathVariable String id,@PathVariable String rId,@RequestBody Reservation reservation) throws BasicException {
//        Meeting meeting = meetingService.getAndCheckMeeting(id);
//        if (meeting.getReservations().stream().anyMatch(r -> r.getId().equalsIgnoreCase(rId))) {
//            meeting.getReservations().removeIf(r -> r.getId().equalsIgnoreCase(rId));
//            if (meetingService.checkReservationOverlapping(meeting, reservation.getFrom(), reservation.getTo())) {
//                throw new BasicException("dateOverlapping error");
//            }
//
//            meeting.getReservations().add(reservation);
//            itemRepo.save(meeting);
//        } else {
//            throw new BasicException("Reservation is invalid");
//        }
//        return "meeting is updated";
//    }
}
