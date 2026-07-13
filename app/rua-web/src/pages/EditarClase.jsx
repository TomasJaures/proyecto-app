import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Card from "../components/Card.jsx";
import SidePanel from "../components/SidePanel.jsx";
import MobileHeader from "../components/MobileHeader.jsx";
import Modal from "../components/Modal.jsx";
import AddStudentModal from "../components/AddStudentModal.jsx";
import PendingChangesBar from "../components/PendingChangesBar.jsx";
import "../styles/EditarClase.css";
import { classApi } from "../services/apiService.js";

function CancelClassModal({ open, onClose, onConfirm, isCancelled }) {
  return (
    <Modal
      open={open}
      onClose={onClose}
      title={isCancelled ? "Des-anular clase" : "Anular clase"}
    >
      <p className="modal-descripcion">
        {isCancelled
          ? "¿Deseas reactivar esta clase? Se restaurará en el calendario normalmente."
          : "¿Seguro que deseas anular esta clase? Esta acción puede revertirse."}
      </p>
      {!isCancelled && (
        <div className="alerta-info">
          <p>
            La clase seguirá visible con indicador de anulada. Puedes
            reactivarla cuando quieras.
          </p>
        </div>
      )}
      <div className="acciones-row">
        <button className="secundario" onClick={onClose}>
          Cancelar
        </button>
        <button className="confirmar" onClick={onConfirm}>
          Confirmar
        </button>
      </div>
    </Modal>
  );
}

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
        <button className="secundario" onClick={onClose}>
          Cancelar
        </button>
        <button className="confirmar peligro" onClick={onConfirm}>
          Eliminar
        </button>
      </div>
    </Modal>
  );
}

const EMPTY_CLASS = {
  id: null,
  courseId: "Cargando...",
  name: "Buscando información de la clase...",
  isCancelled: false,
  students: [],
};

function EditarClase() {
  const { blockId } = useParams();
  const navigate = useNavigate();

  const [savedClass, setSavedClass] = useState(EMPTY_CLASS);
  const [pendingClass, setPendingClass] = useState(EMPTY_CLASS);
  const [pendingAction, setPendingAction] = useState(null);

  const [showAddStudent, setShowAddStudent] = useState(false);
  const [showCancelModal, setShowCancelModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);

  useEffect(() => {
    const fetchClassInfo = async () => {
      try {
        const { data } = await classApi.getClassInfo(blockId);
        const mapped = {
          id: data.classId,
          courseId: data.code,
          name: data.subjectName,
          isCancelled: data.isAnulled,
          students: [],
        };
        setSavedClass(mapped);
        setPendingClass(mapped);
      } catch (error) {
        console.error("Error fetching class info:", error);
      }
    };

    if (blockId) fetchClassInfo();
  }, [blockId]);

  const hasPendingChanges =
    savedClass.isCancelled !== pendingClass.isCancelled ||
    savedClass.students.length !== pendingClass.students.length ||
    pendingAction === "eliminacion";

  const handleAddStudent = (email) => {
    const namePart = email.split("@")[0];
    const formattedName = namePart
      .split(".")
      .map((p) => p.charAt(0).toUpperCase() + p.slice(1))
      .join(" ");

    const newStudent = {
      id: Date.now().toString(),
      name: formattedName,
      email,
      date: new Date().toLocaleDateString("es-CL", {
        day: "numeric",
        month: "short",
        year: "numeric",
      }),
    };

    setPendingClass((prev) => ({
      ...prev,
      students: [...prev.students, newStudent],
    }));
    setPendingAction("alumno");
    setShowAddStudent(false);
  };

  const handleToggleCancelClass = () => {
    const newValue = !pendingClass.isCancelled;

    setPendingClass((prev) => ({
      ...prev,
      isCancelled: newValue,
    }));

    setPendingAction(
      newValue === savedClass.isCancelled ? null : "anulacion"
    );

    setShowCancelModal(false);
  };

  const handleDeleteClass = () => {
    setPendingAction("eliminacion");
    setShowDeleteModal(false);
  };

  const handleSave = async () => {
    try {
      if (pendingAction === "eliminacion") {
        await classApi.deleteClass(pendingClass.id);
        alert("Clase eliminada exitosamente.");
        navigate("/docente");
        return;
      }

      if (pendingAction === "anulacion") {
        await classApi.toggleCancelClass(pendingClass.id, pendingClass.isCancelled);
        alert("El estado de la clase ha sido actualizado.");
      }

      if (pendingAction === "alumno") {
        const lastStudent = pendingClass.students[pendingClass.students.length - 1];
        await classApi.addStudentToClassById(pendingClass.id, lastStudent.email);
        alert("Alumno registrado en la clase.");
      }

      setSavedClass(pendingClass);
      setPendingAction(null);
    } catch (error) {
      console.error("Error saving changes:", error);
      alert("Hubo un problema al conectar con el servidor al intentar guardar.");
    }
  };

  const handleCancel = () => {
    setPendingClass(savedClass);
    setPendingAction(null);
  };

  const pendingMessages = {
    alumno: "⚠ Alumno encolado. Guarda para confirmar.",
    anulacion: `⚠ Clase marcada como ${pendingClass.isCancelled ? "anulada" : "activa"}. Guarda para confirmar.`,
    eliminacion: "⚠ Clase marcada para eliminar. Guarda para confirmar.",
  };

  const menuItems = [
    {
  emoji: "📋",
  label: "Generar código QR",
  action: () =>
    navigate("/generadorqr", {
      state: {
        classId: pendingClass.id,
      },
    }),
  variant: "normal",
},
    { emoji: "➕", label: "Añadir alumno", action: () => setShowAddStudent(true), variant: "normal" },
    { emoji: pendingClass.isCancelled ? "▶" : "⊘", label: pendingClass.isCancelled ? "Des-anular clase" : "Anular clase", action: () => setShowCancelModal(true), variant: "normal" },
    { emoji: "👁", label: "Ver asistencia", action: () => navigate(`/asistenciaclase/${pendingClass.id}`), variant: "normal" },
    { emoji: "✕", label: "Eliminar clase", action: () => setShowDeleteModal(true), variant: "peligro" },
  ];

  return (
    <div className="pagina">
      <SidePanel>Registro de asistencia universitaria</SidePanel>

      <main className="derecha">
        <MobileHeader />

        <Card>
          <h1>{pendingClass.courseId}</h1>
          <p>{pendingClass.name}</p>

          {pendingClass.isCancelled && (
            <span className="badge-anulada">CLASE ANULADA</span>
          )}

          {hasPendingChanges && (
            <PendingChangesBar
              message={pendingMessages[pendingAction] ?? ""}
              onSave={handleSave}
              onCancel={handleCancel}
              variant={pendingAction === "eliminacion" ? "danger" : "warning"}
            />
          )}

          <nav className="menu-clase">
            {menuItems.map((item, i) => (
              <button
                key={i}
                onClick={item.action}
                className={`menu-item ${item.variant === "peligro" ? "menu-item-peligro" : ""}`}
              >
                <div className={`menu-item-icono ${item.variant === "peligro" ? "menu-item-icono-peligro" : ""}`}>
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
        isCancelled={pendingClass.isCancelled}
      />
      <DeleteClassModal
        open={showDeleteModal}
        onClose={() => setShowDeleteModal(false)}
        onConfirm={handleDeleteClass}
        courseId={pendingClass.courseId}
      />
    </div>
  );
}

export default EditarClase;
