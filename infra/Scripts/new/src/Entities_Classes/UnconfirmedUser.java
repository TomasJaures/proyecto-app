@Entity
@Table(name = "unconfirmed_user")
public class UnconfirmedUser {

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

    @Column(name = "attempt")
    public Integer attempt = 0;

    @Column(name = "is_blocked")
    public Boolean isBlocked = false;

    @Column(name = "block_time")
    public LocalDateTime blockTime;

}
