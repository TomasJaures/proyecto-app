import { useState } from "react";
import RuaAside from "../components/rua-aside.jsx";
import Card from "../components/card.jsx";
import { Link } from "react-router-dom";

function SignUp() {
  const [aceptado, setAceptado] = useState(false);


  function onConfirmarClick()
  {

    console.log("datos");
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
          />
          <label>PRIMER APELLIDO</label>

          <input
            type="text"
            placeholder="Farías"
          />
          <label>SEGUNDO APELLIDO</label>

          <input
            type="text"
            placeholder="Ravanal"
          />
          <label>CORREO INSTITUCIONAL</label>

          <input
            type="text"
            placeholder="ejemplo@ufromail.cl"
          />

          <label>CLAVE RUA</label>

          <input type="password" placeholder="Minimo 8 Caracteres" />

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

          <button disabled={!aceptado}
          className="confirmar" onClick={onConfirmarClick}>Confirmar</button>

          <hr />
          <p className="signup">¿Ya tienes una cuenta?<a href="/login"> Log In</a></p>
        </Card>
      </main>
    </div>
  );
}

export default SignUp;