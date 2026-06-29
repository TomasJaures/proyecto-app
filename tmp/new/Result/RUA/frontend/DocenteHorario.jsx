import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import Horario from "../components/horario";
import Navbar from "../components/navbar";
import Card from "../components/card";

// ID del calendario del docente autenticado.
// Ajusta esto según cómo manejes la sesión en tu proyecto
// (ej. desde contexto de auth, localStorage, JWT, etc.)
const CALENDAR_ID = 1;

function DocenteHorario() {
    const navigate = useNavigate();

    const [abierto, setAbierto] = useState(false);
    const [seleccion, setSeleccion] = useState("Seleccione acción");
    const [mostrarAyuda, setMostrarAyuda] = useState(false);
    const [modo, setModo] = useState(null);

    const [mostrarModalClase, setMostrarModalClase] = useState(false);
    const [nombreClase, setNombreClase] = useState("");
    const [codigoClase, setCodigoClase] = useState("");

    // ── NUEVO: estado para bloques del calendario ──────────────────
    const [bloques, setBloques] = useState([]);
    const [cargando, setCargando] = useState(true);
    const [errorBloques, setErrorBloques] = useState(null);

    // Carga los bloques al montar el componente
    useEffect(() => {
        const fetchBloques = async () => {
            try {
                setCargando(true);
                setErrorBloques(null);

                const response = await fetch(
                    `/api/calendars/${CALENDAR_ID}/blocks`
                );

                if (!response.ok) {
                    throw new Error(
                        `Error al cargar el horario (${response.status})`
                    );
                }

                const data = await response.json();
                setBloques(data);
            } catch (err) {
                setErrorBloques(err.message);
            } finally {
                setCargando(false);
            }
        };

        fetchBloques();
    }, []);
    // ───────────────────────────────────────────────────────────────

    const seleccionarModo = (texto, nuevoModo) => {
        setSeleccion(texto);
        setModo(nuevoModo);
        setAbierto(false);
    };

    const continuarAgregarClase = () => {
        if (!nombreClase.trim() || !codigoClase.trim()) {
            alert("Complete todos los campos");
            return;
        }

        setMostrarModalClase(false);
        setSeleccion("Seleccione un bloque vacío para añadir la clase");
        setModo("añadir");
    };

    const opciones = [
        {
            texto: "Añadir Clase",
            accion: () => {
                setAbierto(false);
                setMostrarModalClase(true);
            }
        },
        {
            texto: "Clonar Clase",
            accion: () =>
                seleccionarModo(
                    "Seleccione la clase que desea clonar",
                    "clonar"
                )
        },
        {
            texto: "Editar Clase",
            accion: () =>
                seleccionarModo("Seleccione clase a editar", "editar")
        },
        {
            texto: "Mover Clase",
            accion: () =>
                seleccionarModo(
                    "Seleccione la clase que desea mover",
                    "mover"
                )
        },
        {
            texto: "Remover Clase",
            accion: () =>
                seleccionarModo(
                    "Seleccione la clase que quiere remover",
                    "remover"
                )
        }
    ];

    return (
        <div>
            <Navbar />

            <div className="docente-contenido">
                <h1>Hola,</h1>
                <p>Estás registrado como Docente.</p>

                <Card>
                    <div className="acciones-docente">
                        <button
                            className="btn-qr"
                            onClick={() => navigate("/generadorqr")}
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
                                    {opciones.map((opcion) => (
                                        <div
                                            key={opcion.texto}
                                            onClick={opcion.accion}
                                        >
                                            {opcion.texto}
                                        </div>
                                    ))}
                                </div>
                            )}
                        </div>

                        <span className="texto-seleccion">{seleccion}</span>

                        <button
                            className="btn-ayuda"
                            onClick={() => setMostrarAyuda(true)}
                        >
                            ?
                        </button>
                    </div>
                </Card>

                {/* ── NUEVO: indicadores de carga / error ── */}
                {cargando && (
                    <p className="horario-estado">Cargando horario...</p>
                )}
                {errorBloques && (
                    <p className="horario-estado horario-error">
                        {errorBloques}
                    </p>
                )}
                {/* ─────────────────────────────────────────── */}
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
                            Haz clic en el botón QR y selecciona una clase
                            para generar su código.
                        </p>

                        <h3>Añadir clase</h3>
                        <p>
                            Usa el menú Opciones → Añadir clase, completa los
                            datos y selecciona posición.
                        </p>

                        <h3>Clonar clase</h3>
                        <p>
                            Opciones → Clonar: selecciona la clase origen y
                            luego la posición destino.
                        </p>

                        <h3>Editar clase</h3>
                        <p>
                            Doble clic en cualquier clase del calendario, o
                            Opciones → Editar.
                        </p>

                        <h3>Mover clase</h3>
                        <p>
                            Opciones → Mover: selecciona la clase y luego la
                            nueva posición.
                        </p>

                        <h3>Eliminar clase</h3>
                        <p>
                            Opciones → Remover: selecciona la clase y confirma
                            la eliminación.
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

                        <h2>Crear Clase</h2>

                        <label>Código de la clase</label>
                        <input
                            type="text"
                            value={codigoClase}
                            onChange={(e) => setCodigoClase(e.target.value)}
                            placeholder="Ej: INF221"
                        />

                        <label>Nombre de la clase</label>
                        <input
                            type="text"
                            value={nombreClase}
                            onChange={(e) => setNombreClase(e.target.value)}
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

            {/* ── NUEVO: se pasan bloques al componente Horario ── */}
            <Horario
                modo={modo}
                setModo={setModo}
                nombreClase={nombreClase}
                codigoClase={codigoClase}
                bloques={bloques}          {/* lista de CalendarBlockDTO */}
            />
        </div>
    );
}

export default DocenteHorario;
