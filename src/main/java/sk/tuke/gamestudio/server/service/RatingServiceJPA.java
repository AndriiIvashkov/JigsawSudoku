package sk.tuke.gamestudio.server.service;


import sk.tuke.gamestudio.server.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        entityManager.persist(rating);
    }

    @Override
    public double getAverageRating(String game) throws RatingException {
        List<Rating> ratings = (List<Rating>)entityManager.createQuery("select r from Rating r").getResultList();
        double a = 0;
        for (Rating x : ratings) {
            a += x.getRating();
        }
        return a / ratings.size();
    }

    @Override
    public List<Rating> getRatings() throws RatingException{
        return (List<Rating>)entityManager.createQuery("select r from Rating r").getResultList();
    }

    @Override
    public void reset() throws RatingException {

    }
}
