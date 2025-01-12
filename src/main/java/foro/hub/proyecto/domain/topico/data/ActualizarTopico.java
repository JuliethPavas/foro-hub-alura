package foro.hub.proyecto.domain.topico.data;

import jakarta.validation.constraints.NotNull;

public record ActualizarTopico(
        String titulo,
        String mensaje) {
}
