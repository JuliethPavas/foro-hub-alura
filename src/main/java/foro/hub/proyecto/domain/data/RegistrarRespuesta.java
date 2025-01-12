package foro.hub.proyecto.domain.respuesta.data;

import jakarta.validation.constraints.NotNull;

public record RegistrarRespuesta(
        @NotNull
        String mensaje,
        @NotNull
        Long idTopico) {
}
