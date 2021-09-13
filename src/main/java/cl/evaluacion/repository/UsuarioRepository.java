package cl.evaluacion.repository;

import cl.evaluacion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
