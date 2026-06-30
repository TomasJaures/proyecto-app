import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar.jsx";
import Card from "../components/Card.jsx";
import Schedule from "../components/Schedule.jsx";
import { useAuth } from "../hooks/useAuth.js";

function AlumnoHorario() {
  const navigate = useNavigate();
  const { user } = useAuth();

  const [mode, setMode] = useState(null);
  const [courseName, setCourseName] = useState("");
  const [courseCode, setCourseCode] = useState("");

  return (
    <div>
      <Navbar role="Alumno" name={user?.name || "NoName"} />

      <div className="alumno-contenido">
        <Card>
          <h1>Hola,</h1>
          <p>Estás registrado como Alumno.</p>
        </Card>
      </div>

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
