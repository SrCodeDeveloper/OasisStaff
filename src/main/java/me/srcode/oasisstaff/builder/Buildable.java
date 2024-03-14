package me.srcode.oasisstaff.builder;

public interface Buildable<T> {
   T build();

   default ItemAction<?> toInteractItem(int slot) {
      return null;
   }

   default ItemAction<?> toInteractEntityItem(int slot) {
      return null;
   }
}
