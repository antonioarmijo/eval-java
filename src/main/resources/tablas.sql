CREATE TABLE IF NOT EXISTS USUARIOS
(
    id_usuario BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created DATE,
    modified DATE,
    last_login DATE ,
    token VARCHAR(255),
    isactive INT(1),
    PRIMARY KEY (id_usuario)
    );

CREATE TABLE IF NOT EXISTS TELEFONOS
(
    id_telefono BIGINT NOT NULL AUTO_INCREMENT,
    number VARCHAR(11),
    city_code VARCHAR(25),
    country_code VARCHAR(25),
    ID_USUARIO BIGINT NOT NULL,
    PRIMARY KEY(id_telefono),
    FOREIGN KEY (ID_USUARIO) REFERENCES USUARIOS
);