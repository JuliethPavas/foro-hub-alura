package foro.hub.proyecto.domain.respuesta.Validations;

import foro.hub.proyecto.domain.ValidacionException;
import foro.hub.proyecto.domain.respuesta.data.RegistrarRespuesta;
import foro.hub.proyecto.domain.topico.TopicoRepository;
import org.springframework.stereotype.Component;

@Component("Validar si se esta registrando en un topico con status false")
public class ValidarRespuestaRegistrarElTopicoIsFalse implements ValidadorDeRegisroRespuesta {

    private final TopicoRepository topicoRepository;

    public ValidarRespuestaRegistrarElTopicoIsFalse(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    @Override
    public final void validar(RegistrarRespuesta datos){
        if (topicoRepository.retornarStatusTopico(datos.idTopico())){
            throw new ValidacionException("El topico ya se marco como resuelto no se puede registrar mas respuestas");
        }
    }
}
