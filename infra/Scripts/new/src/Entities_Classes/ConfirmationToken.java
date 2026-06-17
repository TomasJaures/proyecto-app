@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    public Integer tokenId;

    @Column(name = "content", nullable = false)
    public String content;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "expire_at", nullable = false)
    public LocalDateTime expireAt;

    // Relación 1:1 con UnconfirmedUser (UNIQUE en BD)
    @OneToOne
    @JoinColumn(name = "user_id")
    public UnconfirmedUser unconfirmedUser;

}
