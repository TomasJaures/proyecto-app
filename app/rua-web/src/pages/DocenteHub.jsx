import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar.jsx";
import Card from "../components/Card.jsx";
import AppFooter from "../components/AppFooter.jsx";
import { useAuth } from "../hooks/useAuth.js";



function DocenteHub() {
  const navigate = useNavigate();
  const { user } = useAuth();
  const displayName = user?.name || "NoName";
  const calendarId = user?.calendarId || -1;


  const DASHBOARD_ACTIONS = [
    {
      id: "subjects",
      icon: "/assets/asistencia-iconwhite.svg",
      label: "Asignaturas",
      route: "/docenteadmin",
      active: true,
    },
    {
      id: "calendar",
      icon: "/assets/tabla-icon.svg",
      label: "Calendario",
      route: `/docentehorario/${calendarId}`,
      active: false,
    },
  ];
  return (
    <div className="dashboard-layout">
      <Navbar role="Docente" name={displayName} />

      <main className="pagina-dashboard">
        <div className="bienvenida">
          <h1>Hola, {displayName}</h1>
          <p>Estás registrado como Docente.</p>
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

export default DocenteHub;
