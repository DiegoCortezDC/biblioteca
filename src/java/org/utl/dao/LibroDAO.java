package org.utl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.utl.bd.ConexionMySQL;
import org.utl.model.Libro;

public class LibroDAO {

    private ConexionMySQL conexion;

    public LibroDAO() {
        this.conexion = new ConexionMySQL();
    }

    public List<Libro> getAllLibrosPublic() throws SQLException, ClassNotFoundException {
        List<Libro> librosList = new ArrayList<>();
        String query = "SELECT * FROM Libro WHERE estatus = 'Activo'";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = conexion.openConnection();
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();

            while (rs.next()) {
                int Idlibro = rs.getInt("Idlibro");
                String libro = rs.getString("libro");
                String autor = rs.getString("autor");
                String genero = rs.getString("genero");
                String pdf_libro = rs.getString("pdf_libro");
                String estatus = rs.getString("estatus");
                String universidad = rs.getString("universidad");

                Libro libros = new Libro(Idlibro, libro, autor, genero, pdf_libro, estatus, universidad);
                librosList.add(libros);
            }
        } finally {
            if (rs != null) rs.close();
            if (pstm != null) pstm.close();
            conexion.closeConnection();
        }

        return librosList;
    }

    public List<Libro> getAllLibros() throws SQLException, ClassNotFoundException {
        List<Libro> librosList = new ArrayList<>();
        String query = "SELECT * FROM Libro";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = conexion.openConnection();
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();

            while (rs.next()) {
                int Idlibro = rs.getInt("Idlibro");
                String libro = rs.getString("libro");
                String autor = rs.getString("autor");
                String genero = rs.getString("genero");
                String pdf_libro = rs.getString("pdf_libro");
                String estatus = rs.getString("estatus");
                String universidad = rs.getString("universidad");

                Libro libros = new Libro(Idlibro, libro, autor, genero, pdf_libro, estatus, universidad);
                librosList.add(libros);
            }
        } finally {
            if (rs != null) rs.close();
            if (pstm != null) pstm.close();
            conexion.closeConnection();
        }

        return librosList;
    }

    public List<Libro> buscarLibroPorNombre(String nombre) throws SQLException, ClassNotFoundException {
        List<Libro> librosList = new ArrayList<>();
        String query = "SELECT * FROM Libro WHERE libro LIKE ? AND estatus = 'Activo'";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = conexion.openConnection();
            pstm = conn.prepareStatement(query);
            pstm.setString(1, "%" + nombre + "%");
            rs = pstm.executeQuery();

            while (rs.next()) {
                int IdLibro = rs.getInt("Idlibro");
                String libro = rs.getString("libro");
                String autor = rs.getString("autor");
                String genero = rs.getString("genero");
                String pdf_libro = rs.getString("pdf_libro");
                String estatus = rs.getString("estatus");
                String universidad = rs.getString("universidad");

                Libro libros = new Libro(IdLibro, libro, autor, genero, pdf_libro, estatus, universidad);
                librosList.add(libros);
            }
        } finally {
            if (rs != null) rs.close();
            if (pstm != null) pstm.close();
            conexion.closeConnection();
        }

        return librosList;
    }

    public boolean agregarLibro(Libro libro) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Libro (libro, autor, genero, estatus, pdf_libro, universidad) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = conexion.openConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, libro.getLibro());
            pstmt.setString(2, libro.getAutor());
            pstmt.setString(3, libro.getGenero());
            pstmt.setString(4, libro.getEstatus());
            pstmt.setString(5, libro.getPdf_libro());
            pstmt.setString(6, libro.getUniversidad());
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } finally {
            if (pstmt != null) pstmt.close();
            conexion.closeConnection();
        }
    }

    public boolean editarLibro(Libro libro) throws SQLException, ClassNotFoundException {
        String query = "UPDATE Libro SET libro=?, autor=?, genero=?, estatus=?, pdf_libro=?, universidad=? WHERE Idlibro=?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = conexion.openConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, libro.getLibro());
            pstmt.setString(2, libro.getAutor());
            pstmt.setString(3, libro.getGenero());
            pstmt.setString(4, libro.getEstatus());
            pstmt.setString(5, libro.getPdf_libro());
            pstmt.setString(6, libro.getUniversidad());
            pstmt.setInt(7, libro.getIdlibro());
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } finally {
            if (pstmt != null) pstmt.close();
            conexion.closeConnection();
        }
    }
}
