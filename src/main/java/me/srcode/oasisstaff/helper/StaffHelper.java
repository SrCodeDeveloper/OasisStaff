package me.srcode.oasisstaff.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffHelper {
   public static void randomTeleport(@NotNull Player player, @NotNull FileConfiguration configuration) {
      Collection<? extends Player> players = Bukkit.getOnlinePlayers();
      if (players.size() == 1) {
         player.sendMessage(PluginHelper.toFormat(configuration.getString("messages.random-tp-cancelled")));
      } else {
         List<Player> playerList = new ArrayList(players);
         playerList.remove(player);
         Player target = (Player)playerList.get(PluginHelper.getRandom(playerList.size()));
         player.teleport(target);
         player.sendMessage(PluginHelper.toFormat(configuration.getString("messages.random-tp-successfully").replace("<player>", target.getName())));
      }

   }
}
