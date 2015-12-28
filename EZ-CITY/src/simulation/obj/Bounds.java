package simulation.obj;

public class Bounds {
	double xmin = Float.MAX_VALUE;
	double xmax = -Float.MAX_VALUE;
	double ymin = Float.MAX_VALUE;
	double ymax = -Float.MAX_VALUE;
	double zmin = Float.MAX_VALUE;
	double zmax = -Float.MAX_VALUE;

	// keep the ratio of x / y
	public static double ratio = 1;

	public void add(double x, double y, double z) {
		xmin = Math.min(x, xmin);
		xmax = Math.max(x, xmax);
		ymin = Math.min(y, ymin);
		ymax = Math.max(y, ymax);

		if (xmax - xmin > 0 && ymax - ymin > 0)
			ratio = (ymax - ymin) / (xmax - xmin);
		// System.out.println("Ratio: "+ratio);
		zmin = Math.min(z, zmin);
		zmax = Math.max(z, zmax);
	}

//	public float normalizeX(float x) {
//		return xmax - xmin > 0 ? (x - xmin) / (xmax - xmin) : 0;
//	}
//
//	public float normalizeY(float y) {
//		return ymax - ymin > 0 ? (y - ymin) / (ymax - ymin) * ratio : 0;
//	}

	//modified, just to scale it to -1 to -1
	
	public double normalizeX(double d) {
		return xmax - xmin > 0 ? (d - xmax/2.0f - xmin/2.0f) / (xmax/2.0f - xmin/2.0f) : 0;
	}

	public double normalizeY(double y) {
		return ymax - ymin > 0 ? (y - ymax/2.0f - ymin/2.0f) / (ymax/2.0f - ymin/2.0f) * ratio : 0;
	}
	
	
	
	public double normalizeZ(double z) {
		return zmax - zmin > 0 ? (z - zmin) / (zmax - zmin) : 0;
	}

	public double unNormalizeZ(double z) {
		return zmax - zmin > 0 ? z * (zmax - zmin) + zmin : 0;
	}

	@Override
	public String toString() {
		return "[" + xmin + " " + xmax + "][" + ymin + " " + ymax + "][" + zmin
				+ " " + zmax + "]";
	}
}
