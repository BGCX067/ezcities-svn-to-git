package draw.Geometry;

import java.nio.FloatBuffer;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;


public class BndBox  extends Geometry{
	private double minX;
	private double minY;
	private double minZ;
	private double maxX;
	private double maxY;
	private double maxZ;

	public BndBox() 
	{
		this.setInvalid();
	}

	public BndBox(float x, float y, float t) {
		minX = maxX = x;
		minY = maxY = y;
		minZ = maxZ = t;
	}

	public BndBox(Pnt3D p) {
		minX = maxX = p.getX();
		minY = maxY = p.getY();
		minZ = maxZ = p.getZ();
	}

	public BndBox(double d, double e, double f, double g, double h, double i) 
	{
		this.minX = d;
		this.maxX = g;
		this.minY = e;
		this.maxY = h;
		this.minZ = f;
		this.maxZ = i;
	}

	public double[] getAttributes() 
	{
		double[] arr = { minX, maxX, minY, maxY, minZ, maxZ };
		return arr;
	}

	public void setMinX(float minX) 
	{
		this.minX = minX;
	}

	public void setMaxX(float maxX) {
		this.maxX = maxX;
	}

	public void setMinY(float minY) {
		this.minY = minY;
	}

	public void setMaxY(float maxY) {
		this.maxY = maxY;
	}

	public void setMinZ(float minZ) {
		this.minZ = minZ;
	}

	public void setMaxZ(float maxZ) {
		this.maxZ = maxZ;
	}

	public void set(double min_x, double min_y, double min_t, double max_x,
			double max_y, double max_t) {
		this.minX = min_x;
		this.maxX = max_x;
		this.minY = min_y;
		this.maxY = max_y;
		this.minZ = min_t;
		this.maxZ = max_t;
	}

	public void copy(BndBox another) {
		this.minX = another.minX;
		this.maxX = another.maxX;
		this.minY = another.minY;
		this.maxY = another.maxY;
		this.minZ = another.minZ;
		this.maxZ = another.maxZ;
	}

	public boolean contains(Pnt3D point) {
		if (point.getX() >= minX && point.getX() <= maxX
				&& point.getY() >= minY && point.getY() <= maxY
				&& point.getZ() >= minZ && point.getZ() <= maxZ)
			return true;
		return false;
	}

	public boolean contains(BndBox bb) {		
		if(maxX >= bb.maxX && maxY >= bb.maxY && maxZ >= bb.maxZ
				&& minX <= bb.minX && minY <= bb.minY && minZ <= bb.minZ)
			return true;
		return false;
	}
	
	public static boolean contains(float bb1MinX, float bb1MinY, float bb1MinZ, float bb1MaxX, float bb1MaxY, float bb1MaxZ,
									float bb2MinX, float bb2MinY, float bb2MinZ, float bb2MaxX, float bb2MaxY, float bb2MaxZ)
	{
		return bb1MinX < bb2MinX && bb1MinY < bb2MinY && bb1MinZ < bb2MinZ
				&& bb1MaxX > bb2MaxX && bb1MaxY > bb2MaxY && bb1MaxZ > bb2MaxZ;
	}
	public boolean containedBy(BndBox bb) {
		if (maxX <= bb.maxX && maxY <= bb.maxY && maxZ <= bb.maxZ
				&& minX >= bb.minX && minY >= bb.minY && minZ >= bb.minZ)
			return true;
		return false;
	}

	public boolean intersects(BndBox bb) {
		return maxX >= bb.minX && maxY >= bb.minY && maxZ >= bb.minZ
				&& minX <= bb.maxX && minY <= bb.maxY && minZ <= bb.maxZ;
	}


	public boolean equals(Object o) {
		boolean equals = false;
		if (o instanceof BndBox) {
			BndBox bb = (BndBox) o;
			if (minX == bb.minX && minY == bb.minY && minZ == bb.minZ
					&& maxX == bb.maxX && maxY == bb.maxY && maxZ == bb.maxZ) {
				equals = true;
			}
		}
		return equals;
	}

	public void add(float x, float y, float z) {
		if (x < minX)
			minX = x;
		if (x > maxX)
			maxX = x;
		if (y < minY)
			minY = y;
		if (y > maxY)
			maxY = y;
		if (z < minZ)
			minZ = z;
		if (z > maxZ)
			maxZ = z;
	}

	public void add(Pnt3D p) {
		if (p.getX() < minX)
			minX = p.getX();
		if (p.getX() > maxX)
			maxX = p.getX();
		if (p.getY() < minY)
			minY = p.getY();
		if (p.getY() > maxY)
			maxY = p.getY();
		if (p.getZ() < minZ)
			minZ = p.getZ();
		if (p.getZ() > maxZ)
			maxZ = p.getZ();
	}

	public void add(BndBox bb) {
		if (bb.minX < minX)
			this.minX = bb.minX;
		if (bb.maxX > maxX)
			this.maxX = bb.maxX;
		if (bb.minY < minY)
			this.minY = bb.minY;
		if (bb.maxY > maxY)
			this.maxY = bb.maxY;
		if (bb.minZ < minZ)
			this.minZ = bb.minZ;
		if (bb.maxZ > maxZ)
			this.maxZ = bb.maxZ;
	}
	
	public static double enlargement(BndBox bb1, BndBox bb2){
		return enlargement(bb1.minX, bb1.minY, bb1.minZ, bb1.maxX, bb1.maxY, bb1.maxZ,
						   bb2.minX, bb2.minY, bb2.minZ, bb2.maxX, bb2.maxY, bb2.maxZ);
	}

	public static double enlargement(double minX2, double minY2,
			double minZ2, double maxX2, double maxY2, double maxZ2,
			double minX3, double minY3, double minZ3, double maxX3,
			double maxY3, double maxZ3) 
	{
		double bb1Volume = (maxX2 - minX2) * (maxY2 - minY2) * (maxZ2 - minZ2);

		if (bb1Volume == Float.POSITIVE_INFINITY)
			return 0;

		double bb1bb2UnionVolume = (Math.max(maxX2, maxX3) - Math.min(
				minX2, minX3))
				* (Math.max(maxY2, maxY3) - Math.min(minY2, minY3))
				* (Math.max(maxZ2, maxZ3) - Math.min(minZ2, minZ3));

		if (bb1bb2UnionVolume == Float.POSITIVE_INFINITY)
			return Float.POSITIVE_INFINITY;

		return bb1bb2UnionVolume - bb1Volume;
	}

	public static double volume(float bbMinX, float bbMinY, float bbMinZ, float bbMaxX, float bbMaxY, float bbMaxZ) 
	{
		return (bbMaxX - bbMinX) * (bbMaxY - bbMinY) * (bbMaxZ - bbMinZ);
	}

	public double volume() 
	{
		return getHeight() * getWidth() * getDepth();
	}

	public double getHeight() 
	{
		return maxZ - minZ;
	}

	public double getWidth() {
		return maxX - minX;
	}

	public double getDepth() {
		return maxY - minY;
	}
	
	// Xianfeng add for a sample. This Name will be better than the old function name.
	public double deltaX()	{	return getWidth();	}
	public double deltaY()	{	return getDepth();	}
	public double deltaZ()	{	return getHeight();	}

	@Override
	public String toString() {
		return "(" + minX + ", " + minY + ", " + minZ + ", " + maxX + ", "
				+ maxY + ", " + maxZ + ")";
	}

	public BndBox getBoundingBox() {
		return this;
	}
	
	public void render(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2();
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex3d(minX, minY, minZ);
		gl.glVertex3d(minX, maxY, minZ);
		
		gl.glVertex3d(minX, minY, minZ);
		gl.glVertex3d(maxX, minY, minZ);
		
		gl.glVertex3d(maxX, maxY, minZ);
		gl.glVertex3d(maxX, minY, minZ);
		
		gl.glVertex3d(minX, maxY, minZ);
		gl.glVertex3d(maxX, maxY, minZ);
		
		gl.glVertex3d(minX, minY, maxZ);
		gl.glVertex3d(minX, maxY, maxZ);
		
		gl.glVertex3d(minX, minY, maxZ);
		gl.glVertex3d(maxX, minY, maxZ);
		
		gl.glVertex3d(maxX, maxY, maxZ);
		gl.glVertex3d(maxX, minY, maxZ);
		
		gl.glVertex3d(minX, maxY, maxZ);
		gl.glVertex3d(maxX, maxY, maxZ);

		gl.glVertex3d(minX, minY, minZ);
		gl.glVertex3d(minX, minY, maxZ);
		
		gl.glVertex3d(minX, maxY, minZ);
		gl.glVertex3d(minX, maxY, maxZ);
		
		gl.glVertex3d(maxX, minY, minZ);
		gl.glVertex3d(maxX, minY, maxZ);
		
		gl.glVertex3d(maxX, maxY, minZ);
		gl.glVertex3d(maxX, maxY, maxZ);		
		gl.glEnd();
	}
	
	public static void bufferBoundingBox(FloatBuffer buffer, float[] attributes) {
		buffer.put(attributes[0]);
		buffer.put(attributes[2]);
		buffer.put(attributes[4]);

		buffer.put(attributes[1]);
		buffer.put(attributes[2]);
		buffer.put(attributes[4]);

		buffer.put(attributes[1]);
		buffer.put(attributes[2]);
		buffer.put(attributes[4]);

		buffer.put(attributes[1]);
		buffer.put(attributes[2]);
		buffer.put(attributes[5]);

		buffer.put(attributes[1]);
		buffer.put(attributes[2]);
		buffer.put(attributes[5]);

		buffer.put(attributes[0]);
		buffer.put(attributes[2]);
		buffer.put(attributes[5]);

		buffer.put(attributes[0]);
		buffer.put(attributes[2]);
		buffer.put(attributes[4]);

		buffer.put(attributes[0]);
		buffer.put(attributes[2]);
		buffer.put(attributes[5]);

		buffer.put(attributes[0]);
		buffer.put(attributes[3]);
		buffer.put(attributes[5]);

		buffer.put(attributes[1]);
		buffer.put(attributes[3]);
		buffer.put(attributes[5]);

		buffer.put(attributes[1]);
		buffer.put(attributes[3]);
		buffer.put(attributes[4]);

		buffer.put(attributes[0]);
		buffer.put(attributes[3]);
		buffer.put(attributes[4]);

		buffer.put(attributes[1]);
		buffer.put(attributes[3]);
		buffer.put(attributes[4]);

		buffer.put(attributes[1]);
		buffer.put(attributes[3]);
		buffer.put(attributes[5]);

		buffer.put(attributes[0]);
		buffer.put(attributes[2]);
		buffer.put(attributes[5]);

		buffer.put(attributes[0]);
		buffer.put(attributes[3]);
		buffer.put(attributes[5]);

		buffer.put(attributes[0]);
		buffer.put(attributes[2]);
		buffer.put(attributes[4]);

		buffer.put(attributes[0]);
		buffer.put(attributes[3]);
		buffer.put(attributes[4]);

		buffer.put(attributes[0]);
		buffer.put(attributes[3]);
		buffer.put(attributes[5]);

		buffer.put(attributes[0]);
		buffer.put(attributes[3]);
		buffer.put(attributes[4]);

		buffer.put(attributes[1]);
		buffer.put(attributes[2]);
		buffer.put(attributes[5]);

		buffer.put(attributes[1]);
		buffer.put(attributes[3]);
		buffer.put(attributes[5]);

		buffer.put(attributes[1]);
		buffer.put(attributes[3]);
		buffer.put(attributes[4]);

		buffer.put(attributes[1]);
		buffer.put(attributes[2]);
		buffer.put(attributes[4]);
	}
	
	public double getMinX(){
		return minX;
	}
	
	public double getMaxX(){
		return maxX;
	}
	
	public double getMinY(){
		return minY;
	}
	
	public double getMaxY(){
		return maxY;
	}
	
	public double getMinZ(){
		return minZ;
	}
	
	public double getMaxZ(){
		return maxZ;
	}

	@Override
	public boolean isValid() {
		
		return false;
	}

	@Override
	public void setInvalid() {
		minX = minY = minZ = Geometry.MAX_INT;
		maxX = maxY = maxZ = Geometry.MIN_INT;		
	}

}
