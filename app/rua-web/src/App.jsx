import "./App.css";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Login from "./pages/LogIn.jsx";
import Signup from "./pages/SignUp.jsx";
import AlumnoHub from "./pages/AlumnoHub.jsx";
import ErrorPage from "./pages/ErrorPage.jsx";

function App() {

  return (
    <BrowserRouter>

      <Routes>

        <Route path="/" element={<Navigate to="/login" />} />

        <Route path="/login" element={<Login />} />

        <Route path="/signup" element={<Signup />} />

        <Route path="/alumnohub" element={<AlumnoHub />} />

        <Route path="/error" element={<ErrorPage />} />

      </Routes>

    </BrowserRouter>
  );
}

export default App;