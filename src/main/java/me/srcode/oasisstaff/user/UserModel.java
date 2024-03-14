package me.srcode.oasisstaff.user;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class UserModel {
   private final UUID uuid;

   public UserModel(UUID uuid) {
      this.uuid = uuid;
   }

   @Contract("_ -> new")
   @NotNull
   public static UserModel of(@NotNull Player player) {
      return new UserModel(player.getUniqueId());
   }

   @NotNull
   public Player getBukkitPlayer() {
      return Bukkit.getPlayer(this.uuid);
   }
}
