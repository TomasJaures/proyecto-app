import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar.jsx";
import Card from "../components/Card.jsx";
import AppFooter from "../components/AppFooter.jsx";
import { useAuth } from "../hooks/useAuth.js";

const DASHBOARD_ACTIONS = [
  {
    id: "scan-qr",
    icon: "/assets/qr-icon.svg",
    label: "Escanear QR",
    route: "/qrattempt",
    active: true,
  },
  {
    id: "view-attendance",
    icon: "/assets/asistencia-icon.svg",
    label: "Ver Asistencia",
    route: "/alumnohorario",
    active: false,
  },
];

function AlumnoHub() {
  const navigate = useNavigate();
  const { user } = useAuth();
  const displayName = user?.name || "NoName";

  return (
    <div className="dashboard-layout">
      <Navbar role="Alumno" name={displayName} />

      <main className="pagina-dashboard">
        <div className="bienvenida">
          <h1>Hola, {displayName}</h1>
          <p>Estás registrado como Alumno.</p>
        </div>

        <Card>
          <h2 className="titulo-dashboard">Selecciona la opción que necesitas:</h2>
          <div className="contenedor-botones">
            {DASHBOARD_ACTIONS.map((action) => (
              <button
                key={action.id}
                className={`boton-dashboard ${action.active ? "activo" : ""}`}
                onClick={() => navigate(action.route)}
              >
                <img src={action.icon} alt={action.label} />
                <span>{action.label}</span>
              </button>
            ))}
          </div>
        </Card>
      </main>

      <AppFooter />
    </div>
  );
}

export default AlumnoHub;
