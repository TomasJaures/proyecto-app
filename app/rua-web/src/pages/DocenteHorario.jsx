import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar.jsx";
import Card from "../components/Card.jsx";
import Schedule from "../components/Schedule.jsx";

import { useAuth } from "../hooks/useAuth.js";
import { calendarApi } from "../services/apiService.js";
import { calendarMockApi } from "../services/mockServices.js"

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

  useEffect(() => {
    const fetchBlocks = async () => {
      try {
        setIsLoading(true);
        setLoadError(null);
        const {mockBlocks} = await calendarMockApi.getBlocks(calendarId);
        //const { data } = await calendarApi.getBlocks(calendarId);
        setBlocks(mockBlocks);
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
      <Navbar
          role="Docente"
          name={user?.name || "NoName"}
          tutorial="docenteHorario"
      />

      <div className="docente-contenido">
        <h1>Hola, {user?.name || "NoName"}</h1>
        <p>Estás registrado como Docente.</p>

        <Card>
          <div className="acciones-docente">
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
            
          </div>
        </Card>

        {isLoading && <p className="horario-estado">Cargando horario...</p>}
        {loadError && <p className="horario-estado horario-error">{loadError}</p>}
      </div>



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
