import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar.jsx";
import Card from "../components/Card.jsx";
import Schedule from "../components/Schedule.jsx";
import { useAuth } from "../hooks/useAuth.js";
import { calendarApi } from "../services/apiService.js";

function AlumnoHorario() {
  const navigate = useNavigate();
  const { user } = useAuth();
  const calendarId = user?.calendarId;

  const [mode, setMode] = useState(null);
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

    if (calendarId) {
      fetchBlocks();
    }
  }, [calendarId]);


  return (
    <div>
      <Navbar role="Alumno" name={user?.name || "NoName"} />

      <div className="alumno-contenido">
        <Card>
          <h1>Hola,</h1>
          <p>Estás registrado como Alumno.</p>
        </Card>
      </div>
      {isLoading && (
        <p className="horario-estado">
          Cargando horario...
        </p>
      )}

      {loadError && (
        <p className="horario-estado horario-error">
          {loadError}
        </p>
      )}

      <Schedule
        mode={mode}
        setMode={setMode}
        className={courseName}
        courseCode={courseCode}
      />

      <button className="boton-hub" onClick={() => navigate("/alumnohub")}>
        Volver al Hub
      </button>
    </div>
  );
}

export default AlumnoHorario;
