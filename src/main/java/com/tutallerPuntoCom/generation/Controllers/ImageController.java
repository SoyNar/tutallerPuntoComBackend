package com.tutallerPuntoCom.generation.Controllers;

import com.tutallerPuntoCom.generation.DTos.ResponseDTo.UploadImageResponseDTO;
import com.tutallerPuntoCom.generation.Services.Contracts.ImageStorageServiceI;
import com.tutallerPuntoCom.generation.Services.Impl.ImageStorageServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.Map;

@RestController
@RequestMapping("/api/images/")
public class ImageController {

    private  final ImageStorageServiceI  imageStorageServiceI;


    public ImageController(ImageStorageServiceI imageStorageServiceI){
        this.imageStorageServiceI = imageStorageServiceI;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadImageResponseDTO> upload(
            @RequestParam("file")MultipartFile file
            )
    {
        UploadImageResponseDTO  image = imageStorageServiceI.upload(file, "talleres");
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new UploadImageResponseDTO(image.url(), image.publicId()));
    }
}
