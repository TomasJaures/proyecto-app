import axios from "axios";
import { BACKEND_URL } from "../config.js";

const apiClient = axios.create({
  baseURL: BACKEND_URL,
  headers: { "Content-Type": "application/json" },
});

export const authApi = {
  login(credentials) {
    return apiClient.post("/account/login", credentials);
  },

  register(userData) {
    return apiClient.post("/account/create", userData);
  },
};

export const attendanceApi = {
  getPresentStudents(classId) {
    return apiClient.get(`/api/attendance/class/${classId}/present2`);
  },

  addStudentToClass(classId, studentData) {
    return apiClient.post(
      `/api/attendance/class/${classId}/students`,
      studentData
    );
  },
};

export const classApi = {
  getClassInfo(blockId) {
    return apiClient.get(`/api/attendance/class/${blockId}/getInfo`);
  },

  deleteClass(classId) {
    return apiClient.delete(`/api/classes/${classId}`);
  },

  toggleCancelClass(classId, isAnulled) {
    return apiClient.patch(`/api/classes/${classId}/toggle-anular`, {
      isAnulled,
    });
  },

  addStudentToClassById(classId, email) {
    return apiClient.post(`/api/classes/${classId}/students`, { email });
  },
};

export const calendarApi = {
  getBlocks(calendarId) {
    return apiClient.get(`/api/calendars/${calendarId}/blocks`);
  },

  getActualClasses(calendarId) {
    return apiClient.get("/api/calendars/actualClasses", {
      params: {
        calendarId,
      },
    });
  },
};

export const qrApi = {
  scanQr(token) {
    return apiClient.post("/scanqr", { token });
  },

  //DONE: QR debe recibir información del Backend para el UUID
  getQr(classId){
    console.log("DEBUG: Peticion realizada al Backend");
    return apiClient.post("/api/qr/generate", null, { params: { classId: classId }});
  }
};

export default apiClient;
