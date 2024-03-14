package me.srcode.oasisstaff.helper;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ComponentHelper {

    public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    @NotNull
    public static Component asComponent(@Nullable String message){
        return (message == null || message.isEmpty()) ? (Component)Component.empty() : MINI_MESSAGE.deserialize(message);
    }
}
