package draw.Geometry;


/*
 * zw: a line segment contains two 2d points
 */
public class Ln2D extends Geometry
{
	private Pnt2D start;
	private Pnt2D end;

	public Ln2D(Pnt2D point1, Pnt2D point2) 
	{
		this.start = new Pnt2D(point1.getX(), point1.getY());
		this.end = new Pnt2D(point2.getX(), point2.getY());
		Type = Ln2D;
	}
	
	public double getT( Pnt2D pt )
	{	
		double dx = this.deltaX();
		double dy = this.deltaY();
		 
		 if( dx > dy )
		 {
			 return (pt.getX() - start.getX())/dx;					 
		 }
		 else
		 {
			 return (pt.getY() - start.getY())/dy;
		 }
	}
	
	public Pnt2D getPoint(float t )
	{
		Pnt2D pt = new Pnt2D();
		
		double x = start.getX() + t * deltaX();
		double y = start.getY() + t * deltaY();
		
		pt.setX(x);
		pt.setY(y);
		
		return pt; 
	}
	
	public double deltaX()
	{
		return end.getX() - start.getX(); 
	}
	
	public double deltaY()
	{
		return end.getY() - start.getY();
	}

	public boolean intersects(Ln2D line) 
	{
		if (intersects(this, line) == null)
			return false;
		else
			return true;
	}

	// returns the intersection point of two line segments
	// reference http://paulbourke.net/geometry/lineline2d/
	public static Pnt2D intersects(Ln2D line1, Ln2D line2) {
		Pnt2D p1 = line1.getPoints()[0];
		Pnt2D p2 = line1.getPoints()[1];
		Pnt2D p3 = line2.getPoints()[0];
		Pnt2D p4 = line2.getPoints()[1];
		double numerator12 = (p4.getX() - p3.getX()) * (p1.getY() - p3.getY())
				- (p4.getY() - p3.getY()) * (p1.getX() - p3.getX());
		double denominator12 = (p4.getY() - p3.getY()) * (p2.getX() - p1.getX())
				- (p4.getX() - p3.getX()) * (p2.getY() - p1.getY());

		double numerator34 = (p2.getX() - p1.getX()) * (p1.getY() - p3.getY())
				- (p2.getY() - p1.getY()) * (p1.getX() - p3.getX());
		double denominator34 = (p4.getY() - p3.getY()) * (p2.getX() - p1.getX())
				- (p4.getX() - p3.getX()) * (p2.getY() - p1.getY());

		if (denominator12 == 0 || denominator34 == 0)
			return null;
		else if (numerator12 / denominator12 >= 0
				&& numerator12 / denominator12 < 1
				&& numerator34 / denominator34 >= 0
				&& numerator34 / denominator34 < 1) {
			double u = numerator12 / denominator12;
			double x = p1.getX() + u * (p2.getX() - p1.getX());
			double y = p1.getY() + u * (p2.getY() - p1.getY());
			return new Pnt2D(x, y);
		}
		return null;
	}

	// find the 3d pointof p in line p1, p2
	public static Pnt3D pointIn3D(Pnt2D p, Pnt3D p1, Pnt3D p2) {
		if (p == null)
			return null;
		double ux = (p.getX() - p1.getX()) / (p2.getX() - p1.getX());
		double uy = (p.getY() - p1.getY()) / (p2.getY() - p1.getY());

		// p is not on p1, p2
		if (ux != uy)
			return null;

		double z = p1.getZ() + ux * (p2.getZ() - p1.getZ());
		return new Pnt3D(p.getX(), p.getY(), z);
	}

	public Pnt2D[] getPoints() {
		Pnt2D[] points = { start, end };
		return points;
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
