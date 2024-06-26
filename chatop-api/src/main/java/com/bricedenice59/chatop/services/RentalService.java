package com.bricedenice59.chatop.services;

import com.bricedenice59.chatop.exceptions.RentalNotFoundException;
import com.bricedenice59.chatop.models.Rental;
import com.bricedenice59.chatop.models.responses.RentalResponse;
import com.bricedenice59.chatop.repositories.RentalRepository;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Data
@Service
public class RentalService {

    @Value("${server.port}")
    private String serverPort;
    private String serverHost = "127.0.0.1"; //surely there is a better way to do that!
    private final String imagesDirectoryPathname = "images";
    @Value("${upload.volume}")
    private String UPLOAD_VOLUME_PATH;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    private final RentalRepository rentalRepository;
    private String imageServerUrl;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @PostConstruct
    public void init() {
        imageServerUrl = "http://" + serverHost + ":" + serverPort + contextPath + "/" + imagesDirectoryPathname + "/";
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

    public RentalResponse buildRentalResponse(Rental rental){
        return RentalResponse.builder()
                .id(rental.getId())
                .name(rental.getName())
                .surface(rental.getSurface())
                .price(rental.getPrice())
                .picture(imageServerUrl + rental.getPicture())
                .description(rental.getDescription())
                .owner_id(rental.getOwner().getId())
                .createdAt(rental.getCreatedAt())
                .updatedAt(rental.getUpdatedAt())
                .build();
    }

    //https://medium.com/@kkarththi15/saving-images-locally-in-a-spring-boot-web-application-01405a988bc7
    public String saveFile(MultipartFile imageFile) throws IOException {
        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

        Path uploadPath = Path.of(UPLOAD_VOLUME_PATH);
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }
}
