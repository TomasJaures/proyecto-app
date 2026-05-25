import { useNavigate } from "react-router-dom";

function EmailSended(){

    //Navegar a Log In
    const navigate = useNavigate();
    function onConfirmarClick(){
        navigate("/login")
    }

    return (
        <div>
            <h1>
                Te hemos enviado un EMAIL!!! Revisa tu correo
            </h1>
            <button onClick={onConfirmarClick}>
                Volver al Log In
            </button>
        </div>
    );
}

export default EmailSended