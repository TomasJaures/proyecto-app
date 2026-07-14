import { useEffect, useState } from "react";
import Navbar from "../components/Navbar.jsx";
import SubjectCard from "../components/SubjectCard.jsx";
import Modal from "../components/Modal.jsx";
import { useAuth } from "../hooks/useAuth.js";
import { programMockApi } from "../services/mockServices.js";
import { programApi } from "../services/apiService.js";

function AddSubjectModal({ open, onClose, onAdd }) {
  const [name, setName] = useState("");
  const [code, setCode] = useState("");

  const handleAdd = () => {
    onAdd(name, code);
    setName("");
    setCode("");
  };

  return (
    <Modal open={open} onClose={onClose} title="Añadir asignatura">
      <input
        placeholder="Nombre"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />

      <input
        placeholder="Código"
        value={code}
        onChange={(e) => setCode(e.target.value)}
      />

      <button className="confirmar" onClick={handleAdd}>
        Añadir
      </button>

      <button className="secundario" onClick={onClose}>
        Cancelar
      </button>
    </Modal>
  );
}

function DocenteAdmin() {
  const { user } = useAuth();
  const programId = user?.programId;

  const [subjects, setSubjects] = useState([]);
  const [showPopup, setShowPopup] = useState(false);
  const [deleteMode, setDeleteMode] = useState(false);
  const [selectedIds, setSelectedIds] = useState([]);
  const [hasChanges, setHasChanges] = useState(false);


  async function getSubjectsInfo(programId) {
    try {
      const { subjects: subjectDatas } = await programApi.getSubjects(programId);
      return subjectDatas;
    } catch (error) {
      console.error(error);
      return [];
    }
  }


  useEffect(() => {
    async function loadSubjects() {
      const data = await getSubjectsInfo(programId);

      if (data) {
        setSubjects(
          data.map((subject) => ({
            id: subject.subjectId,
            nombre: subject.name,
            codigo: subject.code,
            modulos: subject.modules ?? []
          }))
        );
      }
    }

    if (programId) {
      loadSubjects();
    }

  }, [programId]);


  const handleAddSubject = (name, code) => {
    setSubjects((prev) => [
      ...prev,
      {
        id: Date.now(),
        nombre: name,
        codigo: code,
        modulos: []
      }
    ]);

    setShowPopup(false);
    setHasChanges(true);
  };


  const handleDeleteSelected = () => {
    if (!window.confirm("¿Eliminar asignaturas seleccionadas?")) return;

    setSubjects((prev) =>
      prev.filter((s) => !selectedIds.includes(s.id))
    );

    setSelectedIds([]);
    setDeleteMode(false);
    setHasChanges(true);
  };


  return (
    <div className="pagina-asignaturas">
      <Navbar role="Docente" name={user?.name || "NoName"} />
      <div className="titulo-asignaturas">
        <h1>Gestionar asignaturas</h1>
      </div>
      <div className="toolbar">
        <button onClick={() => setShowPopup(true)}>
          + Añadir
        </button>
        <button onClick={() => setDeleteMode(true)}>
          Eliminar
        </button>
      </div>
      <div className="contenedor-asignaturas">
        {subjects.map((subject) => (
          <SubjectCard
            key={subject.id}
            subject={subject}
            deleteMode={deleteMode}
            selectedIds={selectedIds}
            setSelectedIds={setSelectedIds}
            setSubjects={setSubjects}
            setHasChanges={setHasChanges}
          />
        ))}
      </div>
      {deleteMode && (
        <button className="confirmar-eliminar" onClick={handleDeleteSelected}>
          Confirmar eliminación
        </button>
      )}
      {hasChanges && (
        <div className="acciones-cambios">
          <button className="confirmar">
            Guardar
          </button>
          <button className="confirmar" onClick={() => setHasChanges(false)}>
            Cancelar
          </button>
        </div>
      )}
      <AddSubjectModal
        open={showPopup}
        onClose={() => setShowPopup(false)}
        onAdd={handleAddSubject}
      />
    </div>
  );
}


export default DocenteAdmin;