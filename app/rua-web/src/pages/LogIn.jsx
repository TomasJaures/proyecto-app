import RuaAside from "../components/rua-aside.jsx";
import Card from "../components/card.jsx";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { BACKEND_URL, FRONTEND_URL } from "../config.js";
import axios from "axios";

function LogIn() {

  const [rol, setRol] = useState("docente");
  const [correo, setCorreo] = useState("");
  const [contrasena, setContrasena] = useState("");
  const navigate = useNavigate();

  async function onConfirmarClick() {

  try {

    const respuesta = await axios.post(
      BACKEND_URL + "/account/login",
      {
        //rol: rol,
        correo: correo,
        contrasena: contrasena
      }
    );

    //Usuario esta verificado ()
    console.log("RESPUESTA DEL SERVIDOR:", respuesta.data);
    navigate("/alumnohub");

  } catch (error) {
    if (error.response?.status === 401){
      console.log("Correo no verificado... Error:", error);
    } else {
      console.log("ERROR externo:", error);
    }
    

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
              onClick={() => setRol("estudiante")}
              className={ rol === "estudiante" ? "activo" : "inactivo" }
            >
              Soy Alumno
            </button>

            <button
              onClick={() => setRol("docente")}
              className={ rol === "docente" ? "activo" : "inactivo" }
            >
              Soy Docente
            </button>

          </div>

          {/* INPUTS */}
          <label>CORREO INSTITUCIONAL</label>

          <input
            type="text"
            placeholder={ rol === "estudiante" ? "ejemplo@ufromail.cl" : "ejemplo@ufrontera.cl" }
            value={correo}

            onChange={(e) => setCorreo(e.target.value)}
          />

          <label>CLAVE RUA</label>

          <input type="password" placeholder="••••••••"
          value={contrasena}

          onChange={(e) => setContrasena(e.target.value)}
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