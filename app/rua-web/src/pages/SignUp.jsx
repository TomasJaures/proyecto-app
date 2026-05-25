import { useNavigate } from "react-router-dom";
import { useState } from "react";
import RuaAside from "../components/rua-aside.jsx";
import Card from "../components/card.jsx";
import { Link } from "react-router-dom";
import axios from "axios";
import { BACKEND_URL, FRONTEND_URL, } from "../config.js";

function SignUp() {

  const navigate = useNavigate();

  const [aceptado, setAceptado] = useState(false);
  const [nombre, setNombre] = useState("");
  const [apellido1, setApellido1] = useState("");
  const [apellido2, setApellido2] = useState("");
  const [correo, setCorreo] = useState("");
  const [contrasena, setContrasena] = useState("");

  async function onConfirmarClick() {

    try {

      const respuesta = await axios.post(
        BACKEND_URL + "/account/create",
        {
          nombre: nombre,
          apellido1: apellido1,
          apellido2: apellido2,
          correo: correo,
          contrasena: contrasena
        }
      );


      console.log("RESPUESTA:", respuesta.data);
      navigate("/email_sended")
    } catch (error) {

      console.log("ERROR:", error);

    }
  }

  return (
    <div className="pagina">

      <RuaAside>
        Crea tu cuenta para empezar a registrar tu
        asistencia en la universidad de la frontera
      </RuaAside>

      {/* PANEL DERECHO */}
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
            value={nombre}
            onChange={(e) => setNombre(e.target.value)}
          />

          <label>PRIMER APELLIDO</label>

          <input
            type="text"
            placeholder="Farías"
            value={apellido1}
            onChange={(e) => setApellido1(e.target.value)}
          />

          <label>SEGUNDO APELLIDO</label>

          <input
            type="text"
            placeholder="Ravanal"
            value={apellido2}
            onChange={(e) => setApellido2(e.target.value)}
          />

          <label>CORREO INSTITUCIONAL</label>

          <input
            type="text"
            placeholder="ejemplo@ufromail.cl"
            value={correo}
            onChange={(e) => setCorreo(e.target.value)}
          />

          <label>CLAVE RUA</label>

          <input
            type="password"
            placeholder="Minimo 8 Caracteres"
            value={ contrasena }
            onChange={(e) => setContrasena(e.target.value)}
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