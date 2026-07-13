function ClassBlock({ code, name, status, timeState }) {

  const borderColor = {
    PAST: "blue",
    PRESENT: "magenta",
    FUTURE: "orange"
  }[timeState] || "gray";

  return (
    <div
      className={`bloque-clase ${status}`}
      style={{
        border: `3px solid ${borderColor}`
      }}
    >
      <strong>{code}</strong>
      <span>{name}</span>
    </div>
  );
}

export default ClassBlock;