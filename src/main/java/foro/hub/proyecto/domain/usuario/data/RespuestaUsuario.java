package foro.hub.proyecto.domain.usuario.data;

import foro.hub.proyecto.domain.usuario.Usuario;
import foro.hub.proyecto.domain.usuario.perfil.Perfil;

import java.util.Set;

public record RespuestaUsuario(
        Long id,
        String nombre,
        String correo,
        Set<Perfil> roles) {

    public RespuestaUsuario (Usuario usuario){
        this(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), usuario.getRoles());
    }
}
