package org.utl.rest;


import com.google.gson.Gson;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import org.utl.controller.ControllerUsuario;
import org.utl.model.Usuario;


@Path("usuario")
public class RestUsuario {

    private final ControllerUsuario controllerUsuario;

    public RestUsuario() {
        controllerUsuario = new ControllerUsuario();
    }

    @Path("getAllUsuarios")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAllUsuarios() {
        String out;
        try {
            List<Usuario> usuarios = controllerUsuario.getAllUsuarios();
            out = new Gson().toJson(usuarios);
        } catch (ClassNotFoundException | SQLException e) {
            out = "{\"error\":\"Ocurrió un error. Intente más tarde.\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity(out).build();
        }
        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(out).build();
    }

    @Path("agregarUsuario")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarUsuario(@FormParam("usuario") String usuario,
                                   @FormParam("password") String password,
                                   @FormParam("token") String token,
                                   @FormParam("Apaterno") String a_paterno,
                                   @FormParam("Amaterno") String a_materno,
                                   @FormParam("nombre") String nombre,
                                   @FormParam("rol") String rol) {
        String out;
        try {
            Usuario nuevoUsuario = new Usuario(0, usuario, password, token, a_paterno, a_materno, nombre, rol);
            String resultado = controllerUsuario.agregarUsuario(nuevoUsuario);
            out = "{\"success\":\"" + resultado + "\"}";
            return Response.ok(out).build();
        } catch (ClassNotFoundException | SQLException e) {
            out = "{\"error\":\"Ocurrió un error. Intente más tarde.\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(out).build();
        }
    }
    
    @Path("editarUsuario")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarUsuario(@FormParam("IdUsuario") int IdUsuario,
                                   @FormParam("usuario") String usuario,
                                   @FormParam("password") String password,
                                   @FormParam("token") String token,
                                   @FormParam("Apaterno") String Apaterno,
                                   @FormParam("Amaterno") String Amaterno,
                                   @FormParam("nombre") String nombre,
                                   @FormParam("rol") String rol) {
        String out;
        try {
            Usuario usuarioActualizado = new Usuario(IdUsuario, usuario, password, token, Apaterno, Amaterno, nombre, rol);
            String resultado = controllerUsuario.editarUsuario(usuarioActualizado);
            out = "{\"success\":\"" + resultado + "\"}";
            return Response.ok(out).build();
        } catch (ClassNotFoundException | SQLException e) {
            out = "{\"error\":\"Ocurrió un error. Intente más tarde.\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(out).build();
        }
    }

    @Path("buscarUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response buscarUsuario(@QueryParam("query") String query) {
        String out;
        try {
            List<Usuario> usuarios = controllerUsuario.buscarUsuario(query);
            out = new Gson().toJson(usuarios);
        } catch (ClassNotFoundException | SQLException e) {
            out = "{\"error\":\"Ocurrió un error. Intente más tarde.\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity(out).build();
        }
        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(out).build();
    }
}