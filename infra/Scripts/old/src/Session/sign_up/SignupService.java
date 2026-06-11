/**
 * TODO: Falta añadir que token pueda expirar
*/
@Service
public class SignupService {
    
    private String confirmationLink = RuaConfig.BACKEND_URL + "/account/verify_email"; //Link que el usuario tiene que dar click para confirmar su identidad (faltandole el token)
    private String hostEmail = "ruaaplicacion@gmail.com"; //Email host de la pagina SendGrid

    /**
     * Todo el proceso para crear usuario (Sin confirmar)
     * 1- Generar token de confirmacion por correo
     * 2- encriptar contraseña
     * 3- Guardar usuario en la BD
     * 4- enviar correo de confirmacion
     */
    public void createUser(User user){
        //para validar si es que el correo existe en la bd
        if (userTable.findByCorreo(user.correo).isPresent()) {
            throw new IllegalArgumentException("El correo ya se encuentra registrado.");
        }

        String token = generateToken();
        user.tokenConfirmation = token;

        user.contrasena = encryptPassword(user.contrasena);

        saveUserInBD(user);
        try {
            sendConfirmationEmail(user.correo, token);
        } catch (Exception e) {
            System.out.println("------------------------");
            System.out.println("ERROR EN EL ENVIO DE CORREO");
            System.out.println("------------------------");
            
        }
        
    }

    /**
     * Confirmar que el token recien creado si existe
     */
    public boolean verifyToken(String token){

        Optional<User> optUser = userTable.findByTokenConfirmation(token);

        if (optUser.isPresent()) {
            updateConfirmedUser(optUser.get());
            return true;
        } else {
            return false;
        }
    }


    public void sendConfirmationEmail(String destinationEmail, String token) throws Exception {

        String link = confirmationLink + "?token_verificacion=" + token;

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(hostEmail);
        helper.setTo(destinationEmail);
        helper.setSubject("Verificación de cuenta");

        String html = EmailDesing.createDesign(link);

        helper.setText(html, true);

        //Envio definitivo
        mailSender.send(message);
    }

    public void updateConfirmedUser(User user){
        user.correo_verificado = "true";
        user.tokenConfirmation = null;
        userTable.save(user);
    }

    public void saveUserInBD(User user){
        userTable.save(user);
    }

    public String generateToken(){
        return UUID.randomUUID().toString();
    }

    public String encryptPassword(String password){
        return passwordEncoder.encode(password);
    }

    

    
    //Constructor
    private final UserRepo userTable;
    private JavaMailSender mailSender;
    private BCryptPasswordEncoder passwordEncoder;
    public SignupService(UserRepo userTable, JavaMailSender mailSender, BCryptPasswordEncoder passwordEncoder){
        this.userTable = userTable;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }


}
