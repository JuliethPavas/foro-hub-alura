package foro.hub.proyecto.domain.curso.data;

import foro.hub.proyecto.domain.curso.Categoria;
import jakarta.validation.constraints.NotNull;

public record ActualizarCurso(
        String nombre,
        Categoria categoria) {
}
