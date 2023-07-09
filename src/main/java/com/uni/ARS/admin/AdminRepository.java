package com.uni.ARS.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query(value = "select * from admins where ?1= admins.name", nativeQuery = true)
    Admin findByName(String name);
}
