package foro.hub.proyecto.controller;

import foro.hub.proyecto.domain.respuesta.RespuestaService;
import foro.hub.proyecto.domain.respuesta.data.ActualizarRespuesta;
import foro.hub.proyecto.domain.respuesta.data.DatosRespuesta;
import foro.hub.proyecto.domain.respuesta.data.RegistrarRespuesta;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    private final RespuestaService respuestaService;

    public RespuestaController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    //Agregar respuesta
    @PostMapping
    public ResponseEntity<DatosRespuesta> RegistarRespuesta(@RequestBody @Valid RegistrarRespuesta registrarRespuesta, UriComponentsBuilder builder) {
        DatosRespuesta respuesta = respuestaService.registrarRespuesta(registrarRespuesta);
        URI uri = builder.path("/respuestas/{idAdmin}").buildAndExpand(respuesta.id()).toUri();
        return ResponseEntity.created(uri).body(respuesta);
    }

    //Listar respuestas por topico
    @GetMapping("/todos/{id}")
    public ResponseEntity<Page<DatosRespuesta>> obtenerRespuestaPorTopico(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(respuestaService.listarRespuestasPorTopico(id, pageable));
    }

    //Modificar respuesta
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DatosRespuesta> modificarRespuesta(@RequestBody @Valid ActualizarRespuesta mensaje, @PathVariable Long id){
        return ResponseEntity.ok(respuestaService.modificarRespuesa(id, mensaje));
    }

    @PutMapping("/solucion/{id}")
    @Transactional
    public ResponseEntity<DatosRespuesta> statusRespuesta(@PathVariable Long id){
        return ResponseEntity.ok(respuestaService.statusRespuesta(id));
    }

    //Eliminar respuesta
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id){
        respuestaService.eliminarRespuesta(id);
        return ResponseEntity.noContent().build();
    }

}
