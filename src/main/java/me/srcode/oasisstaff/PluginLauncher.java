package me.srcode.oasisstaff;

import com.xism4.sternalboard.SternalBoardPlugin;
import me.srcode.oasisstaff.commands.*;
import me.srcode.oasisstaff.helper.PluginHelper;
import me.srcode.oasisstaff.loader.ItemLoader;
import me.srcode.oasisstaff.manager.StaffManager;
import me.srcode.oasisstaff.scoreboard.ScoreboardManager;
import me.srcode.oasisstaff.tab.TabManager;
import me.srcode.oasisstaff.listener.BlockBreakListener;
import me.srcode.oasisstaff.listener.BlockPlaceListener;
import me.srcode.oasisstaff.listener.EntityDamageListener;
import me.srcode.oasisstaff.listener.FoodLevelChangeListener;
import me.srcode.oasisstaff.listener.InventoryListener;
import me.srcode.oasisstaff.listener.PlayerChatListener;
import me.srcode.oasisstaff.listener.PlayerCommandListener;
import me.srcode.oasisstaff.listener.PlayerDamageListener;
import me.srcode.oasisstaff.listener.PlayerDropItemListener;
import me.srcode.oasisstaff.listener.PlayerInteractEntityListener;
import me.srcode.oasisstaff.listener.PlayerInteractListener;
import me.srcode.oasisstaff.listener.PlayerItemConsumeListener;
import me.srcode.oasisstaff.listener.PlayerItemPickupListener;
import me.srcode.oasisstaff.listener.PlayerJumpListener;
import me.srcode.oasisstaff.listener.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginLauncher extends JavaPlugin  {
   public static final String PLUGIN_PERMISSION = "oasisstaff.command.use";
   public static SternalBoardPlugin sternalBoardPlugin;
   private static PluginLauncher instance;
   private StaffManager staffManager;
   private ScoreboardManager scoreboardManager;
   private TabManager tabManager;
   private static boolean chatEnabled = true;

   public static String prefix = PluginHelper.toFormat("&8[&bOasisStaff&8] ");

   public void onEnable() {
      instance = this;
      this.getConfig().options().copyDefaults(true);
      this.saveDefaultConfig();
      Plugin scoreboardPlugin = this.getServer().getPluginManager().getPlugin("SternalBoard");
      if (scoreboardPlugin == null) {
         this.getPluginLoader().disablePlugin(this);
         Bukkit.getConsoleSender().sendMessage(PluginHelper.toFormat("&cPor favor descarga SternalBoard by Ismael Hanbel"));
         Bukkit.getConsoleSender().sendMessage(PluginHelper.toFormat("&fDescargalo en: &ehttps://www.spigotmc.org/resources/sternalboard-lightweight-animated-scoreboard.89245/"));
         Bukkit.shutdown();
      } else {
         sternalBoardPlugin = (SternalBoardPlugin)scoreboardPlugin;
         this.tabManager = new TabManager();
         this.scoreboardManager = new ScoreboardManager();
         this.staffManager = new StaffManager(this.getConfig(), this.scoreboardManager, this.tabManager);
         new ItemLoader(this.getConfig(), this.staffManager);
         this.registerListener(new PlayerInteractEntityListener(this.staffManager), new PlayerInteractListener(this.staffManager), new PlayerItemConsumeListener(), new InventoryListener(this.staffManager, this.getConfig()), new PlayerDamageListener(), new FoodLevelChangeListener(), new PlayerChatListener(this.getConfig()), new PlayerQuitListener(this), new PlayerJumpListener(), new PlayerDropItemListener(), new BlockBreakListener(this.getConfig()), new BlockPlaceListener(), new PlayerItemPickupListener(), new EntityDamageListener(), new PlayerCommandListener());
         this.registerCommands();
         Bukkit.getConsoleSender().sendMessage(PluginHelper.toFormat(prefix + "&aEl plugin ha sido encendido correctamente"));
         Bukkit.getConsoleSender().sendMessage(PluginHelper.toFormat(prefix + "&fDeveloper: &a" + this.getDescription().getAuthors()));
         Bukkit.getConsoleSender().sendMessage(PluginHelper.toFormat(prefix + "&fVersion: &6 " + this.getDescription().getVersion()));
         Bukkit.getConsoleSender().sendMessage(PluginHelper.toFormat(prefix + "&eRunning on &6" + Bukkit.getBukkitVersion()));
         Bukkit.getConsoleSender().sendMessage(PluginHelper.toFormat(ChatColor.translateAlternateColorCodes('&', "&cContactame si tienes algun problema con este plugin")));

      }

   }

   public void onDisable() {
      Bukkit.getConsoleSender().sendMessage(PluginHelper.toFormat(prefix + "&cEl plugin ha sido desactivado correctamente"));
      Bukkit.getConsoleSender().sendMessage(PluginHelper.toFormat(prefix + "&fDeveloper: &a" + this.getDescription().getAuthors()));
      Bukkit.getConsoleSender().sendMessage(PluginHelper.toFormat(prefix + "&fVersion: &6 " + this.getDescription().getVersion()));

   }

   private void registerListener(Listener... listeners) {
      Listener[] var2 = listeners;
      int var3 = listeners.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Listener listener = var2[var4];
         Bukkit.getServer().getPluginManager().registerEvents(listener, this);
      }

   }

   public StaffManager getStaffManager() {
      return this.staffManager;
   }

   public ScoreboardManager getScoreboardManager() {
      return this.scoreboardManager;
   }

   public TabManager getTabManager() {
      return this.tabManager;
   }

   public static String getPLUGIN_PERMISSION() {
      return "oasisstaff.command.use";
   }

   public static SternalBoardPlugin getSternalBoardPlugin() {
      return sternalBoardPlugin;
   }

   public static PluginLauncher getInstance() {
      return instance;
   }

   public static boolean isChatEnabled() {
      return chatEnabled;
   }

   public static void setChatEnabled(boolean chatEnabled) {
      PluginLauncher.chatEnabled = chatEnabled;
   }

   public void registerCommands(){

      this.getCommand("vanish").setExecutor(new VanishCommands(this));
      this.getCommand("randomplayer").setExecutor(new RandomPlayerCommands(this.getConfig()));
      this.getCommand("oasisstaff").setExecutor(new OasisStaffCommand(this));
      this.getCommand("xray").setExecutor(new XrayCommands(this));
      this.getCommand("freeze").setExecutor(new FreezeCommands(this));
      this.getCommand("stp").setExecutor(new TeleportCommands(this.getConfig()));
      this.getCommand("staff").setExecutor(new StaffCommands(this));
      this.getCommand("chatprivate").setExecutor(new ChatPrivateCommands(this.getConfig()));
      this.getCommand("chatclear").setExecutor(new ChatClearCommands(this.getConfig()));
      this.getCommand("chattoggle").setExecutor(new ChatToggleCommands(this.getConfig()));
      this.getCommand("invsee").setExecutor(new InvseeCommands(this));
      this.getCommand("oasisstaff").setExecutor(new VersionCommand());

   }



}
