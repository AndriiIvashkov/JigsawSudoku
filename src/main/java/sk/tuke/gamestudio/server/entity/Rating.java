package sk.tuke.gamestudio.server.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity

@NamedQuery(name = "Rating.getRating",
        query="SELECT r.rating FROM Rating r WHERE r.game=:game AND r.player=:player")
//@NamedQuery(name="Rating.getAverageRating",
//        query = "SELECT AVG (r.rating) FROM Rating r WHERE r.game =:game")
//@NamedQuery(name = "Rating.getRating",
//        query = "select r FROM Rating r WHERE r.game=:game AND r.player=:player")



public class Rating implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    private String game;

    private String player;

    private int rating;

    private Date ratedOn;

    public Rating(String game, String player, int rating, Date ratedOn) {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.ratedOn = ratedOn;
    }

    public Rating() {

    }

    public int getRating() {
        return rating;
    }

    public String getGame() {
        return game;
    }

    public String getPlayer() {
        return player;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Date ratedOn) {
        this.ratedOn = ratedOn;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", rating=" + rating +
                ", ratedOn=" + ratedOn +
                '}';
    }
}
