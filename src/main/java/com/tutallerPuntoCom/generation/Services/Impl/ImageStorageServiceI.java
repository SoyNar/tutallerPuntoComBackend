package com.tutallerPuntoCom.generation.Services.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tutallerPuntoCom.generation.Exceptions.Customs.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ImageStorageServiceI  {


    private  static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/png", "image/webp");
    private  static  final long MAX_BYTES = 5 * 1024 * 1024;

    private  final Cloudinary cloudinary;

    public  ImageStorageServiceI(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }


    public String upload(MultipartFile file, String folder) {

        //verificar que no este vacio el archivo
        if(file == null || file.isEmpty()){
            throw new BadRequestException("El archivo esta vacio");
        }

        //validar el formato permitido
        if(!ALLOWED_TYPES.contains(file.getContentType())){
            throw new BadRequestException("Este tipo de archivo no esta permitido");
        }

        //solo el maximo de bytes
        if (file.getSize() > MAX_BYTES){
            throw new BadRequestException("LA imagen supera los 5MB");
        }
        try {
            Map<?,?> resul = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder",
                            "resource_type", "image"));
            return  (String)  resul.get("secure_url");
        }catch (IOException e) {
            throw new RuntimeException("No se pudo subir la imagen", e);
        }

    }
}
