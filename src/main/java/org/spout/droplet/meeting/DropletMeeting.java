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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.spout.api.Spout;
import org.spout.api.command.CommandRegistrationsFactory;
import org.spout.api.command.annotated.AnnotatedCommandRegistrationFactory;
import org.spout.api.command.annotated.SimpleAnnotatedCommandExecutorFactory;
import org.spout.api.command.annotated.SimpleInjector;
import org.spout.api.plugin.CommonPlugin;

import org.spout.droplet.meeting.util.DropletConfiguration;
import org.spout.droplet.meeting.util.MeetingLog;

public class DropletMeeting extends CommonPlugin {
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
		CommandRegistrationsFactory<Class<?>> commandRegFactory = new AnnotatedCommandRegistrationFactory(new SimpleInjector(), new SimpleAnnotatedCommandExecutorFactory());
		getEngine().getRootCommand().addSubCommands(this, DropletCommands.class, commandRegFactory);
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
