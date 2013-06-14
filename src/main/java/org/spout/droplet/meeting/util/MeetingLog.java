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
