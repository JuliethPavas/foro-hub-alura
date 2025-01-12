package foro.hub.proyecto.domain.curso;

import foro.hub.proyecto.domain.ValidacionException;
import foro.hub.proyecto.domain.curso.data.*;
import foro.hub.proyecto.domain.usuario.Usuario;
import foro.hub.proyecto.domain.usuario.UsuarioRepository;
import foro.hub.proyecto.domain.usuario.perfil.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class CursoService {

    private CursoRepository cursoRepository;

    private UsuarioRepository usuarioRepository;

    public final void verificarRol() {
        var usuarioAutenticado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!usuarioRepository.existsByIdAndRolesNombre(usuarioAutenticado.getId(), Rol.ADMINISTRADOR)){
            throw new ValidacionException("El usuario no tiene permisos para registrar");
        }
    }

    public final DatosCurso crearCurso(RegistrarCurso datos){
        verificarRol();
        Curso curso = new Curso(datos);
        cursoRepository.save(curso);
        return new DatosCurso(curso);
    }

    public final DatosCurso obtenerCurso(Long id){
        if(!cursoRepository.existsById(id)){
            throw new ValidacionException("El curso no existe");
        }
        return cursoRepository.findById(id).map(DatosCurso::new).get();
    }

    public final Page<ListasCurso> listaCursos(Pageable pageable){
        Page<Curso> cursos = cursoRepository.findAll(pageable);
        return cursos.map(ListasCurso::new);
    }

    public final Page<ListaNombreCurso> listarCursosPorCategoria(Pageable pageable, Categoria categoria){
        Page<Curso> cursos = cursoRepository.findByCategoria(categoria, pageable);
        return cursos.map(ListaNombreCurso::new);
    }

    public final DatosCurso actualizarCurso(Long id, ActualizarCurso actualizarCurso){
        verificarRol();

        if(!cursoRepository.existsById(id)){
            throw new ValidacionException("El curso no existe");
        }
        Curso curso = cursoRepository.getReferenceById(id);
        curso.actualizar(actualizarCurso);
        return new DatosCurso(curso);
    }
}
