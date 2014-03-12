package com.perfectplay.org;

/*
This file is part of Earth.

Earth is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Earth is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Earth.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.nio.IntBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class SkyBox {
	static final IntBuffer buffer = BufferUtils.newIntBuffer(1);

	private Mesh cube;
	// private Pixmap[] textures = new Pixmap[6];
	private String[] textures = new String[6];
	private ShaderProgram program;
	private int g_cubeTexture;
	private Matrix4 invView = new Matrix4();
	private Matrix4 mvp = new Matrix4();

	private AssetManager manager;

	private static final String fragmentShader = "Shaders/skybox.frag";
	private static final String vertexShader = "Shaders/skybox.vert";

	public SkyBox(AssetManager manager, String[] faces) {

		this.manager = manager;
		this.textures = faces;

		g_cubeTexture = createGLHandle();
		Gdx.gl20.glBindTexture(GL20.GL_TEXTURE_CUBE_MAP, g_cubeTexture);

		program = new ShaderProgram(Gdx.files.internal(vertexShader)
				.readString(), Gdx.files.internal(fragmentShader).readString());
		if (!program.isCompiled())
			throw new GdxRuntimeException("Couldn't compile skybox shader: "
					+ program.getLog());

		cube = genSkyBox();

		reloadCubemap();
	}
	
	public static Mesh genSkyBox() {

		Mesh mesh = new Mesh(true, 8, 14, new VertexAttributes(new VertexAttribute(Usage.Position, 3, "a_position")));

		float[] cubeVerts = { -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f,
				1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f,
				-1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, };

		short[] indices = { 0, 1, 2, 3, 7, 1, 5, 4, 7, 6, 2, 4, 0, 1 };

		mesh.setVertices(cubeVerts);
		mesh.setIndices(indices);

		return mesh;
	}
	
	public void reloadCubemap() {
		g_cubeTexture = createGLHandle();
		Gdx.gl20.glBindTexture(GL20.GL_TEXTURE_CUBE_MAP, g_cubeTexture);

		Pixmap temp = manager.get(textures[0], Pixmap.class);
		Gdx.gl.glTexImage2D(GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_X, 0,
				temp.getGLInternalFormat(), temp.getWidth(), temp.getHeight(),
				0, temp.getGLFormat(), temp.getGLType(), temp.getPixels());
		temp = manager.get(textures[1], Pixmap.class);
		Gdx.gl.glTexImage2D(GL20.GL_TEXTURE_CUBE_MAP_NEGATIVE_X, 0,
				temp.getGLInternalFormat(), temp.getWidth(), temp.getHeight(),
				0, temp.getGLFormat(), temp.getGLType(), temp.getPixels());
		temp = manager.get(textures[2], Pixmap.class);
		Gdx.gl.glTexImage2D(GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_Y, 0,
				temp.getGLInternalFormat(), temp.getWidth(), temp.getHeight(),
				0, temp.getGLFormat(), temp.getGLType(), temp.getPixels());
		temp = manager.get(textures[3], Pixmap.class);
		Gdx.gl.glTexImage2D(GL20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, 0,
				temp.getGLInternalFormat(), temp.getWidth(), temp.getHeight(),
				0, temp.getGLFormat(), temp.getGLType(), temp.getPixels());
		temp = manager.get(textures[4], Pixmap.class);
		Gdx.gl.glTexImage2D(GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_Z, 0,
				temp.getGLInternalFormat(), temp.getWidth(), temp.getHeight(),
				0, temp.getGLFormat(), temp.getGLType(), temp.getPixels());
		temp = manager.get(textures[5], Pixmap.class);
		Gdx.gl.glTexImage2D(GL20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, 0,
				temp.getGLInternalFormat(), temp.getWidth(), temp.getHeight(),
				0, temp.getGLFormat(), temp.getGLType(), temp.getPixels());

		Gdx.gl20.glBindTexture(GL20.GL_TEXTURE_CUBE_MAP, g_cubeTexture);

		Gdx.gl20.glTexParameterf(GL20.GL_TEXTURE_CUBE_MAP,
				GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_LINEAR);
		Gdx.gl20.glTexParameterf(GL20.GL_TEXTURE_CUBE_MAP,
				GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);
		Gdx.gl20.glTexParameteri(GL20.GL_TEXTURE_CUBE_MAP,
				GL20.GL_TEXTURE_WRAP_S, GL20.GL_CLAMP_TO_EDGE);
		Gdx.gl20.glTexParameteri(GL20.GL_TEXTURE_CUBE_MAP,
				GL20.GL_TEXTURE_WRAP_T, GL20.GL_CLAMP_TO_EDGE);
	}

	public void render(PerspectiveCamera camera) {
		invView.set(camera.view);

		// Remove translation
		invView.val[Matrix4.M03] = 0;
		invView.val[Matrix4.M13] = 0;
		invView.val[Matrix4.M23] = 0;

		invView.inv().tra();

		mvp.set(camera.projection);
		mvp.mul(invView);

		Gdx.gl.glEnable(GL20.GL_CULL_FACE);
		Gdx.gl.glCullFace(GL20.GL_FRONT);
		Gdx.gl.glFrontFace(GL20.GL_CCW);

		Gdx.gl20.glDisable(GL20.GL_BLEND);
		Gdx.gl20.glDisable(GL20.GL_DEPTH_TEST);
		Gdx.gl20.glDepthMask(false);

		Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
		Gdx.gl20.glBindTexture(GL20.GL_TEXTURE_CUBE_MAP, g_cubeTexture);

		program.begin();

		program.setUniformMatrix("u_mvpMatrix", mvp);
		program.setUniformi("s_cubemap", 0);

		cube.render(program, GL20.GL_TRIANGLE_STRIP);

		program.end();

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glDepthMask(true);
	}

	protected static int createGLHandle () {
		buffer.position(0);
		buffer.limit(buffer.capacity());
		Gdx.gl.glGenTextures(1, buffer);
		return buffer.get(0);
	}
	
	public void resume() {
		reloadCubemap();
	}

	public void dispose() {
		for (int i = 0; i < 6; i++) {
			if (manager.isLoaded(textures[i])) {
				manager.unload(textures[i]);
			}
		}
		cube.dispose();

		IntBuffer buffer = BufferUtils.newIntBuffer(1);
		buffer.put(0, g_cubeTexture);
		Gdx.gl.glDeleteTextures(1, buffer);
	}
}