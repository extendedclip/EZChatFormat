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

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class EZChatFormatListener implements Listener {

  private EZChatFormat plugin;

  public EZChatFormatListener(EZChatFormat instance) {
    plugin = instance;
  }

  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onChat(AsyncPlayerChatEvent e) {
    ChatFormat f = plugin.getFormat(e.getPlayer());
    if (f == null || f.getFormat() == null) return;
    e.setFormat(PlaceholderAPI.setPlaceholders(e.getPlayer(), f.getFormat()));
  }
}
