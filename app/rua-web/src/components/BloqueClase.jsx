function BloqueClase({
    codigo,
    nombre,
    estado
}) {

    return (

        <div className={`bloque-clase ${estado}`}>

            <strong>
                {codigo}
            </strong>

            <span>
                {nombre}
            </span>

        </div>

    );

}

export default BloqueClase;