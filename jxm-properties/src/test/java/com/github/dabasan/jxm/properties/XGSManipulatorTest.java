package com.github.dabasan.jxm.properties;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import com.github.dabasan.jxm.properties.weapon.xgs.XGSManipulator;

/**
 * Test XGSManipulator
 * 
 * @author Daba
 *
 */
public class XGSManipulatorTest {
	private final String TARGET_DIR = "./Data/Weapon";
	private XGSManipulator manipulator;

	public XGSManipulatorTest() {
		var srcFilepath = Paths.get(TARGET_DIR, "weapons.xgs").toString();
		try {
			manipulator = new XGSManipulator(srcFilepath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.printWeapons();
		this.saveAsXGS();
	}

	private void printWeapons() {
		System.out.println("#Weapons");
		Arrays.asList(manipulator.getWeaponData()).forEach(System.out::println);
	}

	private void saveAsXGS() {
		System.out.println("#saveAsXGS()");

		var saveFilepath = Paths.get(TARGET_DIR, "weapons_2.xgs").toString();
		manipulator.saveAsXGS(saveFilepath);
	}
}
