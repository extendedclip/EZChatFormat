/*
 *
 * EZChatFormat
 * Copyright (C) 2019 Ryan McCarthy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */
package com.extendedclip.ezchatformat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public final class EZChatFormatCommands implements CommandExecutor {

	private EZChatFormat plugin;

	public EZChatFormatCommands(EZChatFormat i) {
		plugin = i;
	}

	private void sms(CommandSender s, String msg) {
		s.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String label, String[] args) {

    if (s instanceof Player) {
      Player p = (Player) s;
      if (!p.hasPermission("ezchatformat.admin")) {
        sms(s, "&cYou don't have permission to do that!");
        return true;
      }
    }

    if (args.length == 0 || args.length > 0 && args[0].equalsIgnoreCase("help")) {
      sms(s, "&bEZChatFormat &f" + plugin.getDescription().getVersion() + " &bHelp");
      sms(s, "&bcreated by: &cclip");
      sms(s, "/ezchatformat reload &8- &7Reload the plugin");
      sms(s, "/ezchatformat list &8- &7List all loaded formats");
      return true;
    } else if (args[0].equalsIgnoreCase("reload")) {
      plugin.getFormats().clear();
      plugin.reloadConfig();
      plugin.saveConfig();
      plugin.getCfg().loadFormats();
      sms(s, "&bPickARank &fconfiguration successfully reloaded!");
      int size = plugin.getFormats().size();
      sms(s, size + " &7format" + (size == 0 || size > 1 ? "s" : "") + " loaded!");
    } else if (args[0].equalsIgnoreCase("list")) {
      if (plugin.getFormats().size() == 0) {
        sms(s, "&7No formats are currently loaded!");
        return true;
      }
      sms(s, plugin.getFormats().size() + " &7formats loaded&f:");
      List<String> names = plugin.getFormats().values().stream().map(ChatFormat::getName).collect(Collectors.toList());
      sms(s, names.toString());
    } else {
      sms(s, "&cInvalid command! &fUse &7/ezchatformat help");
    }
    return true;
  }
}

