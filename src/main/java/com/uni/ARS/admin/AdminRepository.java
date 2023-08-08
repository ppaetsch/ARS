package com.uni.ARS.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface AdminRepository extends MongoRepository<Admin, Integer> {

    @Query("{name:'?0'}")
    Admin findByName(String name);
}
