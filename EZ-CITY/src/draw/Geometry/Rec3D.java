package draw.Geometry;


public class Rec3D extends Geometry{
	
	private double minX;
	private double minY;
	private double maxX;
	private double maxY;
	private double minZ;
	private double maxZ;
	private Pnt3D center;
	private double width;

	public Rec3D() {
		Type = Rec3D;
	}
	
	public Rec3D(Pnt3D point1, Pnt3D point2){
		this.minX = Math.min(point1.getX(), point2.getX());
		this.minY = Math.min(point1.getY(), point2.getY());
		this.maxX = Math.max(point1.getX(), point2.getX());
		this.maxY = Math.max(point1.getY(), point2.getY());
		this.minZ = Math.min(point1.getZ(), point2.getZ());
		this.maxZ = Math.max(point1.getZ(), point2.getZ());
		
		this.center = new Pnt3D((minX+maxX)/2,(minY+maxY)/2,(minZ+maxZ)/2);
        this.width = Math.max(maxX - minX, maxY - minY);
   //     this.width = Math.max(width, maxZ - minZ);
		
		Type = Rec3D;


	}
	
	public Rec3D(Pnt3D cen, double wid){
		center = cen;
		width = wid;
		this.minX = center.getX() - width;
		this.minY = center.getY() - width;
		this.maxX = center.getX() + width;
		this.maxY = center.getY() + width;
		this.minZ = center.getZ() - width;
		this.maxZ = center.getZ() + width;
		
		Type = Rec3D;

	}

	public Rec3D(double a, double b, double c, double d, double e, double f) {
		this.minX = a;
		this.minY = b;
		this.minZ = c;
		this.maxX = d;
		this.maxY = e;
		this.maxZ = f;
		
		Type = Rec3D;

		
	}
	
	
	public void setRec(double a, double b, double c, double d, double e, double f)
	{
		this.minX = a;
		this.minY = b;
		this.minZ = c;
		this.maxX = d;
		this.maxY = e;
		this.maxZ = f;
	}


	@Override
	public String toString() {
		return "(" + minX + ", " + minY + ", " + maxX + ", " + maxY + ")";
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
	
	public double getWidth(){
		return width;
	}
	
	public Pnt3D getCenter(){
		return center;
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
