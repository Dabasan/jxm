package com.github.dabasan.jxm.bd1;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ejml.simple.SimpleMatrix;

import com.github.dabasan.jxm.vectools.VectorFunctions;

import de.javagl.obj.Mtl;
import de.javagl.obj.MtlWriter;
import de.javagl.obj.Mtls;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjWriter;
import de.javagl.obj.Objs;

/**
 * Writes out BD1 blocks into an OBJ file.
 * 
 * @author Daba
 *
 */
class BD1OBJWriter {
	public BD1OBJWriter() {

	}

	public void Write(OutputStream osObj, OutputStream osMtl, List<String> mtlFilenames,
			List<BD1Block> blocks, Map<Integer, String> textureFilenames) throws IOException {
		// Prepare faces.
		var facesMap = new HashMap<Integer, List<BD1Face>>();

		for (var block : blocks) {
			SimpleMatrix[] vertexPositions = block.getVertexPositions();
			UV[] uvs = block.getUVs();
			int[] textureIDs = block.getTextureIDs();

			// Calculate normals.
			SimpleMatrix[] normals = new SimpleMatrix[6];

			for (int i = 0; i < 6; i++) {
				int[] vertexIndices = BD1Functions.getFaceCorrespondingVertexIndices(i);

				var v1 = VectorFunctions.sub(vertexPositions[vertexIndices[3]],
						vertexPositions[vertexIndices[0]]);
				var v2 = VectorFunctions.sub(vertexPositions[vertexIndices[1]],
						vertexPositions[vertexIndices[0]]);

				normals[i] = VectorFunctions.cross(v1, v2);
				normals[i] = VectorFunctions.normalize(normals[i]);
			}

			// Generate faces.
			BD1Face[] faces = new BD1Face[6];
			for (int i = 0; i < 6; i++) {
				faces[i] = new BD1Face();
			}

			for (int i = 0; i < 6; i++) {
				int[] vertexIndices = BD1Functions.getFaceCorrespondingVertexIndices(i);
				int[] uvIndices = BD1Functions.getFaceCorrespondingUVIndices(i);

				SimpleMatrix[] faceVertexPositions = new SimpleMatrix[4];
				UV[] faceUVs = new UV[4];
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

		// Write out into an OBJ file.
		Obj obj = Objs.create();
		var mtls = new ArrayList<Mtl>();

		obj.setMtlFileNames(mtlFilenames);
		obj.setActiveGroupNames(Arrays.asList("map"));

		int count = 0;
		for (var entry : facesMap.entrySet()) {
			int textureID = entry.getKey();
			String textureFilename = textureFilenames.get(textureID);
			if (textureFilename == null) {
				textureFilename = "unknown_" + textureID;
			}

			String materialName;
			materialName = this.getFilenameWithoutDirectory(textureFilename);
			materialName = this.getFilepathWithoutExtension(materialName);
			materialName += "_" + textureID;

			Mtl mtl = Mtls.create(materialName);
			mtl.setNs(0.0f);
			mtl.setKa(1.0f, 1.0f, 1.0f);
			mtl.setKd(1.0f, 1.0f, 1.0f);
			mtl.setKs(0.0f, 0.0f, 0.0f);
			mtl.setD(1.0f);
			mtl.setNs(0.0f);
			mtl.setMapKd(textureFilename);
			mtls.add(mtl);

			obj.setActiveMaterialGroupName(materialName);

			var faces = entry.getValue();
			for (var face : faces) {
				SimpleMatrix[] vertexPositions = face.getVertexPositions();
				SimpleMatrix normal = face.getNormal();
				UV[] uvs = face.getUVs();

				for (int i = 3; i >= 0; i--) {
					obj.addVertex((float) vertexPositions[i].get(0, 0),
							(float) vertexPositions[i].get(1, 0),
							(float) vertexPositions[i].get(2, 0));
					obj.addNormal((float) normal.get(0, 0), (float) normal.get(1, 0),
							(float) normal.get(2, 0));
					obj.addTexCoord(uvs[i].getU(), uvs[i].getV() * (-1.0f));
				}

				int[] indices = new int[]{count, count + 1, count + 2, count + 3};
				obj.addFace(indices, indices, indices);

				count += 4;
			}
		}

		ObjWriter.write(obj, osObj);
		MtlWriter.write(mtls, osMtl);
	}
	private String getFilepathWithoutExtension(String filepath) {
		int lastDotPos = filepath.lastIndexOf('.');
		if (lastDotPos == -1) {
			return filepath;
		}

		return filepath.substring(0, lastDotPos);
	}
	private String getFilenameWithoutDirectory(String filepath) {
		int lastSlashPos = filepath.lastIndexOf('/');
		if (lastSlashPos == -1) {
			return filepath;
		}

		return filepath.substring(lastSlashPos + 1);
	}
}
