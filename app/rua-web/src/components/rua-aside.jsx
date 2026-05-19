function RuaAside({children})
{
    return <aside className="izquierda">

        <div className="contenido-izquierda">
          <h1>RUA</h1>
          <h2>Sistema de Asistencia</h2>
          <p>{children}</p>
        </div>
        <span className="footer">
          Sitio web no afiliado a la UFRO
        </span>

    </aside>
}
export default RuaAside;