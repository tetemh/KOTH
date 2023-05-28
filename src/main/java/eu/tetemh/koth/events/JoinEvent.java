package eu.tetemh.koth.events;

import eu.tetemh.koth.Main;
import eu.tetemh.koth.cclass.CPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    private static Main main = Main.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        main.getPlayersManager().addPlayer(new CPlayer(player));
    }
}
