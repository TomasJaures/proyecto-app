import { useState, useCallback, useEffect } from "react";
import ClassBlock from "./ClassBlock.jsx";
import { useNavigate } from "react-router-dom";

const HOURS = [
  "8:30", "9:40", "10:50", "12:00", "13:20",
  "14:40", "15:50", "17:00", "18:10", "19:20",
];

// Mapeo auxiliar para saber a qué hora termina un bloque según su hora de inicio
const HOUR_END_MAP = {
  "8:30": "09:40",
  "9:40": "10:50",
  "10:50": "12:00",
  "12:00": "13:10",
  "13:20": "14:30",
  "14:40": "15:50",
  "15:50": "17:00",
  "17:00": "18:10",
  "18:10": "19:20",
  "19:20": "20:30"
};

const DAYS = [
  "Lunes", "Martes", "Miércoles", "Jueves",
  "Viernes", "Sábado", "Domingo",
];

// Días sin tildes para el formato que espera el Backend
const DAYS_BACKEND = [
  "Lunes", "Martes", "Miercoles", "Jueves",
  "Viernes", "Sabado", "Domingo",
];

function Schedule({ 
  mode, 
  setMode, 
  className: courseName, 
  courseCode, 
  blocks,
  onAddChange // <-- Nueva prop para reportar los cambios al contenedor padre (DocenteHorario)
}) {
  const [classes, setClasses] = useState([]);
  const [selectedClass, setSelectedClass] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    if (blocks && blocks.length > 0) {
      const dayToSpanish = {
        "MON": "Lunes",
        "TUE": "Martes",
        "WED": "Miércoles",
        "THU": "Jueves",
        "FRI": "Viernes",
        "SAT": "Sábado",
        "SUN": "Domingo"
      };

      const mappedClasses = blocks.map((block) => {
        const spanishDay = dayToSpanish[block.weekDay];
        const col = DAYS.indexOf(spanishDay);
        const cleanStartHour = block.startHour.substring(0, 5).replace(/^0/, '');
        const row = HOURS.indexOf(cleanStartHour);

        if (row !== -1 && col !== -1) {
          return {
            row,
            col,
            code: block.code,
            name: block.subjectName,
            status: block.blockState === "NO_PROJECTIONS" ? "pendiente" : "confirmado",
            blockId: block.blockId,
            moduleId: block.moduleId, // Guardamos el moduleId original para usarlo al clonar/mover
            timeState: block.timeState,
            color: block.color 
          };
        }
        return null;
      }).filter(Boolean);

      setClasses(mappedClasses);
    }
  }, [blocks]);

  const findClassAt = useCallback(
    (row, col) => classes.find((c) => c.row === row && c.col === col),
    [classes]
  );

  const isOccupied = useCallback(
    (row, col) => classes.some((c) => c.row === row && c.col === col),
    [classes]
  );

  // Helper para dar formato de hora de 5 caracteres ("08:30" en vez de "8:30")
  const formatHour = (hourStr) => {
    return hourStr.length === 4 ? `0${hourStr}` : hourStr;
  };

  const handleCellClick = (row, col) => {
    const targetDay = DAYS_BACKEND[col];
    const targetStartHour = formatHour(HOURS[row]);
    const targetEndHour = HOUR_END_MAP[HOURS[row]] || "20:30";

    if (mode === "añadir") {
      if (isOccupied(row, col)) return;

      // TODO: En un escenario real, deberías asociar un moduleId real seleccionado en el formulario.
      // Por ahora asignamos un ID temporal o estático para pruebas (ej: 5)
      const mockModuleId = 5; 

      setClasses((prev) => [
        ...prev,
        { row, col, code: courseCode, name: courseName, status: "pendiente", moduleId: mockModuleId },
      ]);

      // Registramos el cambio de tipo Add
      onAddChange({
        action: "Add",
        day: targetDay,
        startHour: targetStartHour,
        endHour: targetEndHour,
        moduleId: mockModuleId
      });

      setMode(null);
      return;
    }

    if (mode === "remover") {
      if (!isOccupied(row, col)) return;
      const cls = findClassAt(row, col);
      if (!window.confirm("¿Desea eliminar esta clase?")) return;

      setClasses((prev) => prev.filter((c) => !(c.row === row && c.col === col)));

      // Registramos el cambio de tipo Remove
      if (cls.blockId) {
        onAddChange({
          action: "Remove",
          blockId: cls.blockId
        });
      }
      
      setMode(null);
      return;
    }

    if (mode === "editar") {
      const cls = findClassAt(row, col);
      navigate(`/editarclase/${cls.blockId}`);
    }

    if (mode === "qr") {
      const cls = findClassAt(row, col);
      navigate(`/generadorqr/${cls.blockId}`);
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

        // Registramos el cambio de tipo Move
        onAddChange({
          action: "Move",
          day: targetDay,
          startHour: targetStartHour,
          endHour: targetEndHour,
          moduleId: selectedClass.moduleId || 5, // Recuperamos el moduleId del bloque original
          blockId: selectedClass.blockId
        });

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
        setClasses((prev) => [...prev, { ...selectedClass, row, col, status: "pendiente" }]);

        // Registramos el cambio de tipo Clone
        onAddChange({
          action: "Clone",
          day: targetDay,
          startHour: targetStartHour,
          endHour: targetEndHour,
          moduleId: selectedClass.moduleId || 5
        });

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
                      <ClassBlock 
                        code={cls.code} 
                        name={cls.name} 
                        status={cls.status} 
                        timeState={cls.timeState}
                        color={cls.color}
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

export default Schedule;