package com.bricedenice59.chatop.repositories;

import com.bricedenice59.chatop.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {

}
