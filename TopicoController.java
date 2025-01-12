package foro.hub.proyecto.controller;

import foro.hub.proyecto.domain.topico.data.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import foro.hub.proyecto.domain.topico.TopicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    private final TopicoService topicoService;

    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    //Registro Topico
    @PostMapping
    public ResponseEntity<RespuestaTopico> registroTopico(@RequestBody @Valid RegistroTopico datos, UriComponentsBuilder uriBuilder) {
        RespuestaTopico topico = topicoService.registro(datos);
        URI uri = uriBuilder.path("/topicos/{idAdmin}").buildAndExpand(topico.id()).toUri();
        return ResponseEntity.created(uri).body(topico);
    }

    //Listar Topicos
    @GetMapping
    public ResponseEntity<Page<DatosTopico>> listarTopico(@PageableDefault(size = 5,
            sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(topicoService.listadoTopico(paginacion));
    }

    //Listar Topicos por Curso
    @GetMapping("/curso/{nombreCurso}")
    public ResponseEntity<Page<DatosTopico>> listarTopicosPorCurso(@PageableDefault(size = 5,sort = "fechaCreacion", direction = Sort.Direction.ASC)
                                                                         Pageable paginacion,@PathVariable String nombreCurso){
        return ResponseEntity.ok(topicoService.listadoTopicosPorCurso(nombreCurso, paginacion));
    }

    //Detalles Topico
    @GetMapping("/{id}")
    public ResponseEntity<DetalleTopico> detallesTopico(@PathVariable Long id){
        return ResponseEntity.ok(topicoService.detalleTopico(id));
    }


    //Actualizar Topico
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DatosTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid ActualizarTopico datos){
        return ResponseEntity.ok(topicoService.actualizar(id,datos));
    }

    //Eliminar Topico
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }

}
