package com.desafio.literalura.principal;

import com.desafio.literalura.dto.LibroDTO;
import com.desafio.literalura.dto.ListadoLibrosDTO;
import com.desafio.literalura.model.Autor;
import com.desafio.literalura.model.Libro;
import com.desafio.literalura.service.AutorService;
import com.desafio.literalura.service.ConsultaAPI;
import com.desafio.literalura.service.ConvierteDatos;
import com.desafio.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Principal {
    private String URL_BASE = "https://gutendex.com/books/";
    private Scanner teclado = new Scanner(System.in);
    private ConsultaAPI consumoAPI = new ConsultaAPI();
    private List<Libro> libro;
    private List<Autor> autor;
    private ConvierteDatos conversor = new ConvierteDatos();

    @Autowired
    AutorService autorService;

    @Autowired
    LibroService libroService;

    public void mostrarMenu(){
        var opcion = -1;
        while (opcion != 0) {
            do {
                try {
                    var menu = """
                            1 - Buscar libro por titulo
                            2 - Listar libros registrados
                            3 - Listar autores registrados
                            4 - Listar autores vivos en un determinado año
                            5 - Listar libros por idioma
                            0 - Salir
                            """;
                    System.out.println(menu);
                    opcion = teclado.nextInt();
                    teclado.nextLine();
                } catch (InputMismatchException e){
                    System.out.println("Error: Por favor, ingrese un número válido");
                    teclado.nextLine();
                }
            } while (opcion > 5 || opcion < 0);
            switch (opcion) {
                case 1:
                    buscarLibros();
                    break;
                case 2:
                    mostrarLibros();
                    break;
                case 3:
                    listaDeAutores();
                    break;
                case 4:
                    buscarAutoresPorAnio();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        }
    }

    private void buscarAutoresPorAnio() {
        System.out.println("\nIngresa el año para buscar autores: ");
        Integer anio = teclado.nextInt();

        autor = autorService.buscarAutoresPorAnio(anio);
        autor.forEach(System.out::println);

    }

    private void listaDeAutores() {
        autor = autorService.mostrarAutores();
        autor.forEach(System.out::println);
    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("\nIngresa el idioma a listar ( 1.Español , 2.Ingles , 3.Idioma Desconocido)\n ");
        var opcionIdioma = teclado.nextInt();
        String tempOpcion = "";
        switch (opcionIdioma) {
            case 1:
                tempOpcion = "ESPAÑOL";
                break;
            case 2:
                tempOpcion = "INGLES";
                break;
            case 3:
                tempOpcion = "DESCONOCIDO";
                break;
            default:
                System.out.println("Opción incorrecta de idioma\n");
        }

        libro = libroService.mostrarTodosLibrosIdioma(tempOpcion);

        if (libro.isEmpty()) {
            System.out.println("\nNo hay libros registrados en ese idioma.\n");
        } else {
            libro.forEach(System.out::println);
        }
    }


    private void mostrarLibros() {
        libro = libroService.mostrarTodosLibros();

        libro.stream().sorted(Comparator.comparing(Libro::getId))
                .forEach(System.out::println);
    }

    private void buscarLibros() {
        System.out.println("\nIngresa el nombre del libro que deseas buscar: ");
        var busqueda = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + busqueda.replace(" ", "%20"));
        System.out.println(json);

        var datosBusqueda = conversor.convertirDatos(json, ListadoLibrosDTO.class);

        Optional<LibroDTO> datosLibrosOptional = datosBusqueda.libros().stream()
                .filter(libro -> libro.titulo().toLowerCase().contains(busqueda.toLowerCase()))
                .findFirst();

        if (datosLibrosOptional.isPresent()) {
            LibroDTO datosLibros = datosLibrosOptional.get();
            System.out.println("\nLibro encontrado: " + datosLibros.titulo());

            // Obtener el nombre del autor
            String nombreAutor = datosLibros.autor() != null ? datosLibros.autor().get(0).nombre() : "Desconocido";
            System.out.println("Autor: " + nombreAutor);


            System.out.println("\n¿Deseas guardar este libro en la base de datos? 1.Sí  2.No");
            int opcion = teclado.nextInt();
            teclado.nextLine();
            if (opcion == 1) {
                Autor autor = datosLibros.autor() != null ? new Autor(datosLibros.autor()) : null;
                Optional<Autor> existenciaAutores = autorService.comprobarExistenciaDeAutor(nombreAutor);

                if (existenciaAutores.isPresent()) {
                    System.out.println("\n*** Autor ya existe en la base de datos. ***");
                    autor = existenciaAutores.get();
                }
                try {
                    autorService.guardarAutor(autor);
                    Optional<Libro> existenciaLibro = libroService.comprobarExistenciaLibro(datosLibros.titulo());

                    if (existenciaLibro.isEmpty()) {
                        Libro libro = new Libro(datosLibros);
                        libro.setAutor(autor);
                        libroService.guardarLibro(libro);
                    } else {
                        System.out.println("\n*** Este libro ya fue registrado en la base de datos. ***");
                    }
                } catch (Exception e) {
                    System.out.println("\n*** Error al intentar guardar el libro: " + e.getMessage() + " ***");
                }
            }
        } else {
            System.out.println("\n*** Libro no encontrado. ***");
        }
    }
}
