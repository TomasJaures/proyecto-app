import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import SidePanel from "../components/SidePanel.jsx";
import Card from "../components/Card.jsx";
import MobileHeader from "../components/MobileHeader.jsx";
import { useAuth } from "../hooks/useAuth.js";
import { authApi } from "../services/apiService.js";

const ROLE_PLACEHOLDERS = {
  estudiante: "ejemplo@ufromail.cl",
  docente: "ejemplo@ufrontera.cl",
};

function LogIn() {
  const navigate = useNavigate();
  const { login } = useAuth();

  const [role, setRole] = useState("docente");
  const [mail, setMail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async () => {
    try {
      const { data } = await authApi.login({ mail, hashedPassword: password });
      login(data);

      if (data.role === "student") {
        navigate("/alumnohub");
      } else {
        navigate("/docentehub");
      }
    } catch (error) {
      console.error("Login error:", error);
    }
  };

  return (
    <div className="pagina">
      <SidePanel>
        Plataforma de registro de asistencia para la Universidad de la Frontera.
        Gestiona tu asistencia con facilidad.
      </SidePanel>

      <main className="derecha">
        <MobileHeader />

        <Card>
          <h1>Log In</h1>
          <p>Ingresa con tus credenciales institucionales</p>

          <div className="grupo-botones">
            <button
              onClick={() => setRole("estudiante")}
              className={role === "estudiante" ? "activo" : "inactivo"}
            >
              Soy Alumno
            </button>
            <button
              onClick={() => setRole("docente")}
              className={role === "docente" ? "activo" : "inactivo"}
            >
              Soy Docente
            </button>
          </div>

          <label htmlFor="login-email">MAIL INSTITUCIONAL</label>
          <input
            id="login-email"
            type="text"
            placeholder={ROLE_PLACEHOLDERS[role]}
            value={mail}
            onChange={(e) => setMail(e.target.value)}
          />

          <label htmlFor="login-password">CLAVE RUA</label>
          <input
            id="login-password"
            type="password"
            placeholder="••••••••"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <button className="confirmar" onClick={handleSubmit}>
            Confirmar
          </button>

          <hr />
          <p className="signup">
            ¿No te has registrado aún?
            <Link to="/signup"> Sign Up</Link>
          </p>
        </Card>
      </main>
    </div>
  );
}

export default LogIn;
