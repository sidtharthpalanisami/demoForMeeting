package com.example.demo;

import com.example.demo.Repo.MeetingRepo;
import com.example.demo.Repo.ReservationRepo;
import com.example.demo.model.Meeting;
import com.example.demo.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication(scanBasePackages = "com.example")
@EnableMongoRepositories(basePackages = "com.example")
@EnableScheduling
public class DemoApplication implements CommandLineRunner{

	@Autowired
    MeetingRepo meetingRepo;

	@Autowired
	ReservationRepo reservationRepo;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	void createMeetings() {
		System.out.println("Data creation started...");
		meetingRepo.save(new Meeting("1", "Whole Wheat Biscuit", 5, "spices","O"));
		meetingRepo.save(new Meeting("2", "XYZ Kodo Millet healthy", 2, "millets","O"));
		meetingRepo.save(new Meeting("3", "Dried Whole Red Chilli", 2, "spices","U"));
		meetingRepo.save(new Meeting("4", "Healthy Pearl Millet", 1, "millets","U"));
		meetingRepo.save(new Meeting("5", "Bonny Cheese Crackers Plain", 6, "snacks","U"));
		reservationRepo.save(new Reservation("1","1",LocalDateTime.parse("2022-07-20T01:00:00"),LocalDateTime.parse("2022-07-20T04:00:00"),"sid","A"));
		System.out.println("Data creation complete...");
	}
	public void showAllmeetings() {

		meetingRepo.findAll().forEach(item -> System.out.println(getMeetingDetails(item)));
	}

	// 2. Get item by name
	public void getmeetingByName(String name) {
		System.out.println("Getting item by name: " + name);
		Meeting item = meetingRepo.findItemByName(name);
		System.out.println(getMeetingDetails(item));
	}

	// 3. Get name and quantity of a all items of a particular category
	public void getItemsByCapacity(int capacity) {
		System.out.println("Getting items for the category " + capacity);
		List<Meeting> list = meetingRepo.findByCapacity(capacity);

		list.forEach(item -> System.out.println("Name: " + item.getName() + ", capacity: " + item.getCapacity()));
	}

	// 4. Get count of documents in the collection
	public void findCountOfmeetings() {
		long count = meetingRepo.count();
		System.out.println("Number of documents in the collection: " + count);
	}

	public String getMeetingDetails(Meeting item) {

		System.out.println(
				"Meeting name: " + item.getName() +
						", \nCapacity: " + item.getCapacity()+
						", \nDescription: " + item.getDescription()+
						", \nStatus: " +item.getStatus()
//						", \nReservation: "+ item.getReservations().toString()
		);

		return "";
	}

	public void UpdateDescription(String desc) {

		// Change to this new value
		String newDesc = "munchies";

		// Find all the items with the category snacks
		List<Meeting> list = meetingRepo.findByDescription(desc);

		list.forEach(item -> {
			// Update the category in each document
			item.setDescription(newDesc);
		});

		// Save all the items in database
		List<Meeting> itemsUpdated = meetingRepo.saveAll(list);

		if(itemsUpdated != null)
			System.out.println("Successfully updated " + itemsUpdated.size() + " items.");
	}
	public void deletemeeting(String id) {
		meetingRepo.deleteById(id);
		System.out.println("Item with id " + id + " deleted...");
	}

	@Override
	public void run(String... args) {

		System.out.println("-------------CREATE meeting-------------------------------\n");

		createMeetings();

		System.out.println("\n----------------SHOW ALL meeting---------------------------\n");

		showAllmeetings();

		System.out.println("\n--------------GET MEETING BY NAME-----------------------------------\n");

		getmeetingByName("Whole Wheat Biscuit");

		System.out.println("\n-----------GET ITEMS BY CAPACITY---------------------------------\n");

		getItemsByCapacity(5);

		System.out.println("\n-----------UPDATE description----------------\n");

		UpdateDescription("snacks");

		System.out.println("\n----------DELETE A meeting----------------------------------\n");

		deletemeeting("5");

		System.out.println("\n------------FINAL COUNT OF meeting-------------------------\n");

		findCountOfmeetings();

		System.out.println("\n-------------------THANK YOU---------------------------");

	}
}

