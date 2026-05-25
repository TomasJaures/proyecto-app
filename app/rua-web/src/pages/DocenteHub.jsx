import Horario from "../components/horario";
import RuaAside from "../components/rua-aside";
import Navbar from "../components/navbar";
import { useNavigate } from "react-router-dom";


function DocenteHub(){

    const navigate = useNavigate();
    function generateQR(){
        navigate("/generadorqr");
    }

    return (
        <div>
            <Navbar/>
            <button onClick={generateQR}>
            Generar QR
          </button>
            <Horario />
        </div>
    );
}

export default DocenteHub