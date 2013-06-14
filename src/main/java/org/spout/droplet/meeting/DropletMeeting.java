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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.spout.api.Spout;
import org.spout.api.command.annotated.AnnotatedCommandExecutorFactory;
import org.spout.api.plugin.Plugin;

import org.spout.droplet.meeting.util.DropletConfiguration;
import org.spout.droplet.meeting.util.MeetingLog;

public class DropletMeeting extends Plugin {
	private boolean meetingInProgress;
	private final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	private final DateFormat timeFormat = new SimpleDateFormat("HH.mm.ss");
	private static DropletMeeting instance = null;
	private DropletConfiguration config;
	private MeetingLog meetingLog;

	public DropletMeeting() {
		instance = this;
	}

	@Override
	public void onEnable() {
		// Load config
		config = new DropletConfiguration();
		config.load();
		// Register listener
		Spout.getEngine().getEventManager().registerEvents(new DropletListener(), this);
		// Register commands
		AnnotatedCommandExecutorFactory.create(new DropletCommands());
		getLogger().info("DropletMeeting v" + getDescription().getVersion() + " enabled.");
	}

	@Override
	public void onDisable() {
		if (meetingLog != null) {
			meetingLog.save();
		}
		getLogger().info("DropletMeeting v" + getDescription().getVersion() + " disabled.");
	}

	public static DropletMeeting getInstance() {
		return instance;
	}

	public void start() {
		if (meetingInProgress) {
			throw new IllegalStateException("A meeting is already in progress!");
		}
		meetingInProgress = true;
		meetingLog = new MeetingLog();
	}

	public void end() {
		if (!meetingInProgress) {
			throw new IllegalStateException("A meeting is not in progress!");
		}
		meetingInProgress = false;
		meetingLog.save();
		meetingLog = null;
	}

	public MeetingLog getMeetingLog() {
		return meetingLog;
	}

	public String getFormattedDate() {
		return dateFormat.format(Calendar.getInstance(TimeZone.getTimeZone(DropletConfiguration.TIME_ZONE.getString())).getTime());
	}

	public String getFormattedTime() {
		return timeFormat.format(Calendar.getInstance(TimeZone.getTimeZone(DropletConfiguration.TIME_ZONE.getString())).getTime());
	}

	public boolean isMeetingInProgress() {
		return meetingInProgress;
	}

	public DropletConfiguration getConfiguration() {
		return config;
	}
}
