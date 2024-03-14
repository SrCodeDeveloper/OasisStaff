package me.srcode.oasisstaff.scoreboard.task;

import com.xism4.sternalboard.SternalBoard;
import java.util.Iterator;
import java.util.List;
import me.srcode.oasisstaff.PluginLauncher;
import me.srcode.oasisstaff.helper.PluginHelper;
import me.srcode.oasisstaff.scoreboard.ScoreboardManager;
import me.srcode.oasisstaff.user.UserHandler;
import me.srcode.oasisstaff.user.types.StaffUserModel;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class ScoreboardTask extends BukkitRunnable {
   private final ScoreboardManager scoreboardManager;
   private final FileConfiguration configuration;

   public ScoreboardTask(@NotNull ScoreboardManager scoreboardManager) {
      this.scoreboardManager = scoreboardManager;
      this.configuration = PluginLauncher.getInstance().getConfig();
   }

   public void startTask() {
      this.runTaskTimerAsynchronously(PluginLauncher.getInstance(), 0L, 40L);
   }

   public void run() {
      List<String> stringList = this.configuration.getStringList("scoreboard.lines");
      Iterator var2 = this.scoreboardManager.get().values().iterator();

      while(var2.hasNext()) {
         SternalBoard handler = (SternalBoard)var2.next();
         StaffUserModel staffUser = (StaffUserModel)UserHandler.find(handler.getPlayer());
         stringList.replaceAll((scoreboardValue) -> {
            return PluginHelper.processAndFormat(handler.getPlayer(), scoreboardValue.replace("<vanish>", Boolean.toString(staffUser.isVanish())));
         });
         handler.updateLines(stringList);
      }

   }
}
