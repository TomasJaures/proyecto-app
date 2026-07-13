import { useEffect, useRef, useState } from "react";
import { Html5Qrcode } from "html5-qrcode";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar.jsx";
import { qrApi } from "../services/apiService.js";
import { useAuth } from "../hooks/useAuth.js";

function QrAttempt() {
  const navigate = useNavigate();
  const scannerRef = useRef(null);
  const [permissionDenied, setPermissionDenied] = useState(false);
  const [qrError, setQrError] = useState(""); // Estado para el mensaje de error del QR

  const { user } = useAuth();
  const mail = user?.mail || "example@gmail.com";
  const displayName = user?.name || "NoName";

  useEffect(() => {
    const scanner = new Html5Qrcode("reader");
    scannerRef.current = scanner;
    let isMounted = true; // Bandera para evitar actualizar el estado si el componente se desmontó

    const startCamera = async () => {
      try {
        await scanner.start(
          { facingMode: "environment" },
          { fps: 10, qrbox: 250 },
          async (qrContent) => {
            try {
              // Limpiamos errores previos si empieza a procesar
              setQrError(""); 
              
              // Detenemos el escáner inmediatamente para que no siga leyendo mientras procesa
              await scanner.stop();
              
              // Enviamos al backend
              await qrApi.scanQr(qrContent, mail);
              console.log("Operacion exitosa, volviendo al Log In")
              navigate("/login");
            } catch (error) {
              console.log("No se ha recibido la informacion del backend", error);
              
              // Si falla el backend, mostramos el error y reiniciamos el escáner si sigue montado
              if (isMounted) {
                setQrError("Código QR Inválido o vencido. Intenta de nuevo.");
                
                // Opcional: Reiniciar la cámara si se detuvo
                try {
                  await scanner.start({ facingMode: "environment" }, { fps: 10, qrbox: 250 }, () => {}, () => {});
                } catch (e) {
                  console.error("Error al reiniciar la cámara", e);
                }
              }
            }
          },
          () => {} // Ignoramos errores de lectura continua en el escaneo masivo
        );
      } catch (err) {
        if (isMounted) setPermissionDenied(true);
      }
    };

    // Pequeño delay para asegurar que el nodo del DOM "reader" esté 100% listo y evitar duplicados en StrictMode
    const timer = setTimeout(() => {
      startCamera();
    }, 100);

    return () => {
      isMounted = false;
      clearTimeout(timer);
      
      // Apagado seguro: si está escaneando, lo detiene.
      if (scanner.isScanning) {
        scanner.stop()
          .then(() => scanner.clear())
          .catch((err) => console.log("Error al limpiar escáner:", err));
      }
    };
  }, [navigate, mail]); // Añadido 'mail' ya que se usa dentro del useEffect

  if (permissionDenied) {
    return (
      <div className="qr-page">
        <Navbar role="Alumno" name={displayName} />
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
      <Navbar role="Alumno" name={displayName} />
      <div className="qr-container">
        <div className="card qr-card">
          <h1>Escanea el QR</h1>
          <p>Apunta tu cámara al código QR</p>
          
          {/* DIV de la cámara */}
          <div id="reader" />

          {/* Mensaje de error dinámico */}
          {qrError && (
            <div className="qr-error-message" style={{ color: "red", marginTop: "15px", fontWeight: "bold" }}>
              ⚠️ {qrError}
            </div>
          )}

          <button className="confirmar cancelar" onClick={() => navigate("/alumnohub")} style={{ marginTop: "20px" }}>
            Cancelar
          </button>
        </div>
      </div>
    </div>
  );
}

export default QrAttempt;