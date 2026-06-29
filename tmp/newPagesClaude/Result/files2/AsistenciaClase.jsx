import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Card from "../components/card.jsx";
import RuaAside from "../components/rua-aside.jsx";
import "./AsistenciaClase.css";

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

  // Datos mock de la clase — TODO: GET /class/:classId
  const [cls] = useState({
    id: classId,
    courseId: "INF-301",
    name: "Ingeniería de Software",
  });

  // Datos mock de alumnos — TODO: GET /class/:classId/students
  const [savedStudents] = useState([
    { id: "1", name: "Ana Torres",   email: "ana.torres@ufromail.cl",   date: "10 jun 2025" },
    { id: "2", name: "Carlos Muñoz", email: "carlos.munoz@ufromail.cl", date: "10 jun 2025" },
  ]);

  // Lista de alumnos pendiente de guardar (copia de trabajo)
  const [pendingStudents, setPendingStudents] = useState(savedStudents);

  // true cuando pendingStudents difiere de savedStudents
  const [hasPendingChanges, setHasPendingChanges] = useState(false);

  const [showAddStudent, setShowAddStudent] = useState(false);

  // Detecta cambios pendientes comparando longitudes (la única operación posible aquí es añadir)
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
        id: Date.now().toString(),
        name,
        email,
        date: new Date().toLocaleDateString("es-CL", { day: "numeric", month: "short", year: "numeric" }),
      },
    ]);
    setShowAddStudent(false);
  };

  // Confirma los cambios — TODO: POST /class/:classId/students por cada alumno nuevo
  const handleGuardar = () => {
    // savedStudents aquí se actualizaría desde el backend; por ahora simulamos con estado local
    // savedStudents = pendingStudents  ← esto ocurrirá al recargar desde el backend
    setHasPendingChanges(false);
    alert("Cambios guardados (mock). Aquí irá la llamada al backend.");
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
                <button className="secundario" onClick={handleCancelar}>Cancelar</button>
                <button className="confirmar" onClick={handleGuardar}>Guardar</button>
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
                <li key={student.id} className="alumno-item">
                  <div className="alumno-avatar">
                    <span>👤</span>
                  </div>
                  <div className="alumno-info">
                    <p className="alumno-nombre">{student.name}</p>
                    <p className="alumno-email">{student.email}</p>
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
            <button className="confirmar boton-icono" onClick={() => setShowAddStudent(true)}>
              ＋ Añadir alumno
            </button>
            <button className="secundario" onClick={() => navigate(`/docente/clase/${cls.id}`)}>
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
