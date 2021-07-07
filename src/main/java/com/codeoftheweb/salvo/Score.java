package com.codeoftheweb.salvo;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long  id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Player_id")
    Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Game_id")
    Game game;

    private float score;
    private LocalDateTime dateFinish;

    public Score() {
    }

    public Score(Player player, Game game, float score, LocalDateTime dateFinish) {
        this.player = player;
        this.game = game;
        this.score = score;
        this.dateFinish = dateFinish;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public LocalDateTime getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(LocalDateTime dateFinish) {
        this.dateFinish = dateFinish;
    }
}
