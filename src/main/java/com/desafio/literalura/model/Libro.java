package com.desafio.literalura.model;

import com.desafio.literalura.dto.LibroDTO;
import jakarta.persistence.*;
import java.util.OptionalDouble;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Double descargas;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;



    public Libro(){}

    public Libro(LibroDTO datosLibros) {
        String titulo = datosLibros.titulo();
        if (titulo.length() > 255) {
            this.titulo = titulo.substring(0, 255).trim();
        } else {
            this.titulo = titulo.trim();
        }
        this.idioma = datosLibros.idioma().isEmpty() ? Idioma.DESCONOCIDO : Idioma.fromString(datosLibros.idioma().get(0));
        this.descargas = OptionalDouble.of(datosLibros.descargas()).orElse(0);
    }

    public Autor getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        return "titulo: '" + titulo + '\'' +
                ", autor: '" + obtenerNombreAutor() + '\'' +
                ", idioma: " + idioma +
                ", descargas: " + descargas ;
    }
    public String obtenerNombreAutor() {
        if (autor != null) {
            return autor.getNombre().split(",")[0].trim();
        } else {
            return "Desconocido";
        }
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

}
