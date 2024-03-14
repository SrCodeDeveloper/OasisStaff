package me.srcode.oasisstaff.builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.srcode.oasisstaff.helper.PluginHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemBuilder implements Buildable<ItemStack> {
   private String name;
   private Material material;
   private List<String> lore;

   public ItemBuilder() {
      this.name = "";
      this.material = Material.STONE;
      this.lore = null;
   }

   public ItemBuilder(@Nullable String name, @Nullable List<String> lore, @NotNull Material material) {
      this.name = name;
      this.lore = lore;
      this.material = material;
   }

   @Contract(" -> new")
   @NotNull
   public static ItemBuilder builder() {
      return new ItemBuilder();
   }

   @Contract("_ -> this")
   public ItemBuilder name(@Nullable String name) {
      if (name != null && !name.isEmpty()) {
         this.name = name;
         return this;
      } else {
         return this;
      }
   }

   @Contract("_ -> this")
   public ItemBuilder material(@Nullable Material material) {
      if (material == null) {
         return this;
      } else {
         this.material = material;
         return this;
      }
   }

   public ItemBuilder material(@Nullable String material) {
      if (material != null && !material.isEmpty()) {
         Material result = Material.getMaterial(material);
         if (result == null) {
            return this;
         } else {
            this.material = Material.getMaterial(material);
            return this;
         }
      } else {
         return this;
      }
   }

   @Contract("_ -> this")
   public ItemBuilder lore(@Nullable List<String> lore) {
      if (lore == null) {
         return this;
      } else {
         this.lore = lore;
         return this;
      }
   }

   public ItemBuilder loreWithPlaceholder(@NotNull Player target, @Nullable List<String> lore) {
      if (lore == null) {
         return this;
      } else {
         lore.replaceAll((s) -> {
            return PluginHelper.processString(target, s);
         });
         this.lore = lore;
         return this;
      }
   }

   public ItemStack build() {
      ItemStack itemStack = new ItemStack(this.material);
      ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(itemStack.getType());
      if (this.name != null) {
         itemMeta.displayName(Component.text(PluginHelper.toFormat(this.name)));
      }

      if (this.lore != null) {
         List<Component> componentList = new ArrayList();
         Iterator var4 = this.lore.iterator();

         while(var4.hasNext()) {
            String string = (String)var4.next();
            componentList.add(Component.text(PluginHelper.toFormat(string)));
         }

         itemMeta.lore(componentList);
      }

      itemStack.setItemMeta(itemMeta);
      return itemStack;
   }

   public ItemAction<PlayerInteractEvent> toInteractItem(int inventorySlot) {
      return new ItemAction(this.name, this.lore, this.material, inventorySlot);
   }

   public ItemAction<PlayerInteractEntityEvent> toInteractEntityItem(int inventorySlot) {
      return new ItemAction(this.name, this.lore, this.material, inventorySlot);
   }
}
