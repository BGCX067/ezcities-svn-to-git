/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FlowVisWindow.java
 *
 * Created on 6-okt-2009, 21:04:04
 */
package draw.simpleRender;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import simulation.app.celluar.MultiAgentCAModel;
import simulation.app.vectorfield.FlowSimulator;
import simulation.app.vectorfield.ForceRenderer;
import simulation.app.vectorfield.HedgehogRenderer;
import simulation.app.vectorfield.VelocityRenderer;


/**
 * 
 * @author chen further developed from michel
 *
 * it is a render which provide basic funtion for vector field
 * composed by another three renders: velocityRenderer, ForceRenderer, and HedgehogRenderer
 * contain multiple Renderers, each performing a certain visualization method.
 * As examples, we provide HedgehogRenderer.
 *
 */
public class FlowVisRenderer extends RendererBase {

    FlowSimulator simulator;      // Single instance of the flow simulator
    static int DIM = 200;         // Fixed size of the simulation grid

    VelocityRenderer velocity;
    ForceRenderer force;
    HedgehogRenderer hedgehog;
    
    MultiAgentCAModel m_ca;
    
    int width = 2;
    int height = 2;
    
    int lmx = 0, lmy = 0;	//remembers last mouse location

    /**
     * Creates new form FlowVisWindow.<br>
     * Application steps:<br>
     * 1) Create the simulator: instance of FlowSimulator class.<br>
     * 4) Create a renderer for displaying the data sets and add its GUI.<br>
     * 5) Create a renderer for displaying vector direction and add its GUI.<br>
     */

    public FlowVisRenderer() {

        // first we need the simulator
    	
        simulator = new FlowSimulator(DIM, width,height);
        // second we need the rendering engine to support the simulator
        velocity = new VelocityRenderer( simulator ); 
        force = new ForceRenderer( simulator);  
        hedgehog = new HedgehogRenderer(simulator);  
      }


	@Override
	void update(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		  simulator.do_one_simulation_step();
	}


	@Override
	void drawScene(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
		GL2 gl = drawable.getGL().getGL2();

	//	velocity.visualize(gl);
	//	force.visualize(p);
		hedgehog.visualize(gl);
		
	}

	
	  /**
     * When the user drags with the mouse, add a force that corresponds to the direction of the mouse
     * cursor movement. Also inject some new matter into the field at the mouse location.
     * @param mx , detected movement of the mouse in x axis.
     * @param my , detected movement of the mouse in y axis.
     */
    void mousedrag(int mx, int my, int winWidth, int winHeight) {
        int xi, yi, X, Y;
        double dx, dy, len;
        int DIM = simulator.getGridSize();
        // Compute the array index that corresponds to the cursor location
        xi = (int) ((double) (DIM + 1) * ((double) mx / (double) winWidth));
        yi = (int) ((double) (DIM + 1) * ((double) my / (double) winHeight));
        // Assign a valid X, Y value (i.e. for cases where the mouse is out of the simulator's window).
        X = xi;
        Y = yi;
        if (X > (DIM - 1)) X = DIM - 1;
        if (Y > (DIM - 1)) Y = DIM - 1;
        if (X < 0) X = 0;
        if (Y < 0) Y = 0;
        // Add force at the cursor location:
        my = winHeight - my;
        // mx = winWidth - mx; --> (just to test, waldo) 
        // Get the difference between the new mouse location and the last mouse location.
        dx = mx - lmx;
        dy = my - lmy;
        // From the difference (dx, dy), get the 10% of its magnitude.
        len = Math.sqrt(dx * dx + dy * dy);
        if (len != 0.0) {
            dx *= 0.1 / len;
            dy *= 0.1 / len;
        }
        // Add force and density
        simulator.addForce(X, Y, dx, dy);
        simulator.setDensity(X, Y, 10.0d);
        lmx = mx;
        lmy = my;
    }


	
	public void blindWithCA(MultiAgentCAModel ca){
		
		this.m_ca = ca;
		
		simulator.blindWithCA(this.m_ca);
		
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
