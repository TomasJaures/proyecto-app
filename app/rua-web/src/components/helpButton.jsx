function HelpButton({ onClick }) {
  return (
    <button className="btn-ayuda" onClick={onClick} aria-label="Ayuda">
      ?
    </button>
  );
}

export default HelpButton;
