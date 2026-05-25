import Navbar from "../components/navbar";
import Card from "../components/card";
import { useNavigate } from "react-router-dom";
import HelpButton from "../components/helpButton";


function Dashboard() {

  const navigate = useNavigate();
  function scanQR(){
    navigate("/qrattempt")
  }
  function seeAsistance(){
    navigate("/alumno_asistance")
  }


  return (
        
    <div className="pagina-dashboard">
      <HelpButton helpText="Elige la opcion que desees" />
      <Card>

        <h1 className="titulo-dashboard">
          Selecciona una opción
        </h1>

        <div className="contenedor-botones">

          <button 
          className="boton-dashboard"
          onClick={scanQR}
          >

            <img
              src="https://cdn-icons-png.flaticon.com/512/6380/6380112.png"
              alt="Asistencia"
            />

            <span>
              Escanear QR
            </span>

          </button>

          <button className="boton-dashboard">

            <img
              src="https://cdn-icons-png.flaticon.com/512/3567/3567769.png"
              alt="Cursos"
            />

            <span>
              Ver asistencia
            </span>

          </button>

        </div>

      </Card>

    </div>
  );
}

export default Dashboard;