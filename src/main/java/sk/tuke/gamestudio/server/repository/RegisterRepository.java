package sk.tuke.gamestudio.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import sk.tuke.gamestudio.server.entity.User;
import javax.transaction.Transactional;

@Repository
@Component
public interface RegisterRepository extends JpaRepository<User, String> {

    @Transactional
    @Modifying
    @Query(value = "insert into login_user (username, password) values (:username, :password)" , nativeQuery = true)
    void registerUser(String username, String password);
    User findByUsername(String username);
}
