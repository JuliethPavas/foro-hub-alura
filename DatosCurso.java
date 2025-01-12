package foro.hub.proyecto.domain.curso.data;

import foro.hub.proyecto.domain.curso.Categoria;
import foro.hub.proyecto.domain.curso.Curso;

public record DatosCurso(
        Long id,
        String nombre,
        Categoria categoria) {

    public DatosCurso (Curso curso){
        this(curso.getId(), curso.getNombre(),curso.getCategoria());
    }
}
