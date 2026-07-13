import { BACKEND_URL } from "../config.js";

export const calendarMockApi = {
  getBlocks(calendarId) {
    return Promise.resolve({
      mockBlocks: [
        {
          "calendarId": calendarId,
          "blockId": 1,
          "weekDay": "MON",
          "startHour": "08:30:00",
          "endHour": "09:40:00",
          "blockState": "NO_PROJECTIONS",
          "moduleId": 1,
          "moduleNum": 1, // Fila 0 (8:30) - Lunes
          "subjectId": 1,
          "subjectName": "Bases de Datos",
          "code": "ICC705"
        },
        {
          "calendarId": calendarId,
          "blockId": 2,
          "weekDay": "MON",
          "startHour": "09:40:00",
          "endHour": "10:50:00",
          "blockState": "PROJECTED",
          "moduleId": 2,
          "moduleNum": 2, // Fila 1 (9:40) - Lunes (Continuación de la clase)
          "subjectId": 1,
          "subjectName": "Bases de Datos",
          "code": "ICC705"
        },
        {
          "calendarId": calendarId,
          "blockId": 3,
          "weekDay": "TUE",
          "startHour": "10:50:00",
          "endHour": "12:00:00",
          "blockState": "NO_PROJECTIONS",
          "moduleId": 3,
          "moduleNum": 3, // Fila 2 (10:50) - Martes
          "subjectId": 2,
          "subjectName": "Estructuras de Datos",
          "code": "ICC402"
        },
        {
          "calendarId": calendarId,
          "blockId": 4,
          "weekDay": "WED",
          "startHour": "13:20:00",
          "endHour": "14:40:00",
          "blockState": "NO_PROJECTIONS",
          "moduleId": 5,
          "moduleNum": 5, // Fila 4 (13:20) - Miércoles
          "subjectId": 3,
          "subjectName": "Cálculo Multivariable",
          "code": "MAT210"
        },
        {
          "calendarId": calendarId,
          "blockId": 5,
          "weekDay": "THU",
          "startHour": "15:50:00",
          "endHour": "17:00:00",
          "blockState": "PROJECTED",
          "moduleId": 7,
          "moduleNum": 7, // Fila 6 (15:50) - Jueves
          "subjectId": 4,
          "subjectName": "Ingeniería de Software",
          "code": "ICC601"
        },
        {
          "calendarId": calendarId,
          "blockId": 6,
          "weekDay": "FRI",
          "startHour": "08:30:00",
          "endHour": "09:40:00",
          "blockState": "NO_PROJECTIONS",
          "moduleId": 1,
          "moduleNum": 1, // Fila 0 (8:30) - Viernes
          "subjectId": 5,
          "subjectName": "Redes de Computadores",
          "code": "ICC503"
        }
      ]
    });
  },

  getActualClasses(calendarId) {
    // --- MOCK TEMPORAL ---
    return Promise.resolve({
      mockClasses: {
        currentWeek: 29,
        classInfoDTOS: [
          {
            classId: 101,
            blockId: 1, // Enlazado a Bloque 1 (Lunes - Bases de Datos)
            isAnulled: false,
            timeState: "PAST",
            blockState: "ACTIVO",
            num: 1,
            subjectName: "Bases de Datos",
            code: "ICC705",
            weekDay: "LUNES",
            startHour: "08:30:00",
            endHour: "09:40:00"
          },
          {
            classId: 102,
            blockId: 2, // Enlazado a Bloque 2 (Lunes - Bases de Datos Continuación)
            isAnulled: false,
            timeState: "PAST",
            blockState: "ACTIVO",
            num: 2,
            subjectName: "Bases de Datos",
            code: "ICC705",
            weekDay: "LUNES",
            startHour: "09:40:00",
            endHour: "10:50:00"
          },
          {
            classId: 103,
            blockId: 3, // Enlazado a Bloque 3 (Martes - Estructuras de Datos)
            isAnulled: false,
            timeState: "PAST",
            blockState: "ACTIVO",
            num: 3,
            subjectName: "Estructuras de Datos",
            code: "ICC402",
            weekDay: "MARTES",
            startHour: "10:50:00",
            endHour: "12:00:00"
          },
          {
            classId: 104,
            blockId: 4, // Enlazado a Bloque 4 (Miércoles - Cálculo Multivariable)
            isAnulled: false,
            timeState: "PRESENT",
            blockState: "ACTIVO",
            num: 5,
            subjectName: "Cálculo Multivariable",
            code: "MAT210",
            weekDay: "MIERCOLES",
            startHour: "13:20:00",
            endHour: "14:40:00"
          },
          {
            classId: 105,
            blockId: 5, // Enlazado a Bloque 5 (Jueves - Ingeniería de Software)
            isAnulled: false,
            timeState: "FUTURE",
            blockState: "ACTIVO",
            num: 7,
            subjectName: "Ingeniería de Software",
            code: "ICC601",
            weekDay: "JUEVES",
            startHour: "15:50:00",
            endHour: "17:00:00"
          },
          {
            classId: 106,
            blockId: 6, // Enlazado a Bloque 6 (Viernes - Redes de Computadores)
            isAnulled: true, // Ejemplo de clase anulada para variar tus estados visuales
            timeState: "FUTURE",
            blockState: "ANULADO",
            num: 1,
            subjectName: "Redes de Computadores",
            code: "ICC503",
            weekDay: "VIERNES",
            startHour: "08:30:00",
            endHour: "09:40:00"
          }
        ]
      }
    });

    /* 
    // Cuando el Backend esté listo, la llamada real corregida debería ser así:
    return apiClient.get(`/api/calendars/${calendarId}/classes`); 
    */
  },
};