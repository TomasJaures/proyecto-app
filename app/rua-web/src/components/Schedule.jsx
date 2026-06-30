import { useState, useCallback } from "react";
import ClassBlock from "./ClassBlock.jsx";

const HOURS = [
  "8:30", "9:40", "10:50", "12:00", "13:20",
  "14:40", "15:50", "17:00", "18:10", "19:20",
];

const DAYS = [
  "Lunes", "Martes", "Miércoles", "Jueves",
  "Viernes", "Sábado", "Domingo",
];

function Schedule({ mode, setMode, className: courseName, courseCode }) {
  const [classes, setClasses] = useState([]);
  const [selectedClass, setSelectedClass] = useState(null);

  const findClassAt = useCallback(
    (row, col) => classes.find((c) => c.row === row && c.col === col),
    [classes]
  );

  const isOccupied = useCallback(
    (row, col) => classes.some((c) => c.row === row && c.col === col),
    [classes]
  );

  const handleCellClick = (row, col) => {
    if (mode === "añadir") {
      if (isOccupied(row, col)) return;
      setClasses((prev) => [
        ...prev,
        { row, col, code: courseCode, name: courseName, status: "pendiente" },
      ]);
      setMode(null);
      return;
    }

    if (mode === "remover") {
      if (!isOccupied(row, col)) return;
      if (!window.confirm("¿Desea eliminar esta clase?")) return;
      setClasses((prev) => prev.filter((c) => !(c.row === row && c.col === col)));
      setMode(null);
      return;
    }

    if (mode === "mover") {
      const cls = findClassAt(row, col);
      if (cls) {
        setSelectedClass(cls);
        return;
      }
      if (selectedClass && !isOccupied(row, col)) {
        setClasses((prev) =>
          prev.map((c) => (c === selectedClass ? { ...c, row, col } : c))
        );
        setSelectedClass(null);
        setMode(null);
      }
      return;
    }

    if (mode === "clonar") {
      const cls = findClassAt(row, col);
      if (cls) {
        setSelectedClass(cls);
        return;
      }
      if (selectedClass && !isOccupied(row, col)) {
        setClasses((prev) => [...prev, { ...selectedClass, row, col }]);
        setSelectedClass(null);
        setMode(null);
      }
      return;
    }
  };

  return (
    <div className="contenedor-horario">
      <table className="tabla-horario">
        <thead>
          <tr>
            <th>Hora</th>
            {DAYS.map((day) => (
              <th key={day}>{day}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {HOURS.map((hour, rowIndex) => (
            <tr key={rowIndex}>
              <td className="hora">{hour}</td>
              {DAYS.map((_, colIndex) => {
                const cls = findClassAt(rowIndex, colIndex);
                return (
                  <td key={colIndex} onClick={() => handleCellClick(rowIndex, colIndex)}>
                    {cls && (
                      <ClassBlock code={cls.code} name={cls.name} status={cls.status} />
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

export default Schedule;
