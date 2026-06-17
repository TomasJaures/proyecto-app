import { useState } from "react";
import Navbar from "../components/navbar";
import AsignaturaCard from "../components/AsignaturaCard";


function DocenteAdmin(){

    const [asignaturas, setAsignaturas] = useState([
        {
            id:1,
            nombre:"Programación",
            codigo:"INF221",
            modulos:[
                "Introducción",
                "Estructuras de datos"
            ]
        },

        {
            id:2,
            nombre:"Bases de Datos",
            codigo:"INF310",
            modulos:[
                "Modelo entidad relación"
            ]
        }
    ]);


    const [mostrarPopup,setMostrarPopup] = useState(false);

    const [modoEliminar,setModoEliminar] = useState(false);

    const [seleccionadas,setSeleccionadas] = useState([]);


    const [pendiente,setPendiente] = useState(false);



    function agregarAsignatura(nombre,codigo){

        setAsignaturas([
            ...asignaturas,
            {
                id:Date.now(),
                nombre,
                codigo,
                modulos:[]
            }
        ]);

        setMostrarPopup(false);
        setPendiente(true);
    }



    function eliminarSeleccionadas(){

        if(
            !window.confirm(
                "¿Eliminar asignaturas seleccionadas?"
            )
        ){
            return;
        }


        setAsignaturas(
            asignaturas.filter(
                a =>
                !seleccionadas.includes(a.id)
            )
        );


        setSeleccionadas([]);
        setModoEliminar(false);
        setPendiente(true);
    }



    return(

        <div className="pagina-asignaturas">


            <Navbar/>


            <div className="titulo-asignaturas">

                <h1>
                    Gestionar asignaturas
                </h1>

            </div>



            <div className="toolbar">


                <button
                    onClick={()=>
                        setMostrarPopup(true)
                    }
                >
                    + Añadir
                </button>



                <button
                    onClick={()=>
                        setModoEliminar(true)
                    }
                >
                    Eliminar
                </button>


            </div>



            <div className="contenedor-asignaturas">


                {asignaturas.map(asignatura=>(


                    <AsignaturaCard

                        key={asignatura.id}

                        asignatura={asignatura}

                        modoEliminar={modoEliminar}

                        seleccionadas={seleccionadas}

                        setSeleccionadas={setSeleccionadas}

                        setAsignaturas={setAsignaturas}

                        setPendiente={setPendiente}

                    />


                ))}


            </div>



            {
                modoEliminar && (

                    <button

                        className="confirmar-eliminar"

                        onClick={eliminarSeleccionadas}

                    >
                        Confirmar eliminación
                    </button>

                )
            }



            {
                pendiente && (

                    <div className="acciones-cambios">

                        <button>
                            Guardar
                        </button>


                        <button
                            onClick={()=>
                                setPendiente(false)
                            }
                        >
                            Cancelar
                        </button>


                    </div>

                )
            }





            {
                mostrarPopup && (

                <PopupAsignatura

                    cerrar={()=>
                        setMostrarPopup(false)
                    }

                    agregar={agregarAsignatura}

                />

                )
            }



        </div>

    )

}


function PopupAsignatura({cerrar,agregar}){


    const [nombre,setNombre]=useState("");
    const [codigo,setCodigo]=useState("");



    return(

        <div className="modal-overlay">


            <div className="popup">


                <h2>
                    Añadir asignatura
                </h2>



                <input
                    placeholder="Nombre"
                    value={nombre}
                    onChange={
                        e=>setNombre(e.target.value)
                    }
                />


                <input
                    placeholder="Código"
                    value={codigo}
                    onChange={
                        e=>setCodigo(e.target.value)
                    }
                />



                <button
                    onClick={()=>
                        agregar(nombre,codigo)
                    }
                >
                    Añadir
                </button>


                <button
                    onClick={cerrar}
                >
                    Cancelar
                </button>
            </div>
        </div>
    )
}


export default DocenteAdmin;