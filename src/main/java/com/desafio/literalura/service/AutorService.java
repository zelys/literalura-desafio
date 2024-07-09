package com.desafio.literalura.service;

import com.desafio.literalura.model.Autor;
import com.desafio.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public void guardarAutor(Autor autor){
        autorRepository.save(autor);
    }

    public List<Autor> mostrarAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> buscarAutoresPorAnio(Integer anio) {
        return autorRepository.buscarAutoresVivosEnAnio(anio);
    }

    public Optional<Autor> comprobarExistenciaDeAutor(String nombre){
        return autorRepository.comprobarExistenciaAutor(nombre);
    }
}