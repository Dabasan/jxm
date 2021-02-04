package com.github.dabasan.jxm.bd1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;
import org.joml.Vector3fc;

/**
 * BD1 face generator
 * 
 * @author Daba
 *
 */
class BD1FaceGenerator {
	public static Map<Integer, List<BD1Face>> generateFaces(List<BD1Block> blocks) {
		var facesMap = new HashMap<Integer, List<BD1Face>>();

		for (var block : blocks) {
			Vector3fc[] vertexPositions = block.getVertexPositions();
			UV[] uvs = block.getUVs();
			int[] textureIDs = block.getTextureIDs();

			// Calculate normals.
			var normals = new Vector3f[6];

			for (int i = 0; i < 6; i++) {
				int[] vertexIndices = BD1Functions.getFaceCorrespondingVertexIndices(i);

				var v1 = new Vector3f();
				var v2 = new Vector3f();
				vertexPositions[vertexIndices[3]].sub(vertexPositions[vertexIndices[0]], v1);
				vertexPositions[vertexIndices[1]].sub(vertexPositions[vertexIndices[0]], v2);

				normals[i] = v1.cross(v2);
				normals[i].normalize(normals[i]);
			}

			// Generate faces.
			var faces = new BD1Face[6];
			for (int i = 0; i < 6; i++) {
				faces[i] = new BD1Face();
			}

			for (int i = 0; i < 6; i++) {
				int[] vertexIndices = BD1Functions.getFaceCorrespondingVertexIndices(i);
				int[] uvIndices = BD1Functions.getFaceCorrespondingUVIndices(i);

				var faceVertexPositions = new Vector3fc[4];
				var faceUVs = new UV[4];
				for (int j = 0; j < 4; j++) {
					faceVertexPositions[j] = vertexPositions[vertexIndices[j]];
					faceUVs[j] = uvs[uvIndices[j]];
				}

				faces[i].setVertexPositions(faceVertexPositions);
				faces[i].setUVs(faceUVs);
				faces[i].setNormal(normals[i]);
			}

			for (int i = 0; i < 6; i++) {
				// Create a list for this texture ID if it does not exist.
				if (facesMap.containsKey(textureIDs[i]) == false) {
					var facesList = new ArrayList<BD1Face>();
					facesMap.put(textureIDs[i], facesList);
				}
				// Add a face to the list.
				var facesList = facesMap.get(textureIDs[i]);
				facesList.add(faces[i]);
			}
		}

		return facesMap;
	}
}
