-- Tabla de Entrenadores
CREATE TABLE entrenador (
    idEntrenador INT AUTO_INCREMENT PRIMARY KEY,
    nombreEntrenador VARCHAR(100) NOT NULL,
    telefonoEntrenador VARCHAR(20),
    DNI_entrenador VARCHAR(15) UNIQUE,
    activo TINYINT(1) DEFAULT 1
);

-- Tabla de Clientes
CREATE TABLE cliente (
    idCliente INT AUTO_INCREMENT PRIMARY KEY,
    DNI_cliente VARCHAR(15) UNIQUE,
    nombreCliente VARCHAR(100) NOT NULL,
    telefonoCliente VARCHAR(20),
    correoElectronico VARCHAR(100),
    activo TINYINT(1) DEFAULT 1
);

-- Tabla de Salas
CREATE TABLE sala (
    idSala INT AUTO_INCREMENT PRIMARY KEY,
    nombreSala VARCHAR(100) NOT NULL,
    aforo INT NOT NULL,
    activo TINYINT(1) DEFAULT 1
);

-- Tabla de Sesiones
CREATE TABLE sesion (
    idSesion INT AUTO_INCREMENT PRIMARY KEY,
    nombreSesion VARCHAR(100),
    objetivo VARCHAR(100),
    duracion INT,
    horario VARCHAR(50),
    idSala INT NOT NULL,
    idEntrenador INT NOT NULL,
    activo TINYINT(1) DEFAULT 1,
    FOREIGN KEY (idSala) REFERENCES sala(idSala),
    FOREIGN KEY (idEntrenador) REFERENCES entrenador(idEntrenador)
);

-- Tabla de Inscripciones (Cliente-Sesión)
CREATE TABLE apunta (
    idClienteSesion INT AUTO_INCREMENT PRIMARY KEY,
    idCliente INT NOT NULL,
    idSesion INT NOT NULL,
    fecha DATE,
    hora TIME,
    activo TINYINT(1) DEFAULT 1,
    FOREIGN KEY (idCliente) REFERENCES cliente(idCliente),
    FOREIGN KEY (idSesion) REFERENCES sesion(idSesion)
);
