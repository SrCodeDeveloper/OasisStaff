package me.srcode.oasisstaff.message;

import java.util.List;

import me.srcode.oasisstaff.helper.ComponentHelper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MessageManager implements MessageHandler<MessageKey> {
    private final FileConfiguration configuration;

    public MessageManager(@NotNull FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public void send(@NotNull Player player, @NotNull MessageKey messageKey) {
        String message = messageKey.value();
        String result = this.configuration.getString(message);
        if (result == null || result.isEmpty())
            return;
        player.sendMessage(ComponentHelper.asComponent(result));
    }

    public void send(@NotNull Player player, @Nullable String value) {
        if (value == null || value.isEmpty())
            return;
        player.sendMessage(ComponentHelper.asComponent(value));
    }

    public void sendMany(@NotNull Player player, @NotNull MessageKey messageKey) {
        List<String> list = this.configuration.getStringList(messageKey.value());
        if (list.isEmpty())
            return;
        list.forEach(component -> player.sendMessage(ComponentHelper.asComponent(component)));

    }
}