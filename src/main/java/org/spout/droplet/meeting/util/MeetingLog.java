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
package org.spout.droplet.meeting.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.spout.droplet.meeting.DropletMeeting;

public class MeetingLog {
	private final DropletMeeting plugin = DropletMeeting.getInstance();
	private final List<String> log = new ArrayList<String>();

	public void save() {
		try {
			File file = new File(plugin.getDataFolder(), "logs/" + plugin.getFormattedDate() + "_" + plugin.getFormattedTime() + ".txt");
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			BufferedWriter out = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			for (String message : log) {
				out.write("[" + plugin.getFormattedTime() + "] " + message);
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void log(String message) {
		log.add(message);
	}
}
