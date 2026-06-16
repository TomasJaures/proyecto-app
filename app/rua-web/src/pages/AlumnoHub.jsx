import Navbar from "../components/navbar";
import Card from "../components/card";
import { useNavigate } from "react-router-dom";

function Dashboard() {

  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem("user"));

  return (

    <div className="dashboard-layout">

      <Navbar
        rol="Alumno"
        nombre={user?.name || "NoName"}
      />

      <main className="pagina-dashboard">

        <div className="bienvenida">

          <h1>
            Hola, {user?.name || "NoName"}
          </h1>

          <p>
            Estás registrado como Alumno.
          </p>

        </div>

        <Card>

          <h2 className="titulo-dashboard">
            Selecciona la opción que necesitas:
          </h2>
            {/* BOTONES */}
          <div className="contenedor-botones">

            <button className="boton-dashboard activo"
            onClick={() => navigate("/qrattempt")}
            >

              <img
                src="/assets/qr-icon.svg"
                alt="Escaner"
              />

              <span>
                Escanear QR
              </span>

            </button>

            <button className="boton-dashboard" onClick={() => navigate("/alumnohorario")}>

              <img
                src="/assets/asistencia-icon.svg"
                alt="Cursos"
              />

              <span>
                Ver Asistencia
              </span>

            </button>

          </div>

        </Card>

      </main>

      <footer className="footer">
        Sitio web no afiliado con la UFRO
      </footer>

    </div>
  );
}

export default Dashboard;