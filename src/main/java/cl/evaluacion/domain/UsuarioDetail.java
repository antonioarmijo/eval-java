package cl.evaluacion.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class UsuarioDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombreUsuario;
    private Long id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime last_login;
    private String token;
    private Integer isactive;
}
