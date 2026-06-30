import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar.jsx";
import Card from "../components/Card.jsx";
import Schedule from "../components/Schedule.jsx";
import HelpButton from "../components/HelpButton.jsx";
import Modal from "../components/Modal.jsx";
import { useAuth } from "../hooks/useAuth.js";
import { calendarApi } from "../services/apiService.js";

const SCHEDULE_OPTIONS = [
  { label: "Añadir Clase", mode: null, isAddClass: true },
  { label: "Clonar Clase", mode: "clonar", hint: "Seleccione la clase que desea clonar" },
  { label: "Editar Clase", mode: "editar", hint: "Seleccione clase a editar" },
  { label: "Mover Clase", mode: "mover", hint: "Seleccione la clase que desea mover" },
  { label: "Remover Clase", mode: "remover", hint: "Seleccione la clase que quiere remover" },
];

function DocenteHorario() {
  const navigate = useNavigate();
  const { user } = useAuth();
  const calendarId = user?.calendarId;

  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [statusText, setStatusText] = useState("Seleccione acción");
  const [mode, setMode] = useState(null);

  const [showHelp, setShowHelp] = useState(false);
  const [showClassForm, setShowClassForm] = useState(false);
  const [courseName, setCourseName] = useState("");
  const [courseCode, setCourseCode] = useState("");

  const [blocks, setBlocks] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [loadError, setLoadError] = useState(null);

  useEffect(() => {
    const fetchBlocks = async () => {
      try {
        setIsLoading(true);
        setLoadError(null);
        const { data } = await calendarApi.getBlocks(calendarId);
        setBlocks(data);
      } catch (err) {
        setLoadError(err.message);
      } finally {
        setIsLoading(false);
      }
    };

    if (calendarId) fetchBlocks();
  }, [calendarId]);

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
      <Navbar role="Docente" name={user?.name || "NoName"} />

      <div className="docente-contenido">
        <h1>Hola, {user?.name || "NoName"}</h1>
        <p>Estás registrado como Docente.</p>

        <Card>
          <div className="acciones-docente">
            <button className="btn-qr" onClick={() => navigate("/generadorqr")}>
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
            <HelpButton onClick={() => setShowHelp(true)} />
          </div>
        </Card>

        {isLoading && <p className="horario-estado">Cargando horario...</p>}
        {loadError && <p className="horario-estado horario-error">{loadError}</p>}
      </div>

      <Modal open={showHelp} onClose={() => setShowHelp(false)} title="¿Cómo usar RUA?">
        <h3>Generar QR</h3>
        <p>Haz clic en el botón QR y selecciona una clase para generar su código.</p>
        <h3>Añadir clase</h3>
        <p>Usa el menú Opciones → Añadir clase, completa los datos y selecciona posición.</p>
        <h3>Clonar clase</h3>
        <p>Opciones → Clonar: selecciona la clase origen y luego la posición destino.</p>
        <h3>Editar clase</h3>
        <p>Doble clic en cualquier clase del calendario, o Opciones → Editar.</p>
        <h3>Mover clase</h3>
        <p>Opciones → Mover: selecciona la clase y luego la nueva posición.</p>
        <h3>Eliminar clase</h3>
        <p>Opciones → Remover: selecciona la clase y confirma la eliminación.</p>
      </Modal>

      <Modal open={showClassForm} onClose={() => setShowClassForm(false)} title="Crear Clase">
        <label>Código de la clase</label>
        <input
          type="text"
          value={courseCode}
          onChange={(e) => setCourseCode(e.target.value)}
          placeholder="Ej: INF221"
        />
        <label>Nombre de la clase</label>
        <input
          type="text"
          value={courseName}
          onChange={(e) => setCourseName(e.target.value)}
          placeholder="Ej: Programación"
        />
        <button className="btn-qr" onClick={handleContinueAddClass}>
          Continuar
        </button>
      </Modal>

      <Schedule
        mode={mode}
        setMode={setMode}
        className={courseName}
        courseCode={courseCode}
        blocks={blocks}
      />
    </div>
  );
}

export default DocenteHorario;
