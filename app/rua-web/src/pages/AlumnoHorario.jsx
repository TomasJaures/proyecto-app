import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar.jsx";
import Card from "../components/Card.jsx";
import Schedule from "../components/Schedule.jsx";
import { useAuth } from "../hooks/useAuth.js";
import { calendarApi } from "../services/apiService.js";
import { calendarMockApi } from "../services/mockServices.js";
import { weekStorage } from "../services/storeService.js";

function AlumnoHorario() {
  const navigate = useNavigate();
  const { user } = useAuth();
  const calendarId = user?.calendarId;
  const [classInfo, setClassInfo] = useState([]);
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

        const { mockBlocks: blocksData } = await calendarMockApi.getBlocks(calendarId);
        const { mockClasses: classesData } = await calendarMockApi.getActualClasses(calendarId);
        setBlocks(blocksData);
        weekStorage.storeActualWeek(classesData.currentWeek); //Guardar semana actual
        
        const blocksMap = new Map();
        blocksData.forEach(block => {
          blocksMap.set(block.blockId, {
            ...block,
            color: "white"
          });
        });
        
        
        // Colocar a los bloques un TimeState
        const ci = classesData.classInfoDTOS;
        for (let i = 0; i < ci.length; i++) {
          const cb = blocksMap.get(ci[i].blockId);
          cb.timeState = ci[i].timeState
        }
        
        const blocksWithColor = Array.from(blocksMap.values());
        setBlocks(blocksWithColor);
      } catch (err) {
        console.log("ERROR")
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
      <Navbar
          role="Alumno"
          name={user?.name}
          tutorial="alumnoHorario"
      />

      <div className="alumno-contenido">
        <Card>
          <h1>Hola, {user?.name || "NoName"}</h1>
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
        blocks={blocks}
      />

      <button className="boton-hub" onClick={() => navigate("/alumnohub")}>
        Volver al Hub
      </button>
    </div>
  );
}

export default AlumnoHorario;
