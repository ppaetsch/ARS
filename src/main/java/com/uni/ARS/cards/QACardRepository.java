package com.uni.ARS.cards;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QACardRepository extends MongoRepository<QACard, Integer> {

    @Query("{arssession:'?0'}")
    List<QACard> findByArsSession(String arssession);

    @Query("{arssession:'?0'}")
    void deleteByArsSession(String arssession);
}
