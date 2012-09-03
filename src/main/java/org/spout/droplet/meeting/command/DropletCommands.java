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
package org.spout.droplet.meeting.command;

import java.util.TimeZone;

import org.spout.api.Server;
import org.spout.api.Spout;
import org.spout.api.chat.ChatArguments;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.CommandPermissions;
import org.spout.api.entity.Player;
import org.spout.api.exception.CommandException;
import org.spout.api.plugin.Platform;

import org.spout.droplet.meeting.util.DropletConfiguration;
import org.spout.droplet.meeting.DropletMeeting;

public class DropletCommands {
	private final DropletMeeting plugin = DropletMeeting.getInstance();
	private final DropletConfiguration config = plugin.getConfiguration();

	@Command(aliases = "start", desc = "Start a meeting", min = 0, max = 0)
	@CommandPermissions("dropletmeeting.command.start")
	public void start(CommandContext args, CommandSource source) throws CommandException {
		Platform p = Spout.getPlatform();
		if (p != Platform.SERVER && p != Platform.PROXY) {
			throw new CommandException("Meetings are only available in server mode.");
		}
		if (plugin.isMeetingInProgress()) {
			throw new CommandException("A meeting is already in progress!");
		}
		plugin.start();
		((Server) Spout.getEngine()).broadcastMessage(ChatArguments.fromString(formatDate(DropletConfiguration.START_MESSAGE.getString())));
	}

	@Command(aliases = "end", desc = "End a meeting", min = 0, max = 0)
	@CommandPermissions("dropletmeeting.command.end")
	public void end(CommandContext args, CommandSource source) throws CommandException {
		Platform p = Spout.getPlatform();
		if (p != Platform.SERVER && p != Platform.PROXY) {
			throw new CommandException("Meetings are only available in server mode.");
		}
		if (!plugin.isMeetingInProgress()) {
			throw new CommandException("There is no meeting in progress!");
		}
		plugin.end();
		((Server) Spout.getEngine()).broadcastMessage(ChatArguments.fromString(formatDate(DropletConfiguration.END_MESSAGE.getString())));
	}

	private String formatDate(String s) {
		return s.replaceAll("%date%", plugin.getFormattedDate()).replaceAll("%time%", plugin.getFormattedTime()).replaceAll("%time_zone%", TimeZone.getTimeZone(DropletConfiguration.TIME_ZONE.getString()).getDisplayName());
	}
}
