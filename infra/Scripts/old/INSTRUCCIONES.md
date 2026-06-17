# IMPORTANTE

Lee este archivo completo antes de analizar cualquier otro archivo del proyecto.

No realices modificaciones hasta haber entendido:
1. El objetivo.
2. Las restricciones.
3. El alcance de la tarea.

# Refactorización del Backend para Adaptación a la Nueva Base de Datos

## Objetivo

El proyecto "RUA" (Registro Universitario de Asistencia) ha migrado desde una estructura de base de datos MySQL antigua (`old_BD`) hacia una nueva versión (`new_BD`), diseñada para mejorar la escalabilidad, mantenibilidad y eficiencia del sistema.

Tu tarea consiste exclusivamente en adaptar el código actual del backend desarrollado en Java Spring Boot para que funcione correctamente con la nueva estructura de la base de datos.

## Alcance

Debes:

* Analizar la estructura de `old_BD` y `new_BD`.
* Identificar las diferencias entre ambas.
* Modificar únicamente las partes necesarias del backend para reflejar los cambios en la nueva base de datos.
* Corregir entidades, relaciones, consultas, repositorios, servicios y controladores afectados por los cambios.
* Mantener el comportamiento funcional actual del sistema siempre que sea posible.
* Preservar la arquitectura existente del proyecto.

## Restricciones

### NO debes:

* Rediseñar la arquitectura del proyecto.
* Introducir DTOs, Mappers, Specifications, CQRS, Clean Architecture u otros patrones que actualmente no existan en el código.
* Cambiar endpoints existentes salvo que la nueva estructura de la base de datos lo haga estrictamente necesario.
* Realizar refactorizaciones ajenas al objetivo principal.
* Modificar lógica de negocio que no esté relacionada con la migración de la base de datos.

### SÍ puedes:

* Crear nuevas clases cuando sea estrictamente necesario.
* Crear nuevos métodos auxiliares para mejorar la legibilidad.
* Dividir lógica excesivamente compleja en funciones más pequeñas.
* Añadir comentarios breves cuando aporten claridad.

Prioriza siempre la compatibilidad con el código existente.

## Entregable

Debes entregar el resultado como una estructura de proyecto lista para copiar y reemplazar dentro del repositorio existente.

Para cada archivo modificado:

1. Indica la ruta completa.
2. Entrega el contenido completo del archivo.
3. Explica brevemente qué cambios realizaste y por qué fueron necesarios.

Si un archivo no requiere modificaciones, indícalo explícitamente.

## Estructura actual relevante

```text
C:.
│   RuaApplication.java
│   RuaConfig.java
├───Controllers
│       MainPageController.java
├───Entities_Classes
│       User.java
├───General
│       EmailDesing.java
├───Repositories
│       UserRepo.java
└───Session
    ├───log_in
    │       LoginController.java
    │       LoginService.java
    └───sign_up
            SignupController.java
            SignupService.java
```

## Archivos prioritarios

```text
Repositories/UserRepo.java

Entities_Classes/User.java

Session/log_in/LoginController.java
Session/log_in/LoginService.java

Session/sign_up/SignupController.java
Session/sign_up/SignupService.java
```

## Archivos proporcionados

* `old_BD` → Estructura antigua de la base de datos.
* `new_BD` → Nueva estructura de la base de datos.
* Código fuente actual del backend.

## Procedimiento esperado

1. Analiza completamente `new_BD`.
2. Compara contra `old_BD`.
3. Identifica entidades, atributos y relaciones modificadas.
4. Localiza el código afectado.
5. Aplica únicamente los cambios necesarios.
6. Verifica que las consultas JPA y relaciones entre entidades sean coherentes con la nueva estructura.
7. Entrega el código actualizado junto con una explicación resumida de cada modificación.



blockId : 2
blockState : "NO_PROJECTIONS"
calendarId : 1
code : "ICC705"

weekDay : "TUE"
startHour : "10:00:00"
endHour : "11:30:00"

moduleId : 2
moduleNum : 2
subjectId : 1
subjectName : "Bases de Datos"
