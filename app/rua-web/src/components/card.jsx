//función que retorna un div con la className="card" para luego reutilizar

function Card({ children })
{

    return <div className="card">
        {children}
    </div>

}

export default Card;