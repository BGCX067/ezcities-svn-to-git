package draw.Geometry;


public class Pnt2D extends Geometry {
	public double X;
	public double Y;
	
	public Pnt2D()
	{		
		this.X = 0;
		this.Y = 0;
		Type = Pnt2D;

	}
	
	public Pnt2D(double d, double e)
	{
		this.X = d;
		this.Y = e;
		Type = Pnt2D;

	}
		
	public float distance(Pnt2D p)
	{
		double square = Math.pow(this.X - p.getX(), 2) + Math.pow(this.Y - p.getY(), 2); 
		return (float)Math.sqrt(square);
	}
	
	public double getX()
	{
		return X;
	}
	
	public double getY()
	{
		return Y;
	}
	
	public void setX(double x2)
	{
		this.X = x2;
	}

	
	public void setY(double y)
	{
		this.Y = y;
	}	
	
	@Override
	public String toString()
	{
		return "("+X+", "+Y+")";
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

    public Pnt2D plus(Pnt2D other) {
    	Pnt2D result = new Pnt2D();
    	result.X = other.X + X;
    	result.Y = other.Y + Y;
        
        return result;
    }

    public double product(Pnt2D other) {
        return X * other.X + Y * other.Y;
    }

    public double cross(Pnt2D other) {
        return X * other.Y - Y * other.X;
    }

    public boolean equal(Pnt2D other) {
        if (other.X == X && other.Y == Y) {
            return true;
        } else {
            return false;
        }
    }
	
	
	

}
