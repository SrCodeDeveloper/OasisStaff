package me.srcode.oasisstaff.scoreboard;

import com.xism4.sternalboard.SternalBoard;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import me.srcode.oasisstaff.scoreboard.task.ScoreboardTask;
import me.srcode.oasisstaff.PluginLauncher;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ScoreboardManager implements Scoreboard {
   private final ConcurrentHashMap<UUID, SternalBoard> SCOREBOARD_CACHE = new ConcurrentHashMap();
   private final com.xism4.sternalboard.managers.ScoreboardManager SCOREBOARD_MANAGER;
   private ScoreboardTask scoreboardTask;

   public ScoreboardManager() {
      this.SCOREBOARD_MANAGER = PluginLauncher.sternalBoardPlugin.scoreboardManager;
      this.scoreboardTask = new ScoreboardTask(this);
   }

   public void create(@NotNull Player player) {
      this.SCOREBOARD_MANAGER.removeScoreboard(player);
      this.createScoreboard(player);
      if (this.SCOREBOARD_CACHE.size() == 1) {
         this.scoreboardTask.startTask();
      }

   }

   public void reset(@NotNull Player player) {
      this.removeOfCache(player);
      this.SCOREBOARD_MANAGER.setScoreboard(player);
      if (this.SCOREBOARD_CACHE.isEmpty()) {
         this.scoreboardTask.cancel();
         this.scoreboardTask = new ScoreboardTask(this);
      }

   }

   public ConcurrentHashMap<UUID, SternalBoard> get() {
      return this.SCOREBOARD_CACHE;
   }
}
