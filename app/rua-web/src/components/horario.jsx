import { useState } from "react";
import BloqueClase from "./BloqueClase";

function Horario({ modo, setModo }) {

  const horas = [
    "8:30",
    "9:40",
    "10:50",
    "12:00",
    "13:20",
    "14:40",
    "15:50",
    "17:00",
    "18:10",
    "19:20"
  ];

  const dias = [
    "Lunes",
    "Martes",
    "Miércoles",
    "Jueves",
    "Viernes",
    "Sábado",
    "Domingo"
  ];

  const [clases, setClases] = useState([]);

  function manejarClick(fila, columna) {

    if (modo !== "añadir") {
      return;
    }

    const existe = clases.some(
      clase =>
        clase.fila === fila &&
        clase.columna === columna
    );

    if (existe) {
      return;
    }

    const nuevaClase = {
      fila,
      columna,
      nombre: "Nueva Clase",
      estado: "pendiente"
    };

    setClases([...clases, nuevaClase]);

    setModo(null);
  }

  return (
    <div className="contenedor-horario">

      <table className="tabla-horario">

        <thead>

          <tr>

            <th>Hora</th>

            {dias.map((dia, index) => (
              <th key={index}>
                {dia}
              </th>
            ))}

          </tr>

        </thead>

        <tbody>

          {horas.map((hora, fila) => (

            <tr key={fila}>

              <td className="hora">
                {hora}
              </td>

              {dias.map((_, columna) => {

                const clase = clases.find(
                  c =>
                    c.fila === fila &&
                    c.columna === columna
                );

                return (

                  <td
                    key={columna}
                    onClick={() => manejarClick(fila, columna)}
                  >

                    {clase && (
                      <BloqueClase
                        nombre={clase.nombre}
                        estado={clase.estado}
                      />
                    )}

                  </td>

                );

              })}

            </tr>

          ))}

        </tbody>

      </table>

    </div>
  );
}

export default Horario;