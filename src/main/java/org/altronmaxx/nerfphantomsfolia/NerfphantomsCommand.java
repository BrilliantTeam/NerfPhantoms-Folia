package org.altronmaxx.nerfphantomsfolia;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NerfphantomsCommand implements CommandExecutor {

    PluginCommand pluginCommand;
    protected NerfphantomsCommand() {
        pluginCommand = Nerfphantoms_folia.getInstance().getCommand("cnp");
        if (pluginCommand != null){
            pluginCommand.setTabCompleter(new TabCompletion());
            pluginCommand.setExecutor(this);
        }

    }
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 0) {
            return;
        }

        Component permissionMessage = pluginCommand.permissionMessage();
        assert (permissionMessage != null);

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("bcnp.reload")) {
                sender.sendMessage(permissionMessage);
                return;
            }
            Nerfphantoms_folia.getInstance().reloadConfig();
            Nerfphantoms_folia.getInstance().config = Nerfphantoms_folia.getInstance().getConfig();

            Nerfphantoms_folia.getInstance().logger.info("§7｜§6系統§7｜§f飯娘：§7已重新載入設置。");
            if (sender instanceof Player) {
                sender.sendMessage("§7｜§6系統§7｜§f飯娘：§7已重新載入設置。");
            }
            return;
        }

        if (args[0].equalsIgnoreCase("kill")) {
            if (!sender.hasPermission("bcnp.kill")) {
                sender.sendMessage(permissionMessage);
                return;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage("§7｜§6系統§7｜§f飯娘：§7該指令只能由玩家使用。");
                return;
            }
            Player player = (Player) sender;
            int n = PhantomUtils.killAllPhantoms(player.getWorld());
            player.sendMessage("§7｜§6系統§7｜§f飯娘：§7已擊殺§6 " + n + " §7隻夜魅。");
            return;
        }

        if (args[0].equalsIgnoreCase("toggle")) {
            if (args.length == 1) {
                if (!sender.hasPermission("bcnp.disablespawn.self")) {
                    sender.sendMessage(permissionMessage);
                    return;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§7｜§6系統§7｜§f飯娘：§7該指令只能由玩家使用。");
                    return;
                }
                Player player = (Player) sender;
                boolean state = PhantomUtils.togglePhantomSpawn(player);
                player.sendMessage("§7｜§6系統§7｜§f飯娘：§7" + (state ? "§c已停用" : "§a已啟用") + "§6" + player.getName() + "§7的夜魅生成。");
                return;
            }
            if (!sender.hasPermission("bcnp.disablespawn.others")) {
                sender.sendMessage(permissionMessage);
                return;
            }
            Player victim = Bukkit.getPlayer(args[1]);
            if (victim == null) {
                sender.sendMessage("§7｜§6系統§7｜§f飯娘：§7找不到該玩家");
                return;
            }
            boolean state = PhantomUtils.togglePhantomSpawn(victim);
            sender.sendMessage("§7｜§6系統§7｜§f飯娘：§7" + (state ? "§c已停用" : "§a已啟用") + "§6" + victim.getName() + "§7的夜魅生成。");
        }

    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        execute(commandSender, strings);
        return true;
    }
}
