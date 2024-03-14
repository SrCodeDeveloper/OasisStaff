package me.srcode.oasisstaff.tab;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Tab {
   void create(@NotNull Player var1, @NotNull String var2);

   void reset(@NotNull Player var1);
}
