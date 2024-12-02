package org.utl.cqrs;




import java.sql.SQLException;
import org.utl.dao.UsuarioDAO;
import org.utl.model.Usuario;

public class UsuarioCommand {
    private final UsuarioDAO usuarioDAO;

    public UsuarioCommand() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public String agregarUsuario(Usuario usuario) throws SQLException, ClassNotFoundException {
        return usuarioDAO.agregarUsuario(usuario) ? null : "No se pudo agregar el usuario.";
    }

    public String editarUsuario(Usuario usuario) throws SQLException, ClassNotFoundException {
        if (usuario.getIdUsuario() <= 0) {
            return "El ID del usuario debe ser mayor a 0.";
        }
        return usuarioDAO.editarUsuario(usuario) ? null : "No se pudo editar el usuario.";
    }

}
