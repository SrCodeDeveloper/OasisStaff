package me.srcode.oasisstaff.user.data;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class StaffUserDataContainer extends UserDataContainer {
   private final double health;

   public StaffUserDataContainer(@NotNull ItemStack[] armorContents, @NotNull ItemStack[] inventoryContents, @NotNull GameMode gameMode, boolean flight, double health, int foodLevel) {
      super(armorContents, inventoryContents, gameMode, flight, foodLevel);
      this.health = health;
   }

   @Contract("_ -> new")
   @NotNull
   public static StaffUserDataContainer of(@NotNull Player player) {
      return new StaffUserDataContainer(player.getEquipment().getArmorContents(), player.getInventory().getContents(), player.getGameMode(), player.getAllowFlight(), player.getHealth(), player.getFoodLevel());
   }

   public void restore(@NotNull Player player) {
      player.setHealth(this.health);
      player.getEquipment().setArmorContents(this.armorContents);
      player.getInventory().setContents(this.inventoryContents);
      player.setGameMode(this.gameMode);
      player.setAllowFlight(this.flight);
      player.setFoodLevel(this.foodLevel);
   }
}
