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

  const [weekOffset, setWeekOffset] = useState(0);

  const asignClassesWithBlocks = async (week, blocksData, isActual) => {
    try {
      setIsLoading(true);
      setLoadError(null);

      let classesData;
      let attendanceData;

      if (isActual) {
        const response = await calendarMockApi.getActualClasses(calendarId);
        const attendance = await calendarMockApi.getActualAttendace(calendarId);
        classesData = response.mockClasses;
        attendanceData = attendance.mockAttendance;
      } else {
        const response = await calendarMockApi.getCurrentClasses(calendarId, week);
        const attendance = await calendarMockApi.getAttendanceByWeek(calendarId, week);
        classesData = response.mockClasses;
        attendanceData = attendance.mockAttendance;
      }

      weekStorage.storeActualWeek(classesData.currentWeek);
      console.log("Semana cargada:", classesData.currentWeek);
      console.log("Attendance data: ", attendanceData);
    
      const blocksMap = new Map();
      blocksData.forEach(block => {
        blocksMap.set(block.blockId, {
          ...block,
          color: "white",
          timeState: null,
          classId: null
        });
      });
      

      const ci = classesData.classInfoDTOS || [];
      ci.forEach(classInfo => {
        const cb = blocksMap.get(classInfo.blockId);
        if (cb) { 
          cb.timeState = classInfo.timeState;
          cb.classId = classInfo.classId; 
        }
      });

      const attendanceMap = new Map();
      const ad = attendanceData || [];
      ad.forEach(att => {

        attendanceMap.set(att.classId, att.hasAssisted);
      });


      blocksMap.forEach((block) => {
        if (block.classId !== null) {
          const hasAssisted = attendanceMap.get(block.classId);
          
          if (hasAssisted === true) {
            block.color = "green";
          } else if (hasAssisted === false) {
            block.color = "red"; 
          }
        }
      });
      
      
      const blocksWithColor = Array.from(blocksMap.values());
      setBlocks(blocksWithColor);

    } catch (error) {
      console.error("ERROR en asignClassesWithBlocks:", error);
      setLoadError("Error al procesar el horario de esta semana.");
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    const fetchBlocks = async () => {
      try {
        setIsLoading(true);
        setLoadError(null);

        const { mockBlocks: blocksData } = await calendarMockApi.getBlocks(calendarId);
        
        if (weekOffset === 0) {
          
          const { mockClasses: classesData } = await calendarMockApi.getActualClasses(calendarId);
          await asignClassesWithBlocks(classesData.currentWeek, blocksData, true);
        } else {
          
          const targetWeek = weekStorage.getCurrentWeek(); 
          await asignClassesWithBlocks(targetWeek, blocksData, false);
        }

      } catch (err) {
        console.error("ERROR en fetchBlocks:", err);
        setLoadError("Error al cargar el horario de esta semana.");
      } finally {
        setIsLoading(false);
      }
    };

    if (calendarId) {
      fetchBlocks();
    }
  }, [calendarId, weekOffset]); 

  
  const handlePrevWeek = () => {
    weekStorage.subtractCurrentWeek(); 
    setWeekOffset(prev => prev - 1);   
  };
  
  const handleNextWeek = () => {
    weekStorage.increaseCurrentWeek(); 
    setWeekOffset(prev => prev + 1);   
  };

  const handleCurrentWeek = () => {
    weekStorage.resetCurrentWeek();    
    setWeekOffset(0);                  
  };

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

      {/* Selector de Semanas */}
      <div className="semana-navegacion" style={{ display: "flex", justifyContent: "center", gap: "10px", margin: "15px 0" }}>
        <button onClick={handlePrevWeek} className="boton-hub">← Semana Anterior</button>
        <button onClick={handleCurrentWeek} disabled={weekOffset === 0} className="boton-hub">Semana Actual</button>
        <button onClick={handleNextWeek} className="boton-hub">Semana Siguiente →</button>
      </div>

      <div style={{ textAlign: "center", fontStyle: "italic", marginBottom: "10px" }}>
        {weekOffset === 0 ? "Mostrando Semana Actual" : `Desplazamiento: ${weekOffset} semana(s)`}
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