package foro.hub.proyecto.domain.usuario.data;

import foro.hub.proyecto.domain.usuario.perfil.Perfil;

import java.util.Set;

public record ModificarUsuario(
        String nombre,
        String correo,
        String contrasena,
        Set<String> roles) {
}
