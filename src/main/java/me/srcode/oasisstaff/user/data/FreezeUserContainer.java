package me.srcode.oasisstaff.user.data;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class FreezeUserContainer extends UserDataContainer {
   public FreezeUserContainer(@NotNull ItemStack[] armorContents, @NotNull ItemStack[] inventoryContents, @NotNull GameMode gameMode, boolean flight, int foodLevel) {
      super(armorContents, inventoryContents, gameMode, flight, foodLevel);
   }

   @Contract("_ -> new")
   @NotNull
   public static FreezeUserContainer of(@NotNull Player player) {
      return new FreezeUserContainer(player.getEquipment().getArmorContents(), player.getInventory().getContents(), player.getGameMode(), player.getAllowFlight(), player.getFoodLevel());
   }

   public void restore(@NotNull Player player) {
      player.getEquipment().setArmorContents(this.armorContents);
      player.getInventory().setContents(this.inventoryContents);
      player.setGameMode(this.gameMode);
      player.setAllowFlight(this.flight);
      player.setFoodLevel(this.foodLevel);
   }
}
