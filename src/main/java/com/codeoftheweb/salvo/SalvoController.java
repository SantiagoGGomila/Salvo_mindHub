package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {
    @Autowired
    private GameRepository repo;
    @Autowired
    private GamePlayerRepository gamePlayerRepo;

    @RequestMapping("/games")
    public Map<String, Object> getGames() {
        List<Game> gameList = repo.findAll();
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("games", gameList.stream().map(this::gameDto).collect(Collectors.toList()));
        return dto;
    }

    @RequestMapping("/game_view/{id}")
    private Map<String, Object> getGamePlayerId(@PathVariable Long id) {
        GamePlayer gamePlayer=gamePlayerRepo.findById(id).get();
        Game game = gamePlayer.getGame();
        Map<String, Object> shipDto= gameDto(game);
        shipDto.put("ships", gamePlayer.getShips().stream().map(ship -> shipDto(ship)).collect(Collectors.toList()));
        shipDto.put("salvoes",game.getGamePlayer().stream().flatMap(x -> x.getSalvoes().stream().map(this ::salvoDto)).collect(Collectors.toList()));
        return shipDto;
    }

    private Map<String, Object> gameDto(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getDate());
        dto.put("gamePlayers", game.getGamePlayer().stream().map(this::gamePlayerDto).collect(Collectors.toList()));
        dto.put("score", game.getGamePlayer().stream().map(x->scoreDto(x)).collect(Collectors.toList()));



        return dto;
    }

    private Map<String, Object> gamePlayerDto(GamePlayer gameplayer) {
        Game game = gameplayer.getGame();
        Map<String, Object> gamePlayerdto = new LinkedHashMap<String, Object>();
        gamePlayerdto.put("id", gameplayer.getId());
        gamePlayerdto.put("player", playerDto(gameplayer.getPlayer()));



        return gamePlayerdto;
    }

    private Map<String, Object> playerDto(Player player) {
        Map<String, Object> playerDto = new LinkedHashMap<String, Object>();
        playerDto.put("id", player.getId());
        playerDto.put("email", player.getUserName());

        return playerDto;
    }

    private Map<String, Object> shipDto(Ship ship) {
        Map<String, Object> shipDto = new LinkedHashMap<String, Object>();
        shipDto.put("Type", ship.getShipType());
        shipDto.put("locations", ship.getShipLocation());
        return shipDto;
    }
    private  Map<String, Object> salvoDto (Salvo salvo){
        Map<String, Object> salvoDto = new LinkedHashMap<>();
        salvoDto.put("turn", salvo.getTurn());
        salvoDto.put("locations", salvo.getSalvoLocation());
        salvoDto.put("player", salvo.getGamePlayer().getId());

        return salvoDto;
    }

    private Map<String, Object> scoreDto (GamePlayer gamePlayer){
        Map<String, Object> scoreDto = new LinkedHashMap<>();
        if(gamePlayer.getScore().isPresent()){
            scoreDto.put("dateFinish", gamePlayer.getScore().get().getDateFinish());
            scoreDto.put("Score", gamePlayer.getScore().get().getScore());
            scoreDto.put("player", gamePlayer.getScore().get().getPlayer().getId());

        }else{
            scoreDto.put("score", "el juego todavia no ha terminado.");
        }

        return scoreDto;

    }

}
