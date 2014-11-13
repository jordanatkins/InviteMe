package inviteme;

import java.io.PrintStream;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class InviteMe extends JavaPlugin
{
  public void onDisable()
  {
    System.out.println("[InviteMe] Disabled!");
  }

  public void onEnable()
  {
    loadConfig();

    PluginDescriptionFile descFile = getDescription();
    System.out.println("[InviteMe] Enabled!");
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    Player p = (Player)sender;
    String path = "Config.player.";
    String pp = path + p.getName();
    String ppa = pp + ".amount";
    int a = getConfig().getInt(ppa);
    int ad = 2 - (a + 1);
    int ai = 2 - a;
    if (cmd.getName().equalsIgnoreCase("invite")) {
                if (args.length < 1) {
          p.sendMessage(ChatColor.RED + "Please enter a username.");
          return true;
        }
      if (!args[0].equalsIgnoreCase("info")) {
        if (args.length > 1) {
          p.sendMessage(ChatColor.RED + "You can only add one player at a time.");
          return true;
        }
        if (args.length == 1)
        {
          if (!getConfig().isSet(pp))
          {
            if (!getServer().getOfflinePlayer(args[0]).isWhitelisted()) {
              getConfig().set(ppa, Integer.valueOf(1));
              getServer().getOfflinePlayer(args[0]).setWhitelisted(true);

              saveConfig();
              p.sendMessage(ChatColor.BLUE + args[0] + ChatColor.GRAY + " has been added");
              p.sendMessage(ChatColor.GRAY + "You can add " + ChatColor.BLUE + ad + ChatColor.GRAY + " more players");

              return true;
            }

            p.sendMessage(ChatColor.BLUE + args[0] + ChatColor.GRAY + " is already whitelisted ");

            return true;
          }

          if (a < 2)
          {
            if (!getServer().getOfflinePlayer(args[0]).isWhitelisted()) {
              getConfig().set(ppa, Integer.valueOf(a + 1));
              getServer().getOfflinePlayer(args[0]).setWhitelisted(true);
              p.sendMessage(ChatColor.BLUE + args[0] + ChatColor.GRAY + " has been added");
              p.sendMessage(ChatColor.GRAY + "You can add " + ChatColor.BLUE + ad + ChatColor.GRAY + " more players");
              saveConfig();

              return true;
            }

            p.sendMessage(ChatColor.BLUE + args[0] + ChatColor.GRAY + " is already whitelisted ");
            return true;
          }

          if (a >= 2)
          {
            p.sendMessage(ChatColor.RED + "You cannot invite more than 2 players");

            return true;
          }

          return true;
        }
      }

      if (args[0].equalsIgnoreCase("info"))
      {
        p.sendMessage(ChatColor.GRAY + "You can add " + ChatColor.BLUE + ai + ChatColor.GRAY + " more players");
      }
      else
      {
        p.sendMessage(ChatColor.RED + "This Command doesn't exist");
        return true;
      }
    }
    return true;
  }

  private void loadConfig()
  {
    getConfig().options().copyDefaults(true);
    saveConfig();
  }
}
