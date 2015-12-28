/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.app.vectorfield;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import draw.Geometry.ColorMap;

/**
 *
 * @author michel
 *
 * Renderer that draws velocity vectors as short line segments.
 *
 */
public class HedgehogRenderer extends Renderer {
    
    private static boolean VISIBLE = false;
    
    //Parameters of the Panel
    private static float vec_scale = 1000; // scaling factor = default value
    /*vector field : true for force, false for velocity*/
    private static boolean vectorField = false;
    private static int scalar_col;
    
    public static int nbX = 128;
    public static int nbY = 128;
    public static double[][][] originalValues = new double [128][128][4];
    public static int DIM = 128;

    public HedgehogRenderer(FlowSimulator sim) {
        super(sim);
    }

  

    public void setScale(float s) {
        vec_scale = s;
    }

    public static float getScale() { return vec_scale; }
    
    public static void setVectorField(boolean b) {
        vectorField = b;
    }
    
    public static boolean getVectorField() {
        return vectorField;
    }
    
    public static int getColor(){ return scalar_col; }
    /**
     * @param c , new <b>scalar_col</b> value.
     */
    public static void setColor(int c) { scalar_col = c; }

    /**
     * 
     * @param visible 
     */
    public static void setVisible( boolean visible ){ VISIBLE = visible; }
    /**
     * 
     * @return 
     */
    public static boolean getVisible(){ return VISIBLE; }
    
    public static void setNbX (int x) {nbX = x;}
    public static void setNbY (int y) {nbY = y;}

    //direction_to_color: Set the current color by mapping a direction vector (x,y), using
    //                    the color mapping method 'method'. If method==1, map the vector direction
    //                    using a rainbow colormap. If method==0, simply use the white color
    // (x,y) is the direction vector using the velocity x and y at the point (x,y)
    void direction_to_color(GL2 gl, float x, float y/*, int method*/) {
        //float value = (float) (Math.atan2(y, x) / Math.PI + 1);
        float value = (float)Math.sqrt(x*x+y*y);
        if(!vectorField)
            value=(value-VelocityRenderer.MIN_USER_VELOCITY)/(VelocityRenderer.MAX_USER_VELOCITY-VelocityRenderer.MIN_USER_VELOCITY);
         else
            value=(value-ForceRenderer.MIN_USER_FORCE)/(ForceRenderer.MAX_USER_FORCE-ForceRenderer.MIN_USER_FORCE);
        float[] color = new float[3];
        switch( scalar_col ){
            case(0): //White
                color[0] = color[1] = color[2] = 1;
                break;
            case(1): //grey scale
                ColorMap.blackwhite(value, color, false, false);
                break;
            case(2): //rainbow
                ColorMap.rainbow(value, color, false, false);
                break;
            case(3): //blackbody
                ColorMap.blackbody(value, color, false, false);
                break;
            case(4): //temperature
                ColorMap.temperature(value, color, false, false);
                break;
            case(5): //green to red
                ColorMap.green2red(value, color, false, false);
                break;
        }
        gl.glColor3f(color[0], color[1], color[2]);
    }
    
    /* Function to create the array with the x and y original position and their associated magnitude values */
    public void arrayGeneration(){
        double[] vx = simulator.getVelocityX();
        double[] vy = simulator.getVelocityY();
        double own = getWinWidth() / (double) (DIM);   // Grid cell width
        double ohn = getWinHeight() / (double) (DIM);  // Grid cell heigh
        
        if(!vectorField)
        {
            vx = simulator.getVelocityX();
            vy = simulator.getVelocityY();
        }
        else if (vectorField)
        {
            vx = simulator.getForceX();
            vy = simulator.getForceY();
        }
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                originalValues[i][j][0] = own * i+own;
                originalValues[i][j][1] = ohn * j + ohn;
                int idx = (j * DIM) + i;
                originalValues[i][j][2] = vx[idx];
                originalValues[i][j][3] = vy[idx];
                
            }
        }
        
    }
    
    public static double[] nearestNeighbour(double[][][]originalValues, double x, double y) {
        double[] magnitude = new double [2];
        double newX = 10;
        double newY = 10;
        int finalX = 0;
        int finalY = 0;
       
        for (int i = 0; i < DIM; i++) {
           if(Math.abs(originalValues[i][0][0] - x) < newX )
           {
               newX = Math.abs(originalValues[i][0][0] - x);
               finalX = i;
           }
        }

       for (int i = 0; i < DIM; i++) {
           if(Math.abs(originalValues[0][i][1] - y) < newY )
           {
               newY = Math.abs(originalValues[0][i][1] - y);
              finalY = i;
           }
        }
       
       //For vx
       magnitude[0] = originalValues[finalX][finalY][2];
       //for vy
       magnitude[1] = originalValues[finalX][finalY][3];
         
        return magnitude;
    }

    @Override
    public void visualize(GL2 gl) {
        
        //if the checkbox visible is not checked
//        if (!getVisible()) {
//            // don't do anything when this renderer is not visible
//            return;
//        }
        
        int i, j;
        //DIM = number of vector on each column and on each row
        double/*fftw_real*/ wn = getWinWidth() / (double) nbX;   // Grid cell width
        double/*fftw_real*/ hn = getWinHeight() / (double) nbY;  // Grid cell heigh
        arrayGeneration();

        gl.glBegin(GL2.GL_LINES);
        //for all lines and columns
        for (i = 1; i < DIM+1; i++) {
            for (j = 1; j < DIM+1; j++) {
                    //idx = (j * DIM) + i;
                    double x = i * wn;
                    double y = j * hn;
                    double magnitude[] = nearestNeighbour(originalValues, x, y);
                    //explained before
                    System.out.println("avant probleme : magnitude : "+ magnitude[0] + magnitude[1]);
                    direction_to_color(gl, (float) (double) magnitude[0], (float) (double) magnitude[1] );
                    
                    x -= 1;
                    y -= 1;
                    //Display the vectors on the grid
                    gl.glVertex2d(x,y);
                    //Change the position of the vector (velocity) depending on the vector_scale value 
                    //(default 1000)and also depending on the mouse event.
                    gl.glVertex2d(x + vec_scale * magnitude[0], y + vec_scale * magnitude[1]);
                    //gl.glVertex2d((wn + i * wn) + vec_scale * vx[idx], (hn + j * hn) + vec_scale * vy[idx]);
            }
        }
        gl.glEnd();

    }
}
