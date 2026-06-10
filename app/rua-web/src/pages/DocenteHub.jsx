import { useState } from "react";
import Horario from "../components/horario";
import Navbar from "../components/navbar";
import { useNavigate } from "react-router-dom";
import Card from "../components/card";

function DocenteHub() {

    const navigate = useNavigate();

    const [abierto, setAbierto] = useState(false);
    const [seleccion, setSeleccion] = useState("Seleccione acción");
    const [mostrarAyuda, setMostrarAyuda] = useState(false);
    const [modo, setModo] = useState(null);

    const [mostrarModalClase, setMostrarModalClase] = useState(false);

    const [nombreClase, setNombreClase] = useState("");
    const [codigoClase, setCodigoClase] = useState("");


    function generateQR() {
        setSeleccion("Generar QR");
        navigate("/generadorqr");
    }

    function seleccionarOpcion(opcion) {
        setSeleccion(opcion);
        setAbierto(false);
    }

    function continuarAgregarClase() {

    if (
        nombreClase.trim() === "" ||
        codigoClase.trim() === ""
    ) {
        alert("Complete todos los campos");
        return;
    }

    setMostrarModalClase(false);

    setSeleccion(
        "Seleccione un bloque vacío para añadir la clase"
    );

    setModo("añadir");
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
                                        onClick={() => {
                                            setAbierto(false);
                                            setMostrarModalClase(true);
                                        }}
                                    >
                                        Añadir Clase
                                    </div>

                                    <div
                                        onClick={() => {
                                            seleccionarOpcion(
                                                "Seleccione la clase que desea clonar"
                                            );
                                            setModo("clonar");
                                        }}
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
                                        onClick={() => {
                                            seleccionarOpcion(
                                                "Seleccione la clase que desea mover"
                                            );
                                            setModo("mover");
                                        }}
                                    >
                                        Mover Clase
                                    </div>

                                    <div
                                        onClick={() => {
                                            seleccionarOpcion(
                                                "Seleccione la clase que quiere remover"
                                            );
                                            setModo("remover");
                                        }}
                                    >
                                        Remover Clase
                                    </div>

                                </div>
                            )}

                        </div>

                        <span className="texto-seleccion">
                            {seleccion}
                        </span>

                        <button
                            className="btn-ayuda"
                            onClick={() => setMostrarAyuda(true)}
                        >
                            ?
                        </button>

                    </div>

                </Card>

            </div>

            {mostrarAyuda && (

                <div
                    className="modal-overlay"
                    onClick={() => setMostrarAyuda(false)}
                >

                    <div
                        className="modal-ayuda"
                        onClick={(e) => e.stopPropagation()}
                    >

                        <button
                            className="cerrar-modal"
                            onClick={() => setMostrarAyuda(false)}
                        >
                            ✕
                        </button>

                        <h2>¿Cómo usar RUA?</h2>

                        <h3>Generar QR</h3>
                        <p>
                            Haz clic en el botón QR y selecciona una clase para generar su código.
                        </p>

                        <h3>Añadir clase</h3>
                        <p>
                            Usa el menú Opciones → Añadir clase, completa los datos y selecciona posición.
                        </p>

                        <h3>Clonar clase</h3>
                        <p>
                            Opciones → Clonar: selecciona la clase origen y luego la posición destino.
                        </p>

                        <h3>Editar clase</h3>
                        <p>
                            Doble clic en cualquier clase del calendario, o Opciones → Editar.
                        </p>

                        <h3>Mover clase</h3>
                        <p>
                            Opciones → Mover: selecciona la clase y luego la nueva posición.
                        </p>

                        <h3>Eliminar clase</h3>
                        <p>
                            Opciones → Remover: selecciona la clase y confirma la eliminación.
                        </p>

                    </div>

                </div>

            )}

            {mostrarModalClase && (

                <div className="modal-overlay">

                    <div className="modal-ayuda">

                        <button
                            className="cerrar-modal"
                            onClick={() => setMostrarModalClase(false)}
                        >
                            ✕
                        </button>

                        <h2>
                            Crear Clase
                        </h2>

                        <label>
                            Código de la clase
                        </label>

                        <input
                            type="text"
                            value={codigoClase}
                            onChange={(e) =>
                                setCodigoClase(e.target.value)
                            }
                            placeholder="Ej: INF221"
                        />

                        <label>
                            Nombre de la clase
                        </label>

                        <input
                            type="text"
                            value={nombreClase}
                            onChange={(e) =>
                                setNombreClase(e.target.value)
                            }
                            placeholder="Ej: Programación"
                        />

                        <button
                            className="btn-qr"
                            onClick={continuarAgregarClase}
                        >
                            Continuar
                        </button>

                    </div>

                </div>

            )}

            <Horario
                modo={modo}
                setModo={setModo}
                nombreClase={nombreClase}
                codigoClase={codigoClase}
            />

        </div>
    );
}

export default DocenteHub;