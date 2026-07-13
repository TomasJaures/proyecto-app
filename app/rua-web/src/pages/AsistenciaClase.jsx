import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Card from "../components/Card.jsx";
import SidePanel from "../components/SidePanel.jsx";
import MobileHeader from "../components/MobileHeader.jsx";
import AddStudentModal from "../components/AddStudentModal.jsx";
import PendingChangesBar from "../components/PendingChangesBar.jsx";
import "../styles/AsistenciaClase.css";
import { attendanceApi } from "../services/apiService.js";

function buildStudentFromEmail(email) {
  const namePart = email.split("@")[0];
  const formattedName = namePart
    .split(".")
    .map((p) => p.charAt(0).toUpperCase() + p.slice(1))
    .join(" ");

  return {
    userId: Date.now().toString(),
    userName: formattedName,
    mail: email,
    lastName1: "",
    lastName2: "",
    date: new Date().toLocaleDateString("es-CL", {
      day: "numeric",
      month: "short",
      year: "numeric",
    }),
    isNew: true,
  };
}

function StudentListItem({ student }) {
  return (
    <li className="alumno-item">
      <div className="alumno-avatar">
        <span>👤</span>
      </div>
      <div className="alumno-info">
        <p className="alumno-nombre">
          {student.userName} {student.lastName1} {student.lastName2}
        </p>
        <p className="alumno-email">{student.mail}</p>
      </div>
      {student.date && (
        <div className="alumno-fecha">
          <span>📅</span>
          <span>{student.date}</span>
        </div>
      )}
    </li>
  );
}

function AsistenciaClase() {
  const { classId } = useParams();
  const navigate = useNavigate();

  const [classInfo] = useState({
    id: classId,
    courseId: "INF-301",
    name: "Ingeniería de Software",
  });

  const [savedStudents, setSavedStudents] = useState([]);
  const [pendingStudents, setPendingStudents] = useState([]);
  const [showAddStudent, setShowAddStudent] = useState(false);
  const [isSaving, setIsSaving] = useState(false);

  const hasPendingChanges = pendingStudents.length !== savedStudents.length;

  useEffect(() => {
    const fetchStudents = async () => {
      try {
        const { data } = await attendanceApi.getPresentStudents(classId);
        setSavedStudents(data);
        setPendingStudents(data);
      } catch (error) {
        console.error("Error fetching students:", error);
      }
    };
    fetchStudents();
  }, [classId]);

  const handleAddStudent = (email) => {
    const newStudent = buildStudentFromEmail(email);
    setPendingStudents((prev) => [...prev, newStudent]);
    setShowAddStudent(false);
  };

  const handleSave = async () => {
    setIsSaving(true);
    try {
      const newStudents = pendingStudents.filter(
        (ps) => !savedStudents.some((ss) => ss.mail === ps.mail)
      );

      if (newStudents.length === 0) {
        alert("No hay alumnos nuevos para guardar.");
        return;
      }

      for (const student of newStudents) {
        await attendanceApi.addStudentToClass(classId, {
          mail: student.mail,
          userName: student.userName,
          lastName1: student.lastName1,
          lastName2: student.lastName2,
        });
      }

      alert(`✓ ${newStudents.length} alumno(s) guardado(s) correctamente.`);
      setSavedStudents(pendingStudents);
    } catch (error) {
      console.error("Error saving students:", error);
      alert("Error al guardar los alumnos. Intenta de nuevo.");
    } finally {
      setIsSaving(false);
    }
  };

  const handleCancel = () => {
    setPendingStudents(savedStudents);
  };

  return (
    <div className="pagina">
      <SidePanel>Registro de asistencia universitaria</SidePanel>

      <main className="derecha">
        <MobileHeader />

        <Card>
          <h1>Asistencia — {classInfo.courseId}</h1>
          <p>{classInfo.name}</p>

          {hasPendingChanges && (
            <PendingChangesBar
              message="⚠ Tienes cambios sin guardar"
              onSave={handleSave}
              onCancel={handleCancel}
              isSaving={isSaving}
            />
          )}

          <div className="registro-header">
            <div className="registro-header-titulo">
              <span className="icono-usuario">👤</span>
              <span>Registro de asistencia</span>
            </div>
            <span className="badge-alumnos">
              {pendingStudents.length} alumnos
            </span>
          </div>

          {pendingStudents.length === 0 ? (
            <p className="texto-secundario lista-vacia">
              No hay alumnos registrados aún.
            </p>
          ) : (
            <ul className="lista-alumnos">
              {pendingStudents.map((student) => (
                <StudentListItem key={student.userId} student={student} />
              ))}
            </ul>
          )}

          <div className="acciones-row">
            <button
              className="confirmar boton-icono"
              onClick={() => setShowAddStudent(true)}
              disabled={isSaving}
            >
              ＋ Añadir alumno
            </button>
            <button
              className="secundario"
              onClick={() => navigate(`/docente/clase/${classInfo.id}`)}
              disabled={isSaving}
            >
              Volver
            </button>
          </div>
        </Card>
      </main>

      <AddStudentModal
        open={showAddStudent}
        onClose={() => setShowAddStudent(false)}
        onAdd={handleAddStudent}
      />
    </div>
  );
}

export default AsistenciaClase;
