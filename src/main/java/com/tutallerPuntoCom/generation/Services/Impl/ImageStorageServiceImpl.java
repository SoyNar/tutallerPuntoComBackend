package com.tutallerPuntoCom.generation.Services.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.UploadImageResponseDTO;
import com.tutallerPuntoCom.generation.Exceptions.Customs.BadRequestException;
import com.tutallerPuntoCom.generation.Services.Contracts.ImageStorageServiceI;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ImageStorageServiceImpl implements ImageStorageServiceI {

    public record UploadedImage(String url, String publicId) {}

    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/png", "image/webp");
    private static final long MAX_BYTES = 5 * 1024 * 1024;

    private final Cloudinary cloudinary;

    public ImageStorageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public UploadImageResponseDTO upload(MultipartFile file, String folder) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("El archivo está vacío");
        }
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new BadRequestException("Este tipo de archivo no está permitido");
        }
        if (file.getSize() > MAX_BYTES) {
            throw new BadRequestException("La imagen supera los 5 MB");
        }

        try {
            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder", folder, "resource_type", "image"));

            return new UploadImageResponseDTO(
                    (String) result.get("secure_url"),
                    (String) result.get("public_id"));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo subir la imagen", e);
        }
    }

    public void delete(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            // No interrumpe el flujo: la imagen queda huérfana, pero el error sale en el log
            System.err.println("No se pudo borrar la imagen " + publicId + ": " + e.getMessage());
        }
    }
}