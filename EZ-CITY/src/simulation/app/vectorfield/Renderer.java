/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.app.vectorfield;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 * @author michel
 * Abstract class for a renderer. A new renderer should be made by extending
 * this class and implementing the {@link visualize()} method. The new renderer should
 * be added to the visualization in the {@link FlowVisApplication} class.
 */
public abstract class Renderer {

    private int winWidth, winHeight;
    FlowSimulator simulator;

    /**
     * Constructor.
     * @param flow simulator
     */
    public Renderer(FlowSimulator sim) {
        simulator = sim;  
        winWidth = sim.winWidth;
        winHeight = sim.winHeight;
    }

    /**
     * Sets the width of the Renderer.
     * @param width
     */
    public void setWinWidth(int w) { winWidth = w; }
    /**
     * @return the width of the renderer.
     */
    public int getWinWidth() { return winWidth; }
    /**
     * Sets the height of the Renderer.
     * @param height 
     */
    public void setWinHeight(int h) { winHeight = h; }
    /**
     * @return the height of the renderer.
     */
    public int getWinHeight() { return winHeight; }

    /**
     * Method which should be implemented by any extended class.
     * @param gl 
     */
    public abstract void visualize(GL2 gl);

}
