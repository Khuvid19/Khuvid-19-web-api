package khuvid19.vaccinated.LoginUser;

import khuvid19.vaccinated.LoginUser.Data.Child;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Long> {
    Optional<Child> findChildByParent_Id(Long parentId);

    Optional<Child> findByNameAndParent_Id(String name, Long parentId);
}
