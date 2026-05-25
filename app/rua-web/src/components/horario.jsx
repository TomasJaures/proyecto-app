function Horario() {

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

  return (
    <div className="contenedor-horario">
      <table className="tabla-horario">

        <thead>
          <tr>
            <th>Hora</th>

            {dias.map((dia, index) => (
              <th key={index}>{dia}</th>
            ))}

          </tr>
        </thead>

        <tbody>

          {horas.map((hora, fila) => (
            <tr key={fila}>

              <td className="hora">{hora}</td>

              {dias.map((_, columna) => (
                <td key={columna}></td>
              ))}

            </tr>
          ))}

        </tbody>

      </table>
    </div>
  );
}

export default Horario;