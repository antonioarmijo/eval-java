package cl.evaluacion.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Validador {
    private Logger logger = LogManager.getLogger(Validador.class);

    public boolean validadorEmail(String email) throws Exception{
        logger.info("Se inicia validacion de correo "+email);
        try{
            Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }catch (Exception e){
            logger.error(e);
            throw new Exception("Error inesperado nos se ha podido validar correo");
        }
    }

    public boolean validadorFormatoClave(String password) throws Exception{
        logger.info("Se inicia validacion de password");
        try{
            Pattern pattern = Pattern.compile("^[A-Z][a-z]+\\d{2}");
            Matcher matcher = pattern.matcher(password);
            return matcher.matches();
        }catch (Exception e){
            logger.error(e);
            throw new Exception("Error inesperado no se ha podido validar password");
        }
    }


}
