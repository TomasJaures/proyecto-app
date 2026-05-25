import { useEffect, useState } from "react";
import Navbar from "../components/navbar";
import Card from "../components/card";
import {QRCode} from "react-qr-code";

function GeneradorQR() {

  const [qrValue, setQrValue] = useState("");
  const [tiempo, setTiempo] = useState(180);

  function generarQR() {
    
    const codigo = crypto.randomUUID();

    setQrValue(codigo);
    setTiempo(180);

  }

  useEffect(() => {

    generarQR();

  }, []);

  useEffect(() => {

    if (tiempo <= 0) return;

    const intervalo = setInterval(() => {

      setTiempo((prev) => prev - 1);

    }, 1000);

    return () => clearInterval(intervalo);

  }, [tiempo]);

  const minutos = String(Math.floor(tiempo / 60)).padStart(2, "0");
  const segundos = String(tiempo % 60).padStart(2, "0");

  return (
    <div className="pagina-qr">

      <Navbar
        rol="Docente"
        nombre="Juan Pérez"
      />

      <div className="contenedor-central">

        <Card className="card-qr">

          <h1 className="titulo-clase">
            Programación Avanzada
          </h1>

          <p className="texto-qr">
            Muestre este QR a los alumnos para registrar asistencia
          </p>

          <div className="zona-qr">

            {
              tiempo > 0 ? (

                <QRCode
                  value={qrValue}
                  size={220}
                />

              ) : (

                <button
                  className="boton-regenerar"
                  onClick={generarQR}
                >
                  Regenerar QR
                </button>

              )
            }

          </div>

          <p className="temporizador">
            {minutos}:{segundos}
          </p>

          <button className="boton-volver">
            Volver
          </button>

        </Card>

      </div>

    </div>
  );
}

export default GeneradorQR;