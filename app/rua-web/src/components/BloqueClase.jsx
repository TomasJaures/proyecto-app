function BloqueClase({ estado = "pendiente", nombre = "Programación" }) {

    return (
        <div className={`bloque-clase ${estado}`}>
            <span>{nombre}</span>
        </div>
    );
}

export default BloqueClase;