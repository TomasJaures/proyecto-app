import { useEffect, useRef, useState } from "react";
import { Html5Qrcode } from "html5-qrcode";
import { useNavigate } from "react-router-dom";
import { FRONTEND_URL, BACKEND_URL } from "../config.js";


import axios from "axios";

import Navbar from "../components/Navbar.jsx";

function QrAttempt() {

  const navigate = useNavigate();

  const scannerRef = useRef(null);

  const [permisoDenegado, setPermisoDenegado] = useState(false);

  useEffect(() => {

    const scanner = new Html5Qrcode("reader");

    scannerRef.current = scanner;

    async function iniciarCamara() {

      try {

        await scanner.start(
          { facingMode: "environment" },

          {
            fps: 10,
            qrbox: 250
          },

          async (textoQR) => {

            console.log("QR DETECTADO:", textoQR);

            try {

              await scanner.stop();

              // enviar token al backend
              await axios.post(
                BACKEND_URL + "/scanqr",
                {
                  token: textoQR
                }
              );

              // redireccion futura
              navigate("/alumnohub");

            } catch (error) {

              console.log(error);

            }
          },

          () => {
            // errores de lectura se ignoran
          }
        );

      } catch (error) {

        console.log(error);

        setPermisoDenegado(true);

      }
    }

    iniciarCamara();

    return () => {

      if (
        scannerRef.current &&
        scannerRef.current.isScanning
      ) {

        scannerRef.current
          .stop()
          .catch(() => {});

      }
    };

  }, []);

  // ======== QR FAIL ========

  if (permisoDenegado) {

    return (

      <div className="qr-page">

        <Navbar
          rol="Alumno"
          nombre="Usuario"
        />

        <div className="qr-container">

          <div className="card qr-card">

            <div className="qr-error-icon">
              ❌
            </div>

            <h1>Permiso denegado</h1>

            <p>
              Debes permitir acceso a la cámara
              para escanear el QR.
            </p>

            <button
              className="confirmar"
              onClick={() => navigate("/alumnohub")}
            >
              Volver al Hub
            </button>

          </div>

        </div>

      </div>
    );
  }

  // ======== QR SUCCESS / ESCANEO ========

  return (

    <div className="qr-page">

      <Navbar
        rol="Alumno"
        nombre="Usuario"
      />

      <div className="qr-container">

        <div className="card qr-card">

          <h1>Escanea el QR</h1>

          <p>
            Apunta tu cámara al código QR
          </p>

          <div id="reader"></div>

          <button
            className="confirmar cancelar"
            onClick={() => navigate("/alumnohub")}
          >
            Cancelar
          </button>

        </div>

      </div>

    </div>
  );
}

export default QrAttempt;