function ClassBlock({ code, name, status }) {
  return (
    <div className={`bloque-clase ${status}`}>
      <strong>{code}</strong>
      <span>{name}</span>
    </div>
  );
}

export default ClassBlock;
