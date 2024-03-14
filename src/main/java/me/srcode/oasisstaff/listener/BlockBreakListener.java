package me.srcode.oasisstaff.listener;

import java.util.List;
import me.srcode.oasisstaff.PluginLauncher;
import me.srcode.oasisstaff.helper.PluginHelper;
import me.srcode.oasisstaff.user.UserHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public class BlockBreakListener implements Listener {
   private final FileConfiguration configuration;

   public BlockBreakListener(@NotNull FileConfiguration configuration) {
      this.configuration = configuration;
   }

   @EventHandler
   public void onBlockBreak(BlockBreakEvent breakEvent) {
      if (UserHandler.isLoaded(breakEvent.getPlayer())) {
         breakEvent.setCancelled(true);
      } else {
         Block block = breakEvent.getBlock();
         List<String> blockList = this.configuration.getStringList("x-ray.blocks");
         if (blockList.contains(block.getType().name())) {
            Player breakPlayer = breakEvent.getPlayer();
            String messageString = this.configuration.getString("x-ray.message");
            TextComponent message = (TextComponent)Component.text(PluginHelper.toFormat(messageString.replace("<player>", breakPlayer.getName()).replace("<block_type>", block.getType().name()))).append(Component.text(PluginHelper.toFormat(this.configuration.getString("x-ray.message-teleport"))).clickEvent(ClickEvent.runCommand("/stp " + breakEvent.getPlayer().getName())));
            Bukkit.getOnlinePlayers().stream().filter((player) -> {
               return player.hasPermission("kingdomstaff.command.xraytoggle") && !player.getPersistentDataContainer().has(new NamespacedKey(PluginLauncher.getInstance(), "xray"));
            }).forEach((player) -> {
               player.sendMessage(message);
            });
         }
      }

   }
}
