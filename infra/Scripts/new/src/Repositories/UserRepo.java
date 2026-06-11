/**
 * Repositorio para usuarios confirmados en la base de datos.
 * Los usuarios no confirmados tienen su propio repositorio: UnconfirmedUserRepo.
 */
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByMail(String mail);
}
