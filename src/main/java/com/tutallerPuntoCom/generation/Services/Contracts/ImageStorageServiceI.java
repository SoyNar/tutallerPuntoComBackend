package com.tutallerPuntoCom.generation.Services.Contracts;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.UploadImageResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageServiceI {

    UploadImageResponseDTO upload (MultipartFile file, String folder);

    void delete(String publicId);
}
