package com.bricedenice59.chatop.controller;


import com.bricedenice59.chatop.exceptions.RentalChangeOwnerForbiddenException;
import com.bricedenice59.chatop.models.Rental;
import com.bricedenice59.chatop.models.ApiActionSuccessOutputMessage;
import com.bricedenice59.chatop.models.requests.RentalRequest;
import com.bricedenice59.chatop.services.RentalService;
import com.bricedenice59.chatop.services.UserService;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rentals")
public class RentalController {

    private final RentalService rentalService;
    private final UserService userService;

    public RentalController(RentalService rentalService, UserService userService) {
        this.rentalService = rentalService;
        this.userService = userService;
    }

    /**
     * Get all rental advertisements
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Rental> getAll() {
        return rentalService.getRentals();
    }

    /**
     * Get a rental advertisement by id
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRentalById(@PathVariable("id") final Integer id) {
        var rental = rentalService.getRental(id);
        var rentalDto = RentalRequest.builder()
                .name(rental.getName())
                .surface(rental.getSurface())
                .price(rental.getPrice())
                .picture(rental.getPicture())
                .description(rental.getDescription())
                .owner_id(rental.getOwner().getId())
                .createdAt(rental.getCreatedAt())
                .updatedAt(rental.getUpdatedAt())
                .build();
        return new ResponseEntity<>(rentalDto, HttpStatus.OK);
    }

    /**
     * Post a rental advertisement
     */
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addRental(@Valid @RequestBody RentalRequest rentalRequest) {
        var user = userService.getUserById(rentalRequest.getOwner_id());
        var rental = Rental.builder()
                .name(rentalRequest.getName())
                .surface(rentalRequest.getSurface())
                .price(rentalRequest.getPrice())
                .picture(rentalRequest.getPicture())
                .description(rentalRequest.getDescription())
                .Owner(user)
                .build();
        rentalService.saveOrUpdateRental(rental);
        return new ResponseEntity<>(new ApiActionSuccessOutputMessage("Rental created !"), HttpStatus.CREATED);
    }

    /**
     * Update a rental advertisement
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRental(@PathVariable("id") final Integer id, @Valid @RequestBody RentalRequest rentalRequest) {
        var rental = rentalService.getRental(id);
        if(rentalRequest.getOwner_id() != null && !rental.getId().equals(rentalRequest.getOwner_id())) {
            throw new RentalChangeOwnerForbiddenException("You are not allowed to change owner of an existing rental");
        }
        if(StringUtils.isNotEmpty(rentalRequest.getName()) && !rental.getName().equals(rentalRequest.getName())) {
            rental.setName(rentalRequest.getName());
        }
        if(StringUtils.isNotEmpty(rentalRequest.getPicture()) && !rental.getPicture().equals(rentalRequest.getPicture())) {
            rental.setPicture(rentalRequest.getPicture());
        }
        if(StringUtils.isNotEmpty(rentalRequest.getDescription()) && !rental.getDescription().equals(rentalRequest.getDescription())) {
            rental.setDescription(rentalRequest.getDescription());
        }
        if(rentalRequest.getPrice() != null && !rental.getPrice().equals(rentalRequest.getPrice())) {
            rental.setPrice(rentalRequest.getPrice());
        }
        if(rentalRequest.getSurface() != null && !rental.getSurface().equals(rentalRequest.getSurface())) {
            rental.setSurface(rentalRequest.getSurface());
        }

        rentalService.saveOrUpdateRental(rental);
        return new ResponseEntity<>(new ApiActionSuccessOutputMessage("Rental updated !"), HttpStatus.OK);
    }
}
