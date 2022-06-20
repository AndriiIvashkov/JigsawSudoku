package sk.tuke.gamestudio.server.service;

import sk.tuke.gamestudio.server.entity.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score) throws ScoreException;

    List<Score> getTopScores(String game) throws ScoreException;

    void reset() throws ScoreException;
}
