package com.desafio.literalura.service;

import com.desafio.literalura.model.Libro;
import com.desafio.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> mostrarTodosLibrosIdioma(String idioma){
        return libroRepository.mostrarListaPorIdioma(idioma);
    }

    public List<Libro> mostrarTodosLibros() {
        return libroRepository.findAll();
    }

    public Optional<Libro> comprobarExistenciaLibro(String nombre){
        return libroRepository.comprobarExistenciaLibro(nombre);
    }

    public void guardarLibro(Libro libro){
        libroRepository.save(libro);
    }


}
