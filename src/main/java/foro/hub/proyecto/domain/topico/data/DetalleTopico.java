package foro.hub.proyecto.domain.topico.data;

import foro.hub.proyecto.domain.curso.data.DatosCurso;
import foro.hub.proyecto.domain.topico.Topico;

import java.time.LocalDateTime;

public record DetalleTopico(
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Boolean status,
        String autor,
        DatosCurso curso,
        Integer respuestas) {

    public DetalleTopico(Topico topico) {
        this(topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus(),
                topico.getAutor().getNombre(),
                new DatosCurso(topico.getId(),topico.getCurso().getNombre(), topico.getCurso().getCategoria()),
                topico.getRespuestas());
    }
}
