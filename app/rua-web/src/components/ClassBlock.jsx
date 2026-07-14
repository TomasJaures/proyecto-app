function ClassBlock({ code, name, status, timeState, color }) { // 1. Recibe 'color'

  const borderColor = {
    PAST: "blue",
    PRESENT: "magenta",
    FUTURE: "orange"
  }[timeState] || "gray";

  return (
    <div
      className={`bloque-clase ${status}`}
      style={{
        border: `3px solid ${borderColor}`,
        backgroundColor: color || "white" // 2. Aplica el color al fondo (por defecto blanco)
      }}
    >
      <strong>{code}</strong>
      <span>{name}</span>
    </div>
  );
}

export default ClassBlock;  