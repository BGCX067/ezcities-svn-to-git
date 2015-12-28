package draw.Geometry;

public class MathEx {
	
	public static double[] calcNormal(double v[][])				
	{
		double[] v1 = new double[3];
		double[] v2 = new double[3];	
		double[] out = new double[3];
		
		int x = 0;				
		int y = 1;					
		int z = 2;				

		v1[x] = v[0][x] - v[1][x];					
		v1[y] = v[0][y] - v[1][y];					
		v1[z] = v[0][z] - v[1][z];					
		v2[x] = v[1][x] - v[2][x];					
		v2[y] = v[1][y] - v[2][y];					
		v2[z] = v[1][z] - v[2][z];					
		out[x] = v1[y]*v2[z] - v1[z]*v2[y];				
		out[y] = v1[z]*v2[x] - v1[x]*v2[z];				
		out[z] = v1[x]*v2[y] - v1[y]*v2[x];				

		return ReduceToUnit(out);						
	}
	
	public static double[] ReduceToUnit(double[] vector)				
	{							
		double length;
		
		double[] r = new double[3];
		
		length = Math.sqrt((vector[0]*vector[0]) + (vector[1]*vector[1]) + (vector[2]*vector[2]));

		if(length == 0.0f)					
			length = 1.0f;					

		r[0] = vector[0] /length;				
		r[1] = vector[1] /length;						
		r[2] = vector[2] /length;	
		
		return r;
	}

}
