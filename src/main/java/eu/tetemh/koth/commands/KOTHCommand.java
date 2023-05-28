package eu.tetemh.koth.commands;

import eu.tetemh.koth.Main;
import eu.tetemh.koth.cclass.CPlayer;
import eu.tetemh.koth.managers.PlayersManager;
import eu.tetemh.koth.tools.ItemBuilder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.TropicalFish;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class KOTHCommand implements CommandExecutor {

    private Main main = Main.getInstance();
    private PlayersManager playersManager = main.getPlayersManager();
    private int i;
    private int gameTimer;
    private int gTime = 1*60;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if( sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length >= 1) {
                switch (args[0]) {
                    case "start" :
                        player.sendMessage("§aVous avez lancer la partie");
                        playersManager.getPlayers().forEach((uuid, v) ->  {
                            CPlayer cPlayer = (CPlayer) v;
                            cPlayer.setGamemode(true);
                        });
                        i = 6;
                        gameTimer = 0;
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                i--;
                                playersManager.getInGamePlayers().stream().forEach((iPlayer) -> {
                                    if (i == 0) {
                                        iPlayer.getPlayer().sendTitle("§aDémarrage", "", 20, 60, 20);
                                        iPlayer.getPlayer().teleport(main.getSpawn());
                                        iPlayer.getPlayer().getInventory().clear();
                                        iPlayer.getPlayer().setGameMode(GameMode.ADVENTURE);
                                        iPlayer.getPlayer().getInventory().setItem(4, new ItemBuilder(Material.TROPICAL_FISH, 1).addEnchantment(Enchantment.KNOCKBACK, 3, true).build());
                                        iPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 255, false, false, false));
                                        cancel();
                                    }
                                    iPlayer.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§eDémarrage dans : " + i));
                                });
                                if(i == 0) {
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            gameTimer++;
                                            playersManager.getInGamePlayers().stream().forEach((iPlayer) -> {
                                                switch(new Location(iPlayer.getPlayer().getWorld(),
                                                        iPlayer.getPlayer().getLocation().getX(),
                                                        iPlayer.getPlayer().getLocation().getY()-1,
                                                        iPlayer.getPlayer().getLocation().getZ()).getBlock().getType()) {
                                                    case RED_CONCRETE:
                                                        iPlayer.addPoint(2);
                                                        break;
                                                    case BLACK_CONCRETE:
                                                        iPlayer.addPoint(1);
                                                }
                                                iPlayer.getPlayer().sendMessage(String.valueOf(iPlayer.getScore()));
                                                if(gameTimer == gTime) {
                                                    iPlayer.getPlayer().teleport(iPlayer.getPlayer().getWorld().getSpawnLocation());
                                                    iPlayer.getPlayer().getActivePotionEffects().forEach(potionEffect -> iPlayer.getPlayer().removePotionEffect(potionEffect.getType()));
                                                    iPlayer.getPlayer().getInventory().clear();
                                                }
                                            });
                                            if(gameTimer == gTime) {
                                                StringBuilder scoreString = new StringBuilder();
                                                scoreString.append("§eList des scores : \n\r");
                                                scoreString.append("\n\r");
                                                playersManager.getSortedScore().forEach((name, score) -> {
                                                   scoreString.append("§e" + name + ": " + score + "\n\r");
                                                });
                                                playersManager.getInGamePlayers().stream().forEach((iPlayer) -> {
                                                    iPlayer.setPoint(0);
                                                });
                                                main.getServer().broadcastMessage(scoreString.toString());
                                                cancel();
                                            }
                                        }
                                    }.runTaskTimer(main, 0, 20L);
                                }
                            }
                        }.runTaskTimer(main, 0, 20L);
                        break;

                    case "setSpawn":
                        main.setSpawn(player.getLocation());
                        player.sendMessage("§aLe spawn de la partie à bien été définie");
                        break;

                    case "print":
                        playersManager.getPlayers().forEach((uuid, v) ->  {
                            CPlayer cPlayer = (CPlayer) v;
                            boolean tmp = cPlayer.inGame();
                            System.out.println(tmp);
                        });
                        break;
                }
            }
        }

        return false;
    }
}
