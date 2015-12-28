package draw.Geometry;

import java.util.ArrayList;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class PolyTube3D{
	private Poly2DEx base;
	private float zMin;
	private float height;

	public PolyTube3D() {
	}

	public PolyTube3D(Poly2DEx base, float zMin, float height) {
		this.base = base;
		this.zMin = zMin;
		this.height = height;
	}

	public Poly2DEx getBase() {
		return base;
	}

	public float getMinZ() {
		return zMin;
	}

	public float getHeight() {
		return height;
	}

	
//	public void draw(GL2 gl) {
//		gl.glBegin(GL2.GL_LINES);
//		for (int i = 0; i < base.getNumPoints(); i++) {
//			gl.glVertex3d(base.getPosX().get(i), base.getPosY().get(i), zMin);
//			gl.glVertex3d(base.getPosX().get(i), base.getPosY().get(i), zMin
//					+ height);
//		}
//
//		for (int i = 0; i < base.getNumPoints() - 1; i++) {
//			gl.glVertex3d(base.getPosX().get(i), base.getPosY().get(i), zMin);
//			gl.glVertex3d(base.getPosX().get(i + 1), base.getPosY().get(i + 1),
//					zMin);
//		}
//
//		gl.glVertex3d(base.getPosX().get(0), base.getPosY().get(0), zMin);
//		gl.glVertex3d(base.getPosX().get(base.getNumPoints() - 1), base
//				.getPosY().get(base.getNumPoints() - 1), zMin);
//
//		for (int i = 0; i < base.getNumPoints() - 1; i++) {
//			gl.glVertex3d(base.getPosX().get(i), base.getPosY().get(i), zMin
//					+ height);
//			gl.glVertex3d(base.getPosX().get(i + 1), base.getPosY().get(i + 1),
//					zMin + height);
//		}
//
//		gl.glVertex3d(base.getPosX().get(0), base.getPosY().get(0), zMin
//				+ height);
//		gl.glVertex3d(base.getPosX().get(base.getNumPoints() - 1), base
//				.getPosY().get(base.getNumPoints() - 1), zMin + height);
//
//		gl.glEnd();
//	}

	public void render(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}
}
