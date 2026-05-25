import "./App.css";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Login from "./pages/LogIn.jsx";
import Signup from "./pages/SignUp.jsx";
import AlumnoHub from "./pages/AlumnoHub.jsx";
import QrAttempt from "./pages/QrAttempt.jsx";
import FatalError from "./pages/FatalError.jsx";
import GeneradorQR from "./pages/GeneradorQR.jsx";
import EmailSended from "./pages/EmailSended.jsx";
import DocenteHub from "./pages/DocenteHub.jsx";

function App() {

  return (
    <BrowserRouter>

      <Routes>

        <Route path="/" element={<Navigate to="/login" />} />

        <Route path="/login" element={<Login />} />

        <Route path="/signup" element={<Signup />} />

        <Route path="/emailsended" element={<EmailSended />} />

        <Route path="/alumnohub" element={<AlumnoHub />} />

        <Route path="/qrattempt" element={<QrAttempt />} />

        <Route path="/docentehub" element={<DocenteHub />} />

        <Route path="/generadorqr" element={<GeneradorQR />} />

        <Route path="/error" element={<FatalError />} />

      </Routes>

    </BrowserRouter>
  );
}

export default App;