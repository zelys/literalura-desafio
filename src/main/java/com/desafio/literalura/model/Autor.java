package com.desafio.literalura.model;

import com.desafio.literalura.dto.AutorDTO;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;
    private Integer nacimiento;
    private Integer muerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Libro> librosEscritos;

    public Autor() {
    }

    public Autor(List<AutorDTO> autor) {
        if (autor != null && !autor.isEmpty()) {
            this.nombre = autor.get(0).nombre();
            this.nacimiento = autor.get(0).nacimiento();
            this.muerte = autor.get(0).muerte();
        } else {
            this.nombre = "Desconocido";
            this.nacimiento = 0;
            this.muerte = 0;
        }
    }

    public Set<Libro> getLibrosEscritos() {
        return librosEscritos;
    }

    public void setLibrosEscritos(Set<Libro> librosEscritos) {
        this.librosEscritos = librosEscritos;
    }

    @Override
    public String toString() {
        return "nombre: '" + nombre + '\'' +
                ", nacimiento: '" + nacimiento + '\'' +
                ", muerte: '" + muerte + '\'' +
                ", libros: " + mostrarTitulosLibros();
    }

    private List<String> mostrarTitulosLibros(){
        return librosEscritos.stream().map(Libro::getTitulo).toList();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombres) {
        this.nombre = nombres;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getMuerte() {
        return muerte;
    }

    public void setMuerte(Integer muerte) {
        this.muerte = muerte;
    }
}
