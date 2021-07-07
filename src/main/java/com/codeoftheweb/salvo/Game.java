package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private LocalDateTime date;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
     List<GamePlayer> gamePlayer;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
     Set<Score> score;

    public void addGamePlayer(GamePlayer gameplayer) {
        gameplayer.setGame(this);
        gamePlayer.add(gameplayer);
    }
    public List <Player> getPlayers()
    {
        return  gamePlayer.stream().map(sub -> sub.getPlayer()).collect(toList());
    }

    public Game() {
    }

    public Game(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<GamePlayer> getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(List<GamePlayer> gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public Long getId() {
        return id;
    }

    public Set<Score> getScore() {
        return score;
    }
}
