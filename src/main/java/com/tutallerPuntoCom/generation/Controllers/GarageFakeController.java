package com.tutallerPuntoCom.generation.Controllers;

import com.tutallerPuntoCom.generation.DTos.ResponseDTo.GarageFakeDTO;
import com.tutallerPuntoCom.generation.DTos.ResponseDTo.PageResponseDTO;
import com.tutallerPuntoCom.generation.Services.Impl.GarageFakeService;
import com.tutallerPuntoCom.generation.Utils.Enums.GarageStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/garages")
public class GarageFakeController {

    private final GarageFakeService garageFakeService;

    public GarageFakeController(GarageFakeService garageFakeService) {
        this.garageFakeService = garageFakeService;
    }


    @GetMapping("/{id}")
    public GarageFakeDTO findById(@PathVariable Long id) {
        return garageFakeService.findById(id);
    }

    @GetMapping
    public PageResponseDTO<GarageFakeDTO> findAll(
            @PageableDefault(size = 6) Pageable pageable,
            @RequestParam(required = false) GarageStatus status) {
        return garageFakeService.findPage(pageable, status);
    }
}