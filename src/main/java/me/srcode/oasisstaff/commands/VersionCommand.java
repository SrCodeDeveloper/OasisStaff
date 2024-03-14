package me.srcode.oasisstaff.commands;

import me.srcode.oasisstaff.helper.PluginHelper;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

public class VersionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("oasisstaff") && args.length > 0) {
            if (args[0].equalsIgnoreCase("version")) {
                Player player = (Player) sender;
                if (player.hasPermission("oasisstaff.command.version")){
                    player.sendMessage(PluginHelper.toFormat("messages.no-permission"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                }else{
                    player.sendMessage(PluginHelper.toFormat("&bOasisStaff &7| &eSrCode"));
                    player.sendMessage(PluginHelper.toFormat("&fVersion: &a1.1-SNAPSHOT"));
                    player.sendMessage(PluginHelper.toFormat("&cCualquier bug reportar al desarrollador"));
                    player.sendMessage(PluginHelper.toFormat("&cVersion De Minecraft: &a" + Bukkit.getMinecraftVersion()));
                    player.sendMessage(PluginHelper.toFormat("&cVersion Del Fork: &a" + Bukkit.getBukkitVersion().toUpperCase()));
                    player.sendMessage(PluginHelper.toFormat("&cJugadores Conectados: &a" + Bukkit.getOnlinePlayers()));
                    MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
                    String memoryUsage = String.format("%.2f MB", memoryMXBean.getHeapMemoryUsage().getUsed() / 1048576.0);
                    player.sendMessage(PluginHelper.toFormat("&cConsumo de RAM: &a" + memoryUsage));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    return true;
                }
            }
        }
        return false;
    }
}
