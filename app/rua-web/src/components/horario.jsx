import { useState, useEffect } from "react"; // <-- Asegúrate de importar useEffect
import BloqueClase from "./BloqueClase";

function Horario({
  modo,
  setModo,
  nombreClase,
  codigoClase,
  bloques // 1. Recibimos los bloques de la API aquí
}) {

  const horas = [
    "8:30", "9:40", "10:50", "12:00", "13:20", 
    "14:40", "15:50", "17:00", "18:10", "19:20"
  ];

  const dias = [
    "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
  ];

  const [clases, setClases] = useState([]);
  const [claseSeleccionada, setClaseSeleccionada] = useState(null);

  const obtenerFilaPorHora = (horaString, horasDisponibles) => {
  // Tomamos solo la hora y minutos (ej: "10:00:00" -> "10:00")
  const [hora, minutos] = horaString.split(":");
  const horaClase = parseInt(hora) * 60 + parseInt(minutos);

  let filaEncontrada = 0;
  let diferenciaMinima = Infinity;

  // Buscamos cuál es el bloque oficial que más se aproxima
  horasDisponibles.forEach((h, index) => {
    const [hOficial, mOficial] = h.split(":");
    const horaOficial = parseInt(hOficial) * 60 + parseInt(mOficial);
    const dif = Math.abs(horaClase - horaOficial);

    if (dif < diferenciaMinima) {
      diferenciaMinima = dif;
      filaEncontrada = index;
    }
  });

  return filaEncontrada;
};

  // Función para calcular cuántos bloques de ~50-70 min ocupa la clase
  const calcularLongitudBloque = (inicio, fin) => {
    const [hIni, mIni] = inicio.split(":").map(Number);
    const [hFin, mFin] = fin.split(":").map(Number);
    
    const minutosInicio = hIni * 60 + mIni;
    const minutosFin = hFin * 60 + mFin;
    const duracionTotal = minutosFin - minutosInicio;

    // Si dura entre 50 y 90 min es 1 bloque, si dura más (ej: 1.5 horas o 2 horas) ocupa 2 o más bloques
    if (duracionTotal <= 80) return 1;
    if (duracionTotal <= 150) return 2;
    return Math.ceil(duracionTotal / 70); 
  };

  // 2. NUEVO: Efecto para sincronizar e interpretar los bloques del Backend
  useEffect(() => {
    if (bloques && bloques.length > 0) {
      
      // Diccionario para mapear el formato del backend al índice de tu columna (0 a 6)
      const mapeoDias = {
        "MON": 0, // Lunes
        "TUE": 1, // Martes
        "WED": 2, // Miércoles
        "THU": 3, // Jueves
        "FRI": 4, // Viernes
        "SAT": 5, // Sábado
        "SUN": 6  // Domingo
      };

      const clasesMapeadas = [];

      bloques.forEach((bloque) => {
        // 1. Encontrar la columna según el día de la semana
        const columna = mapeoDias[bloque.weekDay];
        
        // 2. Encontrar la fila base según la hora de inicio (ej: "10:00:00")
        const filaBase = obtenerFilaPorHora(bloque.startHour, horas);
        
        // 3. Calcular cuántos bloques de duración se deben pintar
        const bloquesDeDuracion = calcularLongitudBloque(bloque.startHour, bloque.endHour);

        // Agregamos la clase a la fila correspondiente, y si dura más de un bloque, 
        // la duplicamos en las filas consecutivas para que se renderice completa en la tabla
        for (let i = 0; i < bloquesDeDuracion; i++) {
          clasesMapeadas.push({
            fila: filaBase + i,
            columna: columna,
            codigo: bloque.code ? `INF-${bloque.code}` : "S/C",
            nombre: bloque.subjectName || "Clase Cargada",
            estado: "pendiente" // Mantiene el estilo gris que mencionaste
          });
        }
      });

      setClases(clasesMapeadas);
    }
  }, [bloques]);

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