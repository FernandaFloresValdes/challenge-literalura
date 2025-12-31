package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private ConsumoAPI consumoAPI;

    @Autowired
    private ConvierteDatos convierteDatos;

    private static final String BASE_URL = "https://www.qutendex.com/books";

    public void mostrarMenu() {

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- LITERALURA ---");
            System.out.println("1 - Buscar libro por título");
            System.out.println("2 - Listar libros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar autores vivos en un año");
            System.out.println("5 - Listar libros por idioma");
            System.out.println("0 - Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> buscarLibro(scanner);
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida");
            }

        } while (opcion != 0);
    }

    private void buscarLibro(Scanner scanner) {
        System.out.print("Ingrese el título del libro: ");
        String titulo = scanner.nextLine();

        try {
            String encodedTitulo = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
            String json = consumoAPI.obtenerDatos(BASE_URL + "?search=" + encodedTitulo);

            RespuestaLibrosDTO respuestaLibrosDTO =
                    convierteDatos.obtenerDatos(json, RespuestaLibrosDTO.class);

            List<LibroDTO> librosDTO = respuestaLibrosDTO.getLibros();

            if (librosDTO.isEmpty()) {
                System.out.println("Libro no encontrado en la API");
                return;
            }

            boolean libroRegistrado = false;

            for (LibroDTO libroDTO : librosDTO) {

                if (libroDTO.getTitulo().equalsIgnoreCase(titulo)) {

                    Optional<Libro> libroExistente =
                            libroService.obtenerLibroPorTitulo(titulo);

                    if (libroExistente.isPresent()) {
                        System.out.println("Detalle: clave (titulo)=(" + titulo + ")");
                        System.out.println("No se puede registrar el mismo libro más de una vez.");
                        libroRegistrado = true;
                        break;
                    }

                    Libro libro = new Libro();
                    libro.setTitulo(libroDTO.getTitulo());
                    libro.setIdioma(libroDTO.getIdiomas().get(0));
                    libro.setNumeroDescargas(libroDTO.getNumeroDescargas());

                    AutorDTO primerAutorDTO = libroDTO.getAutores().get(0);

                    Autor autor = autorService
                            .obtenerAutorPorNombre(primerAutorDTO.getNombre())
                            .orElseGet(() -> {
                                Autor nuevoAutor = new Autor();
                                nuevoAutor.setNombre(primerAutorDTO.getNombre());
                                nuevoAutor.setAnoNacimiento(primerAutorDTO.getAnoNacimiento());
                                nuevoAutor.setAnoFallecimiento(primerAutorDTO.getAnoFallecimiento());
                                return autorService.crearAutor(nuevoAutor);
                            });

                    libro.setAutor(autor);
                    libroService.crearLibro(libro);

                    System.out.println("Libro registrado: " + libro.getTitulo());
                    mostrarDetallesLibro(libroDTO);

                    libroRegistrado = true;
                    break;
                }
            }

            if (!libroRegistrado) {
                System.out.println("No se encontró un libro exactamente con el título");
            }

        } catch (Exception e) {
            System.out.println("Error al obtener datos de la API: " + e.getMessage());
        }
    }

    private void mostrarDetallesLibro(LibroDTO libroDTO) {
        System.out.println("Título: " + libroDTO.getTitulo());
        System.out.println("Autor: " + libroDTO.getAutores().get(0).getNombre());
        System.out.println("Idioma: " + libroDTO.getIdiomas());
        System.out.println("Descargas: " + libroDTO.getNumeroDescargas());
    }
}
