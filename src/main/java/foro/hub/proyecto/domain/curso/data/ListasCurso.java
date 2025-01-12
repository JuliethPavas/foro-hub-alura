package foro.hub.proyecto.domain.curso.data;

import foro.hub.proyecto.domain.curso.Categoria;
import foro.hub.proyecto.domain.curso.Curso;

public record ListasCurso(
        long id,
        String nombre,
        Categoria categoria) {

    public ListasCurso(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria());
    }
}
