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

import org.bukkit.configuration.file.FileConfiguration;

public final class EZChatFormatConfig {

  private EZChatFormat plugin;
  private FileConfiguration c;

  public EZChatFormatConfig(EZChatFormat instance) {
    plugin = instance;
    c = plugin.getConfig();
  }

  public void loadDefCfg() {
    c.options().header("EZChatFormat v" + plugin.getDescription().getVersion() + " config file");
    if (!c.contains("formats")) {
      c.addDefault("formats.default.priority", Integer.MAX_VALUE);
      c.addDefault("formats.default.format", "%player_name% &7> ");
      c.options().copyDefaults(true);
    }
    plugin.saveConfig();
    plugin.reloadConfig();
  }

  public int loadFormats() {
    if (!c.contains("formats")) {
      return 0;
    }
    plugin.getFormats().clear();
    for (String key : c.getConfigurationSection("formats").getKeys(false)) {
      int priority = c.getInt("formats." + key + ".priority");
      plugin.getFormats().put(priority, new ChatFormat(key, priority, c.getString("formats." + key + ".format")));
    }
    return plugin.getFormats().size();
  }
}
