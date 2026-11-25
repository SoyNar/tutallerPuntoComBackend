package com.tutallerPuntoCom.generation.Services.Impl;

import com.tutallerPuntoCom.generation.DTos.ResponseDTo.GarageFakeDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.Utils.Enums.GarageStatus;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

@Service
public class GarageFakeService {

    private final ObjectMapper objectMapper;
    private List<GarageFakeDTO> garageFakeDTOS;

    public GarageFakeService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    void load() throws Exception {
        try (InputStream in = new ClassPathResource("fake/garages.json").getInputStream()) {
            this.garageFakeDTOS = objectMapper.readValue(in, new TypeReference<List<GarageFakeDTO>>() {});
        }
    }

    public List<GarageFakeDTO> findAll() {
        return garageFakeDTOS;
    }

    public GarageFakeDTO findById(Long id) {
        return garageFakeDTOS.stream()
                .filter(g -> g.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Taller no encontrado: " + id));
    }

    public PageResponseDTO<GarageFakeDTO> findPage(Pageable pageable, GarageStatus status) {
        List<GarageFakeDTO> filtrados = status == null
                ? garageFakeDTOS
                : garageFakeDTOS.stream()
                .filter(g -> g.garageStatus() == status)
                .toList();

        int desde = (int) Math.min(pageable.getOffset(), filtrados.size());
        int hasta = Math.min(desde + pageable.getPageSize(), filtrados.size());

        Page<GarageFakeDTO> page = new PageImpl<>(
                filtrados.subList(desde, hasta),
                pageable,
                filtrados.size()
        );

        return PageResponseDTO.from(page);
    }
}