@Entity
@Table(name = "users")
public class User {

    /**
     * [!!!] Los atributos son públicos para evitar 200+ líneas de Getters/Setters (convención preexistente del proyecto)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public Integer userId;

    @Column(name = "user_name")
    public String userName;

    @Column(name = "last_name1")
    public String lastName1;

    @Column(name = "last_name2")
    public String lastName2;

    @Column(name = "mail")
    public String mail;

    @Column(name = "hashed_password")
    public String hashedPassword;

    @Column(name = "user_role")
    public String userRole;

    // program_id y calendar_id son FKs a tablas propias; por ahora se mapean
    // como simples enteros para no romper la arquitectura existente.
    @Column(name = "program_id")
    public Integer programId;

    @Column(name = "calendar_id")
    public Integer calendarId;

}
