import { useState } from "react";
import Horario from "../components/horario";
import Navbar from "../components/navbar";
import { useNavigate } from "react-router-dom";
import Card from "../components/card";

function AlumnoHorario() {

    const navigate = useNavigate();

    const [abierto, setAbierto] = useState(false);
    const [seleccion, setSeleccion] = useState("Seleccione acción");
    const [mostrarAyuda, setMostrarAyuda] = useState(false);
    const [modo, setModo] = useState(null);

    const [mostrarModalClase, setMostrarModalClase] = useState(false);

    const [nombreClase, setNombreClase] = useState("");
    const [codigoClase, setCodigoClase] = useState("");

    

    return (
        <div>

            <Navbar />

            <div className="alumno-contenido">
                <Card>

                <h1>
                    Hola,
                </h1>

                <p>
                    Estás registrado como Alumno.
                </p>

                

  

                   

                </Card>

            </div>


            <Horario
                modo={modo}
                setModo={setModo}
                nombreClase={nombreClase}
                codigoClase={codigoClase}
            />

            <button
          className="boton-hub"
          onClick={() => navigate("/alumnohub")}
        >
          Volver al Hub
        </button>


        

        </div>

    );



}

export default AlumnoHorario;