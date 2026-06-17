/**
 * Repositorio para usuario en la base de datos
 */
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByTokenConfirmation(String TokenConfirmation);
    Optional<User> deleteByTokenConfirmation(String TokenConfirmation);
    Optional<User> findByCorreo(String correo); //para buscar un usuario usando su correo
}