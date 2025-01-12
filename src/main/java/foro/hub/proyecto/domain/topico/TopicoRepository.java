package foro.hub.proyecto.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;


public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findAllByCursoNombre(String cursoNombre, Pageable pageable);

    boolean existsTopicoByTituloAndMensaje(String titulo, String mensaje);

    @Query("""
            select t.status
            from Topico t
            where t.id = :id  
            """)
    boolean retornarStatusTopico (Long id);
}
