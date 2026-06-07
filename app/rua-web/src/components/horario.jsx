import { useState } from "react";
import BloqueClase from "./BloqueClase";

function Horario({
  modo,
  setModo,
  nombreClase,
  codigoClase
}) {

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
  const [claseSeleccionada, setClaseSeleccionada] = useState(null);

  function manejarClick(fila, columna) {

    /* AÑADIR */

    if (modo === "añadir") {

      const existe = clases.some(
        clase =>
          clase.fila === fila &&
          clase.columna === columna
      );

      if (existe) {
        return;
      }

      setClases([
        ...clases,
        {
          fila,
          columna,

          codigo: codigoClase,
          nombre: nombreClase,

          estado: "pendiente"
        }
      ]);

      setModo(null);
      return;
    }

    /* REMOVER */

    if (modo === "remover") {

      const existe = clases.some(
        clase =>
          clase.fila === fila &&
          clase.columna === columna
      );

      if (!existe) {
        return;
      }

      const confirmar = window.confirm(
        "¿Desea eliminar esta clase?"
      );

      if (!confirmar) {
        return;
      }

      setClases(
        clases.filter(
          clase =>
            !(
              clase.fila === fila &&
              clase.columna === columna
            )
        )
      );

      setModo(null);
      return;
    }

    if (modo === "mover") {

    const clase = clases.find(
        c =>
            c.fila === fila &&
            c.columna === columna
    );

    if (clase) {

        setClaseSeleccionada(clase);

        return;
    }

    if (claseSeleccionada) {

        const ocupado = clases.some(
            c =>
                c.fila === fila &&
                c.columna === columna
        );

        if (ocupado) {
            return;
        }

        setClases(
            clases.map(c =>
                c === claseSeleccionada
                    ? {
                          ...c,
                          fila,
                          columna
                      }
                    : c
            )
        );

        setClaseSeleccionada(null);
        setModo(null);
    }

    return;
}

  if (modo === "clonar") {

    const clase = clases.find(
        c =>
            c.fila === fila &&
            c.columna === columna
    );

    if (clase) {

        setClaseSeleccionada(clase);

        return;
    }

    if (claseSeleccionada) {

        const ocupado = clases.some(
            c =>
                c.fila === fila &&
                c.columna === columna
        );

        if (ocupado) {
            return;
        }

        setClases([
            ...clases,
            {
                ...claseSeleccionada,

                fila,
                columna
            }
        ]);

        setClaseSeleccionada(null);
        setModo(null);
    }

    return;
}

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
                        codigo={clase.codigo}
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