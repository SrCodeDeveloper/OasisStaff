package me.srcode.oasisstaff.user.types;

import java.util.UUID;
import me.srcode.oasisstaff.user.UserModel;
import me.srcode.oasisstaff.user.data.FreezeUserContainer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class FreezeUserModel extends UserModel {
   private final UUID staffTarget;
   private final FreezeUserContainer freezeUserContainer;
   private final boolean flight;
   private boolean freeze;

   public FreezeUserModel(@NotNull UUID uuid, @NotNull UUID staffTarget, @NotNull FreezeUserContainer freezeUserContainer, boolean flight) {
      super(uuid);
      this.staffTarget = staffTarget;
      this.freeze = false;
      this.freezeUserContainer = freezeUserContainer;
      this.flight = flight;
   }

   @NotNull
   public static FreezeUserModel of(@NotNull Player player, @NotNull UUID staffTarget) {
      return new FreezeUserModel(player.getUniqueId(), staffTarget, FreezeUserContainer.of(player), player.getAllowFlight());
   }

   public Player getBukkitStaffPlayer() {
      return Bukkit.getPlayer(this.staffTarget);
   }

   public void freeze() {
      this.freeze = true;
      Player player = this.getBukkitPlayer();
      player.getEquipment().setHelmet(new ItemStack(Material.ICE));
      player.setAllowFlight(false);
      player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 250));
      player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 250));
   }

   public void unfreeze() {
      this.freeze = false;
      Player player = this.getBukkitPlayer();
      player.setAllowFlight(this.flight);
      player.removePotionEffect(PotionEffectType.BLINDNESS);
      player.removePotionEffect(PotionEffectType.SLOW);
      this.freezeUserContainer.restore(player);
   }

   public UUID getStaffTarget() {
      return this.staffTarget;
   }

   public FreezeUserContainer getFreezeUserContainer() {
      return this.freezeUserContainer;
   }

   public boolean isFlight() {
      return this.flight;
   }

   public boolean isFreeze() {
      return this.freeze;
   }

   public void setFreeze(boolean freeze) {
      this.freeze = freeze;
   }
}
