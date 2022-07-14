package com.example.demo.Repo;


import com.example.demo.model.Meeting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepo extends MongoRepository<Meeting,String> {


    @Query(value = "{name:'?0'}")
    Meeting findItemByName(String name);

    @Query(value = "{capacity:'?0'}",fields = "{name:1,status:1}")
    List<Meeting> findByCapacity(int capacity);

    @Query(value = "{description:'?0'}",fields = "{name:1,description:1}")
    List<Meeting> findByDescription(String description);


    @Query(value = "{status:'?0'}")
    List<Meeting> findByStatus(String status);
}
