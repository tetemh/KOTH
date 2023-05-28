package eu.tetemh.koth.managers;

import eu.tetemh.koth.cclass.CPlayer;

import java.util.*;
import java.util.stream.Collectors;

public class PlayersManager {

    private HashMap<UUID, CPlayer> players = new HashMap<>();
    private HashMap<String, Integer> sortedScore = new HashMap<>();


    public HashMap getPlayers () {
        return this.players;
    }

    public PlayersManager addPlayer(CPlayer player) {
        players.put(player.getUuid(), player);
        return this;
    }

    public PlayersManager removePlayer(UUID uuid) {
        players.remove(uuid);
        return this;
    }

    public List<CPlayer> getInGamePlayers() {
        return this.players.values().stream().filter(CPlayer::inGame).collect(Collectors.toList());
    }

    public HashMap<String, Integer> getSortedScore() {

        getInGamePlayers().forEach(iPlayer -> {
            this.sortedScore.put(iPlayer.getName(), iPlayer.getScore());
        });

        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : this.sortedScore.entrySet()) {
            list.add(entry.getValue());
        }
        Collections.sort(list);
        for (int num : list) {
            for (Map.Entry<String, Integer> entry : this.sortedScore.entrySet()) {
                if (entry.getValue().equals(num)) {
                    sortedMap.put(entry.getKey(), num);
                }
            }
        }

        return sortedMap;

    }

}
