package draw.Geometry;

import java.util.ArrayList;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Cylinder3D extends Geometry{
	private Pnt3D center;
	private double radius;
	private double height;

	public Cylinder3D() {
		this.center = new Pnt3D(0.4f, 0.2f, 0);
		this.radius = 0.2f;
		this.height = 1.0f;
		Type = Cylinder3D;

	}

	public Cylinder3D(Pnt3D center, float radius, float height) {
		this.center = new Pnt3D(center);
		this.radius = radius;
		this.height = height;
		Type = Cylinder3D;

	}

	public boolean containPoint(Pnt3D p) {
		return p.getZ() >= center.getZ() && p.getZ() <= center.getZ() + height
				&& center.xydistance(p) <= radius;
	}

	@Override
	public String toString() {
		return "Center Point (" + center.getX() + ", " + center.getY() + ", "
				+ center.getZ() + ") Radius" + radius + " Height" + height;
	}

	public double getRadius() {
		return this.radius;
	}

	public double getCenterX() {
		return center.getX();
	}

	public double getCenterY() {
		return center.getY();
	}

	public double getHeight() {
		return this.height;
	}

	public double getCenterZ() {
		return center.getZ();
	}

	public void set(float x, float y) {
		center.set(x, y, center.getZ());
	}

	public void setRadius(float r) {
		this.radius = r;
	}

	public void set(float x, float y, float z) {
		center.set(x, y, z);
	}

	public void setHeight(float h) {
		this.height = h;
	}

	public boolean inHeightDomain(float start, float end) {
		if (getCenterZ() < end && (getCenterZ() + height) >= start)
			return true;
		return false;
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
