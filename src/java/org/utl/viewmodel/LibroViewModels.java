package org.utl.viewmodel;


public class LibroViewModels {
    private int lId;               
    private String nombreL;        
    private String autor;          
    private String estatus;        
    private String genero;         
    private String universidad;    
    private String pdf;            

    public LibroViewModels(int lId, String nombreL, String autor, String estatus, String genero, String universidad, String pdf) {
        this.lId = lId;
        this.nombreL = nombreL;
        this.autor = autor;
        this.estatus = estatus;
        this.genero = genero;
        this.universidad = universidad;
        this.pdf = pdf;
    }

    public int getlId() {
        return lId;
    }

    public void setlId(int lId) {
        this.lId = lId;
    }

    public String getNombreL() {
        return nombreL;
    }

    public void setNombreL(String nombreL) {
        this.nombreL = nombreL;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
}
