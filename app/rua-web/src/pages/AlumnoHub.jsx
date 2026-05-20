import Navbar from "../components/navbar";
import Card from "../components/card";

function Dashboard() {

  return (
        
    <div className="pagina-dashboard">

      <Card>

        <h1 className="titulo-dashboard">
          Selecciona una opción
        </h1>

        <div className="contenedor-botones">

          <button className="boton-dashboard">

            <img
              src="https://cdn-icons-png.flaticon.com/512/3135/3135755.png"
              alt="Asistencia"
            />

            <span>
              Registrar Asistencia
            </span>

          </button>

          <button className="boton-dashboard">

            <img
              src="https://cdn-icons-png.flaticon.com/512/942/942748.png"
              alt="Cursos"
            />

            <span>
              Ver Cursos
            </span>

          </button>

        </div>

      </Card>

    </div>
  );
}

export default Dashboard;