package com.uni.ARS.session;

import com.uni.ARS.admin.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository extends MongoRepository<Session, Integer> {
}
