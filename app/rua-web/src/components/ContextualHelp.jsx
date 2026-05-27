import React, { useState } from 'react';
import helpCenter from "../assets/Textos/helpCenter.json";

function ContextualHelp() {
  const [isOpen, setIsOpen] = useState(false);

  const toggleOverlay = () => {
    setIsOpen(!isOpen);
  };

  return (
    <div>
      <div onClick={toggleOverlay} title="Click para ver ayuda"> ⓘ</div>

      {isOpen && (
        <div onClick={toggleOverlay}>
          <div onClick={(e) => e.stopPropagation()}>
            <h3>
              {helpCenter.text1}
            </h3>
            
            <p>
              {helpCenter.text2}
            </p>

            <button onClick={toggleOverlay}>
              Cerrar
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default ContextualHelp;