function HelpButton({ helpText }) {

  function mostrarAyuda() {
    alert(helpText);
  }

  return (
    <button onClick={mostrarAyuda}>
      ?
    </button>
  );

}

export default HelpButton;