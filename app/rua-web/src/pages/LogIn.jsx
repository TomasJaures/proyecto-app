import RuaAside from "../components/rua-aside.jsx";
import Card from "../components/card.jsx";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import axios from "axios";
import { BACKEND_URL, FRONTEND_URL } from "../config.js";

function LogIn() {
  
  const navigate = useNavigate();
  const [role, setRole] = useState("docente");
  const [mail, setMail] = useState("");
  const [hashedPassword, setClave] = useState("");

  async function onConfirmarClick() {

    try {

      const respuesta = await axios.post(
        BACKEND_URL + "/account/login",
        {
          mail: mail,
          hashedPassword: hashedPassword
        }
      );

      localStorage.setItem(
        "user",
        JSON.stringify(respuesta.data)
      );

      const user = respuesta.data;

      if (user.role === "student") {
        navigate("/alumnohub");
      } else {
        navigate("/docentehub");
      }

    } catch (error) {
      console.log("ERROR:", error);
    }
  }

  return (
    <div className="pagina">
      
      <RuaAside>
        Plataforma de registro de asistencia para la
        Universidad de la Frontera.
        Gestiona tu asistencia con facilidad.
      </RuaAside>

      {/* PANEL DERECHO */}
      <main className="derecha">

        <div className="barra-mobile">
          <h1>RUA</h1>
        </div>

        <Card>

          <h1>Log In</h1>

          <p>Ingresa con tus credenciales institucionales</p>

          {/* BOTONES */}
          <div className="grupo-botones">

            <button
              onClick={() => setRole("estudiante")}
              className={ role === "estudiante" ? "activo" : "inactivo" }
            >
              Soy Alumno
            </button>

            <button
              onClick={() => setRole("docente")}
              className={ role === "docente" ? "activo" : "inactivo" }
            >
              Soy Docente
            </button>

          </div>

          {/* INPUTS */}
          <label>mail INSTITUCIONAL</label>

          <input
            type="text"
            placeholder={ role === "estudiante" ? "ejemplo@ufromail.cl" : "ejemplo@ufrontera.cl" }
            value={mail}

            onChange={(e) => setMail(e.target.value)}
          />

          <label>CLAVE RUA</label>

          <input type="password" placeholder="••••••••"
          value={hashedPassword}

          onChange={(e) => setClave(e.target.value)}
          />

          <button className="confirmar" onClick={onConfirmarClick}>Confirmar</button>

          <hr />
          <p className="signup">¿No te has registrado aún?<a href="/signup"> Sign Up</a></p>
        </Card>
      </main>
    </div>
  );
}

export default LogIn;