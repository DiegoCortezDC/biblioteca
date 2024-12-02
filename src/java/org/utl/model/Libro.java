package org.utl.model;

public class Libro {

    private int Idlibro;
    private String libro;
    private String autor;
    private String genero;
    private String pdf_libro;
    private String estatus;
    private String universidad;

    public Libro() {
    }

    public Libro(int Idlibro, String libro, String autor, String genero, String pdf_libro, String estatus, String universidad) {
        this.Idlibro = Idlibro;
        this.libro = libro;
        this.autor = autor;
        this.genero = genero;
        this.pdf_libro = pdf_libro;
        this.estatus = estatus;
        this.universidad = universidad;
    }

    

    public int getIdlibro() {
        return Idlibro;
    }

    public void setIdlibro(int Idlibro) {
        this.Idlibro = Idlibro;
    }

    public String getLibro() {
        return libro;
    }

    public void setLibro(String libro) {
        this.libro = libro;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPdf_libro() {
        return pdf_libro;
    }

    public void setPdf_libro(String pdf_libro) {
        this.pdf_libro = pdf_libro;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    
}
