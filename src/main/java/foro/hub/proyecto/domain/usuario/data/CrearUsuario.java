package foro.hub.proyecto.domain.usuario.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CrearUsuario(
        @Email
        @NotNull
        String email,
        @NotNull
        String nombre,
        @NotNull
        String contrasena,
        @NotNull
        Set<String> roles
) {
}
