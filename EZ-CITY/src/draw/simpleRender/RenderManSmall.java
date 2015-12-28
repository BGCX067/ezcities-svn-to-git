/**
 * Big 2Dos:
 * 	keine wireframe
 * skalieren können
 * normalen berechnen
 * initiale position nach drehung abfangen 
 */
package draw.simpleRender;

import java.awt.*;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.glu.GLU;


import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.StringTokenizer;

import com.jogamp.common.nio.Buffers;

import data.FileIO.ExcelLoader;
import data.FileIO.NetworkLoader;
import data.FileIO.RasterpointLoader;
import data.FileIO.ShapefileLoader;
import data.dataManeger.Scene;
import draw.control.MyKeyListener;
import draw.control.MyMouseListener;
import draw.control.MyMouseMotionListener;
import draw.control.MyMouseWheelListener;
import draw.control.ArcBall.*;

public class RenderManSmall implements GLEventListener {
	private static final long serialVersionUID = 1L;
	/*
	 * how to manage the data
	 */
	private GLCanvas canvas = null;
	/*
	 * all kind of render to specific rendering
	 */
	public Scene m_scene = null;
	public CellularMetaRenderer m_cellularRender = null;
	public FlowVisRenderer m_flowVis = null;
	
	public BuildingRenderer m_buildings = null;

	public float WINWIDTH = 800.0f;
	public float WINHEIGHT = 800.0f;

	/*
	 * RENDERING STATES
	 */
	public boolean highlightMeshHoles = false;
	public boolean PICKED = false;

	private SceneRender plain;

	public float scaling = 1f;
	public float lastScale = -1.0f;

	public int shaderprogram;
	public GL2 gl;

	private Point lastPickPoint = null;

	/*
	 * ARcBALL
	 */
	// for rotate
	private Matrix4f LastRot = new Matrix4f();
	private Matrix4f ThisRot = new Matrix4f();

	// for translation
	private Point LastMove = new Point();
	private Matrix4f ThisMove = new Matrix4f();
	private static float translate_x = 0;
	private static float translate_y = 0;

	private final Object matrixLock = new Object();
	private float[] matrix = new float[16];

	public ArcBall arcBall = null;

	public ExcelLoader excel_load = null;
	public RasterpointLoader singapore_load = null;

	public RenderManSmall(){}
	
	
	public void setCanvas(GLCanvas glc) {

		this.canvas = glc;

		this.WINHEIGHT = canvas.getHeight();
		this.WINWIDTH = (WINHEIGHT < canvas.getWidth()) ? WINHEIGHT : canvas
				.getWidth();

		if (arcBall == null) {
			arcBall = new ArcBall(this.WINWIDTH, this.WINHEIGHT);
		}

		canvas.addGLEventListener(this);
//		canvas.addKeyListener(new MyKeyListener(this));
//		canvas.addMouseListener(new MyMouseListener(this));
//		canvas.addMouseMotionListener(new MyMouseMotionListener(this));
//		canvas.addMouseWheelListener(new MyMouseWheelListener(this));
		
	//	loadData();
		setupRender();

	}


//	public void loadData() {
//
//		NetworkLoader ld = new NetworkLoader();
//		this.m_scene = new Scene();
//
//		this.m_scene = ld.load("F://work//ezcity//trans_data");
//
//		// set up data first
//		if (this.singapore_load == null) {
//			String path = "\\data\\vectors.xml";
//			this.singapore_load = new RasterpointLoader(path);
//
//			this.singapore_load.setScene(this.m_scene);
//			this.singapore_load.loadArea();
//		}
//	}

	public void setupRender() {
		// then set up render

		if (this.m_scene != null) {
				
//			this.m_cellularRender = new CellularMetaRenderer();
//			this.m_cellularRender.setScene(m_scene);
//			this.m_cellularRender.m_ca.cells = singapore_load.cells;
//			this.m_cellularRender.m_ca.init();
//
//			// for flow
//			this.m_flowVis = new FlowVisRenderer();
//			this.m_flowVis.blindWithCA(this.m_cellularRender.m_ca);

		}
	}

	// ######################## ARCBALL METHODS
	// ########################################
	public void reset() {
		synchronized (matrixLock) {
			LastRot.setIdentity(); // Reset Rotation
			ThisRot.setIdentity(); // Reset Rotation
		}

		translate_x = 0;
		translate_y = 0;
	}

	public void startDrag(Point MousePt) {
		synchronized (matrixLock) {
			LastRot.set(ThisRot); // Set Last Static Rotation To Last Dynamic
									// One
		}
		arcBall.click(MousePt); // Update Start Vector And Prepare For Dragging
	}

	public void startMove(Point MousePt) {
		synchronized (matrixLock) {
			LastMove.setLocation(MousePt); // Set Last Static Rotation To Last
											// Dynamic
			// One
		}

	}

	public void drag(Point MousePt) // Perform Motion Updates Here
	{
		Quat4f_t ThisQuat = new Quat4f_t();

		// Update End Vector And Get Rotation As Quaternion
		arcBall.drag(MousePt, ThisQuat);
		synchronized (matrixLock) {
			ThisRot.setRotation(ThisQuat); // Convert Quaternion Into Matrix3fT
			ThisRot.mul(ThisRot, LastRot); // Accumulate Last Rotation Into This
											// One
		}
	}

	public void move(Point MousePt) // Perform Motion Updates Here
	{

		int move_x = (int) (MousePt.getX() - LastMove.getX());
		translate_x += move_x * 1.0f / canvas.getWidth();

		int move_y = (int) (LastMove.getY() - MousePt.getY());
		translate_y += move_y * 1.0f / canvas.getHeight();

		LastMove.setLocation(MousePt);

		this.repaint();
	}

	// ######################## END ARCBALL METHODS
	// ###########################################

	
	/**
	 * Is used to trigger a redrawing of canvas
	 */
	public void triggerCanvasDisplay() {
		this.canvas.display();		

	}

	
	/*
	 * ##################### Picking related methods
	 * #######################################
	 */
	/**
	 * Reads the color of the pixel underneath pickPoint. Is used for
	 * color-picking
	 */
	public void processPick(Point pickPoint) {

		int viewport[] = new int[4];
		ByteBuffer pixel = Buffers.newDirectByteBuffer(3);

		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

		/*
		 * x,y : the bottom left corner width, length: the size of the area to
		 * be read format: The type of data to be read. In here it is assumed to
		 * GL_RGB. type: the data type of the elements of the pixel. In here
		 * we'll use GL_UNSIGNED_BYTE. pixels: An array where the pixels will be
		 * stored. This is the result of the function
		 */
		gl.glReadPixels(pickPoint.x, (viewport[3] - pickPoint.y), 1, 1,
				GL.GL_RGB, GL.GL_UNSIGNED_BYTE, pixel);

		/**
		 * Idea by Henning Tjaden
		 */
		int modelId = 0;
		modelId |= 255 & pixel.get(0);
		modelId |= (255 & pixel.get(1)) << 8;
		modelId |= (255 & pixel.get(2)) << 16;

		// Background color equals 5000268
		if (modelId == 5000268)
			modelId = -1;

		canvas.display();

	}

	/*
	 * ######################### END PICKING
	 * #####################################################
	 */
	public void init(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();
		this.gl = gl;

		// Set background color
		gl.glClearColor(0.F, 0.0F, 0.0F, 0.0F);

		float[] lightPos = { 2000, 3000, 2000, 1 }; // light position
		float[] noAmbient = { 0.2f, 0.2f, 0.2f, 1f }; // low ambient light
		float[] diffuse = { 1f, 1f, 1f, 1f }; // full diffuse colour

		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, noAmbient, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);

		gl.glEnable(GL2.GL_LIGHTING);
		// Switch on light0
		gl.glEnable(GL2.GL_LIGHT0);
		// Enable automatic normalization of normals
		// gl.glEnable(GL2.GL_NORMALIZE);
		// Enable z-Buffer test
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL); // the type of depth test to do

		// best perspective correction
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); // best
																		// perspective
																		// correction

		// Enable color material
		gl.glEnable(GL2.GL_COLOR_MATERIAL);

		// Bottom plain
		this.plain = new SceneRender(gl);

		// scaling factor which is used for scaling the models
		scaling = 1.0f;

		// ArcBall: Start Of User Initialization
		LastRot.setIdentity(); // Reset Rotation
		ThisRot.setIdentity(); // Reset Rotation
		ThisRot.get(matrix);

		gl.glDepthFunc(GL2.GL_LEQUAL); // The Type Of Depth Testing (Less Or
										// Equal)
		gl.glEnable(GL2.GL_DEPTH_TEST); // Enable Depth Testing
		// Set Perspective Calculations To Most Accurate
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

	}

	public void display(GLAutoDrawable drawable) {
		
		GL2 gl = drawable.getGL().getGL2();

		/*
		 * ############# ARCBALL STUFF
		 */
		synchronized (matrixLock) {
			ThisRot.get(matrix);
		}

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL2.GL_MODELVIEW);

		gl.glLoadIdentity();

		// gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT);

		// the shadingData Object holds information about the kind of shading to
		// be applied (Flat|Gouroud)

		gl.glShadeModel(GL2.GL_SMOOTH);

		/*
		 * arcball stuff
		 */

		gl.glPushMatrix(); // NEW: Prepare Dynamic Transform
		gl.glMultMatrixf(matrix, 0); // NEW: Apply Dynamic Transform

		gl.glScalef(scaling, scaling, scaling);
		gl.glTranslatef(translate_x, translate_y, 0);

		// gl.glDisable(GL2.GL_LIGHTING);

		// draw the axes
		plain.drawAxes(gl);


//		// draw the grid
//		this.m_cellularRender.update(drawable);
//
//		if (m_cellularRender.isVisible()) {
//			m_cellularRender.draw(drawable);
//		}
//
//	
//		// draw the flows
//		this.m_flowVis.update(drawable);
//		if (this.m_flowVis.isVisible()) {
//			this.m_flowVis.draw(drawable);
//		}

		// draw the SceneGraph
		// sceneGraph.draw(gl, this.PICKED);
		// draw the network

		gl.glPopMatrix(); // NEW: Unapply Dynamic Transform

		// Models were drawn in solid colors for color-picking
		if (this.PICKED) {
			this.PICKED = false;
			// Determine (by color) which model was picked
			processPick(getLastPickPoint());
			System.out.println("Point picked: " + getLastPickPoint().x + " "
					+ getLastPickPoint().y);
		}

		gl.glFlush(); // Flush The GL Rendering Pipeline
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		

		GL2 gl = drawable.getGL().getGL2();
		int size = width < height ? width : height;
		int xbeg = width < height ? 0 : (width - height) / 2;
		int ybeg = width < height ? (height - width) / 2 : 0;

		gl.glViewport(xbeg, ybeg, size, size);

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(-1, 1, -1, 1, -1, 1);

		// set new bounds for Arcball
		arcBall.setBounds((float) width, (float) height);
		
//		//the flow vis should be reshape
//		this.m_flowVis.width = width;
//		this.m_flowVis.height = height;

	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {

	}

	/**
	 * MAps Mouse coordinates to world coordinates. Is not yet used in program,
	 * but works.
	 * 
	 * @param Mouse_X
	 * @param Mouse_Y
	 * @return World coordinates as double[]- first three start, last three end
	 */
	public double[] MouseToWorld(int Mouse_X, int Mouse_Y) {
		GLU glu = new GLU();

		int viewport[] = new int[4];
		double ModelviewMatrix[] = new double[16];
		double ProjectionMatrix[] = new double[16];
		double start[] = new double[3];
		double end[] = new double[3];
		double wcoord[] = new double[6];

		int OGL_Y;

		// Read matrices
		gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
		gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, ModelviewMatrix, 0);
		gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, ProjectionMatrix, 0);

		// turn y-axis
		OGL_Y = viewport[3] - Mouse_Y - 1;
		FloatBuffer tiefe = Buffers.newDirectFloatBuffer(4);
		;
		tiefe.put(0.0f);

		gl.glReadPixels(Mouse_X, OGL_Y, 1, 1, GL2.GL_DEPTH_COMPONENT,
				GL2.GL_FLOAT, tiefe);

		glu.gluUnProject((double) Mouse_X, (double) OGL_Y, 0.0,
				ModelviewMatrix, 0, ProjectionMatrix, 0, viewport, 0, start, 0);

		glu.gluUnProject((double) Mouse_X, (double) OGL_Y, 1.0,
				ModelviewMatrix, 0, ProjectionMatrix, 0, viewport, 0, end, 0);

		System.out.println("World coords at z=0.0 are ( " //
				+ start[0] + ", " + start[1] + ", " + start[2] + ")");

		wcoord[0] = start[0];
		wcoord[1] = start[1];
		wcoord[2] = start[2];
		wcoord[3] = end[0];
		wcoord[4] = end[1];
		wcoord[5] = end[2];

		return wcoord;

	}

	/**
	 * Sets the last picked Point. Is set in MouseListener class by MouseClick
	 * Event.
	 * 
	 * @param lastPickPoint
	 */
	public void setLastPickPoint(Point lastPickPoint) {
		this.lastPickPoint = lastPickPoint;
	}

	public Point getLastPickPoint() {
		return lastPickPoint;
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	public void repaint() {
		// TODO Auto-generated method stub
		this.triggerCanvasDisplay();
	}

}
