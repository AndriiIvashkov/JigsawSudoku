package sk.tuke.gamestudio.server.service;


import sk.tuke.gamestudio.server.entity.Rating;

import java.util.List;

public interface RatingService {
    void setRating(Rating rating) throws RatingException;

    double getAverageRating(String game) throws RatingException;

    public List<Rating> getRatings() throws RatingException;

    void reset() throws RatingException;
}
