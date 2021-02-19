package com.github.dabasan.jxm.properties;

import java.io.IOException;
import java.util.Arrays;

import com.github.dabasan.jxm.properties.weapon.WeaponData;
import com.github.dabasan.jxm.properties.weapon.openxops.WeaponCodeGenerator;
import com.github.dabasan.jxm.properties.weapon.openxops.WeaponVariableNameSettings;
import com.github.dabasan.jxm.properties.weapon.xgs.XGSManipulator;

/**
 * Test WeaponCodeGenerator
 * 
 * @author Daba
 *
 */
public class WeaponCodeGeneratorTest {
	private WeaponCodeGenerator generator;

	public static void main(String[] args) {
		new WeaponCodeGeneratorTest();
	}

	public WeaponCodeGeneratorTest() {
		var settings = new WeaponVariableNameSettings();
		settings.arrayName = "武器";
		settings.model = "モデル";

		generator = new WeaponCodeGenerator(settings);

		XGSManipulator xgsManipulator;
		try {
			xgsManipulator = new XGSManipulator("./Data/Weapon/weapons.xgs");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		WeaponData[] weapons = xgsManipulator.getWeapons();
		String code = generator.generate(Arrays.asList(weapons));
		System.out.println(code);
	}
}
