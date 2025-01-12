package foro.hub.proyecto.domain.respuesta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    Page<Respuesta> findByTopicoId(Long id, Pageable page);

    @Query("""
            SELECT r.topico.id 
            FROM Respuesta r
            WHERE r.id = :id
            """)
    Long buscarTopicoPorId(Long id);

    @Query("""
            select count(r.id)
            from Respuesta r
            where r.topico.id = :id
            """)
    int cantidadDeRespuestasEnUnTopico(Long id);
}
