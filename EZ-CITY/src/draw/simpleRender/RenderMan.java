/**
 * Big 2Dos:
 * 	keine wireframe
 * skalieren können
 * normalen berechnen
 * initiale position nach drehung abfangen 
 */
package draw.simpleRender;

import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_LEQUAL;
import static javax.media.opengl.GL.GL_NICEST;
import static javax.media.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;

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
import draw.Geometry.Pnt3D;
import draw.control.MyKeyListener;
import draw.control.MyMouseListener;
import draw.control.MyMouseMotionListener;
import draw.control.MyMouseWheelListener;
import draw.control.ArcBall.*;

public class RenderMan implements GLEventListener {
	private static final long serialVersionUID = 1L;
	/*
	 * how to manage the data
	 */
	private GLCanvas canvas = null;
	/*
	 * all kind of render to specific rendering
	 */

	public TransportRenderer m_transRender = null;
	public CellularMetaRenderer m_cellularRender = null;
	public ActivityRenderer m_tripTreeRender = null;
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

	public Scene m_scene = null;
	public ExcelLoader excel_load = null;
	public RasterpointLoader singapore_load = null;
	public ShapefileLoader shapefile_load = null;
	
	
	public RenderMan(){}
	
	public void setCanvas(GLCanvas glc){
		
		this.canvas = glc;

		this.WINHEIGHT = canvas.getHeight();
		this.WINWIDTH = (WINHEIGHT < canvas.getWidth()) ? WINHEIGHT : canvas
				.getWidth();

		if (arcBall == null) {
			arcBall = new ArcBall(this.WINWIDTH, this.WINHEIGHT);
		}

		canvas.addGLEventListener(this);
		canvas.addKeyListener(new MyKeyListener(this));
		canvas.addMouseListener(new MyMouseListener(this));
		canvas.addMouseMotionListener(new MyMouseMotionListener(this));
		canvas.addMouseWheelListener(new MyMouseWheelListener(this));

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
//
//		if (this.m_scene != null) {
//
//			if (this.excel_load == null) {
//				String path = System.getProperty("user.dir");
//				path += "\\data\\sample.csv";
//				this.excel_load = new ExcelLoader(path);
//
//				this.excel_load.setScene(this.m_scene);
//
//				try {
//					this.excel_load.read();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//		}
//
//		if (this.singapore_load == null) {
//			String path = "\\data\\vectors.xml";
//			this.singapore_load = new RasterpointLoader(path);
//
//			this.singapore_load.setScene(this.m_scene);
//			this.singapore_load.loadArea();
//
//		}
//
//		// load buildings into
//		if (this.shapefile_load == null) {
//			String path = "F:\\cecile_develpment\\workspace\\EZ-CITY\\data\\shapefile";
//			String filename = "Rochor_Buildings_1";
//			shapefile_load = new ShapefileLoader();
//
//			this.shapefile_load.setScene(this.m_scene);
//
//			try {
//				shapefile_load.loadShape(path, filename);
//
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//	}

	public void setupRender() {
		// then set up render

		if (this.m_scene != null) {

	//		this.m_transRender = new TransportRenderer();
	//		this.m_transRender.setScene(m_scene);

			this.m_cellularRender = new CellularMetaRenderer();
			this.m_cellularRender.setScene(m_scene);
			
			
		//	this.m_cellularRender.m_ca.cells = singapore_load.cells;
		//	this.m_cellularRender.m_ca.init();
			
			this.m_cellularRender.m_landcover = m_scene.landcover;

//			this.m_tripTreeRender = new ActivityRenderer();
//			this.m_tripTreeRender.setScene(m_scene);
//			this.m_tripTreeRender.setSpatialTree(excel_load.functionpoints);

//			// for flow
//			this.m_flowVis = new FlowVisRenderer();
//			this.m_flowVis.blindWithCA(this.m_cellularRender.m_ca);

			this.m_buildings = new BuildingRenderer();
			this.m_buildings.setScene(m_scene);
			this.m_buildings.loadData();

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

	public String readFile(File file) {

		StringBuffer fileBuffer = null;
		String fileString = null;
		String line = null;

		try {
			FileReader in = new FileReader(file);
			BufferedReader brd = new BufferedReader(in);
			fileBuffer = new StringBuffer();

			while ((line = brd.readLine()) != null) {
				fileBuffer.append(line).append(
						System.getProperty("line.separator"));
			}

			in.close();
			fileString = fileBuffer.toString();
		} catch (IOException e) {
			return null;
		}
		return fileString;
	}

	/*
	 * push a map layer int othe iniList of the SceneGraph so it can be
	 * initialized by the next simpleRender.display() call
	 */

	/**
	 * Is used to trigger a redrawing of canvas
	 */
	public void triggerCanvasDisplay() {
		this.canvas.display();

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
		
	   gl.glClearDepth(1.0f);      // set clear depth value to farthest

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
	    gl.glEnable(GL2.GL_NORMALIZE);
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

		// Models were drawn in solid colors for color-picking
		if (this.PICKED) {
			this.PICKED = false;
			// here is to add pick operation

			float[] position = this.MouseToWorld(this.getLastPickPoint().x,
					this.getLastPickPoint().y);

			this.m_cellularRender.pick(position);
			
			this.m_buildings.pick(position);

			System.out.println("position: " + position[0] + " " + position[1]);

		}

		// gl.glDisable(GL2.GL_LIGHTING);

		// draw the axes
		plain.drawAxes(gl);

		// draw the transportation
//		m_transRender.update(drawable);
//
//		if (m_transRender.isVisible()) {
//			m_transRender.draw(drawable);
//		}

		// draw the grid
		this.m_cellularRender.update(drawable);

		if (m_cellularRender.isVisible()) {
			m_cellularRender.autoLOD(scaling);
			m_cellularRender.draw(drawable);
		}

//		// drwa the quadtree
//		this.m_tripTreeRender.update(drawable);
//		if (this.m_tripTreeRender.isVisible()) {
//			this.m_tripTreeRender.draw(drawable);
//		}

//		// draw the flows
//		this.m_flowVis.update(drawable);
//		if (this.m_flowVis.isVisible()) {
//			this.m_flowVis.draw(drawable);
//		}

		// draw the flows
		this.m_buildings.update(drawable);
		if (this.m_buildings.isVisible()) {
			this.m_buildings.draw(drawable);
		}

		// draw the SceneGraph
		// sceneGraph.draw(gl, this.PICKED);
		// draw the network

		gl.glPopMatrix(); // NEW: Unapply Dynamic Transform

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
		gl.glOrtho(-1, 1, -1, 1, -10, 10);

		// set new bounds for Arcball
		arcBall.setBounds((float) width, (float) height);

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
	public float[] MouseToWorld(int Mouse_X, int Mouse_Y) {
		GLU glu = new GLU();

		int viewport[] = new int[4];
		double ModelviewMatrix[] = new double[16];
		double ProjectionMatrix[] = new double[16];
		double start[] = new double[3];
		double end[] = new double[3];
		float[] wcoord = new float[9];

		int OGL_Y;

		// Read matrices
		gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
		gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, ModelviewMatrix, 0);
		gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, ProjectionMatrix, 0);

		// turn y-axis
		OGL_Y = viewport[3] - Mouse_Y;
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

		wcoord[0] = (float) start[0];
		wcoord[1] = (float) start[1];
		wcoord[2] = (float) start[2];
		wcoord[3] = (float) end[0];
		wcoord[4] = (float) end[1];
		wcoord[5] = (float) end[2];
		
	    Pnt3D direction = new Pnt3D(end[0] - start[0],end[2] - start[2],end[2] - start[2]);
		
		
	    double coin = (0 - start[2])*1.0f / (end[2]-start[2]);
		
		wcoord[6] = (float) (start[0] + coin * (end[0] - start[0]));
		wcoord[7] = (float) (start[1] + coin * (end[1] - start[1]));
		wcoord[8] = 0;
				

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
