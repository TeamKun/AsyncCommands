package team.kun.asynccommands.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import team.kun.asynccommands.ItemMap;
import team.kun.asynccommands.runnables.PlayerRunnable;
import team.kun.asynccommands.utils.PlayerUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GiveCommand implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;

    public GiveCommand(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String[] args
    ) {
        if (args.length < 2) {
            return false;
        }

        String targetItem = args[1];
        Material material = ItemMap.findMaterialById(targetItem);

        if (material == null) {
            sender.sendMessage("対応するアイテムが存在しません");
            return false;
        }

        String numText;
        try {
            numText = args[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            numText = "1";
        }

        int num;
        try {
            num = Integer.parseInt(numText);
        } catch (NumberFormatException e) {
            num = 1;
        }

        ItemStack itemStack = new ItemStack(material, num);

        List<Player> players = PlayerUtil.select(plugin, args[0]);
        execute(players, itemStack);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return plugin.getServer().getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
        } else if (args.length == 2) {
            return Arrays.stream(ItemMap.values()).map(itemMap -> itemMap.id).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    private void execute(@NotNull List<Player> players, @NotNull ItemStack itemStack) {
        new PlayerRunnable(plugin, players, 20) {
            @Override
            public void run(Player player) {
                player.getInventory().addItem(itemStack);
            }
        }.runTaskTimer(plugin, 1, 20);
    }
}