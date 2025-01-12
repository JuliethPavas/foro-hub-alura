package foro.hub.proyecto.domain.curso.data;

import foro.hub.proyecto.domain.curso.Curso;

public record ListaNombreCurso(
        long id,
        String nombre) {

    public ListaNombreCurso (Curso curso){
        this(curso.getId(), curso.getNombre());
    }
}
