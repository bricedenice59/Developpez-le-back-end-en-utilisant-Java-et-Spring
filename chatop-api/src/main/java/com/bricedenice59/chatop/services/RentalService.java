package com.bricedenice59.chatop.services;

import com.bricedenice59.chatop.exceptions.RentalNotFoundException;
import com.bricedenice59.chatop.models.Rental;
import com.bricedenice59.chatop.repositories.RentalRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Data
@Service
public class RentalService {

    private static String UPLOAD_DIRECTORY = "chatop-api/src/main/resources/static/images/";

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

    //https://medium.com/@kkarththi15/saving-images-locally-in-a-spring-boot-web-application-01405a988bc7
    public String saveFile(MultipartFile imageFile) throws IOException {
        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

        Path uploadPath = Paths.get(System.getProperty("user.dir")).resolve(Path.of(UPLOAD_DIRECTORY));
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }
}
