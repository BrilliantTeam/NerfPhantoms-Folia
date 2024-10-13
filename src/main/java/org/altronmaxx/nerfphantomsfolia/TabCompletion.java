package org.altronmaxx.nerfphantomsfolia;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("cnp") && args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("help");

            if (sender.hasPermission("bcnp.disablespawn.self")) {
                list.add("toggle");
            }

            if (sender.hasPermission("bcnp.reload")) {
                list.add("reload");
            }

            if (sender.hasPermission("bcnp.kill")) {
                list.add("kill");
            }

            return list;
        }
        List<String> list = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            list.add(p.getName());
        }
        return list;
    }
}