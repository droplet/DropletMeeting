/*
 * This file is part of DropletMeeting.
 *
 * Copyright (c) 2012 Spout LLC <http://www.spout.org/>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.spout.droplet.meeting;

import java.util.TimeZone;

import org.spout.api.Platform;
import org.spout.api.Server;
import org.spout.api.Spout;
import org.spout.api.command.CommandArguments;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.Permissible;
import org.spout.api.exception.CommandException;

import org.spout.droplet.meeting.util.DropletConfiguration;
import org.spout.droplet.meeting.DropletMeeting;

public class DropletCommands {
	private final DropletMeeting plugin = DropletMeeting.getInstance();

	@Command(aliases = "start", desc = "Start a meeting", min = 0, max = 0)
	@Permissible("dropletmeeting.command.start")
	public void start(CommandSource source, CommandArguments args) throws CommandException {
		Platform p = Spout.getPlatform();
		if (p != Platform.SERVER && p != Platform.PROXY) {
			throw new CommandException("Meetings are only available in server mode.");
		}
		if (plugin.isMeetingInProgress()) {
			throw new CommandException("A meeting is already in progress!");
		}
		plugin.start();
		((Server) Spout.getEngine()).broadcastMessage(formatDate(DropletConfiguration.START_MESSAGE.getString()));
	}

	@Command(aliases = "end", desc = "End a meeting", min = 0, max = 0)
	@Permissible("dropletmeeting.command.end")
	public void end(CommandSource source, CommandArguments args) throws CommandException {
		Platform p = Spout.getPlatform();
		if (p != Platform.SERVER && p != Platform.PROXY) {
			throw new CommandException("Meetings are only available in server mode.");
		}
		if (!plugin.isMeetingInProgress()) {
			throw new CommandException("There is no meeting in progress!");
		}
		plugin.end();
		((Server) Spout.getEngine()).broadcastMessage(formatDate(DropletConfiguration.END_MESSAGE.getString()));
	}

	private String formatDate(String s) {
		return s.replaceAll("%date%", plugin.getFormattedDate()).replaceAll("%time%", plugin.getFormattedTime()).replaceAll("%time_zone%", TimeZone.getTimeZone(DropletConfiguration.TIME_ZONE.getString()).getDisplayName());
	}
}
