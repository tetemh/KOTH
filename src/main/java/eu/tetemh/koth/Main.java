package eu.tetemh.koth;

import eu.tetemh.koth.commands.KOTHCommand;
import eu.tetemh.koth.events.JoinEvent;
import eu.tetemh.koth.events.QuitEvent;
import eu.tetemh.koth.managers.PlayersManager;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private PlayersManager playersManager;
    private static Main instance;
    private Location spawn;

    @Override
    public void onEnable() {
        instance = this;
        this.playersManager = new PlayersManager();
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new QuitEvent(), this);

        getCommand("koth").setExecutor(new KOTHCommand());
    }

    @Override
    public void onDisable() {

    }

    public static Main getInstance() {
        return instance;
    }

    public PlayersManager getPlayersManager() {
        return this.playersManager;
    }
    public Location getSpawn() {
        return this.spawn;
    }
    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

}