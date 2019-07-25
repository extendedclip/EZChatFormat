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
 *//*
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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.TreeMap;

public final class EZChatFormat extends JavaPlugin {

  private EZChatFormatConfig cfg;
  private final TreeMap<Integer, ChatFormat> formats = new TreeMap<>();

  @Override
  public void onEnable() {

    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
      throw new RuntimeException("Failed to detect PlaceholderAPI! Disabling EZChatFormat!");
    }

    cfg = new EZChatFormatConfig(this);
    cfg.loadDefCfg();
    getLogger().info(cfg.loadFormats() + " chat formats loaded!");
    getCommand("ezchatformat").setExecutor(new EZChatFormatCommands(this));
    Bukkit.getPluginManager().registerEvents(new EZChatFormatListener(this), this);
  }

  @Override
  public void onDisable() {
    formats.clear();
  }

  public Map<Integer, ChatFormat> getFormats() {
    return formats;
  }

  public EZChatFormatConfig getCfg() {
    return cfg;
  }

  public ChatFormat getFormat(Player p) {
    return formats.values().stream().filter(f -> p.hasPermission("chatformat." + f.getName())).findFirst().orElse(formats.lastEntry().getValue());
  }
}
