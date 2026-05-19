

- **500 (Internal Server Error):** Error en el backend. Significa que la petición llegó al servidor, pero ocurrió una excepción en Spring Boot (puede ser BD, validación, constraint, null, etc.). Se debe revisar la consola para ver el stacktrace exacto (`Caused by`).

- **400 (Bad Request):** El backend no pudo interpretar el JSON o faltan datos requeridos.

- **404 (Not Found):** La ruta no existe o el endpoint no está mapeado correctamente.

- **403 (Forbidden):** El servidor entendió la petición pero no permite el acceso (permisos).

- **401 (Unauthorized):** Falta autenticación o token inválido.
