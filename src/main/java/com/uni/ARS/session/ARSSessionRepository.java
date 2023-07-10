package com.uni.ARS.session;

import com.uni.ARS.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ARSSessionRepository extends JpaRepository<ARSSession, Integer> {

    @Query(value = "select * from arssessions where ?1= arssessions.name", nativeQuery = true)
    ARSSession findByName(String name);
}
