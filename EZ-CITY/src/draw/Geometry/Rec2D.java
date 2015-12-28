package draw.Geometry;


public class Rec2D extends Geometry{
	private double minX;
	private double minY;
	private double maxX;
	private double maxY;

	public Rec2D() {
		Type = Rec2D;
	}
	
	public Rec2D(Pnt2D point1, Pnt2D point2){
		this.minX = Math.min(point1.getX(), point2.getX());
		this.minY = Math.min(point1.getY(), point2.getY());
		this.maxX = Math.max(point1.getX(), point2.getX());
		this.maxY = Math.max(point1.getY(), point2.getY());
		
		Type = Rec2D;

	}

	public Rec2D(double d, double e, double f, double g) {
		this.minX = d;
		this.minY = e;
		this.maxX = f;
		this.maxY = g;
		
		Type = Rec2D;

	}

	public boolean contains(double intersectX, double intersectZ) {
		return intersectX >= this.minX && intersectX <= this.maxX && intersectZ >= this.minY
				&& intersectZ <= this.maxY;
	}
	
	public boolean intersects(Cir2D circle){
		double centerX = circle.getCenter().getX();
		double centerY = circle.getCenter().getY();
		double radius = circle.getRadius();
		
		return intersects(centerX, centerY, radius);
	}

	private boolean intersects(double centerX, double centerY, double radius) {
		double width = this.maxX - this.minX;
		double height = this.maxY - this.minY;
		double centerDistanceX = Math.abs(centerX - this.minX / 2 - this.maxX
				/ 2);
		double centerDistanceY = Math.abs(centerY - this.minY / 2 - this.maxY
				/ 2);

		if (centerDistanceX > width / 2 + radius)
			return false;
		if (centerDistanceY > height / 2 + radius)
			return false;

		if (centerDistanceX <= width / 2)
			return true;
		if (centerDistanceY <= height / 2)
			return true;

		double cornerDistance = Math.pow(centerDistanceX - width / 2, 2)
				+ Math.pow(centerDistanceY - height / 2, 2);

		return cornerDistance <= Math.pow(radius, 2);

	}

	public double[] intersectLine(Pnt2D p1, Pnt2D p2) {
		double[] results = new double[2];
		int assigned = 0;

		Pnt2D minXMinY = new Pnt2D(minX, minY);
		Pnt2D minXMaxY = new Pnt2D(minX, maxY);
		Pnt2D maxXMinY = new Pnt2D(maxX, minY);
		Pnt2D maxXMaxY = new Pnt2D(maxX, maxY);
		// check intersect with the (minX, minY) and (minX, maxY) segment
		double check = intersectLineSegement(p1, p2, minXMinY, minXMaxY);
		if (check != -1) {
			results[assigned] = check;
			assigned++;
		}

		// check intersect with the (maxX, minY) and (maxX, maxY) segment
		check = intersectLineSegement(p1, p2, maxXMinY, maxXMaxY);
		if (check != -1) {
			results[assigned] = check;
			assigned++;
		}

		// check intersect with the (minX, maxY) and (maxX, maxY) segment
		check = intersectLineSegement(p1, p2, minXMaxY, maxXMaxY);
		if (check != -1) {
			results[assigned] = check;
			assigned++;
		}

		// check intersect with the (minX, minY) and (maxX, maxY) segment
		check = intersectLineSegement(p1, p2, minXMinY, maxXMinY);
		if (check != -1) {
			results[assigned] = check;
			assigned++;
		}

		switch (assigned) {
		case 0:
			return null;
		case 2:
			return results;
		default:
			return results;
		}
	}

	//XXX: This should be removed by the method in LineSegment
	private double intersectLineSegement(Pnt2D p1, Pnt2D p2, Pnt2D p3,
			Pnt2D p4) {
		double numerator12 = (p4.getX() - p3.getX()) * (p1.getY() - p3.getY()) - (p4.getY() - p3.getY())
				* (p1.getX() - p3.getX());
		double denominator12 = (p4.getY() - p3.getY()) * (p2.getX() - p1.getX()) - (p4.getX() - p3.getX())
				* (p2.getY() - p1.getY());

		double numerator34 = (p2.getX() - p1.getX()) * (p1.getY() - p3.getY()) - (p2.getY() - p1.getY())
				* (p1.getX() - p3.getX());
		double denominator34 = (p4.getY() - p3.getY()) * (p2.getX() - p1.getX()) - (p4.getX() - p3.getX())
				* (p2.getY() - p1.getY());

		if (denominator12 == 0 || denominator34 == 0)
			return -1;
		else if (numerator12 / denominator12 >= 0
				&& numerator12 / denominator12 < 1
				&& numerator34 / denominator34 >= 0
				&& numerator34 / denominator34 < 1) {
			return numerator12 / denominator12;
		}
		return -1;
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
