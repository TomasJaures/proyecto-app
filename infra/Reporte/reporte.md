# Informe de Refactorización — Backend RUA

> **Proyecto:** RUA (Registro Universitario de Asistencia)
> **Alcance:** Adaptación del backend Spring Boot a la nueva estructura de base de datos (`new_BD`)
> **Fecha:** Junio 2026

---

## 1. Contexto

El proyecto RUA migró su base de datos MySQL desde una estructura monolítica (`old_BD`) hacia un diseño más modular y escalable (`new_BD`). El objetivo de la refactorización fue exclusivamente adaptar el código Java Spring Boot existente para que funcione con la nueva estructura, sin alterar la arquitectura ni la lógica de negocio que no estuviera directamente afectada por la migración.

---

## 2. Diferencias entre old_BD y new_BD

Antes de tocar el código, se identificaron los siguientes cambios estructurales en la base de datos:

### 2.1 Separación del flujo de registro

En `old_BD`, la tabla `usuarios` acumulaba tanto usuarios confirmados como no confirmados. La verificación de correo se gestionaba mediante dos columnas dentro de esa misma tabla: `correo_verificado` (VARCHAR con valor `"true"`) y `token_confirmation`.

En `new_BD`, este flujo fue dividido en dos tablas independientes:

- `unconfirmed_user` — almacena temporalmente a los usuarios que aún no han verificado su correo, junto con campos de control de intentos y bloqueos.
- `confirmation_token` — tabla separada que almacena el token de verificación con fechas de creación y expiración, relacionada 1:1 con `unconfirmed_user`.
- `users` — solo contiene usuarios completamente verificados y activos.

### 2.2 Renombrado general de columnas y tablas

Todos los nombres fueron migrados del español al inglés:

| old_BD (`usuarios`) | new_BD (`users`) |
|---|---|
| `id_usuario` | `user_id` |
| `nombre` | `user_name` |
| `apellido1` | `last_name1` |
| `apellido2` | `last_name2` |
| `correo` | `mail` |
| `contrasena` | `hashed_password` |
| `correo_verificado` | (eliminado) |
| `token_confirmation` | (movido a `confirmation_token`) |

### 2.3 Nuevos campos en `users`

Se añadieron `user_role` (rol del usuario) y claves foráneas a `program` y `calendar`, que en `old_BD` no existían en la tabla de usuarios.

### 2.4 Cambio de tipo en la clave primaria

`old_BD` usaba `BIGINT` para los IDs. `new_BD` usa `INT`.

---

## 3. Cambios realizados en el código

### 3.1 `Entities_Classes/User.java` — Modificado

Se reescribió completamente el mapeo JPA:

- La anotación `@Table` apunta ahora a `users` en lugar de `usuarios`.
- Se renombraron todos los atributos para reflejar los nuevos nombres de columna, añadiendo `@Column(name = "...")` explícito en cada uno.
- Se eliminaron `correo_verificado` y `tokenConfirmation` (ya no existen en `users`).
- Se añadieron `userRole`, `programId` y `calendarId`.
- El tipo de la PK cambió de `Long` a `Integer` para coincidir con `INT` en la BD.

### 3.2 `Entities_Classes/UnconfirmedUser.java` — Nuevo

Entidad creada para mapear la nueva tabla `unconfirmed_user`. Contiene los datos básicos del usuario en registro más los campos de control: `attempt`, `isBlocked` y `blockTime`.

### 3.3 `Entities_Classes/ConfirmationToken.java` — Nuevo

Entidad para la tabla `confirmation_token`. Incluye `content` (el UUID del token), `createdAt`, `expireAt`, y una relación `@OneToOne` hacia `UnconfirmedUser` mediante `@JoinColumn`.

### 3.4 `Repositories/UserRepo.java` — Modificado

Se eliminaron los métodos `findByTokenConfirmation` y `deleteByTokenConfirmation`, que ya no aplican a `users`. El método `findByCorreo` fue reemplazado por `findByMail`. El tipo genérico del repositorio cambió de `Long` a `Integer`.

### 3.5 `Repositories/UnconfirmedUserRepo.java` — Nuevo

Repositorio para `UnconfirmedUser`. Expone `findByMail` para detectar duplicados durante el registro.

### 3.6 `Repositories/ConfirmationTokenRepo.java` — Nuevo

Repositorio para `ConfirmationToken`. Expone `findByContent` (para buscar por el UUID del token) y `deleteByContent`.

### 3.7 `Session/log_in/LoginController.java` — Modificado

Los campos leídos del body de la request cambiaron de `loginData.correo` / `loginData.contrasena` a `loginData.mail` / `loginData.hashedPassword`, acordes a la nueva entidad `User`.

### 3.8 `Session/log_in/LoginService.java` — Modificado

- `findByCorreo` reemplazado por `findByMail`.
- Se eliminó la verificación `"true".equals(user.correo_verificado)`. En la nueva arquitectura, si un usuario existe en la tabla `users` ya está verificado por definición; la verificación ocurre en el momento de moverlo desde `unconfirmed_user`.

### 3.9 `Session/sign_up/SignupController.java` — Modificado

El `@RequestBody` del endpoint `/create` ahora recibe `UnconfirmedUser` en lugar de `User`, ya que el registro crea inicialmente un usuario no confirmado. Los endpoints y su comportamiento externo no cambiaron.

### 3.10 `Session/sign_up/SignupService.java` — Modificado

Es el archivo con más cambios. El flujo de registro se adaptó completamente:

- `createUser()` verifica duplicados tanto en `users` como en `unconfirmed_user`, encripta la contraseña, persiste en `unconfirmed_user`, genera y guarda el token en `confirmation_token`, y envía el correo.
- `verifyToken()` busca el token en `confirmation_token`, llama a `promoteToConfirmedUser()` para copiar los datos a `users`, y luego elimina el registro de `unconfirmed_user` (el token se borra en cascada por la BD).
- Se añadió `promoteToConfirmedUser()` como método auxiliar que construye un `User` a partir de un `UnconfirmedUser` y lo persiste.
- Se añadió `saveConfirmationToken()` para encapsular la creación del token con sus fechas.

### 3.11 `pom.xml` — Sin cambios

No requirió ninguna modificación.

---

## 4. Problemas potenciales del código actual

### 4.1 `program_id` y `calendar_id` son `null` tras el registro

Al crear un usuario confirmado en `promoteToConfirmedUser()`, los campos `programId` y `calendarId` quedan sin asignar. Esto puede romper consultas o lógica en otras partes del sistema que asuman que todo usuario tiene un programa y un calendario asociados. La nueva BD los declara como `NOT NULL` con `UNIQUE`, lo que generaría un error de constraint al intentar insertar sin esos valores.

### 4.2 La expiración del token no se valida

El campo `expireAt` se persiste correctamente con un valor de 24 horas desde la creación, pero `verifyToken()` no comprueba si el token ha expirado antes de aceptarlo. Un token caducado seguiría siendo válido indefinitamente mientras permanezca en la BD.

### 4.3 No se limpian tokens expirados

Los registros en `unconfirmed_user` y `confirmation_token` que nunca son confirmados (usuarios que no hacen clic en el correo) se acumulan sin ser eliminados. Con el tiempo esto ensucia la BD y puede generar problemas de unicidad si el mismo correo intenta registrarse de nuevo.

### 4.4 El mecanismo de bloqueo de `unconfirmed_user` no está implementado

La tabla `unconfirmed_user` tiene los campos `attempt`, `isBlocked` y `blockTime`, pero el código no los lee ni los actualiza en ningún momento. Los reintentos de registro no están controlados.

### 4.5 `userRole` se asigna como valor fijo hardcodeado

En `promoteToConfirmedUser()` el rol queda siempre como `"student"`. Si el sistema necesita registrar docentes u otros roles, esta asignación fallará silenciosamente (sin error, pero con un rol incorrecto).

### 4.6 El tipo de ID es inconsistente con el resto del sistema

La `old_BD` usaba `BIGINT` (mapeado como `Long` en Java). La `new_BD` usa `INT` (mapeado como `Integer`). Si alguna parte del código no incluida en esta refactorización aún opera con `Long` para los IDs de usuario, habrá incompatibilidades de tipo en runtime.

### 4.7 Ausencia de transaccionalidad explícita en `SignupService`

El método `createUser()` realiza múltiples operaciones de escritura (guardar `UnconfirmedUser`, guardar `ConfirmationToken`, enviar correo). Si alguna falla a mitad del proceso (por ejemplo, el correo falla después de guardar el token), la BD queda en un estado parcialmente inconsistente. No hay `@Transactional` que garantice el rollback.

### 4.8 No hay validación de entrada

Los campos de `UnconfirmedUser` que llegan desde el request (`mail`, `userName`, etc.) no se validan antes de intentar persistirlos. Un correo malformado o campos vacíos llegarán directamente a la BD.

---

## 5. Mejoras y características futuras recomendadas

### 5.1 Resolver la asignación de `program` y `calendar`

Definir en qué momento del flujo se crean y asignan los registros en `program` y `calendar`. Las opciones más comunes son:

- Crear ambos automáticamente al confirmar el usuario (en `promoteToConfirmedUser()`).
- Permitir que el usuario los seleccione durante el registro y enviarlos junto con los datos del formulario.

### 5.2 Validar expiración del token

En `verifyToken()`, añadir una comprobación contra `LocalDateTime.now()` antes de aceptar el token:

```java
if (LocalDateTime.now().isAfter(confirmationToken.expireAt)) {
    // token expirado: eliminar y retornar false o lanzar excepción específica
}
```

### 5.3 Tarea programada para limpieza de registros huérfanos

Implementar un `@Scheduled` que se ejecute periódicamente (por ejemplo, cada noche) para eliminar registros de `unconfirmed_user` cuyo token asociado haya expirado. Esto mantiene la BD limpia y libera los correos para nuevos intentos.

### 5.4 Implementar el control de intentos y bloqueo

Aprovechar los campos `attempt`, `isBlocked` y `blockTime` ya presentes en `unconfirmed_user`. La lógica podría funcionar así: al intentar registrar un correo ya presente en `unconfirmed_user`, incrementar `attempt`; al superar un umbral (por ejemplo, 3), marcar `isBlocked = true` y registrar `blockTime`. Al intentar un nuevo registro, verificar si el bloqueo ha expirado antes de proceder.

### 5.5 Recibir `userRole` desde el formulario de registro

Si el sistema necesita distintos tipos de usuarios desde el registro, añadir el campo `userRole` al formulario de alta y propagarlo a través de `UnconfirmedUser` hasta `promoteToConfirmedUser()`. Sería conveniente validar que el valor recibido sea uno de los roles permitidos.

### 5.6 Añadir `@Transactional` en operaciones críticas

Los métodos `createUser()` y `verifyToken()` deberían estar anotados con `@Transactional` para garantizar que todas las operaciones de BD se confirmen o reviertan como una unidad atómica, evitando estados inconsistentes ante fallos parciales.

### 5.7 Validación de entrada con Bean Validation

Añadir anotaciones de validación (`@NotBlank`, `@Email`, `@Size`, etc.) a las entidades o a clases de request, y activar `@Valid` en los controladores. Esto evita que datos malformados lleguen a la capa de servicio o a la BD.

### 5.8 Implementar JWT para el login

El `pom.xml` ya incluye las dependencias de JJWT. El endpoint de login actualmente devuelve solo un `String`. Un siguiente paso natural sería que devuelva un token JWT firmado que el frontend pueda usar para autenticar las siguientes peticiones, aprovechando el campo `userRole` para incluir el rol en el payload del token.

### 5.9 Reenvío de correo de confirmación

Actualmente no hay forma de que un usuario vuelva a solicitar el correo de confirmación si no lo recibió o expiró. Sería útil añadir un endpoint (por ejemplo, `POST /account/resend_verification`) que genere un nuevo token y reenvíe el correo, invalidando el anterior.

### 5.10 Internacionalización de mensajes de error

Los mensajes de error actuales están hardcodeados en español directamente en el código. A medida que el proyecto crezca, centralizar estos mensajes en archivos de propiedades (`messages.properties`) facilitará el mantenimiento y una eventual internacionalización.

---

## 6. Resumen ejecutivo

La refactorización adaptó con éxito el backend a la nueva estructura de base de datos. El cambio más significativo fue la separación del flujo de registro en dos tablas (`unconfirmed_user` / `users`), lo que implicó crear nuevas entidades y repositorios y reescribir la lógica de `SignupService`. Los endpoints públicos y la arquitectura general del proyecto se mantuvieron intactos.

Los problemas más urgentes a resolver antes de poner el sistema en producción son la asignación de `program_id` / `calendar_id` (que actualmente causaría errores de constraint en la BD) y la validación de expiración del token de confirmación. El resto de mejoras pueden priorizarse en iteraciones posteriores.