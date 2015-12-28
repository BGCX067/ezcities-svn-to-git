package draw.simpleRender;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.gl2.GLUgl2;

import com.jogamp.opengl.util.gl2.GLUT;


import data.dataManeger.Scene;
import draw.Geometry.Pnt3D;
import draw.Geometry.Rec2D;
import draw.Geometry.Rec3D;
import draw.Geometry.Symbol;



import simulation.app.celluar.QuadTree3D;
import simulation.app.celluar.QuadTreeNode;
import simulation.app.celluar.Visitor;
import simulation.obj.Node;
import simulation.obj.Stay;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays quad tree with points and quadrants using OpenGL
 */
public class ActivityRenderer extends RendererBase  {

    
	GL2 gl;
    
    private QuadTree3D quad;
    private ArrayList<Pnt3D> found;
    private Pnt3D searchFrom = new Pnt3D(-1,-1,-1);
    private Pnt3D searchTo = new Pnt3D(1,1,1);
    private float angle = 0;
    private boolean isUpdatePoint = false;
    
    private Scene scene;
    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<Pnt3D> addedPoints = new ArrayList<Pnt3D>();

    static int QUADRANT_WIDTH = 2;
    
    public ArrayList<Stay> functionpoints = null;
	public float resolution = 0.1f;
    
  
    public ActivityRenderer(){
    	
    }
    
    
    /**
     * Called by the drawable immediately after the OpenGL context is
     * initialized for the first time. Can be used to perform one-time OpenGL
     * initialization such as setup of lights and display lists.
     *
     * @param gLDrawable The GLAutoDrawable object.
     */
    public void init(GLAutoDrawable gLDrawable) {
        if (scene != null) {
        	
        	quad = new QuadTree3D(new QuadTreeNode(0, 0, 0, QUADRANT_WIDTH));	
        
			nodes = scene.getNodes();
			
        	
//        	for(int i = 0 ; i < 4300; i ++){
//				quad.addPoint(new Pnt3D(nodes.get(i).getX(),nodes.get(i).getY(),0));
//				System.out.println("number + " + i);
//			}
        	
        	for(int i = 60 ; i < 600; i ++){
			    
			    Pnt3D p = functionpoints.get(i).getSpatialloc();
			    
			    Pnt3D a = new Pnt3D(p.getX(),p.getY(),p.getZ());
			    a.SetColor(p.getColor()[0], p.getColor()[1], p.getColor()[2]);
				quad.addPoint(a);
				System.out.print(i);
			}
        	
        	
        	
			
			found = quad.search(searchFrom, searchTo);
			
        	m_initialized = false;
			return;
		}
    }
    

    /**
     * Called by the drawable to initiate OpenGL rendering by the client.
     * After all GLEventListeners have been notified of a display event, the
     * drawable will swap its buffers if necessary.
     *
     * @param gLDrawable The GLAutoDrawable object.
     */
    public void drawScene(final GLAutoDrawable gLDrawable) {
        final GL2 gl = (GL2) gLDrawable.getGL();    
        
    //    double[] color = {0.0f, 1.0f, 1.f, 0.7f};
        
        quad.visit(new Visitor() {
        	@Override
            public void visit(QuadTreeNode node) {
        		
        		double[] color = {0.0f, 1.0f, 1.f, 0.2f};
        		
        		color[0] = node.getColor()[0];
        		color[1] = node.getColor()[1];
        		color[2] = node.getColor()[2];
        		
      //  		Rec2D rec = new Rec2D(node.getCenter().getX() - node.getQuadrantWidth()/2, node.getCenter().getY() - node.getQuadrantWidth()/2,node.getCenter().getX() + node.getQuadrantWidth()/2, node.getCenter().getY() + node.getQuadrantWidth()/2);
        		Rec3D rec = new Rec3D(node.getCenter(),node.getQuadrantWidth()/2);
        		
        		if(node.getQuadrantWidth()<resolution){
        			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        		}else{
        			
        			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        		}
        	
        		m_drawtool.drawRec3D(rec, color, gLDrawable);
                
            }

        });

        gl.glFlush();

    }

    /**
     * Highlights in 3D space region that is containing highlighted (this means found)
     * points
     * @param gl
     */
    private void displaySearchBox(GL2 gl) {
    	
        gl.glColor4d(1.0, 1.0, 1.0, 0.7f);

        gl.glBegin(GL2.GL_QUADS);
   //     gl.glPolygonMode(GL2.GL_BACK, GL2.GL_LINE);
        gl.glVertex3d(searchFrom.getX(), searchFrom.getY(), searchFrom.getZ());
        gl.glVertex3d(searchTo.getX(), searchFrom.getY(), searchFrom.getZ());
        gl.glVertex3d(searchTo.getX(), searchFrom.getY(), searchTo.getZ());
        gl.glVertex3d(searchFrom.getX(), searchFrom.getY(), searchTo.getZ());
        gl.glVertex3d(searchFrom.getX(), searchFrom.getY(), searchFrom.getZ());
        gl.glEnd();
        
        
        gl.glBegin(GL2.GL_QUADS);
     //   gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glVertex3d(searchFrom.getX(), searchTo.getY(), searchFrom.getZ());
        gl.glVertex3d(searchTo.getX(),   searchTo.getY(), searchFrom.getZ());
        gl.glVertex3d(searchTo.getX(),   searchTo.getY(), searchTo.getZ());
        gl.glVertex3d(searchFrom.getX(), searchTo.getY(), searchTo.getZ());
        gl.glVertex3d(searchFrom.getX(), searchTo.getY(), searchFrom.getZ());
        gl.glEnd();
        
        gl.glColor4f(0, 1, 1,1);
    }

    @Override
	public void update(GLAutoDrawable drawable) {
		this.drawable = drawable;
		gl = drawable.getGL().getGL2();

		if (!m_initialized) {
			init(drawable);
			m_initialized = true;
		}
		if(!isUpdatePoint){
		  updatePoint();
		}
	}

	private void updatePoint() {
		// TODO Auto-generated method stub
		
		this.isUpdatePoint = true;
	}

	public void triggerUpdateNode(){
		this.isUpdatePoint = true;
	}


	public void setScene(Scene s) {
		// TODO Auto-generated method stub
		this.scene = s;
	}


	public void setSpatialTree(ArrayList<Stay> fs) {
		// TODO Auto-generated method stub
		this.functionpoints = fs;
		
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
