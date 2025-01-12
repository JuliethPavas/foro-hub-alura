package foro.hub.proyecto.domain.topico.data;

import foro.hub.proyecto.domain.curso.data.DatosCurso;
import foro.hub.proyecto.domain.usuario.Usuario;
import jakarta.validation.constraints.NotNull;

public record RegistroTopico(
        @NotNull
        String titulo,
        @NotNull
        String mensaje,
        @NotNull
        Long idCurso) {
}
