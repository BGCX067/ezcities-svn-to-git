package draw.Geometry;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class ArrayBufferObject {
	int[]	id = new int[1];
	int		size;

	public ArrayBufferObject(GL2 gl) {
		 gl.glGenBuffers(1, id, 0);
	}
	
	public void dispose(GL2 gl) {
		gl.glDeleteBuffers(1, id, 0);
	}
	
	public void load(GL2 gl, FloatBuffer buffer) {
		bind(gl);
		size = buffer.position();
		buffer.position(0);
		gl.glBufferData(GL2.GL_ARRAY_BUFFER, size * 3, buffer, GL.GL_STATIC_DRAW);
	}
	
	public void bind(GL2 gl) {
		gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, id[0]);
	}

	public int size() {
		return size;
	}
	
	public void draw(GL2 gl, int mode) {
		bind(gl);
		gl.glVertexPointer(3, GL.GL_FLOAT, 0, 0);
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glDrawArrays(mode, 0, size/3);
		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
	}
}
