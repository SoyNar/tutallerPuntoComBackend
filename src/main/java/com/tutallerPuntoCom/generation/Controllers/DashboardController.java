package com.tutallerPuntoCom.generation.Controllers;

import com.tutallerPuntoCom.generation.DTos.ResponseDTo.DashboardResponseDTO;
import com.tutallerPuntoCom.generation.Services.Impl.DashboardService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardResponseDTO getDashboard() {
        return dashboardService.getDashboard();
    }
}
