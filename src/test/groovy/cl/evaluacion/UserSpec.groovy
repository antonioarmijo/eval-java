package cl.evaluacion

import cl.evaluacion.controller.UsuarioController
import cl.evaluacion.domain.UsuarioDetail
import cl.evaluacion.entity.User
import cl.evaluacion.service.UsuarioService
import cl.evaluacion.utility.Validador
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import java.time.LocalDateTime

class UserSpec extends Specification {

    UsuarioService usuarioService
    UsuarioController usuarioController

    void setup(){
        this.usuarioService = Mock(UsuarioService.class)
        this.usuarioController = new UsuarioController(usuarioService)
    }

    def "Test_registra_usuario_exitoso"(){
        given: "crea un usuario"
        User usuario = User.builder()
                .name("pepito")
                .email("pepito@pepe.cl")
                .password("Pepe12")
                .isactive(1)
                .build();
        this.usuarioService.createUsuario(usuario)
        when: "crea usuario nuevo"
        ResponseEntity respuesta = this.usuarioController.createUsuario(usuario)
        then: "Resultado existo"
        respuesta.statusCode == HttpStatus.CREATED;
    }

    def "Test_registra_usuario_sin_datos"(){
        given: "crea un usuario"
        this.usuarioService.createUsuario(null)>> { throw new Exception("Sin cuerpo")}
        when: "crea usuario nuevo"
        ResponseEntity respuesta = this.usuarioController.createUsuario(null)
        then: "Resultado existo"
        respuesta.statusCode == HttpStatus.EXPECTATION_FAILED;
    }

    def "Test_actualiza_usuario_noEncontrado"(){
        given: "actualizar usuario"
        User usuario = User.builder()
                .id(1)
                .name("pepito")
                .email("pepito@pepe.cl")
                .password("Pepe12")
                .isactive(1)
                .build();
        when: "actualizar usuario"
        ResponseEntity respuesta = this.usuarioController.updateUsuario(usuario.getId(),usuario)
        then:"Resultado existo"
        respuesta.statusCode == HttpStatus.NOT_FOUND;
    }



    def "Test_UsuarioDetail_Ok"(){
        given: "crea un usuario"
        User usuario = User.builder()
                .name("pepito")
                .email("pepito@pepe.cl")
                .password("Pepe12")
                .isactive(1)
                .build();
        this.usuarioService.createUsuario(usuario) >> { UsuarioDetail.builder()
                .nombreUsuario(usuario.getName())
                .isactive(1)
                .id(1)
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .last_login(LocalDateTime.now())
                .token("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBbnRvbmlvIEVkdWFyZG8iLCJpYXQiOjE2MzE1MDc1MTQsImlzcyI6IkFudG9uaW8gRWR1YXJkbyIsImV4cCI6MTYzMTUwNzU3NH0.8y8h04IrUQNQO4ZTn4ZYIkj2CIE_9tnFU0Kpux10LjstIGMx9Tjz2LO_hXQmyvf0L69JamDB5ByC8pKdSOC4ZQ")
                .build()}
        when: "crea usuario nuevo"
        ResponseEntity respuesta = this.usuarioController.createUsuario(usuario)
        then: "Resultado existo"
        //respuesta.body.getProperties().get("nombreUsuario") == usuario.getName()
        respuesta.body.class == UsuarioDetail

    }

    def "Test_validador_Email_ok"(){
        given: "email correcto"
        String email = "antonio@gmail.com"
        when: "valida correo"
        Validador validador = new Validador();
        Boolean respuesta = validador.validadorEmail(email);
        then: "retorno ok"
        respuesta == true;
    }

    def "Test_validador_Email_notOk"(){
        given: "email incorrecto"
        String email = "@gmail.com"
        when: "valida correo"
        Validador validador = new Validador()
        Boolean respuesta = validador.validadorEmail(email)
        then: "retorno ok"
        respuesta == false;
    }

    def "Test_validador_FormatoClave_ok"(){
        given: "contraseña correcta"
        String password = "Passwor12"
        when: "valida formato clave"
        Validador validador = new Validador()
        Boolean respuesta = validador.validadorFormatoClave(password)
        then: "retorno ok"
        respuesta == true
    }

    def "Test_validador_FormatoClave_NotOk"(){
        given: "contraseña incorrecta"
        String password = "#PPPassw222or12"
        when: "valida formato clave"
        Validador validador = new Validador()
        Boolean respuesta = validador.validadorFormatoClave(password)
        then: "retorno ok"
        respuesta == false
    }
}