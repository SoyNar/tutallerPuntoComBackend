package com.tutallerPuntoCom.generation.DTos.ResponseDTo;



import com.tutallerPuntoCom.generation.Utils.Enums.CategoryServices;

public record CategoryResponseDTO(
        String name,
        String label
) {
    public static CategoryResponseDTO from(CategoryServices category) {
        return new CategoryResponseDTO(category.name(), category.name());
    }
}