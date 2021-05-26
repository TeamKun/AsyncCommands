package team.kun.asynccommands.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import team.kun.asynccommands.runnables.PlayerRunnable;
import team.kun.asynccommands.utils.PlayerUtil;

import java.util.List;
import java.util.stream.Collectors;

public class TeleportCommand implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;

    public TeleportCommand(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String[] args
    ) {
        if (args.length == 1 && sender instanceof Player) {
            execute(PlayerUtil.select(plugin, args[0]), ((Player) sender).getLocation());
            return true;
        } else if (args.length == 2) {
            Player targetPlayer = plugin.getServer().getPlayer(args[1]);
            if (targetPlayer != null) {
                execute(PlayerUtil.select(plugin, args[0]), targetPlayer.getLocation());
                return true;
            } else {
                sender.sendMessage("対象のプレイヤーが存在しません");
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return plugin.getServer().getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
        } else if (args.length == 2) {
            return plugin.getServer().getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    private void execute(@NotNull List<Player> players, @NotNull Location location) {
        new PlayerRunnable(plugin, players, 20) {
            @Override
            public void run(Player player) {
                player.teleport(location);
            }
        }.runTaskTimer(plugin, 1, 20);
    }
}
