import { useEffect, useRef, useState } from "react";
import { Html5Qrcode } from "html5-qrcode";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar.jsx";
import { qrApi } from "../services/apiService.js";

function QrAttempt() {
  const navigate = useNavigate();
  const scannerRef = useRef(null);
  const [permissionDenied, setPermissionDenied] = useState(false);

  useEffect(() => {
    const scanner = new Html5Qrcode("reader");
    scannerRef.current = scanner;

    const startCamera = async () => {
      try {
        await scanner.start(
          { facingMode: "environment" },
          { fps: 10, qrbox: 250 },
          async (qrText) => {
            try {
              await scanner.stop();
              await qrApi.scanQr(qrText);
              navigate("/login");
            } catch (error) {
              console.error("QR processing error:", error);
            }
          },
          () => {}
        );
      } catch {
        setPermissionDenied(true);
      }
    };

    startCamera();

    return () => {
      if (scannerRef.current?.isScanning) {
        scannerRef.current.stop().catch(() => {});
      }
    };
  }, [navigate]);

  if (permissionDenied) {
    return (
      <div className="qr-page">
        <Navbar role="Alumno" name="Usuario" />
        <div className="qr-container">
          <div className="card qr-card">
            <div className="qr-error-icon">❌</div>
            <h1>Permiso denegado</h1>
            <p>Debes permitir acceso a la cámara para escanear el QR.</p>
            <button className="confirmar" onClick={() => navigate("/alumnohub")}>
              Volver al Hub
            </button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="qr-page">
      <Navbar role="Alumno" name="Usuario" />
      <div className="qr-container">
        <div className="card qr-card">
          <h1>Escanea el QR</h1>
          <p>Apunta tu cámara al código QR</p>
          <div id="reader" />
          <button className="confirmar cancelar" onClick={() => navigate("/alumnohub")}>
            Cancelar
          </button>
        </div>
      </div>
    </div>
  );
}

export default QrAttempt;
