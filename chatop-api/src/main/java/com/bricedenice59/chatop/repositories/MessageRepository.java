package com.bricedenice59.chatop.repositories;

import com.bricedenice59.chatop.models.Message;
import com.bricedenice59.chatop.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

}
