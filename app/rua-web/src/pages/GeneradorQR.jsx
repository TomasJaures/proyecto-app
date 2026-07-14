import { useEffect, useCallback, useState } from "react";
import { QRCode } from "react-qr-code";
import Navbar from "../components/Navbar.jsx";
import Card from "../components/Card.jsx";
import { useAuth } from "../hooks/useAuth.js";
import { useCountdown } from "../hooks/useCountdown.js";
import { useNavigate, useLocation } from "react-router-dom";
import { useParams } from "react-router-dom";
import { authApi, qrApi } from "../services/apiService.js";

const QR_DURATION_SECONDS = 180;

function GeneradorQR() {
  const navigate = useNavigate();
  const { user } = useAuth();
  const { minutes, seconds, isExpired, reset } = useCountdown(QR_DURATION_SECONDS);
  const {classId} = useParams() || {};
  const placeholderQR = "Cargando QR...";

  const [qrValue, setQrValue] = useState("");
  const location = useLocation();

  //Obtener UUID del QR apenas se abra la pagina
  useEffect(() => {
    async function recieveQrUUID() {
      try {
        const response = await qrApi.getQr(classId);
        setQrValue(response.data);
        console.log("Se ha cargado el QR del backend correctamente: " + response.data)
      } catch (error) {
        console.log("No se ha recibido la informacion del backend")
        setQrValue("CONTENIDO DE QR CAYO EN EL TRY CATCH!")
      }
      
    }
    recieveQrUUID();
  }, [])

  return (
    <div className="pagina-qr">
      <Navbar role="Docente" name={user?.name || "NoName"} />

      <div className="contenedor-central">
        <Card className="card-qr">
          
          <h1 className="titulo-clase"> QR </h1> 
          <p className="texto-qr">
            Muestre este QR a los alumnos para registrar asistencia
          </p>

          <div className="zona-qr">
            {!isExpired ? (
              qrValue ? (
                <QRCode value={qrValue} size={220} />
              ) : (
                <h1 className="titulo-clase" > Cargando QR...</h1>
              )
            ) : (
              <h1>
                  ...
              </h1>
            )}
          </div>

          <p className="temporizador">
            {minutes}:{seconds}
          </p>

          <button className="confirmar" onClick={() => navigate(`/docentehorario/${user?.calendarId || -1}`)}>
            Volver
          </button>
        </Card>
      </div>
    </div>
  );
}

export default GeneradorQR;
