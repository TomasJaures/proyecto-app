import { useNavigate } from "react-router-dom";

function EmailSended() {
  const navigate = useNavigate();

  return (
    <div className="email-page">
      <h1 className="logo">RUA</h1>

      <div className="email-contenido">
        <img src="/assets/email-icon.svg" alt="Email enviado" />
        <h2>¡Te hemos enviado un correo!</h2>
        <p>Revisa tu bandeja de entrada para continuar.</p>
        <button className="boton-email" onClick={() => navigate("/login")}>
          Volver al Log In
        </button>
      </div>
    </div>
  );
}

export default EmailSended;
