import { useState } from "react";
import Modal from "./Modal.jsx";

function AddStudentModal({ open, onClose, onAdd }) {
  const [email, setEmail] = useState("");
  const [error, setError] = useState("");

  const handleClose = () => {
    setEmail("");
    setError("");
    onClose();
  };

  const handleAdd = () => {
    if (!email.trim()) {
      setError("Ingresa un correo");
      return;
    }
    if (!email.includes("@")) {
      setError("Correo inválido");
      return;
    }
    onAdd(email.trim());
    setEmail("");
    setError("");
  };

  return (
    <Modal open={open} onClose={handleClose} title="Añadir alumno">
      <p className="modal-descripcion">
        Ingresa el correo institucional del alumno a añadir.
      </p>
      <label>CORREO INSTITUCIONAL</label>
      <input
        type="email"
        value={email}
        onChange={(e) => {
          setEmail(e.target.value);
          setError("");
        }}
        placeholder="alumno@ufromail.cl"
      />
      {error && <p className="error-texto">{error}</p>}
      <button className="confirmar" onClick={handleAdd}>
        Confirmar
      </button>
    </Modal>
  );
}

export default AddStudentModal;
