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

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.spout.api.exception.ConfigurationException;
import org.spout.api.util.config.ConfigurationHolder;
import org.spout.api.util.config.ConfigurationHolderConfiguration;
import org.spout.api.util.config.yaml.YamlConfiguration;

import org.spout.droplet.meeting.DropletMeeting;

public class DropletConfiguration extends ConfigurationHolderConfiguration {
	public DropletConfiguration() {
		super(new YamlConfiguration(new File(DropletMeeting.getInstance().getDataFolder(), "config.yml")));
	}

	@Override
	public void load() {
		try {
			super.load();
			setWritesDefaults(true);
			if (!getNode("staff").isAttached()) {
				getNode("staff").setValue(Arrays.asList("Afforess", "alta189", "narrowtux", "Olloth", "Top_Cat", "Wulfspider"));
			}
			if (!getNode("voiced").isAttached()) {
				getNode("voiced").setValue(Arrays.asList("Contex", "NinjaGrinch", "mau5ie", "Owexz", "RoyAwesome", "Rycochet", "W1ndwaker", "Zidane", "zml2008"));
			}
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

	public List<String> getStaff() {
		return getNode("staff").getStringList();
	}

	public void setStaff(List<String> staff) {
		getNode("staff").setValue(staff);
	}

	public void addStaff(String staff) {
		List<String> s = getStaff();
		if (!s.contains(staff)) {
			s.add(staff);
		}
		setStaff(s);
		save();
	}

	public void removeStaff(String staff) {
		List<String> s = getStaff();
		s.remove(staff);
		setStaff(s);
		save();
	}

	public List<String> getVoiced() {
		return getNode("voiced").getStringList();
	}

	public void setVoiced(List<String> voiced) {
		getNode("voiced").setValue(voiced);
	}

	public void addVoiced(String voiced) {
		List<String> s = getVoiced();
		if (!s.contains(voiced)) {
			s.add(voiced);
		}
		setVoiced(s);
		save();
	}

	public void removeVoiced(String voiced) {
		List<String> s = getVoiced();
		s.remove(voiced);
		setVoiced(s);
		save();
	}
}
