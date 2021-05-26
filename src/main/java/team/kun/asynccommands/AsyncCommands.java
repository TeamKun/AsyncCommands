package team.kun.asynccommands;

import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.permission.Permissions;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.bukkit.plugin.java.annotation.plugin.author.Authors;
import team.kun.asynccommands.commands.GiveCommand;
import team.kun.asynccommands.commands.TeleportCommand;

@Plugin(name = "AsyncCommands", version = "1.0-SNAPSHOT")
@Description(value = "Async Commands")
@Authors(
        @Author("ReyADayer")
)
@ApiVersion(ApiVersion.Target.DEFAULT)
@Commands({
        @Command(
                name = PluginCommands.GIVE,
                desc = "give command",
                usage = "/<command>",
                permission = PluginPermissions.ADMIN,
                permissionMessage = "You don't have <permission>"
        ),
        @Command(
                name = PluginCommands.TELEPORT,
                desc = "teleport command",
                usage = "/<command>",
                permission = PluginPermissions.ADMIN,
                permissionMessage = "You don't have <permission>"
        )
})
@Permissions({
        @Permission(
                name = PluginPermissions.ADMIN,
                desc = "Gives access to AsyncCommands admin commands",
                defaultValue = PermissionDefault.OP
        )
})
public final class AsyncCommands extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand(PluginCommands.GIVE).setExecutor(new GiveCommand(this));
        getCommand(PluginCommands.TELEPORT).setExecutor(new TeleportCommand(this));
    }

    @Override
    public void onDisable() {
    }
}
