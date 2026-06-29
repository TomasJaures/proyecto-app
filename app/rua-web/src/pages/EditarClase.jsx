import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Card from "../components/card.jsx";
import RuaAside from "../components/rua-aside.jsx";
import { useLocation } from "react-router-dom";
import "../Styles/EditarClase.css";
import axios from "axios";
import { BACKEND_URL } from "../config.js";


// ─── Modal inline ─────────────────────────────────────────────────────────────
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
      {/* Confirmar encola el cambio, no lo persiste aún */}
      <button className="confirmar" onClick={handleAdd}>Confirmar</button>
    </Modal>
  );
}

// ─── Modal anular / des-anular clase ─────────────────────────────────────────
function CancelClassModal({ open, onClose, onConfirm, isCancelled }) {
  return (
    <Modal open={open} onClose={onClose} title={isCancelled ? "Des-anular clase" : "Anular clase"}>
      <p className="modal-descripcion">
        {isCancelled
          ? "¿Deseas reactivar esta clase? Se restaurará en el calendario normalmente."
          : "¿Seguro que deseas anular esta clase? Esta acción puede revertirse."}
      </p>
      {!isCancelled && (
        <div className="alerta-info">
          <p>La clase seguirá visible con indicador de anulada. Puedes reactivarla cuando quieras.</p>
        </div>
      )}
      <div className="acciones-row">
        <button className="secundario" onClick={onClose}>Cancelar</button>
        {/* Confirmar aquí encola el cambio de estado; el modal cierra y aparece la barra pendiente */}
        <button className="confirmar" onClick={onConfirm}>Confirmar</button>
      </div>
    </Modal>
  );
}

// ─── Modal eliminar clase ────────────────────────────────────────────────────
function DeleteClassModal({ open, onClose, onConfirm, courseId }) {
  return (
    <Modal open={open} onClose={onClose} title="Eliminar clase">
      <div className="alerta-advertencia">
        <span className="alerta-icono">⚠</span>
        <p>
          Esto eliminará <strong>{courseId}</strong> y sus proyecciones futuras.
          Las clases pasadas no se verán afectadas.
        </p>
      </div>
      <div className="acciones-row">
        <button className="secundario" onClick={onClose}>Cancelar</button>
        {/* Confirmar encola la eliminación; se aplica solo al guardar */}
        <button className="confirmar peligro" onClick={onConfirm}>Eliminar</button>
      </div>
    </Modal>
  );
}

// ─── Página principal ─────────────────────────────────────────────────────────
// ─── Página principal Adaptada ─────────────────────────────────────────────────────────
function EditarClase() {
  const { blockId } = useParams();
  const navigate = useNavigate();

  // Estado que almacena la información oficial del Backend
  const [savedCls, setSavedCls] = useState({
    id: null,
    courseId: "Cargando...",
    name: "Buscando información de la clase...",
    isCancelled: false,
    students: [],
  });

  // Estado de trabajo local (cambios temporales)
  const [pendingCls, setPendingCls] = useState(savedCls);
  // Tipo de cambio pendiente para la barra: null | "alumno" | "anulacion" | "eliminacion"
  const [pendingAction, setPendingAction] = useState(null);

  const [showAddStudent, setShowAddStudent]   = useState(false);
  const [showCancelModal, setShowCancelModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);

  // Petición al Backend con la nueva estructura del DTO
  useEffect(() => {
    const obtenerInfoClase = async () => {
      try {
        const respuesta = await axios.get(`${BACKEND_URL}/api/attendance/class/${blockId}/getInfo`);
        console.log("RESPUESTA DETALLADA:", respuesta.data);
        
        // Mapeamos los datos que llegan del DTO del backend a tu estructura del Front
        const dataMapeada = {
          id: respuesta.data.classId,          // classes.class_id
          courseId: respuesta.data.code,       // subjects.code
          name: respuesta.data.subjectName,    // subjects.subject_name
          isCancelled: respuesta.data.isAnulled, // classes.is_anulled
          students: [] // Inicialmente vacío o maneja su propia petición
        };

        setSavedCls(dataMapeada);
        setPendingCls(dataMapeada);
      } catch (error) {
        console.log("ERROR AL OBTENER LA CLASE:", error);
        // Opcional: redirigir a /error si no encuentra la clase o el bloque
      }
    };

    if (blockId) {
      obtenerInfoClase();
    }
  }, [blockId]);

  const hasPendingChanges = pendingAction !== null;

  // ── Handlers que encolan cambios locales ──────────────────────────────────────────
  const handleAddStudent = (email) => {
    const namePart = email.split("@")[0];
    const name = namePart.split(".").map((p) => p.charAt(0).toUpperCase() + p.slice(1)).join(" ");
    const nuevoAlumno = {
      id: Date.now().toString(),
      name,
      email,
      date: new Date().toLocaleDateString("es-CL", { day: "numeric", month: "short", year: "numeric" }),
    };
    setPendingCls((prev) => ({ ...prev, students: [...prev.students, nuevoAlumno] }));
    setPendingAction("alumno");
    setShowAddStudent(false);
  };

  const handleToggleCancelClass = () => {
    setPendingCls((prev) => ({ ...prev, isCancelled: !prev.isCancelled }));
    setPendingAction("anulacion");
    setShowCancelModal(false);
  };

  const handleDeleteClass = () => {
    setPendingAction("eliminacion");
    setShowDeleteModal(false);
  };

  // ── Guardar: Aplica los cambios de forma real en el backend ────────────────────────
  const handleGuardar = async () => {
    try {
      if (pendingAction === "eliminacion") {
        // Ejemplo de petición DELETE real usando el id de clase obtenido
        await axios.delete(`${BACKEND_URL}/api/classes/${pendingCls.id}`);
        alert("Clase eliminada exitosamente.");
        navigate("/docente");
        return;
      }
      
      if (pendingAction === "anulacion") {
        // Petición PATCH real para cambiar el estado de anulación
        await axios.patch(`${BACKEND_URL}/api/classes/${pendingCls.id}/toggle-anular`, {
          isAnulled: pendingCls.isCancelled
        });
        alert("El estado de la clase ha sido actualizado.");
      }
      
      if (pendingAction === "alumno") {
        // Enviar los alumnos encolados (tomamos el último añadido para el ejemplo)
        const ultimoAlumno = pendingCls.students[pendingCls.students.length - 1];
        await axios.post(`${BACKEND_URL}/api/classes/${pendingCls.id}/students`, {
          email: ultimoAlumno.email
        });
        alert("Alumno registrado en la clase.");
      }

      // Sincronizamos el estado guardado con los cambios aceptados
      setSavedCls(pendingCls);
      setPendingAction(null);
    } catch (error) {
      console.error("Error al guardar los cambios:", error);
      alert("Hubo un problema al conectar con el servidor al intentar guardar.");
    }
  };

  // ── Cancelar: descarta el cambio y restaura el estado original del backend ────────────
  const handleCancelar = () => {
    setPendingCls(savedCls);
    setPendingAction(null);
  };

  const mensajePendiente = {
    alumno:      "⚠ Alumno encolado. Guarda para confirmar.",
    anulacion:   `⚠ Clase marcada como ${pendingCls.isCancelled ? "anulada" : "activa"}. Guarda para confirmar.`,
    eliminacion: "⚠ Clase marcada para eliminar. Guarda para confirmar.",
  }[pendingAction] ?? "";

  const menuItems = [
    {
      emoji: "📋",
      label: "Generar código QR",
      action: () => navigate(`/docente/qr/${pendingCls.id}`),
      variante: "normal",
    },
    {
      emoji: "➕",
      label: "Añadir alumno",
      action: () => setShowAddStudent(true),
      variante: "normal",
    },
    {
      emoji: pendingCls.isCancelled ? "▶" : "⊘",
      label: pendingCls.isCancelled ? "Des-anular clase" : "Anular clase",
      action: () => setShowCancelModal(true),
      variante: "normal",
    },
    {
      emoji: "👁",
      label: "Ver asistencia",
      action: () => navigate(`/asistenciaclase/${pendingCls.id}`),
      variante: "normal",
    },
    {
      emoji: "✕",
      label: "Eliminar clase",
      action: () => setShowDeleteModal(true),
      variante: "peligro",
    },
  ];

  return (
    <div className="pagina">
      <RuaAside>Registro de asistencia universitaria</RuaAside>

      <main className="derecha">
        <div className="barra-mobile"><h1>RUA</h1></div>

        <Card>
          <h1>{pendingCls.courseId}</h1>
          <p>{pendingCls.name}</p>

          {pendingCls.isCancelled && (
            <span className="badge-anulada">CLASE ANULADA</span>
          )}

          {hasPendingChanges && (
            <div className={`barra-pendiente ${pendingAction === "eliminacion" ? "barra-pendiente-peligro" : ""}`}>
              <span>{mensajePendiente}</span>
              <div className="barra-pendiente-acciones">
                <button className="secundario" onClick={handleCancelar}>Cancelar</button>
                <button
                  className={`confirmar ${pendingAction === "eliminacion" ? "peligro" : ""}`}
                  onClick={handleGuardar}
                >
                  Guardar
                </button>
              </div>
            </div>
          )}

          <nav className="menu-clase">
            {menuItems.map((item, i) => (
              <button
                key={i}
                onClick={item.action}
                className={`menu-item ${item.variante === "peligro" ? "menu-item-peligro" : ""}`}
              >
                <div className={`menu-item-icono ${item.variante === "peligro" ? "menu-item-icono-peligro" : ""}`}>
                  {item.emoji}
                </div>
                <span className="menu-item-label">{item.label}</span>
                <span className="menu-item-chevron">›</span>
              </button>
            ))}
          </nav>

          <button className="secundario" onClick={() => navigate("/docente")}>
            Volver
          </button>
        </Card>
      </main>

      <AddStudentModal
        open={showAddStudent}
        onClose={() => setShowAddStudent(false)}
        onAdd={handleAddStudent}
      />
      <CancelClassModal
        open={showCancelModal}
        onClose={() => setShowCancelModal(false)}
        onConfirm={handleToggleCancelClass}
        isCancelled={pendingCls.isCancelled}
      />
      <DeleteClassModal
        open={showDeleteModal}
        onClose={() => setShowDeleteModal(false)}
        onConfirm={handleDeleteClass}
        courseId={pendingCls.courseId}
      />
    </div>
  );
}

export default EditarClase;
