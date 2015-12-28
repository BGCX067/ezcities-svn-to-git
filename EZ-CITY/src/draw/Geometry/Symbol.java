package draw.Geometry;

import java.util.ArrayList;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/*
 * including all glbegin + advanced draw
 * what is missing
 * GL_TRIANGLES
 Treats each triplet of vertices as an independent triangle. 
 Vertices 3 �?� n - 2 , 3 �?� n - 1 , and 3 �?� n define triangle n. N 3 triangles are drawn.

 GL_TRIANGLE_STRIP
 Draws a connected group of triangles. 
 One triangle is defined for each vertex presented after the first two vertices. 
 For odd n, vertices n, n + 1 , and n + 2 define triangle n. 
 For even n, vertices n + 1 , n, and n + 2 define triangle n. N - 2 triangles are drawn.

 GL_TRIANGLE_FAN
 Draws a connected group of triangles. 
 One triangle is defined for each vertex presented after the first two vertices. 
 Vertices 1 , n + 1 , and n + 2 define triangle n. N - 2 triangles are drawn.
 */

public class Symbol {

	double[] DEFAULT_COLOR = { 0.7, 0.7, 0.7, 1.0 };

	public int drawPnt3d(Pnt3D pt, GLAutoDrawable drawable) {

		return drawPnt3d(pt, DEFAULT_COLOR, drawable);
	}

	public int drawPnt2D(Pnt3D pt, GLAutoDrawable drawable) {

		return drawPnt2D(pt, this.DEFAULT_COLOR, drawable);
	}

	public int drawLine3D(Ln3D l3d, GLAutoDrawable drawable) {

		return drawLine3D(l3d, this.DEFAULT_COLOR, drawable);
	}

	public int drawLine2D(Ln2D l2d, GLAutoDrawable drawable) {

		return this.drawLine2D(l2d, DEFAULT_COLOR, drawable);
	}

	public int drawStripLine3D(Pnt3D[] pts, GLAutoDrawable drawable) {

		return this.drawStripLine3D(pts, this.DEFAULT_COLOR, drawable);
	}

	public int drawStripLine2D(Pnt2D[] pts, GLAutoDrawable drawable) {

		return this.drawStripLine2D(pts, DEFAULT_COLOR, drawable);
	}

	public int drawLoopLine3D(Pnt3D[] pts, GLAutoDrawable drawable) {

		return this.drawLoopLine3D(pts, DEFAULT_COLOR, drawable);
	}

	public int drawLoopLine2D(Pnt2D[] pts, GLAutoDrawable drawable) {

		return this.drawLoopLine2D(pts, DEFAULT_COLOR, drawable);
	}

	public int drawRec(Rec2D r, GLAutoDrawable drawable) {

		return this.drawRec(r, DEFAULT_COLOR, drawable);
	}

	private int drawRec3D(Rec3D r, GLAutoDrawable drawable) {

		return this.drawRec3D(r, DEFAULT_COLOR, drawable);
	}

	public int drawStripRectangle2D(Pnt3D[] pts, int start, int end,
			GLAutoDrawable drawable) {

		return this.drawStripRectangle2D(pts, DEFAULT_COLOR, start, end,
				drawable);
	}

	public int drawPoly3D(ArrayList<Pnt3D> pts, GLAutoDrawable drawable) {

		return this.drawPoly3D(pts, DEFAULT_COLOR, drawable);
	}

	public int drawPoly2DEx(ArrayList<Pnt3D> pts, double height,
			GLAutoDrawable drawable) {

		return this.drawPoly2DEx(pts, height, DEFAULT_COLOR, drawable);
	}

	public int drawPoly2D(Pnt2D[] pts, GLAutoDrawable drawable) {

		return this.drawPoly2D(pts, DEFAULT_COLOR, drawable);
	}

	public int drawPnt3d(Pnt3D pt, double[] color, GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glColor4d(color[0], color[1], color[2], color[3]);
		gl.glBegin(GL2.GL_POINT);
		gl.glVertex3d(pt.getX(), pt.getY(), pt.getZ());
		gl.glEnd();

		return 1;
	}

	public int drawPnt2D(Pnt3D pt, double[] color, GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();
		gl.glColor4d(color[0], color[1], color[2], color[3]);
		gl.glBegin(GL2.GL_POINT);
		gl.glVertex2d(pt.getX(), pt.getY());
		gl.glEnd();

		return 1;
	}

	public int drawLine3D(Ln3D l3d, double[] color, GLAutoDrawable drawable) {
		Pnt3D[] p = l3d.getPoints();
		GL2 gl = drawable.getGL().getGL2();

		gl.glColor4d(color[0], color[1], color[2], color[3]);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex3d(p[0].getX(), p[0].getY(), p[0].getZ());
		gl.glVertex3d(p[1].getX(), p[1].getY(), p[1].getZ());
		gl.glEnd();

		return 1;
	}

	public int drawLine2D(Ln2D l2d, double[] color, GLAutoDrawable drawable) {
		Pnt2D[] p = l2d.getPoints();
		GL2 gl = drawable.getGL().getGL2();

		gl.glColor4d(color[0], color[1], color[2], color[3]);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(p[0].getX(), p[0].getY());
		gl.glVertex2d(p[1].getX(), p[1].getY());
		gl.glEnd();

		return 1;
	}

	// normal

	public int drawStripLine3D(Pnt3D[] pts, double[] color,
			GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glColor4d(color[0], color[1], color[2], color[3]);
		gl.glBegin(GL2.GL_LINE_STIPPLE);
		for (int i = 0; i < pts.length; i++) {
			gl.glVertex3d(pts[i].getX(), pts[i].getY(), pts[i].getZ());
		}
		gl.glEnd();

		return 1;
	}

	public int drawStripLine2D(Pnt2D[] pts, double[] color,
			GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glColor4d(color[0], color[1], color[2], color[3]);
		gl.glBegin(GL2.GL_LINE_STIPPLE);
		for (int i = 0; i < pts.length; i++) {
			gl.glVertex2d(pts[i].getX(), pts[i].getY());
		}
		gl.glEnd();

		return 1;
	}

	public int drawLoopLine3D(Pnt3D[] pts, double[] color,
			GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glColor4d(color[0], color[1], color[2], color[3]);
		gl.glBegin(GL2.GL_LINE_LOOP);
		for (int i = 0; i < pts.length; i++) {
			gl.glVertex3d(pts[i].getX(), pts[i].getY(), pts[i].getZ());
		}
		gl.glEnd();

		return 1;
	}

	public int drawLoopLine2D(Pnt2D[] pts, double[] color,
			GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glColor4d(color[0], color[1], color[2], color[3]);
		gl.glBegin(GL2.GL_LINE_LOOP);
		for (int i = 0; i < pts.length; i++) {
			gl.glVertex2d(pts[i].getX(), pts[i].getY());
		}
		gl.glEnd();

		return 1;
	}

	public int drawRec(Rec2D r, double[] color, GLAutoDrawable drawable) {

		Rec2D rec = r;
		GL2 gl = drawable.getGL().getGL2();

		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4d(color[0], color[1], color[2], color[3]);

		gl.glVertex2d(rec.getMinX(), rec.getMinY());
		gl.glVertex2d(rec.getMaxX(), rec.getMinY());
		gl.glVertex2d(rec.getMaxX(), rec.getMaxY());
		gl.glVertex2d(rec.getMinX(), rec.getMaxY());
		
		gl.glEnd();

		return 1;
	}
	
	public int drawRec2DEx(Rec3D r, double[] color, GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

		Rec3D rec = r;
		GL2 gl = drawable.getGL().getGL2();

		gl.glColor4d(color[0], color[1], color[2], color[3]);
		// BOTTOM
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(rec.getMinX(), rec.getMinY(), rec.getMinZ());
		gl.glVertex3d(rec.getMaxX(), rec.getMinY(), rec.getMinZ());
		gl.glVertex3d(rec.getMaxX(), rec.getMaxY(), rec.getMinZ());
		gl.glVertex3d(rec.getMinX(), rec.getMaxY(), rec.getMinZ());
		gl.glEnd();

		return 1;

	}

	public int drawRec3D(Rec3D r, double[] color, GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

		Rec3D rec = r;
		GL2 gl = drawable.getGL().getGL2();

		gl.glColor4d(color[0], color[1], color[2], color[3]);
		// BOTTOM
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(rec.getMinX(), rec.getMinY(), rec.getMinZ());
		gl.glVertex3d(rec.getMaxX(), rec.getMinY(), rec.getMinZ());
		gl.glVertex3d(rec.getMaxX(), rec.getMaxY(), rec.getMinZ());
		gl.glVertex3d(rec.getMinX(), rec.getMaxY(), rec.getMinZ());
		gl.glEnd();

		// UP
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(rec.getMinX(), rec.getMinY(), rec.getMaxZ());
		gl.glVertex3d(rec.getMaxX(), rec.getMinY(), rec.getMaxZ());
		gl.glVertex3d(rec.getMaxX(), rec.getMaxY(), rec.getMaxZ());
		gl.glVertex3d(rec.getMinX(), rec.getMaxY(), rec.getMaxZ());
		gl.glEnd();

		// LEFT
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(rec.getMinX(), rec.getMaxY(), rec.getMinZ());
		gl.glVertex3d(rec.getMinX(), rec.getMinY(), rec.getMinZ());
		gl.glVertex3d(rec.getMinX(), rec.getMinY(), rec.getMaxZ());
		gl.glVertex3d(rec.getMinX(), rec.getMaxY(), rec.getMaxZ());
		gl.glEnd();

		// RIGHT
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(rec.getMaxX(), rec.getMinY(), rec.getMinZ());
		gl.glVertex3d(rec.getMaxX(), rec.getMaxY(), rec.getMinZ());
		gl.glVertex3d(rec.getMaxX(), rec.getMaxY(), rec.getMaxZ());
		gl.glVertex3d(rec.getMaxX(), rec.getMinY(), rec.getMaxZ());
		gl.glEnd();

		// FRONT
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(rec.getMinX(), rec.getMinY(), rec.getMinZ());
		gl.glVertex3d(rec.getMaxX(), rec.getMinY(), rec.getMinZ());
		gl.glVertex3d(rec.getMaxX(), rec.getMinY(), rec.getMaxZ());
		gl.glVertex3d(rec.getMinX(), rec.getMinY(), rec.getMaxZ());
		gl.glEnd();

		// BACK
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(rec.getMinX(), rec.getMaxY(), rec.getMinZ());
		gl.glVertex3d(rec.getMaxX(), rec.getMaxY(), rec.getMinZ());
		gl.glVertex3d(rec.getMaxX(), rec.getMaxY(), rec.getMaxZ());
		gl.glVertex3d(rec.getMinX(), rec.getMaxY(), rec.getMaxZ());
		gl.glEnd();

		return 1;

	}

	/*
	 * like matrix
	 */

	public int drawStripRectangle2D(Pnt3D[] pts, double[] color, int start,
			int end, GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

		int i = 0;
		int j = pts.length;

		if (start != -1) {
			i = start;
		}

		if (end != -1) {
			j = end - start;
		}

		if ((j - i + 1) / 2 != 0) {
			return 0;
		}

		gl.glColor4d(color[0], color[1], color[2], color[3]);
		gl.glBegin(GL2.GL_QUAD_STRIP);
		for (i = 0; i < j; i = i + 2) {
			gl.glVertex2d(pts[i].getX(), pts[i].getY());
			gl.glVertex2d(pts[i + 1].getX(), pts[i + 1].getY());
		}
		gl.glEnd();

		return 1;
	}

	public int drawPoly3D(ArrayList<Pnt3D> pts, double[] color,
			GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glColor4d(color[0], color[1], color[2], color[3]);

		gl.glBegin(GL2.GL_TRIANGLE_STRIP);
		for (int i = 0; i < pts.size(); i++) {
			gl.glVertex3d(pts.get(i).getX(), pts.get(i).getY(), pts.get(i)
					.getZ());
		}
		gl.glEnd();

		return 1;
	}

	public int drawPoly3DEx(ArrayList<Pnt3D> pts, double height,
			double[] color, GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		GL2 gl = drawable.getGL().getGL2();

		gl.glColor4d(color[0], color[1], color[2], color[3]);

		gl.glBegin(GL2.GL_POLYGON);
		for (int i = 0; i < pts.size()-1; i++) {
			gl.glVertex3d(pts.get(i).getX(), pts.get(i).getY(), pts.get(i).getZ() + height);
		}
		gl.glEnd();

		return 1;

	}

	public int drawPoly2DEx(ArrayList<Pnt3D> points, double height,
			double[] color, GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

		GL2 gl = drawable.getGL().getGL2();

		gl.glColor4d(color[0], color[1], color[2], color[3]);

		int number = points.size();

		// gl.glBegin(GL2.GL_POLYGON);
		// for(int i = 0; i<number; i++ )
		// {
		// gl.glVertex3d(points.get(i).X, points.get(i).Y, 0);
		// }
		// gl.glEnd();

		for (int i = 0; i < number - 1; i++) {

			double[][] ps = new double[3][3];

			ps[0][0] = points.get(i).getX();
			ps[0][1] = points.get(i).getY();
			ps[0][2] = points.get(i).getZ();

			ps[1][0] = points.get(i + 1).getX();
			ps[1][1] = points.get(i + 1).getY();
			ps[1][2] = points.get(i + 1).getZ();

			ps[2][0] = points.get(i + 1).getX();
			ps[2][1] = points.get(i + 1).getY();
			ps[2][2] = points.get(i + 1).getZ() + height;

			double[] normal = MathEx.calcNormal(ps);

			gl.glNormal3d(normal[0], normal[1], normal[2]); // 设置法线

			gl.glBegin(GL2.GL_POLYGON);

			gl.glVertex3d(points.get(i).getX(), points.get(i).getY(), points
					.get(i).getZ());
			gl.glVertex3d(points.get(i + 1).getX(), points.get(i + 1).getY(),
					points.get(i + 1).getZ());
			gl.glVertex3d(points.get(i + 1).getX(), points.get(i + 1).getY(),
					points.get(i + 1).getZ() + height);
			gl.glVertex3d(points.get(i).getX(), points.get(i).getY(), points
					.get(i).getZ() + height);

			gl.glEnd();

		}

		/*
		 * gl.glBegin(GL2.GL_POLYGON);
		 * 
		 * 
		 * gl.glVertex3d(points.get(number-1).getX(),
		 * points.get(number-1).getY(), points.get(number-1).getZ()+0);
		 * gl.glVertex3d(points.get(0).getX(), points.get(0).getY(),
		 * points.get(0).getZ() + 0); gl.glVertex3d(points.get(0).getX(),
		 * points.get(0).getY(), points.get(0).getZ() + height);
		 * gl.glVertex3d(points.get(number-1).getX(),
		 * points.get(number-1).getY(), points.get(number - 1).getZ() + height);
		 * 
		 * gl.glEnd();
		 */

		// gl.glBegin(GL2.GL_POLYGON);
		// for(int i = 0; i<number; i++ )
		// {
		// gl.glVertex3d(points.get(i).getX(), points.get(i).getY(), height);
		// }
		// gl.glEnd();

		return 1;
	}

	public int drawPoly2D(Pnt2D[] pts, double[] color, GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glColor4d(color[0], color[1], color[2], color[3]);
		gl.glBegin(GL2.GL_POLYGON);
		for (int i = 0; i < pts.length; i++) {
			gl.glVertex2d(pts[i].getX(), pts[i].getY());
		}
		gl.glEnd();

		return 1;
	}

	// ---------------advanced draw----------------------------------//
	// color denotes the value
	/*
	 * input: is the matrix
	 */
	public int drawGrid2D(double[][] grid, int n, GLAutoDrawable drawable) {

		// find the min Value and max Value
		double min = 999999;
		double max = -999999;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] < min) {
					min = grid[i][j];
				}
				if (grid[i][j] > max) {
					max = grid[i][j];
				}
			}
		}

		GL2 gl = drawable.getGL().getGL2();
		gl.glPushMatrix();

		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE);
		// this is a n * n matrix
		double size = 2.0 / n;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Pnt2D point1 = new Pnt2D(i * size - 1.0, j * size - 1.0);
				Pnt2D point2 = new Pnt2D((i + 1) * size - 1.0, (j + 1) * size
						- 1.0);
				Rec2D rec = new Rec2D(point1, point2);

				// double c = (grid[i][j]-min)*1.0/(max - min);
				double c = grid[i][j];
				if (c == 0) {
					continue;
				}

				double[] color = { 0.0f, c, c, 0.7f };
				// gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_LINE);
				this.drawRec(rec, color, drawable);
			}
		}
		gl.glDisable(GL2.GL_BLEND);
		gl.glPopMatrix();
		return 1;
	}

	public void drawGrid2D(double[][][] grid, int n, GLAutoDrawable drawable) {
		// find the min Value and max Value
		double min = 999999;
		double max = -999999;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j][0] < min) {
					min = grid[i][j][0];
				}
				if (grid[i][j][0] > max) {
					max = grid[i][j][0];
				}
			}
		}

		GL2 gl = drawable.getGL().getGL2();
		gl.glPushMatrix();

		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE);
		// this is a n * n matrix
		double size = 2.0 / n;
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Pnt2D point1 = new Pnt2D(i * size - 1.0, j * size - 1.0);
				Pnt2D point2 = new Pnt2D((i + 1) * size - 1.0, (j + 1) * size
						- 1.0);
				Rec2D rec = new Rec2D(point1, point2);

				// double c = (grid[i][j]-min)*1.0/(max - min);
				double c = grid[i][j][2];

				if (c == 0) {
					continue;
				}

				double[] color = { (float) (200 - c) * 1.0 / 255,
						(float) (c * 20.0) * 1.0 / 255, (float) c * 10.0 / 255,
						0.7f };
				// gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_LINE);
				this.drawRec(rec, color, drawable);
			}
		}
		gl.glDisable(GL2.GL_BLEND);
		gl.glPopMatrix();

	}

	// height denotes the value
	public int drawGrid3D(double[][] grid, int n, float depth, float[] color,
			GLAutoDrawable drawable) {

		// this is a n * n matrix
		double size = 2.0 / n;
		// find the min Value and max Value
		double min = 999999;
		double max = -999999;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] < min) {
					min = grid[i][j];
				}
				if (grid[i][j] > max) {
					max = grid[i][j];
				}
			}
		}

		GL2 gl = drawable.getGL().getGL2();

		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE);
		// this is a n * n matrix

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				// depth
				double c = depth * 2 * (grid[i][j] - min) / (max - min) - depth;

				Pnt3D point1 = new Pnt3D(i * size - 1.0, j * size - 1.0, 0);
				Pnt3D point2 = new Pnt3D((i + 1) * size - 1.0, (j + 1) * size
						- 1.0, c);

				Rec3D rec = new Rec3D(point1, point2);

				gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_LINE);
				this.drawRec3D(rec, drawable);
			}
		}
		gl.glDisable(GL2.GL_BLEND);

		return 1;
	}

	public int drawCylinder(Cylinder c, GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE);
		GLU glu = new GLU();
		GLUquadric quadric = glu.gluNewQuadric();
		GLUquadric quadric2 = glu.gluNewQuadric();

		gl.glPushMatrix();
		int slices = 64;
		int stacks = 32;
		int loops = 32;
		float r = 0.5f;
		float g = 0.75f;
		float b = 1.0f;
		float r2 = 0.5f * 0.5f;
		float g2 = 0.5f * 0.75f;
		float b2 = 0.5f * 1.0f;

		gl.glLineWidth(1.2f);
		gl.glPushMatrix();
		gl.glTranslated(c.getCenterX(), c.getCenterY(), c.getCenterZ());
		// gl.glRotatef(-90, 1, 0, 0);

		gl.glColor4f(r, g, b, 0.2f);
		glu.gluQuadricDrawStyle(quadric2, GLU.GLU_FILL);
		glu.gluDisk(quadric2, 0, c.getRadius(), slices, loops);
		gl.glColor4f(r2, g2, b2, 1f);
		glu.gluQuadricDrawStyle(quadric2, GLU.GLU_SILHOUETTE);
		glu.gluDisk(quadric2, 0, c.getRadius(), slices, loops);

		gl.glPushMatrix();
		gl.glTranslated(0, 0, c.getHeight());
		gl.glColor4f(r, g, b, 0.2f);
		glu.gluQuadricDrawStyle(quadric2, GLU.GLU_FILL);
		glu.gluDisk(quadric2, 0, c.getRadius(), slices, loops);
		gl.glColor4f(r2, g2, b2, 1f);
		glu.gluQuadricDrawStyle(quadric2, GLU.GLU_SILHOUETTE);
		glu.gluDisk(quadric2, 0, c.getRadius(), slices, loops);
		gl.glPopMatrix();

		if (true) {
			gl.glColor4f(r, g, b, 0.1f);
			glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
			glu.gluCylinder(quadric, c.getRadius(), c.getRadius(),
					c.getHeight(), slices, stacks);
		}

		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glDisable(GL2.GL_BLEND);

		return 1;
	}

}
