package com.codeoftheweb.salvo;

import org.apache.tomcat.jni.Local;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SalvoApplication.class, args);
    }


    @Bean
    public CommandLineRunner initData(PlayerRepository repository,
                                      GameRepository gameRepo,
                                      GamePlayerRepository gamePlayerRepo,
                                      ShipRepository shipRepo,
                                      SalvoRepository salvoRepo,
                                      ScoreRepository scoreRepo) {
        return (args) -> {

            Player player1=new Player("saggomila@gmail.com");
            Player player2=new Player("santi@gmail.com");

            Game game1=new Game(LocalDateTime.now());
            Game game2=new Game(LocalDateTime.now().plusHours(1));

            GamePlayer gamePlayer1 = new GamePlayer(game1, player1, LocalDateTime.now());
            GamePlayer gamePlayer2 = new GamePlayer(game1, player2, LocalDateTime.now());



            Ship ship1 = new Ship(gamePlayer1, "Submarine", List.of("J5", "J6", "J7") );
            Ship ship2 = new Ship(gamePlayer1, "Carrier", List.of("B5", "B6", "B7", "B8", "B9") );

            Salvo salvo1 = new Salvo(gamePlayer1, List.of("J8", "B1"),  1);
            Salvo salvo2 = new Salvo(gamePlayer2, List.of("J2", "B3"), 2);

            Score score1 = new Score(player1, game1, 1f, LocalDateTime.now());
            Score score2 = new Score(player2, game2, 0f, LocalDateTime.now());



            repository.save(player1);
            repository.save(player2);
            gameRepo.save(game1);
            gameRepo.save(game2);
            gamePlayerRepo.save(gamePlayer1);
            gamePlayerRepo.save(gamePlayer2);
            shipRepo.save(ship1);
            shipRepo.save(ship2);
            salvoRepo.save(salvo1);
            salvoRepo.save(salvo2);
            scoreRepo.save(score1);
            scoreRepo.save(score2);





        };
    }
}