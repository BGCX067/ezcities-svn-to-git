/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.app.vectorfield;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import draw.Geometry.ColorMap;

/**
 * @author adrien
 */
public class ForceRenderer extends Renderer {

    // Original value taken from tests
    public static float MAX_USER_FORCE = 0.25f;
    public static float MIN_USER_FORCE = 0.0f;
    /**
     * 
     */
    private static boolean USE_SATURATION;
    /**
     * 
     */
    private static boolean USE_HUE;
    /**
     * Is the VelocityRenderer visible?
     */
    private static boolean VISIBLE = false;
    /**
     * Amount of levels to use in color banding
     */
    private static int NLEVELS = 127;
    /**
     * Scalar coloring.
     */
    static int scalar_col = 0;
    /**
     * GUI panel for this renderer.
     */
    
    /**
     * Constructor
     * @param flowSimulator 
     */
    public ForceRenderer( FlowSimulator flowSimulator ){
        super( flowSimulator );
        setVisible( false );
    }

    /**
     * @param new value for {@link ForceRenderer#VISIBLE}
     */
    public static void setVisible( boolean visible ){ VISIBLE = visible; }
    /**
     * @return value of {@link ForceRenderer#VISIBLE}
     */
    public static boolean getVisible(){ return VISIBLE; }

    /**
     * @return Current scalar coloring value.
     */
    public int getColoring(){ return scalar_col; }
    /**
     * @param c , new <b>scalar_col</b> value.
     */
    public void setColoring(int c) { scalar_col = c; }
    /**
     * Sets the amount of bands (used to calculate color levels).
     * @param l , amount of levels.
     */
    public void setNLevels(int l){ NLEVELS = l; }
    /**
     * @return Amount of bands
     */
    public int getNLevels(){ return NLEVELS; }
    /**
     * 
     * @return 
     */
    public static boolean withHue(){ return USE_HUE; }
    /**
     * 
     * @param hue 
     */
    public static void useHue( boolean useHue ){ USE_HUE = useHue; }
    /**
     * 
     * @return 
     */
    public static boolean withSaturation(){ return USE_SATURATION; }
    /**
     * 
     * @param saturation 
     */
    public static void useSaturation( boolean useSaturation ){ USE_SATURATION = useSaturation; }

    /**
     * According to the scalar coloring value, the {@code rgb} values are calculated.<br>
     * 1. Switch to the operation which corresponds to the scalar value.<br>
     * 2.a {@link ColorMap#COLOR_BLACKWHITE}.<br>
     * 2.b {@link ColorMap#COLOR_BLACKWHITE_BANDS}.<br>
     * 2.c {@link ColorMap#COLOR_RAINBOW}.<br>
     * 2.d {@link ColorMap#COLOR_RAINBOW_BANDS}.<br>
     * 2.e {@link ColorMap#COLOR_BLACKBODY}.<br>
     * 2.f {@link ColorMap#COLOR_BLACKBODY_BANDS}.<br>
     * 2.g {@link ColorMap#COLOR_TEMPERATURE}.<br>
     * 2.h {@link ColorMap#COLOR_TEMPERATURE_BANDS}.<br>
     * 2.i {@link ColorMap#COLOR_GRADIENT_G2R}.<br>
     * 2.j {@link ColorMap#COLOR_GRADIENT_G2R_BANDS}.<br>
     * 3. Assign RGB color by calling {@code GL.glColor3f(R,G,B)}.
     * <br><br>
     * GL methods used:<br>
     * {@code glColor3f(red, green, blue)}: Specify new red, green, and blue
     * values for the current color.<br>
     * - GLfloat  red.<br>
     * - GLfloat  green.<br>
     * - GLfloat  blue.<br>
     * {@see http://www.opengl.org/sdk/docs/man/xhtml/glColor.xml}<br><br>
     * @param gl , related to OpenGL.
     * @param vy , float value to calculate the color to set.
     */
    private static void set_colormap(GL2 gl, float vy) {
        // Apply the color mapping equation
        if( vy>MAX_USER_FORCE ) vy=MAX_USER_FORCE;
        if( vy<MIN_USER_FORCE ) vy=MIN_USER_FORCE;
        vy=(vy-MIN_USER_FORCE)/(MAX_USER_FORCE-MIN_USER_FORCE);
        float[] rgb = new float[3];
        switch( scalar_col ){
            case ColorMap.COLOR_BLACKWHITE:
                ColorMap.blackwhite(vy, rgb, USE_HUE, USE_SATURATION );
                break;
            case ColorMap.COLOR_BLACKWHITE_BANDS:
                vy *= NLEVELS;
                vy = (int) (vy);
                vy /= NLEVELS;
                ColorMap.blackwhite(vy, rgb, USE_HUE, USE_SATURATION );
                break;
            case ColorMap.COLOR_RAINBOW:
                ColorMap.rainbow(vy, rgb, USE_HUE, USE_SATURATION );
                break;
            case ColorMap.COLOR_RAINBOW_BANDS:
                vy *= NLEVELS;
                vy = (int) (vy);
                vy /= NLEVELS;
                ColorMap.rainbow(vy, rgb, USE_HUE, USE_SATURATION );
                break;
            case ColorMap.COLOR_BLACKBODY:
                ColorMap.blackbody( vy, rgb, USE_HUE, USE_SATURATION );
                break;
            case ColorMap.COLOR_BLACKBODY_BANDS:
                vy *= NLEVELS;
                vy = (int) (vy);
                vy /= NLEVELS;
                ColorMap.blackbody( vy, rgb, USE_HUE, USE_SATURATION );
                break;
            case ColorMap.COLOR_TEMPERATURE:
                ColorMap.temperature(vy, rgb, USE_HUE, USE_SATURATION );
                break;
            case ColorMap.COLOR_TEMPERATURE_BANDS:
                vy *= NLEVELS;
                vy = (int) (vy);
                vy /= NLEVELS;
                ColorMap.temperature( vy, rgb, USE_HUE, USE_SATURATION );
                break;
            case ColorMap.COLOR_GRADIENT_G2R:
                ColorMap.green2red(vy, rgb, USE_HUE, USE_SATURATION);
                break;
            case ColorMap.COLOR_GRADIENT_G2R_BANDS:
                vy *= NLEVELS;
                vy = (int) (vy);
                vy /= NLEVELS;
                ColorMap.green2red( vy, rgb, USE_HUE, USE_SATURATION );
                break;
            default: break;
        }
        gl.glColor3f(rgb[0], rgb[1], rgb[2]);
    }

    /**
     * Velocity visualization.
     * @param gl 
     */
    @Override public void visualize(GL2 gl) {
        if (!getVisible()) return;
        int DIM = simulator.getGridSize();
        // Grid cell width
        double/*fftw_real*/ wn = getWinWidth() / (double) (DIM + 1);
        // Grid cell heigh
        double/*fftw_real*/ hn = getWinHeight() / (double) (DIM + 1);  
        // Get force magnitude from the flow simulator.
        double[] fm = simulator.getForceMagnitude();
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
        
        // Draw smoke algorithm
        double px, py;
        int i, j, idx;
        for( j = 0; j < DIM - 1; j++ ){
            gl.glBegin(GL.GL_TRIANGLE_STRIP);
            i = 0;
            px = wn + (float) i * wn;
            py = hn + (float) j * hn;
            idx = (j * DIM) + i;
            set_colormap(gl, (float)fm[idx]);
            gl.glVertex2d(px, py);
            for( i = 0; i < DIM - 1; i++ ){
                px = wn + i * wn;
                py = hn + (j + 1) * hn;
                idx = ((j + 1) * DIM) + i;
                set_colormap(gl, (float)fm[idx]);
                gl.glVertex2d(px, py);
                px = wn + (i + 1) * wn;
                py = hn + j * hn;
                idx = (j * DIM) + (i + 1);
                set_colormap(gl, (float)fm[idx]);
                gl.glVertex2d(px, py);
            }
            px = wn + (float) (DIM - 1) * wn;
            py = hn + (float) (j + 1) * hn;
            idx = ((j + 1) * DIM) + (DIM - 1);
            set_colormap(gl, (float)fm[idx]);
            gl.glVertex2d(px, py);
            gl.glEnd();
        }
    }
    
}
