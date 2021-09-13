package cl.evaluacion.service.imp;

import cl.evaluacion.domain.UsuarioDetail;
import cl.evaluacion.entity.User;
import cl.evaluacion.repository.UsuarioRepository;
import cl.evaluacion.service.UsuarioService;
import cl.evaluacion.utility.Validador;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImp implements UsuarioService {

    private static final Logger LOGGER = LogManager.getLogger(UsuarioServiceImp.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Validador validador;

    @Override
    public UsuarioDetail createUsuario(User user) throws Exception {
        try {
            validacionesCreacionUsuario(user);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        LOGGER.info("Se inicia el guardado de usuario " + user.getName());
        User resp;
        try {
            user.setIsactive(1);
            user.setCreated(LocalDateTime.now());
            user.setLast_login(LocalDateTime.now());
            user.setToken(generaToken(user));
            resp = usuarioRepository.save(user);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return UsuarioDetail.builder()
                .nombreUsuario(resp.getName())
                .id(resp.getId())
                .created(resp.getCreated())
                .modified(resp.getModified())
                .last_login(resp.getLast_login())
                .token(resp.getToken())
                .isactive(resp.getIsactive())
                .build();
    }

    public void validacionesCreacionUsuario(User user) throws Exception{

        if(user == null || user.getName() == null){
            LOGGER.info("Body ingresado no cumple con formato de esperado");
            throw new Exception("Body ingresado no cumple con formato esperado");
        }
        if(!validador.validadorEmail(user.getEmail())){
            throw new Exception("Correo "+user.getEmail()+" no cumple con el formato estandar Ej: Test22@dominio.cl");
        }
        if(!validador.validadorFormatoClave(user.getPassword())){
            throw new Exception("password "+user.getPassword()+" no cumple con el formato. " +
                    "Una Mayuscula, letras minúsculas, y dos numeros");
        }

        validacionEmailDuplicado(user);
    }
    public void validacionEmailDuplicado(User user) throws Exception{
        try {
            User checkEmail = usuarioRepository.findByEmail(user.getEmail());
            if(checkEmail != null ){
                LOGGER.info("email "+checkEmail.getEmail()+" ya se encuentra registrado en el sistema");
                throw new Exception("El correo ya registrado");
            }
        }catch (Exception e){
            LOGGER.error("Ha ocurrido un error: "+e.getStackTrace());
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public Optional<User> findById(Long id) {
        return usuarioRepository.findById(id);
    }


    @Override
    public List<User> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Boolean deleteUsuario(Long id) throws Exception {
            Optional <User> optUser = usuarioRepository.findById(id);
            if(!optUser.isPresent()) {
                throw new Exception("Eliminacion no realizada, no se ha encontrado usuario con id " + id);
            }
            optUser.get().setIsactive(0);
            optUser.get().setModified(LocalDateTime.now());
            try {
                usuarioRepository.save(optUser.get());
                return true;
        }catch (Exception e){
            LOGGER.error(e);
            throw new Exception("Eliminacion no realizada, se ha producido el siguiente error " + e.getMessage());
        }
    }

    @Override
    public User updateUsuario(Long id, User user) throws Exception{
        LOGGER.info("Se inicia la actualizacion de usuario " + user.getName());
        Optional <User> bdUser = usuarioRepository.findById(id);

        if(!bdUser.isPresent()){
            throw new Exception("Actualización no realizada, no se ha encontrado usuario con id "+id);
        }
        try {
            validacionEmailDuplicado(user);
            user.setId(id);
            user.setPassword(bdUser.get().getPassword());
            user.setCreated(bdUser.get().getCreated());
            user.setModified(LocalDateTime.now());
            user.setLast_login(bdUser.get().getLast_login());
            user.setToken(generaToken(user));
            return usuarioRepository.save(user);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    private String generaToken(User user){
        Claims claims = Jwts.claims().setSubject(user.getName());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getName())
                .signWith(SignatureAlgorithm.HS512,user.getPassword().getBytes())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer(user.getName())
                .setExpiration(new Date(System.currentTimeMillis()+60*1000))
                .compact();
    }
}
