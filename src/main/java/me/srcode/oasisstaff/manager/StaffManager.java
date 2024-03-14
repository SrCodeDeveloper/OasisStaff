package me.srcode.oasisstaff.manager;

import java.util.UUID;

import me.srcode.oasisstaff.scoreboard.ScoreboardManager;
import me.srcode.oasisstaff.helper.PluginHelper;
import me.srcode.oasisstaff.tab.TabManager;
import me.srcode.oasisstaff.user.UserHandler;
import me.srcode.oasisstaff.user.UserModel;
import me.srcode.oasisstaff.user.types.FreezeUserModel;
import me.srcode.oasisstaff.user.types.StaffUserModel;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StaffManager {
   private final ItemManager itemManager = new ItemManager();
   private final ScoreboardManager scoreboardManager;
   private final TabManager tabManager;
   private final FileConfiguration configuration;

   public StaffManager(@NotNull FileConfiguration configuration, @NotNull ScoreboardManager scoreboardManager, @NotNull TabManager tabManager) {
      this.configuration = configuration;
      this.scoreboardManager = scoreboardManager;
      this.tabManager = tabManager;
   }

   public void join(@NotNull Player player) {
      StaffUserModel staffUser = StaffUserModel.of(player);
      UserHandler.registry((UserModel)staffUser);
      staffUser.vanish();
      player.getEquipment().setArmorContents((ItemStack[])null);
      player.getInventory().clear();
      this.tabManager.create(player, "tags.staff");
      this.scoreboardManager.create(player);
      this.itemManager.getItems(player);
      player.getEquipment().setHelmet(new ItemStack(Material.TURTLE_HELMET));
      player.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
      player.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
      player.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
      player.setGameMode(GameMode.SURVIVAL);
      player.setAllowFlight(true);
      player.setFlying(true);
      player.setHealth(20.0D);
      player.setFoodLevel(20);
   }

   public void unjoin(@NotNull Player player) {
      StaffUserModel staffUser = (StaffUserModel)UserHandler.find(player);
      if (staffUser != null) {
         if (staffUser.isVanish()) {
            staffUser.unvanish();
         }

         this.tabManager.reset(player);
         this.scoreboardManager.reset(player);
         player.getEquipment().setArmorContents((ItemStack[])null);
         player.getInventory().clear();
         staffUser.getStaffUserDataContainer().restore(player);
         UserHandler.remove(player);
      }

   }

   public void freeze(@NotNull Player staff, @NotNull Player target) {
      if (target.hasPermission("oasisstaff.command.staffmode")) {
         staff.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.try-freeze-staff")));
      } else {
         boolean isInCache = true;
         StaffUserModel staffUser = (StaffUserModel)UserHandler.find(staff);
         if (staffUser == null) {
            isInCache = false;
            staffUser = StaffUserModel.of(staff);
            UserHandler.registry((UserModel)staffUser);
         }

         FreezeUserModel freezeUser = (FreezeUserModel)UserHandler.find(target);
         if (freezeUser == null) {
            freezeUser = FreezeUserModel.of(target, staff.getUniqueId());
            UserHandler.registry((UserModel)freezeUser);
            staffUser.setFreezeUser(target.getUniqueId());
         }

         if (freezeUser.isFreeze()) {
            freezeUser.unfreeze();
            this.tabManager.reset(target);
            StaffUserModel bukkitStaffUser = (StaffUserModel)UserHandler.find(freezeUser.getBukkitStaffPlayer());
            if (bukkitStaffUser != null) {
               bukkitStaffUser.setFreezeUser((UUID)null);
               if (!isInCache) {
                  UserHandler.remove(staff);
               }

               staff.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.unfreeze-staff").replace("<player>", target.getName())));
               target.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.unfreeze-player").replace("<player>", staff.getName())));
            }

            UserHandler.remove(target);
         } else {
            freezeUser.freeze();
            this.tabManager.create(target, "tags.freeze");
            staff.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.freeze-staff").replace("<player>", target.getName())));
            target.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.freeze-player").replace("<player>", staff.getName())));
         }
      }

   }

   public ItemManager getItemManager() {
      return this.itemManager;
   }

   public ScoreboardManager getScoreboardManager() {
      return this.scoreboardManager;
   }

   public TabManager getTabManager() {
      return this.tabManager;
   }

   public FileConfiguration getConfiguration() {
      return this.configuration;
   }
}
