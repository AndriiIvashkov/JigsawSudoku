package sk.tuke.gamestudio.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sk.tuke.gamestudio.server.entity.User;
import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Transactional
    @Query(value = "select * from login_user u where u.username = ?1  and u.password = ?2", nativeQuery = true)
    User findUser(String username, String password);

}
