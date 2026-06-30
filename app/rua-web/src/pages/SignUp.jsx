import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import SidePanel from "../components/SidePanel.jsx";
import Card from "../components/Card.jsx";
import MobileHeader from "../components/MobileHeader.jsx";
import { authApi } from "../services/apiService.js";

function SignUp() {
  const navigate = useNavigate();
  const [termsAccepted, setTermsAccepted] = useState(false);

  const [formData, setFormData] = useState({
    userName: "",
    lastName1: "",
    lastName2: "",
    mail: "",
    hashedPassword: "",
  });

  const updateField = (field) => (e) =>
    setFormData((prev) => ({ ...prev, [field]: e.target.value }));

  const handleSubmit = async () => {
    try {
      await authApi.register(formData);
      navigate("/emailsended");
    } catch (error) {
      console.error("Signup error:", error);
      navigate("/error");
    }
  };

  const fields = [
    { key: "userName", label: "NOMBRE", placeholder: "Alonso", type: "text" },
    { key: "lastName1", label: "PRIMER APELLIDO", placeholder: "Farías", type: "text" },
    { key: "lastName2", label: "SEGUNDO APELLIDO", placeholder: "Ravanal", type: "text" },
    { key: "mail", label: "CORREO INSTITUCIONAL", placeholder: "ejemplo@ufromail.cl", type: "text" },
    { key: "hashedPassword", label: "CLAVE RUA", placeholder: "Minimo 8 Caracteres", type: "password" },
  ];

  return (
    <div className="pagina">
      <SidePanel>
        Crea tu cuenta para empezar a registrar tu asistencia en la
        Universidad de la Frontera
      </SidePanel>

      <main className="derecha">
        <MobileHeader />

        <Card>
          <h1>Sign Up</h1>
          <p>Crea tu cuenta para poder acceder</p>

          {fields.map(({ key, label, placeholder, type }) => (
            <div key={key}>
              <label htmlFor={`signup-${key}`}>{label}</label>
              <input
                id={`signup-${key}`}
                type={type}
                placeholder={placeholder}
                value={formData[key]}
                onChange={updateField(key)}
              />
            </div>
          ))}

          <div className="terminos">
            <input
              type="checkbox"
              id="terms-checkbox"
              checked={termsAccepted}
              onChange={(e) => setTermsAccepted(e.target.checked)}
            />
            <label htmlFor="terms-checkbox">
              Acepto los términos y condiciones
            </label>
          </div>

          <button
            disabled={!termsAccepted}
            className="confirmar"
            onClick={handleSubmit}
          >
            Confirmar
          </button>

          <hr />
          <p className="signup">
            ¿Ya tienes una cuenta?
            <Link to="/login"> Log In</Link>
          </p>
        </Card>
      </main>
    </div>
  );
}

export default SignUp;
