import { useState } from "react";

function SubjectCard({
  subject,
  deleteMode,
  selectedIds,
  setSelectedIds,
  setSubjects,
  setHasChanges,
}) {
  const [isEditing, setIsEditing] = useState(false);
  const [editName, setEditName] = useState(subject.nombre);

  const handleSaveName = () => {
    setSubjects((prev) =>
      prev.map((s) => (s.id === subject.id ? { ...s, nombre: editName } : s))
    );
    setIsEditing(false);
    setHasChanges(true);
  };

  const handleAddModule = () => {
    setSubjects((prev) =>
      prev.map((s) =>
        s.id === subject.id
          ? { ...s, modulos: [...s.modulos, "Nuevo módulo"] }
          : s
      )
    );
    setHasChanges(true);
  };

  const handleDeleteModule = (index) => {
    if (!window.confirm("¿Eliminar módulo?")) return;
    setSubjects((prev) =>
      prev.map((s) =>
        s.id === subject.id
          ? { ...s, modulos: s.modulos.filter((_, i) => i !== index) }
          : s
      )
    );
  };

  const handleToggleSelect = () => {
    setSelectedIds((prev) =>
      prev.includes(subject.id)
        ? prev.filter((id) => id !== subject.id)
        : [...prev, subject.id]
    );
  };

  return (
    <div className="card-asignatura">
      {deleteMode && (
        <input
          type="checkbox"
          checked={selectedIds.includes(subject.id)}
          onChange={handleToggleSelect}
          aria-label={`Seleccionar ${subject.nombre}`}
        />
      )}

      <div className="header-asignatura">
        {isEditing ? (
          <input value={editName} onChange={(e) => setEditName(e.target.value)} />
        ) : (
          <h2>{subject.nombre}</h2>
        )}
        <span>{subject.codigo}</span>
        <button onClick={() => (isEditing ? handleSaveName() : setIsEditing(true))}>
          Editar
        </button>
        <button>☰</button>
      </div>

      <div className="modulos">
        {subject.modulos.map((module, index) => (
          <div key={index}>
            {module}
            <button onClick={() => handleDeleteModule(index)} aria-label={`Eliminar ${module}`}>
              🗑️
            </button>
          </div>
        ))}
        <button className="nuevo-modulo" onClick={handleAddModule}>
          + Añadir módulo
        </button>
      </div>
    </div>
  );
}

export default SubjectCard;
