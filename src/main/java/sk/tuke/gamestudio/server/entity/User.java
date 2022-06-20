package sk.tuke.gamestudio.server.entity;
import javax.persistence.*;

@Entity
@Table(name = "login_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    @Transient
    private String verifiedPassword;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getVerifiedPassword() {
        return verifiedPassword;
    }

    public void setVerifiedPassword(String verifiedPassword) {
        this.verifiedPassword = verifiedPassword;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username='" + username +
            ", password='" + password +
            ", verifiedpassword='" + verifiedPassword +
            '}';
    }
}
