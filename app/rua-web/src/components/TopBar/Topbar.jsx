import React from 'react';
import './TopBar.css'; // Archivo de estilos temporales

export const TopBar = ({ nameAndRolString, URLToGoWhenClick }) => {
  
  const handleVolverClick = () => {
    if (URLToGoWhenClick) {
      window.location.href = URLToGoWhenClick;
    }
  };

  return (
    <header className="topbar-container-temp">
      {/* LADO IZQUIERDO: LOGO */}
      <div className="topbar-logo-area-temp">
        {/* Reemplaza este SVG por tu archivo de logo real si lo prefieres */}
        <svg className="topbar-logo-svg-temp" viewBox="0 0 100 30" width="120">
          <rect width="100" height="30" rx="5" fill="#4A90E2"/>
          <text x="50%" y="55%" dominantBaseline="middle" textAnchor="middle" fill="white" fontSize="12" fontWeight="bold">LOGO</text>
        </svg>
      </div>

      {/* LADO DERECHO: INFO Y BOTÓN */}
      <div className="topbar-actions-area-temp">
        {/* Info usuario: Desaparece en pantallas chicas mediante CSS */}
        {nameAndRolString && (
          <p className="topbar-user-info-temp">
            {nameAndRolString}
          </p>
        )}
        
        {/* Botón Volver: Siempre visible */}
        <button 
          className="topbar-btn-volver-temp" 
          onClick={handleVolverClick}
        >
          Volver
        </button>
      </div>
    </header>
  );
};