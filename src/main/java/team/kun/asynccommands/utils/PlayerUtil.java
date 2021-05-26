package team.kun.asynccommands.utils;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtil {
    public static List<Player> select(@NotNull JavaPlugin plugin, @NotNull String selector) {
        if (selector.equals("@a")) {
            return new ArrayList<>(plugin.getServer().getOnlinePlayers());
        } else {
            ArrayList<Player> array = new ArrayList<>();
            Player player = plugin.getServer().getPlayer(selector);
            if (player != null) {
                array.add(player);
            }
            return array;
        }
    }
}
