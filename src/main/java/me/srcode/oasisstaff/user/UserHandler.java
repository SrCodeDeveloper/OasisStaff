package me.srcode.oasisstaff.user;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class UserHandler {
   private static final HashMap<UUID, UserModel> userCache = new HashMap();

   public static UserModel find(@NotNull Player player) {
      return (UserModel)userCache.get(player.getUniqueId());
   }

   public static void registry(UserModel userModel) {
      userCache.put(userModel.getBukkitPlayer().getUniqueId(), userModel);
   }

   @NotNull
   public static UserModel registry(@NotNull Player player) {
      UserModel userModel = UserModel.of(player);
      userCache.put(player.getUniqueId(), userModel);
      return userModel;
   }

   public static boolean isLoaded(@NotNull Player player) {
      return userCache.containsKey(player.getUniqueId());
   }

   public static UserModel removeAndGet(@NotNull Player player) {
      return (UserModel)userCache.remove(player.getUniqueId());
   }

   public static void remove(@NotNull Player player) {
      userCache.remove(player.getUniqueId());
   }

   private UserHandler() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
