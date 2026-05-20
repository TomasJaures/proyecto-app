import { useNavigate } from "react-router-dom";

function ErrorPage() {

  const navigate = useNavigate();

  return (

    <div className="error-page">

      {/* LOGO */}
      <h1 className="logo">RUA</h1>

      {/* CONTENIDO */}
      <div className="error-contenido">

        <div className="error-icono">
          ❌
        </div>

        <h2>Ha ocurrido un error</h2>

        <p>
          Algo salió mal. Inténtalo nuevamente.
        </p>

        <button
          className="boton-error"
          onClick={() => navigate(-1)}
        >
          Retroceder
        </button>

      </div>

    </div>
  );
}

export default ErrorPage;