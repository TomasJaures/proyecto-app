/**
 * Repositorio para usuarios pendientes de verificación de correo.
 */
public interface UnconfirmedUserRepo extends JpaRepository<UnconfirmedUser, Integer> {
    Optional<UnconfirmedUser> findByMail(String mail);
}
