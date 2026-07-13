import { useNavigate } from "react-router-dom";

function FatalError() {
  const navigate = useNavigate();

  return (
    <div className="error-page">
      <h1 className="logo">RUA</h1>

      <div className="error-contenido">
        <img src="/assets/error.icon.svg" alt="Error" />
        <h2>Ha ocurrido un error</h2>
        <p>Algo salió mal. Inténtalo nuevamente.</p>
        <button className="boton-error" onClick={() => navigate("/login")}>
          Volver al Log In
        </button>
      </div>
    </div>
  );
}

export default FatalError;
