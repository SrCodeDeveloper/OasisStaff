package me.srcode.oasisstaff.user.types;

import java.util.Iterator;
import java.util.UUID;
import me.srcode.oasisstaff.PluginLauncher;
import me.srcode.oasisstaff.user.UserModel;
import me.srcode.oasisstaff.user.data.StaffUserDataContainer;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StaffUserModel extends UserModel {
   private final StaffUserDataContainer staffUserDataContainer;
   private UUID freezeUser;
   private boolean vanish;
   private boolean privateChat;

   public StaffUserModel(@NotNull UUID uuid, StaffUserDataContainer staffUserDataContainer) {
      super(uuid);
      this.staffUserDataContainer = staffUserDataContainer;
      this.privateChat = false;
   }

   public StaffUserModel(@NotNull UUID uuid, @NotNull UUID freezeUser, StaffUserDataContainer staffUserDataContainer) {
      super(uuid);
      this.freezeUser = freezeUser;
      this.vanish = false;
      this.staffUserDataContainer = staffUserDataContainer;
   }

   @NotNull
   public static StaffUserModel of(@NotNull Player player) {
      return new StaffUserModel(player.getUniqueId(), StaffUserDataContainer.of(player));
   }

   @Nullable
   public Player getBukkitFreezePlayer() {
      return this.freezeUser == null ? null : Bukkit.getPlayer(this.freezeUser);
   }

   public void vanish() {
      PersistentDataContainer persistentDataContainer = this.getBukkitPlayer().getPersistentDataContainer();
      PluginLauncher pluginLauncher = PluginLauncher.getInstance();
      NamespacedKey namespacedKey = new NamespacedKey(pluginLauncher, "vanish");
      if (persistentDataContainer.has(namespacedKey)) {
         persistentDataContainer.remove(namespacedKey);
      }

      this.vanish = true;
      Player player = this.getBukkitPlayer();
      Iterator var5 = Bukkit.getOnlinePlayers().iterator();

      while(var5.hasNext()) {
         Player online = (Player)var5.next();
         if (!online.hasPermission("kingdomstaff.command.staffmode")) {
            online.hidePlayer(pluginLauncher, player);
         }
      }

   }

   public void unvanish() {
      this.vanish = false;
      Player player = this.getBukkitPlayer();
      Iterator var2 = Bukkit.getOnlinePlayers().iterator();

      while(var2.hasNext()) {
         Player online = (Player)var2.next();
         if (!online.hasPermission("kingdomstaff.command.staffmode")) {
            online.showPlayer(PluginLauncher.getInstance(), player);
         }
      }

   }

   public StaffUserDataContainer getStaffUserDataContainer() {
      return this.staffUserDataContainer;
   }

   public UUID getFreezeUser() {
      return this.freezeUser;
   }

   public boolean isVanish() {
      return this.vanish;
   }

   public boolean isPrivateChat() {
      return this.privateChat;
   }

   public void setFreezeUser(UUID freezeUser) {
      this.freezeUser = freezeUser;
   }

   public void setVanish(boolean vanish) {
      this.vanish = vanish;
   }

   public void setPrivateChat(boolean privateChat) {
      this.privateChat = privateChat;
   }
}
