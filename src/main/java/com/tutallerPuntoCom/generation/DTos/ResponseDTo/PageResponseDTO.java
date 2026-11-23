package com.tutallerPuntoCom.generation.DTos.ResponseDTo;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponseDTO<T>(List<T> content, int page, int size, long totalElements, int totalPage, boolean last) {

    /**
     * MEtodo estaqtico para converit modelo en paginacdor Dto
     * */

    public static <T> PageResponseDTO<T> from(Page<T> pageData){
        return  new PageResponseDTO<>(
                pageData.getContent(),
                pageData.getNumber(),
                pageData.getSize(),
                pageData.getTotalElements(),
                pageData.getTotalPages(),
                pageData.isLast()
        );
    }

}
