import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar.jsx";
import Card from "../components/Card.jsx";
import Schedule from "../components/Schedule.jsx";

import { useAuth } from "../hooks/useAuth.js";
import { calendarApi } from "../services/apiService.js";
import { calendarMockApi } from "../services/mockServices.js";

const SCHEDULE_OPTIONS = [
  { label: "Añadir Clase", mode: null, isAddClass: true },
  { label: "Clonar Clase", mode: "clonar", hint: "Seleccione la clase que desea clonar" },
  { label: "Editar Clase", mode: "editar", hint: "Seleccione clase a editar" },
  { label: "Mover Clase", mode: "mover", hint: "Seleccione la clase que desea mover" },
  { label: "Remover Clase", mode: "remover", hint: "Seleccione la clase que quiere remover" }
];

function DocenteHorario() {
  const navigate = useNavigate();
  const { user } = useAuth();
  const calendarId = user?.calendarId;

  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [statusText, setStatusText] = useState("Seleccione acción");
  const [mode, setMode] = useState(null);
 
  const [showClassForm, setShowClassForm] = useState(false);
  const [courseName, setCourseName] = useState("");
  const [courseCode, setCourseCode] = useState("");

  const [blocks, setBlocks] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [loadError, setLoadError] = useState(null);

  // --- NUEVOS ESTADOS PARA PERSISTENCIA ---
  const [changesHistory, setChangesHistory] = useState([]);
  const [isSaving, setIsSaving] = useState(false);

  useEffect(() => {
    const fetchBlocks = async () => {
      try {
        setIsLoading(true);
        setLoadError(null);
        const { mockBlocks } = await calendarMockApi.getBlocks(calendarId);
        // const { data } = await calendarApi.getBlocks(calendarId);
        setBlocks(mockBlocks);
      } catch (err) {
        setLoadError(err.message);
      } finally {
        setIsLoading(false);
      }
    };

    if (calendarId) fetchBlocks();
  }, [calendarId]);

  // Función para acumular los cambios en nuestro historial local
  const handleAddChange = (newChange) => {
    setChangesHistory((prev) => [...prev, newChange]);
    console.log("Historial de cambios actual:", [...changesHistory, newChange]);
  };

  // Función para enviar los cambios al Backend
  const handleSaveChanges = async () => {
    if (changesHistory.length === 0) {
      alert("No hay cambios para guardar.");
      return;
    }

    try {
      setIsSaving(true);
      // Enviamos el array de cambios acumulados al Backend
      await calendarApi.saveScheduleChanges(changesHistory);
      
      alert("¡Horario guardado y sincronizado con éxito!");
      
      // Limpiamos el historial para la próxima sesión de edición
      setChangesHistory([]); 
      
      // Opcional: Recargar los bloques reales desde el backend para ver el estado fresco
      // fetchBlocks(); 
    } catch (err) {
      alert("Error al intentar guardar los cambios: " + err.message);
    } finally {
      setIsSaving(false);
    }
  };

  const selectMode = (text, newMode) => {
    setStatusText(text);
    setMode(newMode);
    setDropdownOpen(false);
  };

  const handleOptionClick = (option) => {
    setDropdownOpen(false);
    if (option.isAddClass) {
      setShowClassForm(true);
      return;
    }
    selectMode(option.hint, option.mode);
  };

  const handleContinueAddClass = () => {
    if (!courseName.trim() || !courseCode.trim()) {
      alert("Complete todos los campos");
      return;
    }
    setShowClassForm(false);
    setStatusText("Seleccione un bloque vacío para añadir la clase");
    setMode("añadir");
  };

  return (
    <div>
      <Navbar
          role="Docente"
          name={user?.name || "NoName"}
          tutorial="docenteHorario"
      />

      <div className="docente-contenido">
        <h1>Hola, {user?.name || "NoName"}</h1>
        <p>Estás registrado como Docente.</p>

        <Card>
          <div className="acciones-docente" style={{ display: 'flex', alignItems: 'center', gap: '10px', flexWrap: 'wrap' }}>
            <button className="btn-qr" onClick={() => handleOptionClick({mode: "qr", hint: "Seleccione la clase que quiere generar el QR"})}>
              Generar QR
            </button>

            <div className="dropdown">
              <button className="btn-opciones" onClick={() => setDropdownOpen(!dropdownOpen)}>
                Opciones ▼
              </button>
              {dropdownOpen && (
                <div className="dropdown-menu">
                  {SCHEDULE_OPTIONS.map((option) => (
                    <div key={option.label} onClick={() => handleOptionClick(option)}>
                      {option.label}
                    </div>
                  ))}
                </div>
              )}
            </div>

            <span className="texto-seleccion">{statusText}</span>

            {/* BOTÓN DE GUARDADO DINÁMICO */}
            {changesHistory.length > 0 && (
              <button 
                className="btn-guardar" 
                onClick={handleSaveChanges} 
                disabled={isSaving}
                style={{
                  marginLeft: 'auto',
                  backgroundColor: '#4CAF50',
                  color: 'white',
                  border: 'none',
                  padding: '8px 16px',
                  borderRadius: '4px',
                  cursor: 'pointer',
                  fontWeight: 'bold'
                }}
              >
                {isSaving ? "Guardando..." : `Guardar Cambios (${changesHistory.length})`}
              </button>
            )}
          </div>
        </Card>

        {/* Modal Simple para añadir Clase */}
        {showClassForm && (
          <div className="modal-formulario" style={{
            position: 'fixed', top: '50%', left: '50%', transform: 'translate(-50%, -50%)',
            background: 'white', padding: '20px', border: '1px solid #ccc', zIndex: 1000,
            borderRadius: '8px', boxShadow: '0px 4px 6px rgba(0,0,0,0.1)'
          }}>
            <h3>Añadir Nueva Clase</h3>
            <div style={{ marginBottom: '10px' }}>
              <label>Asignatura: </label>
              <input type="text" value={courseName} onChange={(e) => setCourseName(e.target.value)} />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Código: </label>
              <input type="text" value={courseCode} onChange={(e) => setCourseCode(e.target.value)} />
            </div>
            <button onClick={handleContinueAddClass}>Continuar</button>
            <button onClick={() => setShowClassForm(false)} style={{ marginLeft: '10px' }}>Cancelar</button>
          </div>
        )}

        {isLoading && <p className="horario-estado">Cargando horario...</p>}
        {loadError && <p className="horario-estado horario-error">{loadError}</p>}
      </div>

      <Schedule
        mode={mode}
        setMode={setMode}
        className={courseName}
        courseCode={courseCode}
        blocks={blocks}
        onAddChange={handleAddChange} // <-- Pasamos el manejador de historial
      />
    </div>
  );
}

export default DocenteHorario;