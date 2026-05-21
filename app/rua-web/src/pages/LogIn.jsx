import { useState } from "react";
import axios from "axios";

function Login() {

  const [nombre, setNombre] = useState("");
  const [password, setPassword] = useState("");

  async function enviar() {

    try {

      const res = await axios.post(
        "http://localhost:8080/login",
        {
          nombre,
          password
        }
      );

      localStorage.setItem(
        "token", res.data.token
      )

      console.log(res.data);

    } catch (err) {
      console.log(err);
    }

  }

  return (
    <div>

      <input
        type="text"
        placeholder="Nombre"
        value={nombre}
        onChange={(e) => setNombre(e.target.value)}
      />

      <input
        type="password"
        placeholder="Contraseña"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <button onClick={enviar}>
        Login
      </button>

    </div>
  );

}

export default Login;