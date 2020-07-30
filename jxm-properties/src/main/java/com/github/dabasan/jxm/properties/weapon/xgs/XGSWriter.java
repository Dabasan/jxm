package com.github.dabasan.jxm.properties.weapon.xgs;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.jxm.bintools.ByteFunctions;
import com.github.dabasan.jxm.properties.weapon.BinEnumConverter;
import com.github.dabasan.jxm.properties.weapon.ModelFilepaths;
import com.github.dabasan.jxm.properties.weapon.ModelType;
import com.github.dabasan.jxm.properties.weapon.ScopeMode;
import com.github.dabasan.jxm.properties.weapon.ShootingStance;
import com.github.dabasan.jxm.properties.weapon.TextureFilepaths;
import com.github.dabasan.jxm.properties.weapon.TextureType;
import com.github.dabasan.jxm.properties.weapon.WeaponData;

/**
 * XGS writer
 * 
 * @author Daba
 *
 */
class XGSWriter {
	public XGSWriter() {

	}

	public void write(OutputStream os, WeaponData[] weapons, int numWeapons) throws IOException {
		var bin = new ArrayList<Byte>();

		bin.add((byte) 0x58);// X
		bin.add((byte) 0x47);// G
		bin.add((byte) 0x53);// S
		bin.add((byte) 0x00);
		bin.add((byte) 0x01);
		bin.add((byte) 0x00);
		bin.add((byte) 0x0E);
		bin.add((byte) 0x00);
		bin.add((byte) 0x17);
		bin.add((byte) 0x00);
		bin.add((byte) 0x1D);
		bin.add((byte) 0x00);
		bin.add((byte) 0x08);
		bin.add((byte) 0x00);

		for (int i = 0; i < numWeapons; i++) {
			// Attacks
			ByteFunctions.addShortValueToBinLE(bin, (short) weapons[i].getAttacks());
			// Penetration
			ByteFunctions.addShortValueToBinLE(bin, (short) weapons[i].getPenetration());
			// Blazings
			ByteFunctions.addShortValueToBinLE(bin, (short) weapons[i].getBlazings());
			// Speed
			ByteFunctions.addShortValueToBinLE(bin, (short) weapons[i].getSpeed());
			// NbsMax
			ByteFunctions.addShortValueToBinLE(bin, (short) weapons[i].getNbsMax());
			// Reloads
			ByteFunctions.addShortValueToBinLE(bin, (short) weapons[i].getReloads());
			// Reaction
			ByteFunctions.addShortValueToBinLE(bin, (short) weapons[i].getReaction());
			// ErrorRangeMin
			ByteFunctions.addShortValueToBinLE(bin, (short) weapons[i].getErrorRangeMin());
			// ErrorRangeMax
			ByteFunctions.addShortValueToBinLE(bin, (short) weapons[i].getErrorRangeMax());
			// Model position
			Vector modelPosition = weapons[i].getM();
			ByteFunctions.addShortValueToBinLE(bin, (short) Math.round(modelPosition.getX()));
			ByteFunctions.addShortValueToBinLE(bin, (short) Math.round(modelPosition.getY()));
			ByteFunctions.addShortValueToBinLE(bin, (short) Math.round(modelPosition.getZ()));
			// Flash position
			Vector flashPosition = weapons[i].getM();
			ByteFunctions.addShortValueToBinLE(bin, (short) Math.round(flashPosition.getX()));
			ByteFunctions.addShortValueToBinLE(bin, (short) Math.round(flashPosition.getY()));
			ByteFunctions.addShortValueToBinLE(bin, (short) Math.round(flashPosition.getZ()));
			// Yakkyou position
			Vector yakkyouPosition = weapons[i].getYakkyouPosition();
			ByteFunctions.addShortValueToBinLE(bin, (short) Math.round(yakkyouPosition.getX()));
			ByteFunctions.addShortValueToBinLE(bin, (short) Math.round(yakkyouPosition.getY()));
			ByteFunctions.addShortValueToBinLE(bin, (short) Math.round(yakkyouPosition.getZ()));
			// WeaponP
			ShootingStance shootingStance = weapons[i].getWeaponP();
			int shootingStanceSpc = BinEnumConverter
					.getBinSpecifierFromShootingStance(shootingStance);
			ByteFunctions.addShortValueToBinLE(bin, (short) shootingStanceSpc);
			// Blazing mode
			boolean blazingMode = weapons[i].isBlazingMode();
			int blazingModeSpc = (blazingMode) ? 0 : 1;
			ByteFunctions.addShortValueToBinLE(bin, (short) blazingModeSpc);
			// Scope mode
			ScopeMode scopeMode = weapons[i].getScopeMode();
			int scopeModeSpc = BinEnumConverter.getBinSpecifierFromScopeMode(scopeMode);
			ByteFunctions.addShortValueToBinLE(bin, (short) scopeModeSpc);
			// Texture
			String textureFilepath = weapons[i].getTexture();
			TextureType textureType = TextureFilepaths.getEnumFromFilepath(textureFilepath);
			int textureTypeSpc = BinEnumConverter.getBinSpecifierFromTextureType(textureType);
			ByteFunctions.addShortValueToBinLE(bin, (short) textureTypeSpc);
			// Model
			String modelFilepath = weapons[i].getModel();
			ModelType modelType = ModelFilepaths.getEnumFromFilepath(modelFilepath);
			int modelTypeSpc = BinEnumConverter.getBinSpecifierFromModelType(modelType);
			ByteFunctions.addShortValueToBinLE(bin, (short) modelTypeSpc);
			// Size
			ByteFunctions.addShortValueToBinLE(bin,
					(short) Math.round(weapons[i].getSize() * 10.0));
			// Yakkyou speed
			Vector yakkyouSpeed = weapons[i].getYakkyouSpeed();
			ByteFunctions.addShortValueToBinLE(bin, (short) Math.round(yakkyouSpeed.getX()));
			ByteFunctions.addShortValueToBinLE(bin, (short) Math.round(yakkyouSpeed.getY()));
			// Sound ID
			ByteFunctions.addShortValueToBinLE(bin, (short) weapons[i].getSoundID());
			// Sound volume
			ByteFunctions.addShortValueToBinLE(bin, (short) weapons[i].getSoundVolume());
			// Silencer
			boolean silencer = weapons[i].isSilencer();
			int silencerSpc = (silencer) ? 1 : 0;
			ByteFunctions.addShortValueToBinLE(bin, (short) silencerSpc);
		}

		// Name
		for (int i = 0; i < numWeapons; i++) {
			String name = weapons[i].getName();
			this.addNameToBin(bin, name);
		}

		// Zero padding
		for (int i = 0; i < 16; i++) {
			bin.add((byte) 0x00);
		}

		try (var bos = new BufferedOutputStream(os)) {
			for (Byte b : bin) {
				bos.write(b);
			}
		}
	}
	private void addNameToBin(List<Byte> bin, String name) {
		var nameBuffer = new byte[16];
		for (int i = 0; i < 16; i++) {
			nameBuffer[i] = 0;
		}

		for (int i = 0; i < name.length(); i++) {
			if (i >= 15) {
				break;
			}
			nameBuffer[i] = (byte) name.charAt(i);
		}

		for (int i = 0; i < 16; i++) {
			bin.add(nameBuffer[i]);
		}
	}
}
