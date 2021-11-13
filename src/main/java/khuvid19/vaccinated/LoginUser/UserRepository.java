package khuvid19.vaccinated.LoginUser;

import khuvid19.vaccinated.LoginUser.Data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickName(String nickName);
    Optional<User> findByAccessToken(String accessToken);
    boolean existsByNickName(String nickName);
}