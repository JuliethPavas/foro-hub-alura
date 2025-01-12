package foro.hub.proyecto.controller;

import foro.hub.proyecto.domain.curso.Categoria;
import foro.hub.proyecto.domain.curso.CursoService;
import foro.hub.proyecto.domain.curso.data.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    //Crear Curso
    @PostMapping
    public ResponseEntity<DatosCurso> crearCurso(@RequestBody @Valid RegistrarCurso registrarCurso, UriComponentsBuilder builder){
        DatosCurso curso = cursoService.crearCurso(registrarCurso);
        URI uri = builder.path("/cursos/{id}").buildAndExpand(curso.id()).toUri();
        return ResponseEntity.created(uri).body(curso);
    }

    //Curso Por id
    @GetMapping("{id}")
    public ResponseEntity<DatosCurso> buscarCurso(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.obtenerCurso(id));
    }

    //Listar Cursos
    @GetMapping
    public ResponseEntity<Page<ListasCurso>> listaCursos(@PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(cursoService.listaCursos(pageable));
    }

    //Listar Cursos Por Categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<Page<ListaNombreCurso>> listaCursosPorCategoria(@PageableDefault(size = 5) Pageable pageable,
                                                                           @PathVariable Categoria categoria) {
        return ResponseEntity.ok(cursoService.listarCursosPorCategoria(pageable, categoria));
    }

    //Actualizar Curso
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarCurso(@PathVariable Long id, @RequestBody @Valid ActualizarCurso actualizarCurso) {
        return ResponseEntity.ok(cursoService.actualizarCurso(id, actualizarCurso));
    }
}
