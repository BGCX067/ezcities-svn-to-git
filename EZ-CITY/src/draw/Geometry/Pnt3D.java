package draw.Geometry;

//import javax.media.opengl.GL2;
//import javax.media.opengl.GLAutoDrawable;

public class Pnt3D extends Geometry{
    double X;
	double Y;
    double Z;
	
	private double r = 0;
	private double g = 0;
	private double b = 0;
	
	public Pnt3D()
	{
		this.setInvalid();	
		Type = Pnt3D;

	}
	
	public Pnt3D(double x, double y, double z)
	{
		this.X = x;
		this.Y = y;
		this.Z = z;
		
		Type = Pnt3D;

	}
	
	public Pnt3D(Pnt3D p) {
		this.X = p.getX();
		this.Y = p.getY();
		this.Z = p.getZ();
		Type = Pnt3D;

		
	}
	
	public void SetColor(double r2, double g2, double b2){
		this.r = (float) r2;
		this.g = (float) g2;
		this.b = (float) b2;
	}
	
	
	public double[] getColor(){
		double color[] = new double[4];
		color[0] = r;
		color[1] = g;
		color[2] = b;
		color[3] = 0.7f;
		return color;
	}

	public float distance(Pnt3D p){
		float square = (float) (Math.pow(this.X - p.getX(), 2) + Math.pow(this.Y - p.getY(), 2) + Math.pow(this.Z - p.getZ(), 2)); 
		return (float)Math.sqrt(square);
	}
	
	public double xydistance(Pnt3D p){
		double square =  (Math.pow(this.X - p.getX(), 2) + Math.pow(this.Y - p.getY(), 2)); 
		return Math.sqrt(square);
	}
	
	
	public float distance (Ln3D line) 
	{
	
	   // lb - line start
	  //lE - line end
	   Pnt3D start = line.getPoints()[0];
	   Pnt3D end = line.getPoints()[1];

		
	   float sqrP_LB = distance(start)*distance(start);
	   float sqrP_LE = distance(end)*distance(end);
	   float sqrLB_LE = line.getLength()*line.getLength();
	   float LB_LE = (float) Math.sqrt(sqrLB_LE);
	   float I_LB = (sqrP_LB + sqrLB_LE - sqrP_LE)/(2*LB_LE);
	   if (I_LB < 0 ) return (float) Math.sqrt(sqrP_LB); // intersection point is before beginning line segment
	   if (I_LB > LB_LE ) return (float) Math.sqrt(sqrP_LE); // intersection point is behind end line segment
	
	   /*    use this part if you want to know what the closest point on the line is.
	   float u = I_LB/LB_LE;
	   float[] closestPoint=  new float[]
	   {
	     lB[0]+ u*(lE[0]-lB[0]),
	     lB[1]+ u*(lE[1]-lB[1]),
	     lB[2]+ u*(lE[2]-lB[2])
	   };
	   */
	   float distance = (float) Math.sqrt(sqrP_LB - I_LB*I_LB);
	   return distance;
	}
	 

	
	
	public double getX(){
		return X;
	}
	
	public double getY(){
		return Y;
	}
	
	public double getZ(){
		return Z;
	}
	
	public void setX(double x){
		this.X = x;
	}
	
	public void setY(double y){
		this.Y = y;
	}
	
	public void setZ(double z){
		this.Z = z;
	}
	
	@Override
	public String toString(){
		return "("+X+", "+Y+", "+Z+")";
	}

	public void set(double x2, double y2, double d) {
		this.X = x2;
		this.Y = y2;
		this.Z = d;
		
	}

	@Override
	public boolean isValid() {
		// check the X,Y,Z		
		return X != Geometry.MIN_INT && Y != Geometry.MIN_INT && Z != Geometry.MIN_INT;
	}

	@Override
	public void setInvalid() {
		X = Y = Z = Type = Geometry.MIN_INT;		
	}	
	
	public double  cross2d (Pnt3D other) {
	        return X * other.Y - Y * other.X;
	}
	 
	 
	public Pnt3D crossAndAssign(Pnt3D a, Pnt3D b) {
		float tempX = (float) (a.Y * b.Z - a.Z * b.Y);
		float tempY = (float) (a.Z * b.X - a.X * b.Z);
		float tempZ = (float) (a.X * b.Y - a.Y * b.X);
		
		Pnt3D p = new Pnt3D(tempX, tempY, tempZ);
 
		return p;
	}
 
	public Pnt3D scale(float scalar) {
	
		return new Pnt3D(X*scalar, Y*scalar, Z*scalar);
	}
 
	public Pnt3D normalize() {
		float length = l2Norm();
 
		return new Pnt3D(X/length, Y/length, Z/length);
	}
	
	/**
	 * Returns the length of the vector, also called L2-Norm or Euclidean Norm.
	 */
	public float l2Norm() {
		return (float) Math.sqrt(X*X+Y*Y+Y*Y);
	}
 
}
