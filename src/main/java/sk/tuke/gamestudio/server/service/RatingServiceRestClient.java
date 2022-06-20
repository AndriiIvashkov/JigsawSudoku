package sk.tuke.gamestudio.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.server.entity.Rating;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RatingServiceRestClient implements RatingService {
    private final String url = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) throws RatingException {
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public double getAverageRating(String game) throws RatingException {
        return restTemplate.getForObject(url + "/" + game, Double.class);
    }

    @Override
    public List<Rating> getRatings() throws RatingException{
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(url, Rating[].class).getBody()));
    }

    @Override
    public void reset() throws RatingException {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
