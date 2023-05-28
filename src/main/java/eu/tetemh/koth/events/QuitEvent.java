package eu.tetemh.koth.events;

import eu.tetemh.koth.Main;
import eu.tetemh.koth.cclass.CPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    private static Main main = Main.getInstance();

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        main.getPlayersManager().removePlayer(player.getUniqueId());
    }
}
