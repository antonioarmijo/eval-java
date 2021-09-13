package cl.evaluacion.service;

import cl.evaluacion.domain.UsuarioDetail;
import cl.evaluacion.entity.User;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    UsuarioDetail createUsuario(User user) throws Exception;

    Optional<User> findById(Long id);

    List<User> getAllUsuarios();

    Boolean deleteUsuario(Long id) throws Exception;

    User updateUsuario(Long id, User user) throws Exception;
}
