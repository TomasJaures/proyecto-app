import { useState } from "react";
import Horario from "../components/horario";
import RuaAside from "../components/rua-aside";
import Navbar from "../components/navbar";
import { useNavigate } from "react-router-dom";
import Card from "../components/card";

function DocenteHub() {

    const navigate = useNavigate();

    const [abierto, setAbierto] = useState(false);
    const [seleccion, setSeleccion] = useState("Seleccione acción");

    function generateQR() {
        setSeleccion("Generar QR");
        navigate("/generadorqr");
    }

    function seleccionarOpcion(opcion) {
        setSeleccion(opcion);
        setAbierto(false);
    }

    return (
        <div>
            <Navbar />

            <div className="docente-contenido">

                <h1>
                    Hola,
                </h1>

                <p>
                    Estás registrado como Docente.
                </p>

                <Card>

                    <div className="acciones-docente">

                        <button
                            className="btn-qr"
                            onClick={generateQR}
                        >
                            Generar QR
                        </button>

                        <div className="dropdown">

                            <button
                                className="btn-opciones"
                                onClick={() => setAbierto(!abierto)}
                            >
                                Opciones ▼
                            </button>

                            {abierto && (
                                <div className="dropdown-menu">

                                    <div
                                        onClick={() => seleccionarOpcion(
                                            "Seleccione un bloque vacío para añadir la clase"
                                        )}
                                    >
                                        Añadir Clase
                                    </div>

                                    <div
                                        onClick={() => seleccionarOpcion(
                                            "Seleccione clase para clonar"
                                        )}
                                    >
                                        Clonar Clase
                                    </div>

                                    <div
                                        onClick={() => seleccionarOpcion(
                                            "Seleccione clase a editar"
                                        )}
                                    >
                                        Editar Clase
                                    </div>

                                    <div
                                        onClick={() => seleccionarOpcion(
                                            "Seleccione clase para moverla"
                                        )}
                                    >
                                        Mover Clase
                                    </div>

                                    <div
                                        onClick={() => seleccionarOpcion(
                                            "Seleccione la clase que quiere remover"
                                        )}
                                    >
                                        Remover Clase
                                    </div>

                                </div>
                            )}

                        </div>

                        <span className="texto-seleccion">
                            {seleccion}
                        </span>

                    </div>

                </Card>

            </div>

            <Horario />

        </div>
    );
}

export default DocenteHub;