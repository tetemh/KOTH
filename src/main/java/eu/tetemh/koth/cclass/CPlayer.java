package eu.tetemh.koth.cclass;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class CPlayer {

    private Player player;
    private UUID uuid;
    private String name;
    private int score;
    private boolean inGame;

    public CPlayer (Player player) {
        this.player = player;
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.score = 0;
        this.inGame = false;
    }

    public CPlayer setGamemode(boolean mode) {
        this.inGame = mode;
        return this;
    }

    public CPlayer addPoint(int points) {
        this.score += points;
        return this;
    }
    public CPlayer setPoint(int points){
        this.score = points;
        return this;
    }
    public CPlayer removePoints(int points) {
        this.score -=  points;
        return this;
    }
    public Boolean inGame() {
        return this.inGame;
    }
}
