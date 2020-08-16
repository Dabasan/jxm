package com.github.dabasan.jxm.bd1;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.dabasan.ejml_3dtools.Vector;

/**
 * BD1 buffer generator
 * 
 * @author Daba
 *
 */
class BD1BufferGenerator {
	public static List<BD1Buffer> generateBuffers(Map<Integer, List<BD1Face>> facesMap) {
		var buffers = new ArrayList<BD1Buffer>();

		for (var entry : facesMap.entrySet()) {
			var indexValues = new ArrayList<Integer>();
			var posValues = new ArrayList<Float>();
			var uvValues = new ArrayList<Float>();
			var normValues = new ArrayList<Float>();

			int countIndex = 0;
			for (var face : entry.getValue()) {
				Vector[] vertexPositions = face.getVertexPositions();
				UV[] uvs = face.getUVs();
				Vector normal = face.getNormal();

				// First triangle
				indexValues.add(countIndex);
				indexValues.add(countIndex + 1);
				indexValues.add(countIndex + 2);
				// Second triangle
				indexValues.add(countIndex + 2);
				indexValues.add(countIndex + 3);
				indexValues.add(countIndex);
				countIndex += 4;

				for (int i = 3; i >= 0; i--) {
					// Position
					posValues.add(vertexPositions[i].getXFloat());
					posValues.add(vertexPositions[i].getYFloat());
					posValues.add(vertexPositions[i].getZFloat());
					// UV
					uvValues.add(uvs[i].getUFloat());
					uvValues.add(uvs[i].getVFloat() * (-1.0f));
					// Normal
					normValues.add(normal.getXFloat());
					normValues.add(normal.getYFloat());
					normValues.add(normal.getZFloat());
				}
			}

			IntBuffer indexBuffer = IntBuffer.allocate(indexValues.size());
			FloatBuffer posBuffer = FloatBuffer.allocate(posValues.size());
			FloatBuffer uvBuffer = FloatBuffer.allocate(uvValues.size());
			FloatBuffer normBuffer = FloatBuffer.allocate(normValues.size());
			for (var indexValue : indexValues) {
				indexBuffer.put(indexValue);
			}
			for (var posValue : posValues) {
				posBuffer.put(posValue);
			}
			for (var uvValue : uvValues) {
				uvBuffer.put(uvValue);
			}
			for (var normValue : normValues) {
				normBuffer.put(normValue);
			}
			indexBuffer.flip();
			posBuffer.flip();
			uvBuffer.flip();
			normBuffer.flip();

			var buffer = new BD1Buffer();
			buffer.setIndexBuffer(indexBuffer);
			buffer.setPosBuffer(posBuffer);
			buffer.setUVBuffer(uvBuffer);
			buffer.setNormBuffer(normBuffer);

			int textureID = entry.getKey();
			buffer.setTextureID(textureID);

			buffers.add(buffer);
		}

		return buffers;
	}
}
