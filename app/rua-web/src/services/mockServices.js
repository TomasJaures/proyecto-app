import { BACKEND_URL } from "../config.js";

export const calendarMockApi = {
  getBlocks(calendarId) {
    return Promise.resolve({
      data: [
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
    return apiClient.get("/api/calendars/actualClasses", {
      params: {
        calendarId,
      },
    });
  },
};