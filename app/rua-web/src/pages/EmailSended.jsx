import { useNavigate } from "react-router-dom";

function EmailSended() {

  const navigate = useNavigate();

  return (

    <div className="email-page">

      {/* LOGO */}
      <h1 className="logo">RUA</h1>

      {/* CONTENIDO */}
      <div className="email-contenido">

        <div className="email-icono">
          ✉️
        </div>

        <h2>¡Te hemos enviado un correo!</h2>

        <p>
          Revisa tu bandeja de entrada para continuar.
        </p>

        <button
          className="boton-email"
          onClick={() => navigate("/login")}
        >
          Volver al Log In
        </button>

      </div>

    </div>
  );
}

export default EmailSended;