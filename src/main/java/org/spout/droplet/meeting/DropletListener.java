/*
 * This file is part of DropletMeeting.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
 * DropletMeeting is licensed under the SpoutDev License Version 1.
 *
 * DropletMeeting is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * DropletMeeting is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spout.droplet.meeting;

import org.spout.api.chat.ChatArguments;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.entity.Player;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.player.PlayerChatEvent;

import org.spout.droplet.meeting.util.DropletConfiguration;
import org.spout.droplet.meeting.util.MeetingLog;

public class DropletListener implements Listener {
	@EventHandler(order = Order.LATEST)
	public void playerChat(PlayerChatEvent event) {
		Player player = event.getPlayer();
		DropletMeeting plugin = DropletMeeting.getInstance();
		DropletConfiguration config = plugin.getConfiguration();
		if (plugin.isMeetingInProgress()) {
			if (!player.hasPermission("dropletmeeting.voiced")) {
				player.sendMessage(ChatArguments.fromString(DropletConfiguration.MUTE_MESSAGE.getString()));
				event.setCancelled(true);
				return;
			}
			MeetingLog meetingLog = plugin.getMeetingLog();
			if (meetingLog != null) {
				ChatArguments template = event.getFormat().getArguments();
				template.setPlaceHolder(PlayerChatEvent.NAME, new ChatArguments(player.getDisplayName()));
				template.setPlaceHolder(PlayerChatEvent.MESSAGE, event.getMessage());
				meetingLog.log(template.getPlainString());
			}
		}
	}
}
