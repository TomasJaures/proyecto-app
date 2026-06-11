@Entity
@Table(name = "usuarios") //Tabla usuarios en la BD;
public class User {

    /**
     * [!!!] No hay motivos por lo que todos los atributos sean publicos, unicamente es para que la seccion no tenga 200+ lineas de Getters y Setters
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idUsuario;

    public String nombre;
    public String apellido1;
    public String apellido2;
    public String correo;
    public String contrasena;

    public String correo_verificado;

    /**
     * token_verificacion me daba problemas por el metodo de UserRepo.java, Spring Boot solo aceptaba camelCase, de ahi a mas nada.
     */
    public String tokenConfirmation;

    @Column(insertable = false, updatable = false)
    public LocalDateTime fecha_creacion;

}