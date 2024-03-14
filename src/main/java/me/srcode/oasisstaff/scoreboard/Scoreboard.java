package me.srcode.oasisstaff.scoreboard;

import com.xism4.sternalboard.SternalBoard;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import me.srcode.oasisstaff.PluginLauncher;
import me.srcode.oasisstaff.helper.PluginHelper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Scoreboard {
   ConcurrentHashMap<UUID, SternalBoard> get();

   void reset(@NotNull Player var1);

   void create(@NotNull Player var1);

   default void createScoreboard(@NotNull Player player) {
      SternalBoard handler = new SternalBoard(player);
      FileConfiguration configuration = PluginLauncher.getInstance().getConfig();
      handler.updateTitle(PluginHelper.toFormat(configuration.getString("scoreboard.title")));
      this.get().put(player.getUniqueId(), handler);
   }

   @Nullable
   default SternalBoard find(@NotNull Player player) {
      return (SternalBoard)this.get().get(player.getUniqueId());
   }

   default void removeOfCache(@NotNull Player player) {
      SternalBoard sternalBoard = this.find(player);
      if (sternalBoard != null) {
         sternalBoard.delete();
         this.get().remove(player.getUniqueId());
      }

   }

   default void update(@NotNull Player player, @NotNull List<String> stringList) {
      SternalBoard handler = this.find(player);
      if (handler != null) {
         handler.updateLines(stringList);
      }

   }
}
