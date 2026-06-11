@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:1427")
public class SignupController {

    //Constructor
    private final SignupService signupService;
    public SignupController(SignupService signupService){
        this.signupService = signupService;
    }

    /**
     * Crear usuario en la BD
     * @param user : JSON del usuario creado (Nombre, apellido, etc.)
     * @return : Redirrecion a la email_sended
     */

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody User user){
        signupService.createUser(user);

        URI uri = URI.create(RuaConfig.BACKEND_URL + "/account/email_sended");

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(uri)
                .build();
    }

    /**
     * 
     * @param token_verificacion
     * @return
     */
    @GetMapping("/verify_email")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token_verificacion){

        //Si en el futuro da u
        boolean result = signupService.verifyToken(token_verificacion);

        URI uri = URI.create(
            result ? 
            RuaConfig.BACKEND_URL + "/account/confirmation_success" : 
            RuaConfig.BACKEND_URL + "/account/confirmation_fail");
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }

    /**
     * ========== Paginas informando la situacion de la confirmacion por correo ==========
     */

    
    @GetMapping("/email_sended")
    public String notifyEmailSended(){
        return "Te hemos enviado un EMAIL!! Revisa tu correo de SPAM";
    }

    @GetMapping("/confirmation_success")
    public String confirmationSuccess(){
        return "Correo verificado correctamente! Puedes volver al Log In";
    }

    @GetMapping("/confirmation_fail")
    public String confirmationFail(){
        return "Hubo un problema con tu correo :(";
    }

}
