package com.desafio.literalura.repository;

import com.desafio.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    @Query("SELECT DISTINCT a FROM Autor a WHERE a.nacimiento <= :anio AND (a.muerte IS NULL OR a.muerte >= :anio)")
    List<Autor> buscarAutoresVivosEnAnio(Integer anio);

    @Query("SELECT a FROM Autor a WHERE LOWER(a.nombre) LIKE LOWER(concat('%', :nombre, '%'))")
    Optional<Autor> comprobarExistenciaAutor(String nombre);
}
