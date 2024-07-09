package com.desafio.literalura.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ListadoLibrosDTO(
        @JsonAlias("results") List<LibroDTO> libros
) {

}