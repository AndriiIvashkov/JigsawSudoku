package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.server.entity.Rating;
import sk.tuke.gamestudio.server.service.RatingException;
import sk.tuke.gamestudio.server.service.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/rating")

public class RatingServiceRest {
    @Autowired
    private RatingService ratingService;

    @GetMapping("/{game}")
    public double getAverageRating(@PathVariable String game) {
        return ratingService.getAverageRating(game);
    }
    @GetMapping
    public List<Rating> getRatings() throws RatingException {
        return ratingService.getRatings();
    }
    @PostMapping
    public void setRating(@RequestBody Rating rating) {
        ratingService.setRating(rating);
    }
}
