package sk.tuke.gamestudio.server.entity;


import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery( name = "Comment.getComments",
        query = "SELECT c FROM Comment c WHERE c.game=:game")
@NamedQuery( name = "Comment.resetComments",
        query = "DELETE FROM Comment")


public class Comment implements Serializable {


    @Id
    @GeneratedValue
    private int id;

    private String game;

    private String player;

    private String comment;

    private Date commentedOn;


    public Comment(String game, String player, String comment, Date commentedOn) {
        this.game = game;
        this.player = player;
        this.comment = comment;
        this.commentedOn = commentedOn;
    }

    public Comment() {

    }

    public Date getCommentedOn() {
        return commentedOn;
    }

    public String getComment() {
        return comment;
    }

    public String getGame() {
        return game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setCommentedOn(Date commentedOn) {
        this.commentedOn = commentedOn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", comment=" + comment +
                ", commentedOn=" + commentedOn +
                '}';
    }


}
