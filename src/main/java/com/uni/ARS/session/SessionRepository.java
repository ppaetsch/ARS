package com.uni.ARS.session;

import com.uni.ARS.admin.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository extends MongoRepository<Session, Integer> {

    @Query("{name:'?0'}")
    void deleteByName(String name);
}
