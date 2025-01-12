package foro.hub.proyecto.controller;


import foro.hub.proyecto.domain.usuario.Usuario;
import foro.hub.proyecto.domain.usuario.data.DatosAutenticacion;
import foro.hub.proyecto.infra.security.DatosJWTToken;
import foro.hub.proyecto.infra.security.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticación", description = "Operaciones de login")
public class AutenticationController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public AutenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public final ResponseEntity<DatosJWTToken> autenticarUsuario(@RequestBody @Valid DatosAutenticacion datosAutenticacionUsuario){
        Authentication authcatoken = new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuario.nombre(), datosAutenticacionUsuario.contrasena());
        var usuarioAutenticado = authenticationManager.authenticate(authcatoken);
        var JWtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWtoken));
    }

}
