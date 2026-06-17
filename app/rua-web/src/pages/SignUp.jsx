import { useState } from "react";
import RuaAside from "../components/rua-aside.jsx";
import Card from "../components/card.jsx";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { BACKEND_URL } from "../config.js";
import { Navigate } from "react-router-dom";

function SignUp() {

  const navigate = useNavigate();

  const [aceptado, setAceptado] = useState(false);
  
  const [userName, setNombre] = useState("");
  const [lastName1, setApellido1] = useState("");
  const [lastName2, setApellido2] = useState("");
  const [mail, setCorreo] = useState("");
  const [hashedPassword, setClave] = useState("");

  async function onConfirmarClick() {

    try {

      const respuesta = await axios.post(
        BACKEND_URL + "/account/create",
        {
          userName: userName,
          lastName1: lastName1,
          lastName2: lastName2,
          mail: mail,
          hashedPassword: hashedPassword
        }
      );
      
      // Redirigir al componente de confirmación
      navigate("/emailsended");

    } catch (error) {

      console.log("ERROR:", error);
      navigate("/error");

    }
  }

  return (
    <div className="pagina">

      <RuaAside>
        Crea tu cuenta para empezar a registrar tu
        asistencia en la universidad de la frontera
      </RuaAside>

      <main className="derecha">

        <div className="barra-mobile">
          <h1>RUA</h1>
        </div>

        <Card>

          <h1>Sign Up</h1>

          <p>Crea tu cuenta para poder acceder</p>

          {/* INPUTS */}

          <label>NOMBRE</label>

          <input
            type="text"
            placeholder="Alonso"
            value={userName}
            onChange={(e) => setNombre(e.target.value)}
          />

          <label>PRIMER APELLIDO</label>

          <input
            type="text"
            placeholder="Farías"
            value={lastName1}
            onChange={(e) => setApellido1(e.target.value)}
          />

          <label>SEGUNDO APELLIDO</label>

          <input
            type="text"
            placeholder="Ravanal"
            value={lastName2}
            onChange={(e) => setApellido2(e.target.value)}
          />

          <label>CORREO INSTITUCIONAL</label>

          <input
            type="text"
            placeholder="ejemplo@ufromail.cl"
            value={mail}
            onChange={(e) => setCorreo(e.target.value)}
          />

          <label>CLAVE RUA</label>

          <input
            type="password"
            placeholder="Minimo 8 Caracteres"
            value={hashedPassword}
            onChange={(e) => setClave(e.target.value)}
          />

          <div className="terminos">

            <input
              type="checkbox"
              checked={aceptado}
              onChange={(e) => setAceptado(e.target.checked)}
            />

            <label>
              Acepto los términos y condiciones
            </label>

          </div>

          <button
            disabled={!aceptado}
            className="confirmar"
            onClick={onConfirmarClick}
          >
            Confirmar
          </button>

          <hr />

          <p className="signup">
            ¿Ya tienes una cuenta?
            <Link to="/login"> Log In</Link>
          </p>

        </Card>
      </main>
    </div>
  );
}

export default SignUp;