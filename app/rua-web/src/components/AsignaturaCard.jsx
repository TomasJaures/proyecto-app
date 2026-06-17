import {useState} from "react";


function AsignaturaCard({
    asignatura,
    modoEliminar,
    seleccionadas,
    setSeleccionadas,
    setAsignaturas,
    setPendiente
}){


const [editando,setEditando]=useState(false);

const [nombre,setNombre]=useState(
    asignatura.nombre
);



function cambiarNombre(){


setAsignaturas(prev=>

prev.map(a=>

a.id===asignatura.id

?
{
...a,
nombre
}

:a

)

);


setEditando(false);
setPendiente(true);

}




function agregarModulo(){


setAsignaturas(prev=>

prev.map(a=>

a.id===asignatura.id

?

{
...a,

modulos:[
...a.modulos,
"Nuevo módulo"
]

}

:a

)

);


setPendiente(true);

}




function eliminarModulo(index){


if(
!window.confirm(
"¿Eliminar módulo?"
)

)return;



setAsignaturas(prev=>

prev.map(a=>

a.id===asignatura.id

?

{
...a,

modulos:
a.modulos.filter(
(_,i)=>i!==index
)

}

:a

)

);


}




return(

<div className="card-asignatura">


{
modoEliminar && (

<input

type="checkbox"

checked={
seleccionadas.includes(asignatura.id)
}

onChange={()=>{

setSeleccionadas(prev=>

prev.includes(asignatura.id)

?

prev.filter(
x=>x!==asignatura.id
)

:

[
...prev,
asignatura.id
]

)

}}

/>

)

}




<div className="header-asignatura">


{
editando

?

<input

value={nombre}

onChange={
e=>setNombre(e.target.value)
}

/>

:

<h2>
{asignatura.nombre}
</h2>

}



<span>
{asignatura.codigo}
</span>


<button
onClick={()=>

editando

?

cambiarNombre()

:

setEditando(true)

}
>
Editar
</button>


<button>
☰
</button>


</div>





<div className="modulos">


{
asignatura.modulos.map(
(modulo,index)=>(


<div key={index}>


{modulo}


<button
onClick={()=>
eliminarModulo(index)
}
>
🗑️
</button>


</div>


)

)

}




<button
className="nuevo-modulo"

onClick={agregarModulo}

>

+ Añadir módulo

</button>



</div>



</div>


)

}


export default AsignaturaCard;