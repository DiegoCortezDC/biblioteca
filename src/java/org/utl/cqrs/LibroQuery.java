package org.utl.cqrs;



import java.sql.SQLException;
import java.util.List;
import org.utl.dao.LibroDAO;
import org.utl.model.Libro;

public class LibroQuery {

    private final LibroDAO libroDAO;

    public LibroQuery() {
        this.libroDAO = new LibroDAO();
    }

    public List<Libro> getAllLibros() throws SQLException, ClassNotFoundException {
        return libroDAO.getAllLibros();
    }
    
    public List<Libro> getAllLibrosPublic() throws SQLException, ClassNotFoundException {
        return libroDAO.getAllLibrosPublic();
    }

    public List<Libro> buscarLibroPorNombre(String nombre) throws SQLException, ClassNotFoundException {
        return libroDAO.buscarLibroPorNombre(nombre);
    }

}
