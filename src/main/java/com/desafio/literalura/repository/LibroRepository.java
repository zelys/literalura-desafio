package com.desafio.literalura.repository;

import com.desafio.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    @Query("SELECT a FROM Libro a WHERE LOWER(a.titulo) LIKE LOWER(concat('%', :nombre, '%'))")
    Optional<Libro> comprobarExistenciaLibro(String nombre);

    @Query("SELECT l FROM Libro l WHERE :idioma = '' OR UPPER(l.idioma) = UPPER(:idioma)")
    List<Libro> mostrarListaPorIdioma(@Param("idioma") String idioma);






}