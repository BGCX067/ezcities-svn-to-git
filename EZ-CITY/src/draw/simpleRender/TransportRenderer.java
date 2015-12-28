package draw.simpleRender;

import java.nio.FloatBuffer;
import java.util.ArrayList;


import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.gl2.GLUgl2;

import com.jogamp.opengl.util.gl2.GLUT;

import simulation.obj.Edge;
import simulation.obj.Node;
import check.tools.MBTools;

import data.dataManeger.Scene;
import draw.Geometry.ArrayBufferObject;
import draw.Geometry.Pnt3D;

public class TransportRenderer extends RendererBase {
	
	protected Scene scene = null;

	// keep track of agents and nodes and links
	private ArrayList<Node> nodes;
	private ArrayList<Edge> links;
	
	//the queried 
	
	GLUgl2 glu = new GLUgl2();
	GLUT glut;
	GL2 gl;
	
	// display list for outgrayed nodes
	private int busNodesDisplayList = -1;
	
	//display list for links
	private int busLinksDisplayList = -1;
	
	// fog rendering settings
	float density = 0.1f;
	//for rendering
	// for network
	DefaultNodeRenderer nodeRend = null;
	DefaultLinkRenderer linkRend = null;
	
	public TransportRenderer() {
		// TODO Auto-generated constructor stub
		this.enableVisible();
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	//------------------------since here is the drawable stuff that we have to do---------//

	// called by Renderer
	@Override
	public void update(GLAutoDrawable drawable) {
		this.drawable = drawable;
		gl = drawable.getGL().getGL2();

		if (!m_initialized) {
			init(drawable);
			m_initialized = true;
		}
	}

	// called by renderer
	@Override
	public void drawScene(GLAutoDrawable drawable) {
		
		this.drawable = drawable;
		gl = drawable.getGL().getGL2();
		// TODO Auto-generated method stub
		
	//	enableFog();

		gl.glPushMatrix();
//		gl.glTranslatef(-.7f, -.5f, 0f);

		// FIRST PASS: render all agents in light gray
		gl.glColor4f(0, 1, 0, 0);
		StateManager.enableBlend(drawable);
		StateManager.disableDepthTest(drawable);
        this.renderAgentDisplayList();
        StateManager.disableBlend(drawable);

		gl.glPopMatrix();
		
	}
	
	public void init(GLAutoDrawable drawable) {
		
		if (scene == null) {
			m_initialized = false;
			return;
		}
	
		nodes = scene.getNodes();
		links = scene.getLinks();

		// scene.cleanupSpatialIndex(controller.si);
		// controller.si.initialized = true;

		glut = new GLUT();
		
		createTransDisplayList(drawable);

		//for testing - draw nodes only
	//	nodeRend = new DefaultNodeRenderer(gl, nodes, links);
		linkRend = new DefaultLinkRenderer(gl, nodes, links);
		
		
	}
	
		
	public void drawGrid(GLAutoDrawable drawable) {
		this.drawable = drawable;
		gl = drawable.getGL().getGL2();
		
		StateManager.enableBlend(drawable);
		gl.glColor4f(0.5f, 0.5f, 0.5f, 0.f);
		MBTools.drawGrid( gl, 10.0f, 0.05f);
		gl.glColor4f(1.f, 1.f, 1.f, 0.1f);
		MBTools.drawGrid( gl, 10.0f, 0.05f);
		StateManager.disableBlend(drawable);
	}
	
	public void renderNodes(GLAutoDrawable drawable) {
		this.drawable = drawable;
		gl = drawable.getGL().getGL2();
		
		//gl.glBegin(GL.GL_LINE_STRIP);
		gl.glPointSize(2.0f);
		gl.glColor4f(1, 1, 1, 1);
		gl.glBegin(GL.GL_POINTS);
		for (int i = 0; i < this.nodes.size(); i++) {
			Pnt3D loc = new Pnt3D(this.nodes.get(i).getX(),this.nodes.get(i).getY(),0);
			
			gl.glVertex3d(loc.getX(), loc.getY(), loc.getZ());
		}
		gl.glEnd();
	}
	
	private void renderLinks(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		this.drawable = drawable;
		gl = drawable.getGL().getGL2();
		
		gl.glColor4f(1, 1, 1, 1);
		
		for (int i = 0; i < this.links.size(); i++) {
			
			int from = this.links.get(i).getFrom();
			int to = this.links.get(i).getTo();
			
			Pnt3D loc1 = new Pnt3D(this.nodes.get(from).getX(),this.nodes.get(from).getY(),0);
			Pnt3D loc2 = new Pnt3D(this.nodes.get(to).getX(),nodes.get(to).getY(),0);
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex3d(loc1.getX(), loc1.getY(), loc1.getZ());
			gl.glVertex3d(loc2.getX(), loc2.getY(), loc2.getZ());
			gl.glEnd();
		}
		
	}
	
	
	
	private void createTransDisplayList(GLAutoDrawable drawable) {
		this.drawable = drawable;
		gl = drawable.getGL().getGL2();
		
		busNodesDisplayList = gl.glGenLists(1);
		gl.glNewList(busNodesDisplayList, GL2.GL_COMPILE);

		renderNodes(drawable);
		gl.glEndList();
		
		busLinksDisplayList = gl.glGenLists(1);
		gl.glNewList(busLinksDisplayList, GL2.GL_COMPILE);

		renderLinks(drawable);
		gl.glEndList();
	}
	
	

	private void renderAgentDisplayList() {
		if (busNodesDisplayList < 0) {
			MBTools.debug(
					"TransportScene.renderAgentDisplayList agentDisplayList = "
							+ busNodesDisplayList, true);
			return;
		}
		gl.glCallList(this.busNodesDisplayList);
		
		if (busLinksDisplayList < 0) {
			MBTools.debug(
					"TransportScene.renderAgentDisplayList agentDisplayList = "
							+ busNodesDisplayList, true);
			return;
		}
		gl.glCallList(this.busLinksDisplayList);
	}

	
	class DefaultNodeRenderer {
		
		protected ArrayBufferObject linkBuffer;

		public DefaultNodeRenderer(GL2 gl, ArrayList<Node> nodes,
				ArrayList<Edge> lines) {
			// load links
			FloatBuffer buffer = FloatBuffer.allocate(nodes.size() * 3);
			
			for (Node n : nodes) {
				
				buffer.put( (float)n.getX());
				buffer.put( (float)n.getY());
				buffer.put(0);
			}
			
			linkBuffer = new ArrayBufferObject(gl);
			linkBuffer.load(gl, buffer);
		}

		public void render(GLAutoDrawable drawable) {
			GL2 gl = drawable.getGL().getGL2();
			gl.glPointSize(2.0f);
			linkBuffer.draw(gl, GL2.GL_POINTS);
		}
	}
	
	
	class DefaultLinkRenderer{
		
		protected ArrayBufferObject linkBuffer;

		public DefaultLinkRenderer(GL2 gl, ArrayList<Node> nodes,
				ArrayList<Edge> lines) {
			// load links
			FloatBuffer buffer = FloatBuffer.allocate(links.size() * 2 * 3);
			for (Edge link : links) {
				Node from = nodes.get(link.getFrom());
				Node to = nodes.get(link.getTo());
				buffer.put(from.getX());
				buffer.put(from.getY());
				buffer.put(0);
				buffer.put(to.getX());
				buffer.put(to.getY());
				buffer.put(0);
			}
			linkBuffer = new ArrayBufferObject(gl);
			linkBuffer.load(gl, buffer);
		}

		public void render(GLAutoDrawable drawable) {
			GL2 gl = drawable.getGL().getGL2();
			gl.glPointSize(2.0f);
			linkBuffer.draw(gl, GL2.GL_LINE);
		}
	
	}


	@Override
	void loadData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void drawSelect(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}
}
