package me.srcode.oasisstaff.message;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MessageHandler<E extends Enum<E>> {
    void send(@NotNull Player paramPlayer, E paramE);

    void send(@NotNull Player paramPlayer, @Nullable String paramString);

    void sendMany(@NotNull Player paramPlayer, E paramE);
}
