package me.srcode.oasisstaff.loader;

import me.srcode.oasisstaff.builder.ItemBuilder;
import me.srcode.oasisstaff.helper.StaffHelper;
import me.srcode.oasisstaff.inventories.InventoryPreview;
import me.srcode.oasisstaff.manager.ItemManager;
import me.srcode.oasisstaff.manager.StaffManager;
import me.srcode.oasisstaff.PluginLauncher;
import me.srcode.oasisstaff.helper.PluginHelper;
import me.srcode.oasisstaff.user.UserHandler;
import me.srcode.oasisstaff.user.types.StaffUserModel;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ItemLoader implements Loader {
   private final FileConfiguration configuration;
   private final StaffManager staffManager;
   private final ItemManager manager;

   public ItemLoader(@NotNull FileConfiguration configuration, @NotNull StaffManager staffManager) {
      this.configuration = configuration;
      this.staffManager = staffManager;
      this.manager = staffManager.getItemManager();
      this.load();
   }

   public void load() {
      ConfigurationSection section = this.configuration.getConfigurationSection("items");
      this.manager.setVanishItem(ItemBuilder.builder().name(section.getString("vanish.display-name")).lore(section.getStringList("vanish.lore")).material(section.getString("vanish.material")).toInteractItem(section.getInt("vanish.slot")).action((interactEvent) -> {
         interactEvent.setCancelled(true);
         Player player = interactEvent.getPlayer();
         StaffUserModel staffUser = (StaffUserModel)UserHandler.find(player);
         if (staffUser != null) {
            if (staffUser.isVanish()) {
               staffUser.unvanish();
               player.getInventory().setItemInMainHand(this.manager.getUnVanishItemStack());
               player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.vanish-off")));
            } else {
               staffUser.vanish();
               player.getInventory().setItemInMainHand(this.manager.getVanishItemStack());
               player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.vanish-on")));
            }
         }

      }));
      this.manager.setInventoryItem(ItemBuilder.builder().name(this.configuration.getString("items.inventory-see.display-name")).lore(this.configuration.getStringList("items.inventory-see.lore")).material(this.configuration.getString("items.inventory-see.material")).toInteractEntityItem(this.configuration.getInt("items.inventory-see.slot")).action((interactEntityEvent) -> {
         interactEntityEvent.getPlayer().openInventory((new InventoryPreview(PluginLauncher.getInstance(), (Player)interactEntityEvent.getRightClicked())).getInventory());
      }));
      this.manager.setRandomTeleportItem(ItemBuilder.builder().name(section.getString("random-tp.display-name")).lore(section.getStringList("random-tp.lore")).material(section.getString("random-tp.material")).toInteractItem(section.getInt("random-tp.slot")).action((interactEvent) -> {
         interactEvent.setCancelled(true);
         StaffHelper.randomTeleport(interactEvent.getPlayer(), this.configuration);
      }));
      this.manager.setFreezeItem(ItemBuilder.builder().name(section.getString("freeze.display-name")).lore(section.getStringList("freeze.lore")).material(section.getString("freeze.material")).toInteractEntityItem(section.getInt("freeze.slot")).action((interactEntityEvent) -> {
         interactEntityEvent.setCancelled(true);
         this.staffManager.freeze(interactEntityEvent.getPlayer(), (Player)interactEntityEvent.getRightClicked());
      }));
   }
}
