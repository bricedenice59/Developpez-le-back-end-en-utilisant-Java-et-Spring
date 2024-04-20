package com.bricedenice59.chatop.services;

import com.bricedenice59.chatop.exceptions.RentalNotFoundException;
import com.bricedenice59.chatop.model.Rental;
import com.bricedenice59.chatop.repositories.RentalRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public Rental getRental(Integer id) {
        var rental = rentalRepository.findById(id);
        return rental.orElseThrow(() -> new RentalNotFoundException("Rental not found"));
    }

    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public Rental saveOrUpdateRental(Rental rental) {
        return rentalRepository.save(rental);
    }
}
