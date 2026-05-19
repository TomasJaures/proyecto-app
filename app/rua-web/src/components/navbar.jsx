function Navbar({ rol, nombre }) {

  return (
    <header className="navbar">

      {/* IZQUIERDA */}
      <h1 className="logo">
        RUA
      </h1>

      {/* CENTRO */}
      <div className="usuario">

        <span className="rol">
          {rol}
        </span>

        <span className="nombre">
          {nombre}
        </span>

      </div>

      {/* DERECHA */}
      <button className="salir">
        Salir
      </button>

    </header>
  );
}

export default Navbar;