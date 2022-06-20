package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.ConsoleUI.ConsoleUI;
import sk.tuke.gamestudio.core.Field;
import sk.tuke.gamestudio.core.Game;
import sk.tuke.gamestudio.core.Solver;
import sk.tuke.gamestudio.core.TilesField;
import sk.tuke.gamestudio.server.service.*;

import java.util.Random;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.gamestudio.server.*"))
public class SpringClient {
    Random random = new Random();
    int field = random.nextInt(4);

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }
    @Bean
    public RestTemplate restTemplate() {return new RestTemplate();}
    @Bean
    public CommandLineRunner runner(ConsoleUI ui) { return args -> ui.startMenu(); }

    @Bean
    public ConsoleUI consoleUI() { return new ConsoleUI(); }
    @Bean
    public Game game() {
        return new Game(new Solver(new Field(9, new TilesField(field))), new TilesField(field));
    }


    @Bean
    public CommentService commentService() {
        //return new CommentServiceJPA();
        return new CommentServiceRestClient();
    }
    @Bean
    public ScoreService scoreService() {
        //return new ScoreServiceJPA();
        return new ScoreServiceRestClient();
    }
    @Bean
    public RatingService ratingServiceService() {
        //return new RatingServiceJPA();
        return new RatingServiceRestClient();
    }
}
