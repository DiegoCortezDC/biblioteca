package org.utl.cqrs;



import java.sql.SQLException;
import org.utl.dao.LibroDAO;
import org.utl.model.Libro;


public class LibroCommand {
    private final LibroDAO libroDAO;

    public LibroCommand() {
        this.libroDAO = new LibroDAO();
    }

    public String agregarLibro(Libro libro) throws SQLException, ClassNotFoundException {
        String validationError = validateLibro(libro);
        if (validationError != null) {
            return validationError; 
        }
        return libroDAO.agregarLibro(libro) ? null : "No se pudo agregar el libro.";
    }

    public String editarLibro(Libro libro) throws SQLException, ClassNotFoundException {
        if (libro.getIdlibro()<= 0) {
            return "El ID del libro debe ser mayor a 0.";
        }
        String validationError = validateLibro(libro);
        if (validationError != null) {
            return validationError; 
        }
        return libroDAO.editarLibro(libro) ? null : "No se pudo editar el libro.";
    }

    private String validateLibro(Libro libro) {
        String nombre = libro.getLibro();
        String genero = libro.getGenero();
        if (nombre.length() < 5 || nombre.length() > 100) {
            return "El nombre del libro debe tener entre 5 y 100 caracteres.";
        }
        if (genero.length() < 5 || genero.length() > 100) {
            return "El género del libro debe tener entre 5 y 100 caracteres.";
        }
        return null;
    }
}