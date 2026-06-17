-- BD: rua

CREATE TABLE `alumno_asignatura` (
  `id_alumno` bigint NOT NULL,
  `id_asignatura` bigint NOT NULL,
  PRIMARY KEY (`id_alumno`,`id_asignatura`),
  KEY `id_asignatura` (`id_asignatura`),
  CONSTRAINT `alumno_asignatura_ibfk_1` FOREIGN KEY (`id_alumno`) REFERENCES `alumnos` (`id_alumno`) ON DELETE CASCADE,
  CONSTRAINT `alumno_asignatura_ibfk_2` FOREIGN KEY (`id_asignatura`) REFERENCES `asignaturas` (`id_asignatura`) ON DELETE CASCADE
);

CREATE TABLE `alumnos` (
  `id_alumno` bigint NOT NULL AUTO_INCREMENT,
  `id_usuario` bigint NOT NULL,
  PRIMARY KEY (`id_alumno`),
  UNIQUE KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `alumnos_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE
);

CREATE TABLE `asignaturas` (
  `id_asignatura` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `codigo` varchar(50) NOT NULL,
  `estado` enum('ACTIVA','INACTIVA') DEFAULT 'ACTIVA',
  `id_docente` bigint NOT NULL,
  PRIMARY KEY (`id_asignatura`),
  UNIQUE KEY `codigo` (`codigo`),
  KEY `id_docente` (`id_docente`),
  CONSTRAINT `asignaturas_ibfk_1` FOREIGN KEY (`id_docente`) REFERENCES `docentes` (`id_docente`)
);


CREATE TABLE `asistencias` (
  `id_asistencia` bigint NOT NULL AUTO_INCREMENT,
  `id_alumno` bigint NOT NULL,
  `id_bloque` bigint NOT NULL,
  `fecha_hora_llegada` datetime NOT NULL,
  PRIMARY KEY (`id_asistencia`),
  UNIQUE KEY `id_alumno` (`id_alumno`,`id_bloque`),
  KEY `idx_asistencia_alumno` (`id_alumno`),
  KEY `idx_asistencia_bloque` (`id_bloque`),
  CONSTRAINT `asistencias_ibfk_1` FOREIGN KEY (`id_alumno`) REFERENCES `alumnos` (`id_alumno`) ON DELETE CASCADE,
  CONSTRAINT `asistencias_ibfk_2` FOREIGN KEY (`id_bloque`) REFERENCES `bloques_horario` (`id_bloque`) ON DELETE CASCADE
);



CREATE TABLE `bloques_horario` (
  `id_bloque` bigint NOT NULL AUTO_INCREMENT,
  `id_asignatura` bigint NOT NULL,
  `dia_semana` enum('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY') NOT NULL,
  `hora_inicio` time NOT NULL,
  `hora_fin` time NOT NULL,
  PRIMARY KEY (`id_bloque`),
  KEY `id_asignatura` (`id_asignatura`),
  CONSTRAINT `bloques_horario_ibfk_1` FOREIGN KEY (`id_asignatura`) REFERENCES `asignaturas` (`id_asignatura`) ON DELETE CASCADE
); 


CREATE TABLE `docentes` (
  `id_docente` bigint NOT NULL AUTO_INCREMENT,
  `id_usuario` bigint NOT NULL,
  PRIMARY KEY (`id_docente`),
  UNIQUE KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `docentes_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE
);


CREATE TABLE `qr` (
  `id_qr` bigint NOT NULL AUTO_INCREMENT,
  `token` varchar(255) NOT NULL,
  `fecha_generacion` datetime NOT NULL,
  `fecha_expiracion` datetime NOT NULL,
  `estado` enum('ACTIVO','EXPIRADO') DEFAULT 'ACTIVO',
  `id_bloque` bigint NOT NULL,
  PRIMARY KEY (`id_qr`),
  UNIQUE KEY `token` (`token`),
  KEY `id_bloque` (`id_bloque`),
  KEY `idx_qr_token` (`token`),
  CONSTRAINT `qr_ibfk_1` FOREIGN KEY (`id_bloque`) REFERENCES `bloques_horario` (`id_bloque`) ON DELETE CASCADE
); 

CREATE TABLE `usuarios` (
  `id_usuario` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `apellido1` varchar(255) DEFAULT NULL,
  `apellido2` varchar(255) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `contrasena` varchar(255) NOT NULL,
  `correo_verificado` varchar(255) DEFAULT NULL,
  `token_verificacion` varchar(255) DEFAULT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `token_confirmation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `correo` (`correo`),
  KEY `idx_usuario_correo` (`correo`)
);