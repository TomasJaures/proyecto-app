/**
 * TODO: Falta añadir expiración de token (campo expire_at ya existe en confirmation_token)
 */
@Service
public class SignupService {

    private final String confirmationLink = RuaConfig.BACKEND_URL + "/account/verify_email";
    private final String hostEmail = "ruaaplicacion@gmail.com";

    private final UnconfirmedUserRepo unconfirmedUserRepo;
    private final ConfirmationTokenRepo confirmationTokenRepo;
    private final UserRepo userRepo;
    private final JavaMailSender mailSender;
    private final BCryptPasswordEncoder passwordEncoder;

    public SignupService(UnconfirmedUserRepo unconfirmedUserRepo,
                         ConfirmationTokenRepo confirmationTokenRepo,
                         UserRepo userRepo,
                         JavaMailSender mailSender,
                         BCryptPasswordEncoder passwordEncoder) {
        this.unconfirmedUserRepo = unconfirmedUserRepo;
        this.confirmationTokenRepo = confirmationTokenRepo;
        this.userRepo = userRepo;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Proceso completo para crear un usuario no confirmado:
     * 1- Verificar que el correo no esté ya registrado (en users ni en unconfirmed_user)
     * 2- Encriptar contraseña
     * 3- Guardar en unconfirmed_user
     * 4- Generar y guardar token en confirmation_token
     * 5- Enviar correo de confirmación
     */
    public void createUser(UnconfirmedUser user) {
        // Verificar duplicados en usuarios confirmados
        if (userRepo.findByMail(user.mail).isPresent()) {
            throw new IllegalArgumentException("El correo ya se encuentra registrado.");
        }
        // Verificar duplicados en usuarios pendientes
        if (unconfirmedUserRepo.findByMail(user.mail).isPresent()) {
            throw new IllegalArgumentException("Ya existe una solicitud de registro pendiente para este correo.");
        }

        user.hashedPassword = encryptPassword(user.hashedPassword);
        UnconfirmedUser savedUser = saveUnconfirmedUser(user);

        String token = generateToken();
        saveConfirmationToken(savedUser, token);

        try {
            sendConfirmationEmail(user.mail, token);
        } catch (Exception e) {
            System.out.println("------------------------");
            System.out.println("ERROR EN EL ENVIO DE CORREO");
            System.out.println("------------------------");
        }
    }

    /**
     * Verifica el token recibido por correo.
     * Si es válido, promueve al usuario de unconfirmed_user a users
     * y elimina el registro temporal.
     */
    public boolean verifyToken(String token) {
        Optional<ConfirmationToken> optToken = confirmationTokenRepo.findByContent(token);

        if (optToken.isPresent()) {
            ConfirmationToken confirmationToken = optToken.get();
            promoteToConfirmedUser(confirmationToken.unconfirmedUser);
            // Al eliminar el unconfirmed_user, confirmation_token se borra en cascada (ON DELETE CASCADE en BD)
            unconfirmedUserRepo.delete(confirmationToken.unconfirmedUser);
            return true;
        }
        return false;
    }

    /**
     * Mueve los datos del usuario no confirmado a la tabla users (usuarios verificados).
     * Los campos program_id y calendar_id requieren registros previos en sus tablas;
     * por ahora se dejan en null hasta que se defina el flujo de asignación.
     */
    public void promoteToConfirmedUser(UnconfirmedUser unconfirmedUser) {
        User confirmedUser = new User();
        confirmedUser.userName = unconfirmedUser.userName;
        confirmedUser.lastName1 = unconfirmedUser.lastName1;
        confirmedUser.lastName2 = unconfirmedUser.lastName2;
        confirmedUser.mail = unconfirmedUser.mail;
        confirmedUser.hashedPassword = unconfirmedUser.hashedPassword;
        // TODO: Definir rol por defecto o recibirlo durante el registro
        confirmedUser.userRole = "student";
        userRepo.save(confirmedUser);
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

        mailSender.send(message);
    }

    public UnconfirmedUser saveUnconfirmedUser(UnconfirmedUser user) {
        return unconfirmedUserRepo.save(user);
    }

    public void saveConfirmationToken(UnconfirmedUser user, String token) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.content = token;
        confirmationToken.createdAt = LocalDateTime.now();
        // TODO: Definir tiempo de expiración real (ej: 24 horas)
        confirmationToken.expireAt = LocalDateTime.now().plusHours(24);
        confirmationToken.unconfirmedUser = user;
        confirmationTokenRepo.save(confirmationToken);
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
