import Navbar from "../components/navbar";
import Card from "../components/card";
import { useNavigate } from "react-router-dom";

function Dashboard() {

  const user = JSON.parse(localStorage.getItem("user"));
  
  const navigate = useNavigate();

  
  return (

    <div className="dashboard-layout">

      <Navbar
        rol="Docente"
        nombre={user?.name || "NoName"}
      />

      <main className="pagina-dashboard">

        <div className="bienvenida">

          <h1>
            Hola, {user?.name || "NoName"}
          </h1>

          <p>
            Estás registrado como Docente.
          </p>

        </div>

        <Card>
            {/* BOTONES */}
          <h2 className="titulo-dashboard">
            Selecciona la opción que necesitas:
          </h2>

          <div className="contenedor-botones">

            <button className="boton-dashboard activo"
            onClick={() => navigate("/docenteadmin")}
            >

              <img
                src="/assets/asistencia-iconwhite.svg"
                alt="Escaner"
              />

              <span>
                Asignaturas
              </span>

            </button>

            <button className="boton-dashboard" onClick={() => navigate("/docentehorario")}>

              <img
                src="/assets/tabla-icon.svg"
                alt="Cursos"
              />

              <span>
                Calendario
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