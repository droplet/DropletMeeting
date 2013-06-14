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

import java.io.File;
import org.spout.api.exception.ConfigurationException;
import org.spout.api.util.config.ConfigurationHolder;
import org.spout.api.util.config.ConfigurationHolderConfiguration;
import org.spout.api.util.config.yaml.YamlConfiguration;

import org.spout.droplet.meeting.DropletMeeting;

public class DropletConfiguration extends ConfigurationHolderConfiguration {
	public static final ConfigurationHolder TIME_ZONE = new ConfigurationHolder("America/New_York", "time-zone");
	public static final ConfigurationHolder MUTE_MESSAGE = new ConfigurationHolder('\u00A7' + 'c' + "You don't have permission to talk during a meeting.", "mute-message");
	public static final ConfigurationHolder START_MESSAGE = new ConfigurationHolder('\u00A7' + 'a' + "Meeting in progress as of %date% %time% %time_zone%", "start-message");
	public static final ConfigurationHolder END_MESSAGE = new ConfigurationHolder('\u00A7' + 'a' + "Meeting adjourned as of %date% %time% %time_zone%", "end-message");

	public DropletConfiguration() {
		super(new YamlConfiguration(new File(DropletMeeting.getInstance().getDataFolder(), "config.yml")));
	}

	@Override
	public void load() {
		try {
			super.load();
			super.save();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save() {
		try {
			super.save();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
}
