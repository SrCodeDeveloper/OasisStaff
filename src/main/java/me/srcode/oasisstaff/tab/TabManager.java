package me.srcode.oasisstaff.tab;

import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.nametag.UnlimitedNameTagManager;
import me.srcode.oasisstaff.helper.PluginHelper;
import me.srcode.oasisstaff.PluginLauncher;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TabManager implements Tab {
   private final TabAPI tabAPI = TabAPI.getInstance();
   private final FileConfiguration configuration = PluginLauncher.getInstance().getConfig();
   private UnlimitedNameTagManager manager;

   public TabManager() {
      if (this.tabAPI.getNameTagManager() instanceof UnlimitedNameTagManager) {
         this.manager = (UnlimitedNameTagManager)this.tabAPI.getNameTagManager();
      }

   }

   public void create(@NotNull Player player, @NotNull String line) {
      TabPlayer tabPlayer = this.tabAPI.getPlayer(player.getUniqueId());
      if (tabPlayer != null) {
         this.manager.setLine(tabPlayer, "belowname", PluginHelper.toFormat(this.configuration.getString(line)));
      }

   }

   public void reset(@NotNull Player player) {
      TabPlayer tabPlayer = this.tabAPI.getPlayer(player.getUniqueId());
      if (tabPlayer != null) {
         this.manager.setLine(tabPlayer, "belowname", (String)null);
      }

   }
}
