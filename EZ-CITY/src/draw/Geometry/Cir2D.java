package draw.Geometry;

public class Cir2D extends Geometry{
	
	private Pnt2D center;
	private double radius;
	
	public Cir2D(Pnt2D center, float radius)
	{
		this.center = center;
		this.radius = radius;
		Type = Cir2D;
	}
	
	public Cir2D(double x, double y, double r){
		center = new Pnt2D(x, y);
		this.radius = r;
		Type = Cir2D;

	}
	
	public Cir2D(double x, double y, float radius2) {
		// TODO Auto-generated constructor stub
		center = new Pnt2D(x, y);
		this.radius = radius;
		Type = Cir2D;

	}

	public boolean contains(Pnt2D p){
		double centerDistance = Math.pow(center.getX() - p.getX(), 2) + Math.pow(center.getY() - p.getY(), 2);
		return centerDistance < Math.pow(radius, 2);
	}
	
	public boolean contains(double intersectX, double intersectY){
		double centerDistance = Math.pow(center.getX() - intersectX, 2) + Math.pow(center.getY() - intersectY, 2);
		return centerDistance < Math.pow(radius, 2);	
	}
	
	public boolean contains(Rec2D r){
		return contains(r.getMinX(), r.getMinY()) && contains(r.getMinX(), r.getMaxY())
			   && contains(r.getMaxX(), r.getMinY()) && contains(r.getMaxX(), r.getMaxY());
	}
	
	public float[] intersectLine(Pnt3D p1, Pnt3D p2){
		double a = Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2);
		double b = 2 * (p1.getX() - p2.getX()) * (p2.getX() - this.center.getX()) + 2 * (p1.getY() - p2.getY()) * (p2.getY() - this.center.getY());
		double c = Math.pow(p2.getX() - this.center.getX(), 2) + Math.pow(p2.getY() - this.center.getY(), 2) - Math.pow(radius, 2);
		
		double test = Math.pow(b, 2) - 4 * a * c;
		
		if(test > 0){
			float[] results = {(float) ((-b - Math.sqrt(test)) / (2 * a)), (float) ((-b + Math.sqrt(test)) / (2 * a))};
			return results;
		}
		
		else
			return null;			
	}

	
	public Pnt2D getCenter(){
		return center;
	}
	
	public double getRadius(){
		return this.radius;
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
