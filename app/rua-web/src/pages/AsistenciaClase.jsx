import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Card from "../components/card.jsx";
import RuaAside from "../components/rua-aside.jsx";
import "../Styles/AsistenciaClase.css";
import axios from "axios";
import { BACKEND_URL } from "../config.js";

// ─── Modal inline ────────────────────────────────────────────────────────────
function Modal({ open, onClose, title, children }) {
  if (!open) return null;
  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-caja" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h2 className="modal-titulo">{title}</h2>
          <button className="modal-cerrar" onClick={onClose}>✕</button>
        </div>
        {children}
      </div>
    </div>
  );
}

// ─── Modal añadir alumno ──────────────────────────────────────────────────────
function AddStudentModal({ open, onClose, onAdd }) {
  const [email, setEmail] = useState("");
  const [err, setErr] = useState("");

  const handleClose = () => { setEmail(""); setErr(""); onClose(); };

  // Validación de correo antes de encolar el cambio
  const handleAdd = () => {
    if (!email.trim()) { setErr("Ingresa un correo"); return; }
    if (!email.includes("@")) { setErr("Correo inválido"); return; }
    onAdd(email.trim());
    setEmail(""); setErr("");
  };

  return (
    <Modal open={open} onClose={handleClose} title="Añadir alumno">
      <p className="modal-descripcion">
        Ingresa el correo institucional del alumno a añadir.
      </p>
      <label>CORREO INSTITUCIONAL</label>
      <input
        type="email"
        value={email}
        onChange={(e) => { setEmail(e.target.value); setErr(""); }}
        placeholder="alumno@ufromail.cl"
      />
      {err && <p className="error-texto">{err}</p>}
      {/* Confirmar encola el alumno como cambio pendiente, no lo guarda aún */}
      <button className="confirmar" onClick={handleAdd}>Confirmar</button>
    </Modal>
  );
}

// ─── Página principal ─────────────────────────────────────────────────────────
function AsistenciaClase() {
  const { classId } = useParams();
  const navigate = useNavigate();

  const [cls] = useState({
    id: classId,
    courseId: "INF-301",
    name: "Ingeniería de Software",
  });

  const [savedStudents, setSavedStudents] = useState([]);
  const [pendingStudents, setPendingStudents] = useState([]);
  const [hasPendingChanges, setHasPendingChanges] = useState(false);
  const [showAddStudent, setShowAddStudent] = useState(false);
  const [isSaving, setIsSaving] = useState(false);

  // ✅ Llamada correcta: useEffect + async/await + setState
  useEffect(() => {
    const fetchPresentStudents = async () => {
      try {
        const respuesta = await axios.get(
          `${BACKEND_URL}/api/attendance/class/${classId}/present2`
        );
        console.log("RESPUESTA:", respuesta.data);
        setSavedStudents(respuesta.data);
        setPendingStudents(respuesta.data);
      } catch (error) {
        console.error("Error al obtener alumnos presentes:", error);
      }
    };

    fetchPresentStudents();
  }, [classId]);

  // Detecta cambios pendientes comparando longitudes
  useEffect(() => {
    setHasPendingChanges(pendingStudents.length !== savedStudents.length);
  }, [pendingStudents, savedStudents]);

  // Encola el nuevo alumno sin guardar todavía
  const handleAddStudent = (email) => {
    const namePart = email.split("@")[0];
    const name = namePart.split(".").map((p) => p.charAt(0).toUpperCase() + p.slice(1)).join(" ");
    setPendingStudents((prev) => [
      ...prev,
      {
        userId: Date.now().toString(),
        userName: name,
        mail: email,
        lastName1: "",
        lastName2: "",
        date: new Date().toLocaleDateString("es-CL", { day: "numeric", month: "short", year: "numeric" }),
        isNew: true, // Marca para identificar alumnos nuevos
      },
    ]);
    setShowAddStudent(false);
  };

  // Confirma los cambios — Obtiene alumnos nuevos y envía al backend
  const handleGuardar = async () => {
    setIsSaving(true);
    try {
      // Obtener solo los alumnos nuevos (que no están en savedStudents)
      const newStudents = pendingStudents.filter(
        (pStudent) => !savedStudents.some(
          (sStudent) => sStudent.mail === pStudent.mail
        )
      );

      console.log("Alumnos nuevos a guardar:", newStudents);

      // Enviar cada alumno nuevo al backend
      if (newStudents.length > 0) {
        for (const student of newStudents) {
          await axios.post(
            `${BACKEND_URL}/api/attendance/class/${classId}/students`,
            {
              mail: student.mail,
              userName: student.userName,
              lastName1: student.lastName1,
              lastName2: student.lastName2,
            }
          );
        }
        alert(`✓ ${newStudents.length} alumno(s) guardado(s) correctamente.`);
      } else {
        alert("No hay alumnos nuevos para guardar.");
      }

      // Actualizar el estado guardado
      setSavedStudents(pendingStudents);
      setHasPendingChanges(false);
    } catch (error) {
      console.error("Error al guardar alumnos:", error);
      alert("Error al guardar los alumnos. Intenta de nuevo.");
    } finally {
      setIsSaving(false);
    }
  };

  // Descarta los cambios pendientes y vuelve al estado guardado
  const handleCancelar = () => {
    setPendingStudents(savedStudents);
    setHasPendingChanges(false);
  };

  return (
    <div className="pagina">
      <RuaAside>Registro de asistencia universitaria</RuaAside>

      <main className="derecha">
        <div className="barra-mobile"><h1>RUA</h1></div>

        <Card>
          <h1>Asistencia — {cls.courseId}</h1>
          <p>{cls.name}</p>

          {/* Barra de cambios pendientes */}
          {hasPendingChanges && (
            <div className="barra-pendiente">
              <span>⚠ Tienes cambios sin guardar</span>
              <div className="barra-pendiente-acciones">
                <button className="secundario" onClick={handleCancelar} disabled={isSaving}>
                  Cancelar
                </button>
                <button className="confirmar" onClick={handleGuardar} disabled={isSaving}>
                  {isSaving ? "Guardando..." : "Guardar"}
                </button>
              </div>
            </div>
          )}

          {/* Encabezado del registro */}
          <div className="registro-header">
            <div className="registro-header-titulo">
              <span className="icono-usuario">👤</span>
              <span>Registro de asistencia</span>
            </div>
            <span className="badge-alumnos">{pendingStudents.length} alumnos</span>
          </div>

          {/* Lista de alumnos */}
          {pendingStudents.length === 0 ? (
            <p className="texto-secundario lista-vacia">No hay alumnos registrados aún.</p>
          ) : (
            <ul className="lista-alumnos">
              {pendingStudents.map((student) => (
                <li key={student.userId} className="alumno-item">
                  <div className="alumno-avatar">
                    <span>👤</span>
                  </div>
                  <div className="alumno-info">
                    <p className="alumno-nombre">{student.userName} {student.lastName1} {student.lastName2}</p>
                    <p className="alumno-email">{student.mail}</p>
                  </div>
                  {student.date && (
                    <div className="alumno-fecha">
                      <span>📅</span>
                      <span>{student.date}</span>
                    </div>
                  )}
                </li>
              ))}
            </ul>
          )}

          {/* Acciones */}
          <div className="acciones-row">
            <button className="confirmar boton-icono" onClick={() => setShowAddStudent(true)} disabled={isSaving}>
              ＋ Añadir alumno
            </button>
            <button className="secundario" onClick={() => navigate(`/docente/clase/${cls.id}`)} disabled={isSaving}>
              Volver
            </button>
          </div>
        </Card>
      </main>

      <AddStudentModal
        open={showAddStudent}
        onClose={() => setShowAddStudent(false)}
        onAdd={handleAddStudent}
      />
    </div>
  );
}

export default AsistenciaClase;