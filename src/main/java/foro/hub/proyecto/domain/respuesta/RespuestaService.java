package foro.hub.proyecto.domain.respuesta;

import foro.hub.proyecto.domain.ValidacionException;
import foro.hub.proyecto.domain.respuesta.Validations.ValidadorDeRegisroRespuesta;
import foro.hub.proyecto.domain.respuesta.data.ActualizarRespuesta;
import foro.hub.proyecto.domain.respuesta.data.DatosRespuesta;
import foro.hub.proyecto.domain.respuesta.data.RegistrarRespuesta;
import foro.hub.proyecto.domain.topico.Topico;
import foro.hub.proyecto.domain.topico.TopicoRepository;
import foro.hub.proyecto.domain.usuario.Usuario;
import foro.hub.proyecto.domain.usuario.UsuarioRepository;
import foro.hub.proyecto.domain.usuario.perfil.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;

    private final TopicoRepository topicoRepository;

    private final UsuarioRepository usuarioRepository;

    private final List<ValidadorDeRegisroRespuesta> validadorDeRegisroRespuesta;

    public RespuestaService(RespuestaRepository respuestaRepository, TopicoRepository topicoRepository, UsuarioRepository usuarioRepository, List<ValidadorDeRegisroRespuesta> validadorDeRegisroRespuesta) {
        this.respuestaRepository = respuestaRepository;
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.validadorDeRegisroRespuesta = validadorDeRegisroRespuesta;
    }

    public DatosRespuesta registrarRespuesta(RegistrarRespuesta datos) {
        if (!topicoRepository.existsById(datos.idTopico())) {
            throw new ValidacionException("El topico no existe");
        }

        System.out.println("Entrando aqui");

        //validaciones
        validadorDeRegisroRespuesta.forEach(v -> v.validar(datos));

        Topico topico = topicoRepository.getById(datos.idTopico());
        topico.aumentarRespuestas();
        var usuarioAutenticado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario autor = usuarioRepository.getReferenceById(usuarioAutenticado.getId());
        LocalDateTime fechaCreacion = LocalDateTime.now();
        Boolean status = false;

        Respuesta respuesta = new Respuesta(null, datos.mensaje(),topico, fechaCreacion, autor, status);

        respuestaRepository.save(respuesta);

        return new DatosRespuesta(respuesta);
    }

    public Page<DatosRespuesta> listarRespuestasPorTopico(Long id, Pageable page) {
        if (!topicoRepository.existsById(id)) {
            throw new ValidacionException("El topico no existe");
        }

        Page<Respuesta> respuesta = respuestaRepository.findByTopicoId(id, page);

        return respuesta.map(DatosRespuesta::new);
    }

    public DatosRespuesta modificarRespuesa(Long id, ActualizarRespuesta datos) {
        Optional<Respuesta> buscar = respuestaRepository.findById(id);
        if (buscar.isEmpty()) {
            throw new ValidacionException("El respuesta no existe");
        }
        Respuesta respuesta = buscar.get();
        if (datos.mensaje()!= null) respuesta.setMensaje(datos.mensaje());
        return new DatosRespuesta(respuesta);
    }

    public DatosRespuesta statusRespuesta(Long id) {
        Optional<Respuesta> buscar = respuestaRepository.findById(id);
        if (buscar.isEmpty()) {
            throw new ValidacionException("El respuesta no existe");
        }
        Respuesta respuesta = buscar.get();
        respuesta.setSolucion(true);
        Long topicoId = respuestaRepository.buscarTopicoPorId(id);
        Topico topico = topicoRepository.getReferenceById(topicoId);
        topico.setStatus(true);
        return new DatosRespuesta(respuesta);
    }

    public void eliminarRespuesta(Long id) {
        var usuarioAutenticado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!usuarioRepository.existsByIdAndRolesNombre(usuarioAutenticado.getId(), Rol.ADMINISTRADOR)){
            throw new ValidacionException("El usuario no tiene permisos para eliminar respuesta");
        }

        if (!respuestaRepository.existsById(id)) {
            throw new ValidacionException("El respuesta no existe");
        }
        respuestaRepository.deleteById(id);
    }

}
