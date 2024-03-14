package me.srcode.oasisstaff.helper;

import java.util.Random;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class PluginHelper {
   private static final Random RANDOM = new Random();

   public static String processAndFormat(@NotNull Player player, @NotNull String string) {
      return toFormat(processString(player, string));
   }

   public static String processString(@NotNull Player player, @NotNull String string) {
      return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(player, string) : string;
   }

   public static String toFormat(String message) {
      return message != null ? ChatColor.translateAlternateColorCodes('&', message) : "";
   }

   public static int getRandom(int size) {
      return RANDOM.nextInt(size);
   }

}
