package cl.evaluacion.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Getter @Setter
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "TELEFONOS")
public class Phone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TELEFONO")
    private Long id;
    @Column(name = "NUMBER")
    private String number;
    @Column(name = "CITY_CODE")
    private String cityCode;
    @Column(name = "COUNTRY_CODE")
    private String countryCode;
    @Column(name = "ID_USUARIO")
    @JsonIgnore
    private Long id_usuario;

}


