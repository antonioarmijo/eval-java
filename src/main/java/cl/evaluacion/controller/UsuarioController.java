package cl.evaluacion.controller;

import cl.evaluacion.entity.User;
import cl.evaluacion.exception.MensajeBody;
import cl.evaluacion.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class UsuarioController {

    private final Logger LOGGER = LogManager.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/usuario")
    public ResponseEntity<?> createUsuario(@RequestBody User user) {

        LOGGER.info("Inicio proceso creacion de usuario");
        try {
            return new ResponseEntity<>(usuarioService.createUsuario(user), HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity(new MensajeBody(e.getMessage()), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> usuarioFindById(@PathVariable("id") Long id) {
        LOGGER.info("Se realiza busqueda de usuario por ID " + id);
        try {
            Optional<?> opt = usuarioService.findById(id);
            if (!opt.isPresent()) {
                return new ResponseEntity(new MensajeBody("Usuario no encontrado"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(opt, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new MensajeBody(e.getCause().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/usuario")
    public ResponseEntity<?> getAllUsuarios() {
        LOGGER.info("Buscando todos los usuarios");

        return new ResponseEntity<>(usuarioService.getAllUsuarios(), HttpStatus.OK);
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable("id") Long id) {
        LOGGER.info("Inicio proceso eliminacion de usuario con ID " + id);
        try {
            return new ResponseEntity(new MensajeBody(usuarioService.deleteUsuario(id).toString()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new MensajeBody(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/usuario/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable("id") Long id, @RequestBody User usuario) {

        LOGGER.info("Inicio proceso de actualizacion de usuario con ID " + id);

        try {
            User user = usuarioService.updateUsuario(id, usuario);
            if (user == null) {
                LOGGER.info("No se a actualizado usuario " + id);
                return new ResponseEntity(new MensajeBody("Usuario no encontrado"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user,HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("No se a actualizado usuario, se ha producido el siguiente error " + e.getStackTrace());
            return new ResponseEntity(new MensajeBody(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}