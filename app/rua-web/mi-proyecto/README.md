# Carga del Horario del Alumno y Estado Actual de las Clases

## Contexto

Anteriormente, la vista **AlumnoHorario** no obtenía desde el Backend los bloques correspondientes al calendario del alumno, por lo que el horario no reflejaba la información almacenada en la base de datos.

Además, el Backend proporciona un endpoint que entrega el estado actual de cada clase de la semana académica, permitiendo indicar si una clase fue anulada y su estado temporal (pasada, en desarrollo o futura).

## Objetivo

Implementar la carga del horario del alumno reutilizando la misma lógica utilizada en **DocenteHorario**, incorporando además la información del estado actual de las clases para representarla visualmente dentro del calendario.

---

## Implementación

### 1. Carga de bloques del calendario

Se reutilizó el método existente:

```javascript
calendarApi.getBlocks(calendarId);
```

Este método obtiene desde el Backend todos los bloques asociados al calendario del alumno autenticado.

Los bloques son almacenados en el estado del componente para posteriormente ser renderizados en el horario.

---

### 2. Obtención del estado actual de las clases

Se incorporó un nuevo método dentro de `calendarApi`:

```javascript
getActualClasses(calendarId)
```

Este método realiza una petición al endpoint:

```
GET /api/calendars/actualClasses?calendarId={calendarId}
```

La respuesta del Backend posee una estructura similar a:

```json
{
  "currentWeek": 27,
  "classInfoDTOs": [
    {
      "classId": 101,
      "blockId": 5,
      "isAnulled": 0,
      "timeState": "PAST"
    }
  ]
}
```

---

### 3. Asociación de información

Los bloques obtenidos desde:

```
GET /api/calendars/{calendarId}/blocks
```

se relacionan con la información entregada por:

```
GET /api/calendars/actualClasses
```

utilizando el atributo `blockId`.

De esta manera, cada bloque del calendario conoce:

- El identificador de la clase.
- Si la clase fue anulada.
- Su estado temporal dentro de la semana.

---

### 4. Almacenamiento de la semana actual

El atributo `currentWeek` recibido desde el Backend se almacena utilizando:

```javascript
sessionStorage.setItem("currentWeek", currentWeek);
```

Este valor podrá reutilizarse posteriormente por otras funcionalidades del sistema sin necesidad de volver a consultar el Backend.

---

## Representación visual

Cada bloque del horario representa el estado de la clase mediante un borde de color.

| Estado | Color |
|--------|--------|
| Clase realizada (`PAST`) | Azul |
| Clase en desarrollo (`PRESENT`) | Morado |
| Clase futura (`FUTURE`) | Naranjo |

En caso de que una clase se encuentre anulada (`isAnulled`), el Frontend puede aplicar un estilo visual adicional para diferenciarla de las clases activas.

---

## Resultado

Con esta implementación:

- El horario del alumno carga automáticamente los bloques desde el Backend.
- Se consulta el estado actual de las clases correspondientes a la semana académica.
- Se almacena la semana actual de la sesión (`currentWeek`).
- Cada bloque del calendario representa visualmente el estado de la clase asociada.
- Se reutiliza la misma lógica implementada previamente para la vista **DocenteHorario**, manteniendo consistencia entre ambos módulos.