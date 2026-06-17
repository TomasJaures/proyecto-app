@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:1427")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    //TODO: Falta el rol
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginData) {
        boolean isAuthenticated = loginService.authenticate(loginData.correo, loginData.contrasena);

        if (isAuthenticated) {
            System.out.println("Esta autenticado");
            return ResponseEntity.ok("Inicio de sesión exitoso. ¡Bienvenido!");
        } else {
            System.out.println("No Esta autenticado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas o correo no verificado.");
        }
    }
}