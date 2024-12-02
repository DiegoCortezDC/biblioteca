package org.utl.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.utl.controller.ControllerLibro;
import org.utl.model.Libro;
import org.utl.viewmodel.LibroViewModels;

@Path("libro")
public class RestLibro {

    private final ControllerLibro cl = new ControllerLibro();
    private final Gson gson = new Gson();

    @Path("getAllLibrosPublico")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAllLibrosPublic() {
        try {
            List<LibroViewModels> libros = cl.getAllLibrosPublic();
            return Response.ok(gson.toJson(libros)).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Ocurrió un error. Intente más tarde.\"}")
                    .build();
        }
    }

    @Path("getAllLibros")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAllLibros() {
        try {
            List<Libro> libros = cl.getAllLibros();
            return Response.ok(gson.toJson(libros)).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Ocurrió un error. Intente más tarde.\"}")
                    .build();
        }
    }

    @Path("buscarLibroPorNombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response buscarLibroPorNombre(@PathParam("nombre") String nombre) {
        try {
            List<LibroViewModels> libros = cl.buscarLibroPorNombre(nombre);
            return Response.ok(gson.toJson(libros)).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Ocurrió un error. Intente más tarde.\"}")
                    .build();
        }
    }

    @Path("agregarLibro")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarLibro(@FormParam("libro") String libro,
                                 @FormParam("autor") String autor,
                                 @FormParam("genero") String genero,
                                 @FormParam("estatus") String estatus,
                                 @FormParam("pdf_libro") String pdf_libro,
                                 @FormParam("universidad") String universidad) {
        if (libro == null || autor == null || genero == null || pdf_libro == null || estatus == null || universidad == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Todos los campos son obligatorios.\"}")
                    .build();
        }

        Libro nuevoLibro = new Libro(0, libro, autor, genero, pdf_libro, estatus, universidad);
        try {
            String resultado = cl.agregarLibro(nuevoLibro);
            if (resultado == null) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"success\":\"Libro agregado correctamente\"}")
                        .build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\":\"" + resultado + "\"}")
                        .build();
            }
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Ocurrió un error. Intente más tarde.\"}")
                    .build();
        }
    }

    @Path("editarLibro/{Idlibro}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarLibro(@PathParam("Idlibro") int Idlibro,
                                @FormParam("libro") String libro,
                                @FormParam("autor") String autor,
                                @FormParam("genero") String genero,
                                @FormParam("estatus") String estatus,
                                @FormParam("pdf_libro") String pdf_libro,
                                @FormParam("universidad") String universidad) {
        if (libro == null || autor == null || genero == null || pdf_libro == null || estatus == null || universidad == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Todos los campos son obligatorios.\"}")
                    .build();
        }

        Libro editarLibro = new Libro(Idlibro, libro, autor, genero, pdf_libro, estatus, universidad);
        try {
            String resultado = cl.editarLibro(editarLibro);
            if (resultado == null) {
                return Response.ok("{\"success\":\"Libro editado correctamente\"}").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\":\"" + resultado + "\"}")
                        .build();
            }
        } catch (ClassNotFoundException | SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Ocurrió un error. Intente más tarde.\"}")
                    .build();
        }
    }
     @Path("getLibrosTodos")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getLibrosTodos() throws IOException {
        try {
            List<LibroViewModels> libros = cl.getLibrosTodos();
            return Response.ok(gson.toJson(libros)).build();
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Ocurrió un error. Intente más tarde.\"}")
                    .build();
        }
    }
}
