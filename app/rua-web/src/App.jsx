import MyButtons from "./components/MyButtons";
import { TopBar } from "./components/TopBar/Topbar";
import ContextualHelp from "./components/ContextualHelp";


function App() {


  const tmp = {
    background: "#723c3c"
  }

  const rolAndName = "Alumno - Tomas"
  return (
    <div style={tmp}>
      <TopBar 
      nameAndRolString={rolAndName} URLToGoWhenClick="/home"
      />
      <ContextualHelp/>
      
      <h1> Bienvenido! </h1>
      <MyButtons/>
    </div>
  );
}

export default App;