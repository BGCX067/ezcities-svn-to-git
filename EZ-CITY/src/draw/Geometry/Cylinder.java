package draw.Geometry;

import java.util.ArrayList;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import data.dataManeger.Scene;

import simulation.obj.Node;
import simulation.obj.Trip;


public class Cylinder extends Geometry{
	private Pnt3D center;
	private double radius;
	private double height;

	public Cylinder() {
		this.center = new Pnt3D(0.4f, 0.2f, 0);
		this.radius = 0.2f;
		this.height = 1.0f;
		Type = Cylinder;
	}

	public Cylinder(Pnt3D center, float radius, float height) {
		this.center = new Pnt3D(center);
		this.radius = radius;
		this.height = height;
		Type = Cylinder;

	}

	public boolean containPoint(Pnt3D p) {
		return p.getZ() >= center.getZ() && p.getZ() <= center.getZ() + height
				&& center.xydistance(p) <= radius;
	}

	public boolean containTrip(Trip trip, Scene scene) {
		float time = (trip.getArrTime() - trip.getDepTime())
				/ trip.getNodes().length;

		// return true if the trip is contained within the cylinder
		for (int i = 0; i < trip.getNodes().length; i++) {
			Node node = scene.getNodes().get(trip.getNodes()[i]);
			if (!this.containPoint(new Pnt3D(node.getX(), node.getY(), trip
					.getDepTime() + time * i))) {
				return false;
			}
		}
		return true;
	}

	public ArrayList<Pnt3D> getIntersectPoint(Trip trip, Scene scene) {
		float time = (trip.getArrTime() - trip.getDepTime())
				/ trip.getNodes().length;

		if (trip.getArrTime() < this.getCenterZ()
				|| trip.getDepTime() > this.getCenterZ() + this.getHeight())
			return null;

		ArrayList<Pnt3D> intersectPoints = new ArrayList<Pnt3D>();
		// return the intersection points
		for (int i = 0; i < trip.getNodes().length - 1; i++) {
			Node node = scene.getNodes().get(trip.getNodes()[i]);
			Node nextNode = scene.getNodes().get(trip.getNodes()[i + 1]);

			ArrayList<Pnt3D> point = this.intersectLineSegment(
					new Pnt3D(node.getX(), node.getY(), trip.getDepTime()
							+ time * i),
					new Pnt3D(nextNode.getX(), nextNode.getY(), trip
							.getDepTime() + time * (i + 1)));

			if (point.size() != 0) {
				for (int j = 0; j < point.size(); j++)
					intersectPoints.add(point.get(j));
			}
		}
		return intersectPoints;
	}

	
	public ArrayList<Pnt3D> intersectLineSegment(Pnt3D p1, Pnt3D p2) {
		Cir2D xy = new Cir2D(center.getX(), center.getY(), radius);
		Rec2D xz = new Rec2D(center.getX() - radius, center.getZ(),
				center.getX() + radius, center.getZ() + height);
		Rec2D yz = new Rec2D(center.getY() - radius, center.getZ(),
				center.getY() + radius, center.getZ() + height);

		ArrayList<Pnt3D> points = new ArrayList<Pnt3D>();

		if (xy.intersectLine(p1, p2) != null) {
			float[] t = xy.intersectLine(p1, p2);
			for (int i = 0; i < 2; i++)
				if (t[i] >= 0 && t[i] <= 1) {
					double intersectX = (p1.getX() - p2.getX()) * t[i]
							+ p2.getX();
					double intersectY = (p1.getY() - p2.getY()) * t[i]
							+ p2.getY();
					double intersectZ = (p1.getZ() - p2.getZ()) * t[i]
							+ p2.getZ();
					if (xz.contains(intersectX, intersectZ)
							&& yz.contains(intersectY, intersectZ))
						points.add(new Pnt3D(intersectX, intersectY,intersectZ));
				}
		}

		double[] t = xz.intersectLine(new Pnt2D(p1.getX(), p1.getZ()),
				new Pnt2D(p2.getX(), p2.getZ()));
		if (t != null) {
			for (int i = 0; i < 2; i++)
				if (t[i] >= 0 && t[i] <= 1) {
					float intersectX = (float) ((p1.getX() - p2.getX()) * t[i]
							+ p2.getX());
					float intersectY = (float) ((p1.getY() - p2.getY()) * t[i]
							+ p2.getY());
					float intersectZ = (float) ((p1.getZ() - p2.getZ()) * t[i]
							+ p2.getZ());
					if (xy.contains(intersectX, intersectY)
							&& yz.contains(intersectY, intersectZ))
						points.add(new Pnt3D(intersectX, intersectY,
								intersectZ));
				}
		}

		t = yz.intersectLine(new Pnt2D(p1.getY(), p1.getZ()),
				new Pnt2D(p2.getY(), p2.getZ()));
		if (t != null) {
			for (int i = 0; i < 2; i++)
				if (t[i] >= 0 && t[i] <= 1) {
					float intersectX = (float) ((p1.getX() - p2.getX()) * t[i]
							+ p2.getX());
					float intersectY = (float) ((p1.getY() - p2.getY()) * t[i]
							+ p2.getY());
					float intersectZ = (float) ((p1.getZ() - p2.getZ()) * t[i]
							+ p2.getZ());
					if (xy.contains(intersectX, intersectY)
							&& xz.contains(intersectX, intersectZ))
						points.add(new Pnt3D(intersectX, intersectY,
								intersectZ));
				}
		}
		return points;
	}

	@Override
	public String toString() {
		return "Center Point (" + center.getX() + ", " + center.getY() + ", "
				+ center.getZ() + ") Radius" + radius + " Height" + height;
	}


	public BndBox getBoundingBox() {
		return new BndBox(center.getX() - radius, center.getY() - radius,
				center.getZ() - height, center.getX() + radius, center.getY()
						+ radius, center.getZ() + height);
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

	public void set(double x, double y) {
		center.set(x, y, center.getZ());
	}

	public void setRadius(double r) {
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
