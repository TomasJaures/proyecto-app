import { useNavigate } from "react-router-dom";
import { useAuth } from "../hooks/useAuth.js";

function Navbar({ role, name }) {
  const navigate = useNavigate();
  const { logout } = useAuth();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <header className="navbar">
      <h1 className="logo">RUA</h1>

      <div className="usuario">
        <span className="rol">{role}</span>
        <span className="nombre">{name}</span>
      </div>

      <button className="salir" onClick={handleLogout}>
        Salir
      </button>
    </header>
  );
}

export default Navbar;
