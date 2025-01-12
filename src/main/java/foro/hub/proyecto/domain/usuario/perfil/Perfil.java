package foro.hub.proyecto.domain.usuario.perfil;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "perfil")
@Table(name = "pefiles")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Rol nombre;

    public Perfil(String s) {
        this.nombre = Rol.valueOf(s);
    }
}