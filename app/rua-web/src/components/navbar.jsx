import { useNavigate } from "react-router-dom";
import { useAuth } from "../hooks/useAuth.js";
import { useState } from "react";
import HelpButton from "./HelpButton";
import Modal from "./Modal";
import tutorials from "../data/tutorials.json";

function Navbar({ role, name, tutorial }) {
  const navigate = useNavigate();
  const { logout } = useAuth();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };
  const [showTutorial, setShowTutorial] = useState(false);

  const tutorialData = tutorial
      ? tutorials[tutorial]
      : null;


  return (
    <header className="navbar">
      <h1 className="logo">RUA</h1>

      <div className="usuario">
        <span className="rol">{role}</span>
        <span className="nombre">{name}</span>
      </div>

      {tutorialData && (
          <HelpButton onClick={() => setShowTutorial(true)} />
      )}

      <Modal
          open={showTutorial}
          onClose={() => setShowTutorial(false)}
          title={tutorialData?.title}
      >
          {tutorialData?.sections.map((section) => (
              <div key={section.title}>
                  <h3>{section.title}</h3>
                  <p>{section.description}</p>
              </div>
          ))}
      </Modal>

      <button className="salir" onClick={handleLogout}>
        Salir
      </button>
    </header>
  );
}

export default Navbar;
