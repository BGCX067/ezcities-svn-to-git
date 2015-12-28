package draw.Geometry;

import java.util.ArrayList;

public class Poly2D extends Geometry {
	// private ArrayList<Float> xPos;
	// private ArrayList<Float> yPos;

	private ArrayList<Pnt2D> points;
	private int nPoints;
	private int mArea = 0;
    private ArrayList<Pnt2D> mCutLines;


	public Poly2D() {
		points = new ArrayList<Pnt2D>();
	    mCutLines = new ArrayList<Pnt2D>();

		nPoints = 0;
		Type = Poly2D;

	}

	public Poly2D(ArrayList<Pnt2D> _ps) {

		points = _ps;
		nPoints = points.size();
	    mCutLines = new ArrayList<Pnt2D>();

		Type = Poly2D;

	}

	public void addPoint(float x, float y) {
		points.add(new Pnt2D(x, y));
		nPoints++;
	}

	public void addPoint(Pnt2D p) {
		points.add(p);
		nPoints++;
	}

	public ArrayList<Pnt2D> getPoints() {

		return points;

	}

	// Reference:
	// http://introcs.cs.princeton.edu/java/35purple/Polygon.java.html
	public boolean contains(double d, double e) {
		// int crossings = 0;
		// for (int i = 0; i < nPoints - 1; i++) {
		// double slope = (xPos.get(i + 1) - xPos.get(i))
		// / (yPos.get(i + 1) - yPos.get(i));
		// boolean cond1 = (yPos.get(i) <= e) && (e < yPos.get(i + 1));
		// boolean cond2 = (yPos.get(i + 1) <= e) && (e < yPos.get(i));
		// boolean cond3 = d < slope * (e - yPos.get(i)) + xPos.get(i);
		// if ((cond1 || cond2) && cond3)
		// crossings++;
		// }
		//
		// double slope = (xPos.get(0) - xPos.get(nPoints - 1))
		// / (yPos.get(0) - yPos.get(nPoints - 1));
		// boolean cond1 = (yPos.get(nPoints - 1) <= e) && (e < yPos.get(0));
		// boolean cond2 = (yPos.get(0) <= e) && (e < yPos.get(nPoints - 1));
		// boolean cond3 = d < slope * (e - yPos.get(nPoints - 1))
		// + xPos.get(nPoints - 1);
		// if ((cond1 || cond2) && cond3)
		// crossings++;

		// return (crossings % 2 != 0);
		return false;
	}

	public boolean contains(Pnt2D point) {
		return contains(point.getX(), point.getY());
	}

	public int getNumPoints() {
		return nPoints;
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

	public int Is_point_in_poly(double X, double Y) {
		int n_intersects;

		n_intersects = segments_x_ray(X, Y, points);

		if (n_intersects == -1)
			return 2;

		if (n_intersects % 2 == 0)
			return 1;
		else
			return 0;
	}

	public int segments_x_ray(double X, double Y, ArrayList<Pnt2D> pArray) {
		double x1, x2, y1, y2;
		double x_inter;
		int nPoints = 0;
		int n_intersects;
		int n;

		n_intersects = 0;
		nPoints = pArray.size();
		for (n = 0; n < nPoints - 1; n++) {
			x1 = pArray.get(n).getX();
			y1 = pArray.get(n).getY();

			x2 = pArray.get(n + 1).getX();
			y2 = pArray.get(n + 1).getY();
			/*
			 * I know, it should be possible to do that with less conditions,
			 * but it should be enough readable also!
			 */

			/* segment left from X -> no intersection */
			if (x1 < X && x2 < X)
				continue;

			/* point on vertex */
			if ((x1 == X && y1 == Y) || (x2 == X && y2 == Y))
				return -1;

			/* on vertical boundary */
			if ((x1 == x2 && x1 == X)
					&& ((y1 <= Y && y2 >= Y) || (y1 >= Y && y2 <= Y)))
				return -1;

			/* on horizontal boundary */
			if ((y1 == y2 && y1 == Y)
					&& ((x1 <= X && x2 >= X) || (x1 >= X && x2 <= X)))
				return -1;

			/* segment on ray (X is not important) */
			if (y1 == Y && y2 == Y)
				continue;

			/* segment above (X is not important) */
			if (y1 > Y && y2 > Y)
				continue;

			/* segment below (X is not important) */
			if (y1 < Y && y2 < Y)
				continue;

			/* one end on Y second above (X is not important) */
			if ((y1 == Y && y2 > Y) || (y2 == Y && y1 > Y))
				continue;

			/*
			 * For following cases we know that at least one of x1 and x2 is >=
			 * X
			 */

			/* one end of segment on Y second below Y */
			if (y1 == Y && y2 < Y) {
				if (x1 >= X) /* x of the end on the ray is >= X */
					n_intersects++;
				continue;
			}
			if (y2 == Y && y1 < Y) {
				if (x2 >= X)
					n_intersects++;
				continue;
			}

			/* one end of segment above Y second below Y */
			if ((y1 < Y && y2 > Y) || (y1 > Y && y2 < Y)) {
				if (x1 >= X && x2 >= X) {
					n_intersects++;
					continue;
				}

				/*
				 * now either x1 < X && x2 > X or x1 > X && x2 < X -> calculate
				 * intersection
				 */
				double b, a;

				b = (x2 - x1) / (y2 - y1);
				a = x1 - b * y1;
				x_inter = a + b * Y; // dig_x_intersect ( x1, x2, y1, y2, Y);

				if (x_inter == X)
					return 1;
				else if (x_inter > X)
					n_intersects++;

				continue; /* would not be necessary, just to check, see below */
			}
			/*
			 * should not be reached (one condition is not necessary, but it is
			 * may be better readable and it is a check)
			 */
			// G_warning ( "segments_x_ray() conditions failed:" );
			// G_warning ( "X = %f Y = %f x1 = %f y1 = %f x2 = %f y2 = %f", X,
			// Y, x1, y1, x2, y2 );
		}

		return n_intersects;
	}

	private int getPolygonArea(ArrayList<Pnt2D> points) {
		if (points.size() < 3) {// nodes greater than 3
			return 0;
		}
		int result = 0;
		for (int i = 0; i < points.size() - 1; i++) {//
			Pnt2D a = points.get(i);
			Pnt2D b = points.get(i + 1);
			result += a.cross(b);
		}
		Pnt2D begin = points.get(0);//
		Pnt2D end = points.get(points.size() - 1);
		result += begin.cross(end);
		result *= 0.5;// half
		return result;
	}

	private void proTri() {

		mArea = getPolygonArea(points);

		ArrayList<Pnt2D> tri = new ArrayList<Pnt2D>();
		for (int i = 0; i < points.size(); i++) {
			Pnt2D a = points.get(fixIndex(i));
			Pnt2D b = points.get(fixIndex(i + 1));
			Pnt2D c = points.get(fixIndex(i + 2));
			Pnt2D ab = new Pnt2D(b.X - a.X, b.Y - a.Y);
			Pnt2D bc = new Pnt2D(c.X - b.X, c.Y - b.Y);
			Pnt2D ca = new Pnt2D(a.X - c.X, a.X - c.X);
			tri.clear();
			tri.add(ab);
			tri.add(bc);
			tri.add(ca);
			int test = getPolygonArea(tri);
			if (test == mArea) {// last tri, end seg
				return;
			}
			if (test > 0) {
				if (test < mArea) {// valid tri
					mArea -= test;// remove tri and updata area
					mCutLines.add(a);
					mCutLines.add(c);
					points.remove(fixIndex(i + 1));// remove
					i = 0;// key point, avoid recursion
				} else {// is contains trough
					continue;
				}
			} else {// direction is different
				continue;
			}
		}
	}

	private int fixIndex(int i) {
		if (i == -1) {
			return points.size() - 1;
		}
		if (i == points.size()) {
			return 0;
		}
		if (i == points.size() + 1) {
			return 1;
		}
		return i;
	}
}
