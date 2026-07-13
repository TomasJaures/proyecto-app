function PendingChangesBar({ message, onSave, onCancel, isSaving, variant = "warning" }) {
  const barClass = variant === "danger" ? "barra-pendiente barra-pendiente-peligro" : "barra-pendiente";
  const btnClass = variant === "danger" ? "confirmar peligro" : "confirmar";

  return (
    <div className={barClass}>
      <span>{message}</span>
      <div className="barra-pendiente-acciones">
        <button className="secundario" onClick={onCancel} disabled={isSaving}>
          Cancelar
        </button>
        <button className={btnClass} onClick={onSave} disabled={isSaving}>
          {isSaving ? "Guardando..." : "Guardar"}
        </button>
      </div>
    </div>
  );
}

export default PendingChangesBar;
