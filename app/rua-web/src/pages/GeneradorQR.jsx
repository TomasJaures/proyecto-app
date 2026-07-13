import { useEffect, useCallback, useState } from "react";
import { QRCode } from "react-qr-code";
import Navbar from "../components/Navbar.jsx";
import Card from "../components/Card.jsx";
import { useAuth } from "../hooks/useAuth.js";
import { useCountdown } from "../hooks/useCountdown.js";
import { useNavigate, useLocation } from "react-router-dom";

const QR_DURATION_SECONDS = 180;

function GeneradorQR() {
  const navigate = useNavigate();
  const { user } = useAuth();
  const { minutes, seconds, isExpired, reset } = useCountdown(QR_DURATION_SECONDS);

  const [qrValue, setQrValue] = useState("");
  const location = useLocation();
  const { classId } = location.state || {};

  useEffect(() => {
    setQrValue(crypto.randomUUID());
  }, []);

  useEffect(() => {
    generateQrValue();
  }, [generateQrValue]);

  const handleRegenerate = () => {
    setQrValue(crypto.randomUUID());
    reset();
  };

  

  return (
    <div className="pagina-qr">
      <Navbar role="Docente" name={user?.name || "NoName"} />

      <div className="contenedor-central">
        <Card className="card-qr">
          <h1 className="titulo-clase">Programación Avanzada</h1>
          <p className="texto-qr">
            Muestre este QR a los alumnos para registrar asistencia
          </p>

          <div className="zona-qr">
            {!isExpired ? (
              <QRCode value={qrValue} size={220} />
            ) : (
              <button className="boton-regenerar" onClick={handleRegenerate}>
                Regenerar QR
              </button>
            )}
          </div>

          <p className="temporizador">
            {minutes}:{seconds}
          </p>

          <button className="boton-volver" onClick={() => navigate("/docentehorario")}>
            Volver
          </button>
        </Card>
      </div>
    </div>
  );
}

export default GeneradorQR;
