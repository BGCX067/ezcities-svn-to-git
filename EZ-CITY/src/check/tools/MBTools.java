package check.tools;

import javax.media.opengl.GL2;
import javax.vecmath.*;

public class MBTools {

	// from www.processing.org PConstants.java
	static final float DEG_TO_RAD = (float) (Math.PI / 180.0f);
	static final float RAD_TO_DEG = (float) (180.0f / Math.PI);

	public static final Vector3f X_AXIS = new Vector3f(1, 0, 0);
	public static final Vector3f Y_AXIS = new Vector3f(0, 1, 0);
	public static final Vector3f Z_AXIS = new Vector3f(0, 0, 1);

	/**
	 * 
	 * copied from www.processing.org, PApplet.map
	 */
	static public final float map(float value, float istart, float istop,
			float ostart, float ostop) {
		return ostart + (ostop - ostart)
				* ((value - istart) / (istop - istart));
	}

	public static void rect(GL2 gl, float x, float y, float w, float h) {

		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex2f(x, y);
		gl.glVertex2f(x + w, y);
		gl.glVertex2f(x + w, y + h);
		gl.glVertex2f(x, y + h);
		gl.glEnd();
	}

	public static void drawGrid(GL2 gl, float size, float step) {
		// gl::color( Colorf(0.2f, 0.2f, 0.2f) );
		for (float i = -size; i <= size; i += step) {

			drawLine(gl, new Vector3f(i, -size, 0.0f), new Vector3f(i, size,
					0.0f));
			drawLine(gl, new Vector3f(-size, i, 0.0f), new Vector3f(size, i,
					0.0f));
		}

		drawLine(gl, new Vector3f(size, -size, 0.0f), new Vector3f(size, size,
				0.0f));
		drawLine(gl, new Vector3f(-size, size, 0.0f), new Vector3f(size, size,
				0.0f));
	}

	public static void drawLine(GL2 gl, Vector3f start, Vector3f end) {
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex3f(start.x, start.y, start.z);
		gl.glVertex3f(end.x, end.y, end.z);
		gl.glEnd();
	}

	public static void drawAxis(GL2 gl, float size) {

		gl.glColor3f(1, 0, 0);
		drawLine(gl, new Vector3f(0, 0, 0), new Vector3f(size, 0.0f, 0.0f));
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		drawLine(gl, new Vector3f(0, 0, 0), new Vector3f(0.0f, size, 0.0f));
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		drawLine(gl, new Vector3f(0, 0, 0), new Vector3f(0.0f, 0.0f, size));

	}

	public static void debug(String s, boolean b) {
		if (b) {
			System.out.println("DEBUG: " + s);
		}
	}

	public static Vector3f rotate(Vector3f v, Vector3f axis, float angle) {
		// Rotate the point (x,y,z) around the vector (u,v,w)
		// Function RotatePointAroundVector(x#,y#,z#,u#,v#,w#,a#)
		float ux = axis.x * v.x;
		float uy = axis.x * v.y;
		float uz = axis.x * v.z;
		float vx = axis.y * v.x;
		float vy = axis.y * v.y;
		float vz = axis.y * v.z;
		float wx = axis.z * v.x;
		float wy = axis.z * v.y;
		float wz = axis.z * v.z;
		float sa = (float) Math.sin(angle);
		float ca = (float) Math.cos(angle);
		float x = axis.x
				* (ux + vy + wz)
				+ (v.x * (axis.y * axis.y + axis.z * axis.z) - axis.x
						* (vy + wz)) * ca + (-wy + vz) * sa;
		float y = axis.y
				* (ux + vy + wz)
				+ (v.y * (axis.x * axis.x + axis.z * axis.z) - axis.y
						* (ux + wz)) * ca + (wx - uz) * sa;
		float z = axis.z
				* (ux + vy + wz)
				+ (v.z * (axis.x * axis.x + axis.y * axis.y) - axis.z
						* (ux + vy)) * ca + (-vx + uy) * sa;

		return new Vector3f(x, y, z);

	}

	public static void debug(Vector3f v, boolean b) {

		MBTools.debug("Vector3f: " + v.x + "," + v.y + "," + v.z, b);

	}

	// from www.processing.org PApplet.java
	static public final float degrees(float radians) {
		return radians * RAD_TO_DEG;
	}

	// from www.processing.org PApplet.java
	static public final float radians(float degrees) {
		return degrees * DEG_TO_RAD;
	}

	// from www.processing.org PApplet.java
	static public final int constrain(int amt, int low, int high) {
		return (amt < low) ? low : ((amt > high) ? high : amt);
	}

	// from www.processing.org PApplet.java
	static public final double constrain(double amt, double low, double high) {
		return (amt < low) ? low : ((amt > high) ? high : amt);
	}
	
	//map num from [0, 1emax] to [0, 1]
	public static float exp(int num, int max){
		int i = 0;
		float ratio = 0;
		for (; i <= max; i++) {
			ratio = (float) (num / Math.pow(10, i));
			if (ratio >= 0 && ratio < 10)
				break;
		}
		return (float) i / max + 1.0f / max * ratio / 10;
	}
	
	public static int sum(int[] input){
		int sum = 0;
		for(int i = 0; i < input.length; i++)
			sum += input[i];
		
		return sum;	
	}

}
