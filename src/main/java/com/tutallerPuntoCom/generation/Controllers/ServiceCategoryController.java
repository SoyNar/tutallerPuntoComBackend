    package com.tutallerPuntoCom.generation.Controllers;


    import com.tutallerPuntoCom.generation.DTos.ResponseDTo.CategoryResponseDTO;
    import com.tutallerPuntoCom.generation.Services.Contracts.OfferServiceI;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.List;

    @RestController
    @RequestMapping("/api/service-categories")
    public class ServiceCategoryController {

        private final OfferServiceI offerService;

        public ServiceCategoryController(OfferServiceI offerService) {
            this.offerService = offerService;
        }

        @GetMapping
        public ResponseEntity<List<CategoryResponseDTO>> getCategories() {
            return ResponseEntity.ok(offerService.getCategories());
        }
}