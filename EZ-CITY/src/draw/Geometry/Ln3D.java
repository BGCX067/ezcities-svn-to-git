package draw.Geometry;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;


public class Ln3D extends Geometry{
	private Pnt3D p1, p2;

	public Ln3D() {
		p1 = new Pnt3D();
		p2 = new Pnt3D();
		Type = Ln3D;

	}

	public Ln3D(Pnt3D p1, Pnt3D p2) {
		this.p1 = p1;
		this.p2 = p2;
		Type = Ln3D;

	}

	public Ln3D(float x1, float y1, float z1, float x2, float y2, float z2) {
		
		p1 = new Pnt3D(x1,y1,z1);
		p2 = new Pnt3D(x2,y2,z2);
		Type = Ln3D;
		
	}

	public Pnt3D[] getPoints() {
		Pnt3D[] points = { p1, p2 };
		return points;
	}

	public double getMinZ() {
		return Math.min(p1.getZ(), p2.getZ());
	}

	public double getMaxZ() {
		return Math.max(p1.getZ(), p2.getZ());
	}

	public void render(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex3d(p1.getX(), p1.getY(), p1.getZ());
		gl.glVertex3d(p2.getX(), p2.getY(), p2.getZ());
		gl.glEnd();
	}
	
	public float getLength()
	{
		return p1.distance(p2);
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInvalid() {
		// TODO Auto-generated method stub
		
	}
}
