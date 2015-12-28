package draw.Geometry;

public abstract class Geometry
{
	protected int Type;
	
    public final static int Pnt2D = 0;
    public final static int Pnt3D = 1;
    public final static int Ln2D = 2;
    public final static int Ln3D = 3;
    public final static int Rec2D = 4;
    public final static int Rec3D = 5;
    public final static int Poly2D = 6;
    public final static int Poly3D = 7;
    public final static int Cir2D = 8;
    public final static int Cylinder = 9;
    public final static int Cylinder3D = 10;
    public final static int Poly2DEx = 11;

    
	protected int ID; 
	
	public static int INVALID_VALUE = -999999999;
	public static int MAX_INT = Integer.MAX_VALUE;
	public static int MIN_INT = Integer.MIN_VALUE;
	
	public static double min(double a, double b)	{	return a < b ? a : b; 	}
	public static double max(double a, double b)	{	return a > b ? a : b; 	}
	
	public Geometry()
	{		
		this.Type = this.ID = MIN_INT;		
	}
	
	public int getType(){
		return this.Type;
	}
	
	public int getTag()
	{
		return this.Type;
	}
	
	public void setTag(int _tag)
	{
		this.Type = _tag;
	}
	
	public int getID()
	{
		return this.ID;
	}
	
	public void setID(int _id)
	{
		this.ID = _id;
	}
	
	
	// check is this geometry is valid.
	public abstract boolean isValid();
	
	// set one geometry to its default state.
	public abstract void setInvalid();	
}
