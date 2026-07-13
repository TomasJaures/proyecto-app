import "./App.css";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import LogIn from "./pages/LogIn.jsx";
import SignUp from "./pages/SignUp.jsx";
import AlumnoHub from "./pages/AlumnoHub.jsx";
import AlumnoHorario from "./pages/AlumnoHorario.jsx";
import QrAttempt from "./pages/QrAttempt.jsx";
import FatalError from "./pages/FatalError.jsx";
import GeneradorQR from "./pages/GeneradorQR.jsx";
import EmailSended from "./pages/EmailSended.jsx";
import DocenteHub from "./pages/DocenteHub.jsx";
import DocenteAdmin from "./pages/DocenteAdmin.jsx";
import DocenteHorario from "./pages/DocenteHorario.jsx";
import AsistenciaClase from "./pages/AsistenciaClase.jsx";
import EditarClase from "./pages/EditarClase.jsx";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<LogIn />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/emailsended" element={<EmailSended />} />
        <Route path="/alumnohub" element={<AlumnoHub />} />
        <Route path="/alumnohorario" element={<AlumnoHorario />} />
        <Route path="/qrattempt" element={<QrAttempt />} />
        <Route path="/docentehub" element={<DocenteHub />} />
        <Route path="/docenteadmin" element={<DocenteAdmin />} />
        <Route path="/docentehorario" element={<DocenteHorario />} />
        <Route path="/generadorqr/:classId" element={<GeneradorQR />} />
        <Route path="/error" element={<FatalError />} />
        <Route path="/editarclase/:blockId" element={<EditarClase />} />
        <Route path="/asistenciaclase/:classId" element={<AsistenciaClase />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
