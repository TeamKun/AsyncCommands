package team.kun.asynccommands.runnables;

import one.util.streamex.StreamEx;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class PlayerRunnable extends BukkitRunnable {
    private final JavaPlugin plugin;
    private int count = 0;
    private final List<List<Player>> list;

    public PlayerRunnable(
            @NotNull JavaPlugin plugin,
            @NotNull List<Player> players,
            int length
    ) {
        this.plugin = plugin;
        this.list = StreamEx.ofSubLists(players, length).toList();
    }

    @Override
    public void run() {
        if (count >= list.size()) {
            plugin.getServer().getScheduler().cancelTask(getTaskId());
        } else {
            list.get(count).forEach(this::run);
            count += 1;
        }
    }

    public abstract void run(Player player);
}
