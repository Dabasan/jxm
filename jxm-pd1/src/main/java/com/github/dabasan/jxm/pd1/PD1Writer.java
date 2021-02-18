package com.github.dabasan.jxm.pd1;

import static com.github.dabasan.jxm.bintools.ByteFunctions.*;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3fc;

/**
 * PD1 writer
 * 
 * @author Daba
 *
 */
class PD1Writer {
	public void write(OutputStream os, List<PD1Point> points) throws IOException {
		List<Byte> bin = new ArrayList<>();

		// Number of points
		int numPoints = points.size();
		addUnsignedShortValueToBinLE(bin, numPoints);

		// Point data
		for (int i = 0; i < numPoints; i++) {
			PD1Point point = points.get(i);

			// Position
			Vector3fc position = point.position;
			addFloatValueToBinLE(bin, position.x());
			addFloatValueToBinLE(bin, position.y());
			addFloatValueToBinLE(bin, position.z());

			// Rotation
			float rotation = point.rotation;
			addFloatValueToBinLE(bin, rotation);

			// Parameters
			int[] parameters = point.parameters;
			for (int j = 0; j < 4; j++) {
				bin.add((byte) parameters[j]);
			}
		}

		try (var bos = new BufferedOutputStream(os)) {
			for (Byte b : bin) {
				bos.write(b);
			}
		}
	}
}
