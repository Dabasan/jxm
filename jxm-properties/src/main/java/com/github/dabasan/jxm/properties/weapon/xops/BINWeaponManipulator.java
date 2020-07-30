package com.github.dabasan.jxm.properties.weapon.xops;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.jxm.properties.weapon.WeaponData;

/**
 * BIN weapon manipulator
 * 
 * @author Daba
 *
 */
public class BINWeaponManipulator {
	private Logger logger = LoggerFactory.getLogger(BINWeaponManipulator.class);

	private static final int NUM_WEAPONS = 23;
	private WeaponData[] weapons;

	public BINWeaponManipulator() {
		weapons = new WeaponData[NUM_WEAPONS];
	}
	public BINWeaponManipulator(List<Byte> bin, int dataStartPos, int nameStartPos) {
		var binArray = new byte[bin.size()];
		for (int i = 0; i < bin.size(); i++) {
			binArray[i] = bin.get(i);
		}

		var reader = new BINWeaponReader(binArray, NUM_WEAPONS, dataStartPos, nameStartPos);
		weapons = reader.getWeaponData();
	}

	/**
	 * Returns weapon data.
	 * 
	 * @return Array containing weapon data
	 */
	public WeaponData[] getWeaponData() {
		return weapons.clone();
	}
	/**
	 * Sets weapon data.
	 * 
	 * @param weapons
	 *            Array containing weapon data
	 */
	public void setWeaponData(WeaponData[] weapons) {
		if (weapons == null) {
			logger.warn("Null argument where non-null required.");
			return;
		}
		if (weapons.length != NUM_WEAPONS) {
			logger.warn("Invalid number of data contained in the array. number={}", weapons.length);
			return;
		}

		this.weapons = weapons;
	}

	/**
	 * Writes out weapon data to a list containing bytes.
	 * 
	 * @param bin
	 *            List
	 * @param dataStartPos
	 *            Start position of weapon data
	 * @param nameStartPos
	 *            Start position of weapon names
	 */
	public void write(List<Byte> bin, int dataStartPos, int nameStartPos) {
		var writer = new BINWeaponWriter();
		writer.write(bin, weapons, dataStartPos, nameStartPos);
	}
}
