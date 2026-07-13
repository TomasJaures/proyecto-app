function Modal({ open, onClose, title, children }) {
  if (!open) return null;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-caja" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h2 className="modal-titulo">{title}</h2>
          <button className="modal-cerrar" onClick={onClose} aria-label="Cerrar modal">
            ✕
          </button>
        </div>
        {children}
      </div>
    </div>
  );
}

export default Modal;
