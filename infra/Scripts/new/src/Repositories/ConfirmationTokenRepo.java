/**
 * Repositorio para tokens de confirmación de correo.
 */
public interface ConfirmationTokenRepo extends JpaRepository<ConfirmationToken, Integer> {
    Optional<ConfirmationToken> findByContent(String content);
    void deleteByContent(String content);
}
