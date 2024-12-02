package org.utl.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.utl.AppService.LibrosExternosAppService;
import org.utl.cqrs.LibroCommand;
import org.utl.cqrs.LibroQuery;
import org.utl.model.Libro;
import org.utl.viewmodel.LibroViewModels;

public class ControllerLibro {

    private final LibroCommand libroCommand;
    private final LibroQuery libroQuery;
    static LibrosExternosAppService le = new LibrosExternosAppService();

    public ControllerLibro() {
        this.libroCommand = new LibroCommand();
        this.libroQuery = new LibroQuery();
    }

    public List<LibroViewModels> getAllLibrosPublic() throws SQLException, ClassNotFoundException {
        List<Libro> libros = libroQuery.getAllLibrosPublic();
        List<LibroViewModels> respuesta = new ArrayList<>();
        for (Libro i : libros) {
            // Crear un objeto LibroViewModels con la nueva estructura
            LibroViewModels item = new LibroViewModels(
                i.getIdlibro(),           
                i.getLibro(),             
                i.getAutor(),            
                i.getEstatus(),          
                i.getGenero(),            
                i.getUniversidad(),       
                i.getPdf_libro()          
            );
            respuesta.add(item);
        }
        return respuesta;
    }

    public List<Libro> getAllLibros() throws SQLException, ClassNotFoundException {
        return libroQuery.getAllLibros();
    }

    public List<LibroViewModels> buscarLibroPorNombre(String nombre) throws SQLException, ClassNotFoundException {
        List<Libro> libros = libroQuery.buscarLibroPorNombre(nombre);
        List<LibroViewModels> respuesta = new ArrayList<>();
        for (Libro i : libros) {
            LibroViewModels item = new LibroViewModels(
                i.getIdlibro(),           
                i.getLibro(),             
                i.getAutor(),             
                i.getEstatus(),          
                i.getGenero(),            
                i.getUniversidad(),      
                i.getPdf_libro()          
            );
            respuesta.add(item);
        }
        return respuesta;
    }

    public String agregarLibro(Libro libro) throws ClassNotFoundException, SQLException {
        return libroCommand.agregarLibro(libro);
    }

    public String editarLibro(Libro libro) throws ClassNotFoundException, SQLException {
        return libroCommand.editarLibro(libro);
    }

    public List<LibroViewModels> getLibrosTodos() throws SQLException, ClassNotFoundException, IOException {
        List<LibroViewModels> librosLocales = getAllLibrosPublic();
        List<LibroViewModels> externalBooks1 = le.getAllLibrosExternos();

        librosLocales.addAll(externalBooks1);
        return librosLocales;
    }
}
