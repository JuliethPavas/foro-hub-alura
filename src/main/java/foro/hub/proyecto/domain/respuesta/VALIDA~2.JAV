package foro.hub.proyecto.domain.respuesta.Validations;

import foro.hub.proyecto.domain.ValidacionException;
import foro.hub.proyecto.domain.respuesta.RespuestaRepository;
import foro.hub.proyecto.domain.respuesta.data.RegistrarRespuesta;
import org.springframework.stereotype.Component;

@Component("Validar si tiene mas de 2 respuestas en el mismo topico")
public class ValidarCantidadDeRespuestas implements ValidadorDeRegisroRespuesta{

    private RespuestaRepository respuestaRepository;

    public ValidarCantidadDeRespuestas(RespuestaRepository respuestaRepository) {
        this.respuestaRepository = respuestaRepository;
    }

    @Override
    public void validar(RegistrarRespuesta datos) {
        int cantidadDeRespuestas = respuestaRepository.cantidadDeRespuestasEnUnTopico(datos.idTopico());

        if (cantidadDeRespuestas > 2){
            throw new ValidacionException("No puedes registrar mas de dos respuestas en el mismo topico");
        }

    }
}
