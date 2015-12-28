/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.Geometry;

/**
 * Generic implementation to apply color mapping.
 * @author waldo, adrien
 */
public class ColorMap {
	
	
	//specially defined something for master plan
	
	
	
	
	
    
    private static float saturation = 0.0f;
    private static float hue = 0.0f;
    
    /**
     * Black-and-white color mapping
     */
    public final static int COLOR_BLACKWHITE = 0;
    /**
     * Banded {@link #COLOR_BLACKWHITE}
     */
    public final static int COLOR_BLACKWHITE_BANDS = 1;
    /**
     * Rainbow color mapping
     */
    public final static int COLOR_RAINBOW = 2;
    /**
     * Banded {@link #COLOR_RAINBOW}
     */
    public final static int COLOR_RAINBOW_BANDS = 3;
    /**
     * Black body color mapping
     */
    public final static int COLOR_BLACKBODY = 4;
    /**
     * Banded {@link #COLOR_BLACKBODY}
     */
    public final static int COLOR_BLACKBODY_BANDS = 5;
    /**
     * Temperature color mapping
     */
    public final static int COLOR_TEMPERATURE = 6;
    /**
     * Banded {@link #COLOR_TEMPERATURE}
     */
    public final static int COLOR_TEMPERATURE_BANDS = 7;
    /**
     * Gradient green to red color mapping
     */
    public final static int COLOR_GRADIENT_G2R = 8;
    /**
     * Banded {@link #COLOR_GRADIENT_G2R}
     */
    public final static int COLOR_GRADIENT_G2R_BANDS = 9;
    
    /**
     * 
     * @param s 
     */
    public static void setSaturation(float s){ saturation = s;}
    /**
     * 
     * @return 
     */
    public static float getSaturation(){ return saturation; }
    /**
     * 
     * @param h 
     */
    public static void setHue(float h) {hue = h;}
    /**
     * 
     * @return 
     */
    public static float getHue(){return hue;}
    
    /**
     * Only white color (used for glyphs)
     * @param value
     * @param color
     * @param bolHue
     * @param bolSaturation 
     */
    public static void white( float value, float[] color, boolean bolHue, boolean bolSaturation ){
        color[0] = color[1] = color[2] = 1.0f;
        float[] color_hsv = new float[3];
        color_hsv = setSaturationHue(color, bolHue, bolSaturation);
        color[0] = color_hsv[0];
        color[1] = color_hsv[1];
        color[2] = color_hsv[2];
    }
    
    /**
     * Implements the blackbody color pallete, mapping the scalar 'value' with transfer functions.<br>
     * 1. Clamp the {@code value} to 0.0 or 1.0 in case it exceeds the range [0.0,1.0].<br>
     * 2. Map the value to the transfer functions {@code RGB = value}
     * @param value to color
     * @param color to visualize (RGB).
     * @param  returnHSV
     */
    public static void blackwhite(float value, float[] color, boolean bolHue, boolean bolSaturation ){
        if (value < 0) { value = 0; }
        if (value > 1) { value = 1; }
        color[0] = color[1] = color[2] = value;
        
        float[] color_hsv = new float[3];
        color_hsv = setSaturationHue(color, bolHue, bolSaturation);
        color[0] = color_hsv[0];
        color[1] = color_hsv[1];
        color[2] = color_hsv[2];
    }

    /**
     * Implements the rainbow color palette, mapping the scalar 'value' with transfer functions.<br>
     * 1. Assign 0.8f to a float named {@code dx}.<br>
     * 2. {@code value} is confirmed to be in the range [0,1].<br>
     * 3. {@code value = (6 - 2 * dx) * value + dx}.
     * 4. For each color, return the max value between 0.0f and a mathematical operation. 
     * @param value
     * @param color 
     * @param  returnHSV
     */
    public static void rainbow(float value, float[] color, boolean bolHue, boolean bolSaturation) {
        final float dx = 0.8f;
        if (value < 0) { value = 0; }
        if (value > 1) { value = 1; }
        value = (6 - 2 * dx) * value + dx;
        color[0] = Math.max(0.0f, (3 - Math.abs(value - 4) - Math.abs(value - 5)) / 2);
        color[1] = Math.max(0.0f, (4 - Math.abs(value - 2) - Math.abs(value - 4)) / 2);
        color[2] = Math.max(0.0f, (3 - Math.abs(value - 1) - Math.abs(value - 2)) / 2);
        
        float[] color_hsv = new float[3];
        color_hsv = setSaturationHue(color, bolHue, bolSaturation);
        color[0] = color_hsv[0];
        color[1] = color_hsv[1];
        color[2] = color_hsv[2];
    }

    /**
     * Implements the blackbody color pallete, mapping the scalar 'value' with transfer functions.<br>
     * 1. Clamp the {@code value} to 0.0 or 1.0 in case it exceeds the range [0.0,1.0].<br>
     * 2. Map the value to the transfer functions {@code RGB = 3.0(value) - b}
     * @param value to color
     * @param color to visualize (RGB).
     * @param  returnHSV
     */
    public static void blackbody( float value, float[] color, boolean bolHue, boolean bolSaturation ){
        // clamp the value
        if (value < 0.0f) { value=0.0f; }
        if (value > 1.0f) { value=1.0f; }
        // Slopes for the transfer functions (y = mx + b).
        final float m = 3.0f;
        // R transfer function
        color[0] = Math.min( 1.0f, m*value );
        // G transfer function
        color[1] = Math.max( 0.0f, Math.min( 1.0f, m*value-1.0f ) );
        // B transfer function
        color[2] = Math.max( 0.0f, m*value-2.0f );
        
        float[] color_hsv = new float[3];
        color_hsv = setSaturationHue(color, bolHue, bolSaturation);
        color[0] = color_hsv[0];
        color[1] = color_hsv[1];
        color[2] = color_hsv[2];
        
    }

    /*
     * Red : like the gray scale
     * Green : 0 if the value is [0;0,33] and growing (intensity from 0 to 1) from [0,33;1]
     * Blue : 0 if the value is [0;0,66] and growing (intensity from 0 to 1) from [0,66;1]
     * @param  returnHSV
     */
    public static void temperature(float value, float[] color, boolean bolHue, boolean bolSaturation) {
        // clamp the value
        if (value < 0.0f) { value=0.0f; }
        if (value > 1.0f) { value=1.0f; }
        final float mb=3.0f, mg=1.5f;
        //R transfer function
        color[0] = value;
        //G transfer function
        color[1] = Math.max( 0.0f, mg*value-0.5f);
        // B transfer function
        color[2] = Math.max( 0.0f, mb*value-2.0f );
        
        float[] color_hsv = new float[3];
        color_hsv = setSaturationHue(color, bolHue, bolSaturation);
        color[0] = color_hsv[0];
        color[1] = color_hsv[1];
        color[2] = color_hsv[2];
    }
    
    /**
     * Implements the green to red gradient pallete, mapping the scalar 'value' with transfer functions.<br>
     * 1. Clamp the {@code value} to 0.0 or 1.0 in case it exceeds the range [0.0,1.0].<br>
     * 2. Map the value to the transfer functions:<br>
     * R = 0.78 (value)<br>
     * G = -0.91(value) + 0.95<br>
     * B = 0.16 (value)
     * @see http://www.mathworks.com/matlabcentral/fx_files/31524/1/colorGradient.png
     * @param value
     * @param color
     * @param bolHue
     * @param bolSaturation 
     */
    public static void green2red( float value, float[] color, boolean bolHue, boolean bolSaturation){
        // clamp the value
        if (value < 0.0f) { value=0.0f; }
        if (value > 1.0f) { value=1.0f; }
        final float mr=0.78f, mg=-0.91f, mb=0.16f;
        //R transfer function
        color[0] = value*mr;
        //G transfer function
        color[1] = mg*value+0.95f;
        // B transfer function
        color[2] = mb*value;

        float[] color_hsv = new float[3];
        color_hsv = setSaturationHue(color, bolHue, bolSaturation);
        color[0] = color_hsv[0];
        color[1] = color_hsv[1];
        color[2] = color_hsv[2];
    }

    /**
     * 
     * @param color
     * @param bolHue
     * @param bolSaturation
     * @return 
     */
    public static float[] setSaturationHue(float[] color, boolean bolHue, boolean bolSaturation){
                //setting saturation and/or hue
        float[] color_hsv = new float[3];
        float[] color_tmp = new float[3];
        color_hsv = rgb2hsv(color[0], color[1], color[2]);
        if(bolSaturation)
            color_hsv[1] = saturation/100.0f;
        if(bolHue)
            color_hsv[0] = hue/60.0f;
        else
            color_hsv[0] = color_hsv[0]/60.0f;
        
        color_tmp = HSVtoRGB(color_hsv[0], color_hsv[1], color_hsv[2]);
        color[0] = color_tmp[0];
        color[1] = color_tmp[1];
        color[2] = color_tmp[2];
        return color;
    }
    
    /**
     * Change an RGB color to HSV color. We don't bother converting the alpha
     * as that stays the same regardless of color space.
     *
     * @param r   The r component of the color
     * @param g   The g component of the color
     * @param b   The b component of the color
     * @param hsv An array to return the HSV colour values in
     */
     public static float[] HSVtoRGB(float h, float s, float v){
        // H is given on [0->6] or -1. S and V are given on [0->1].
        // RGB are each returned on [0->1].
        float m, n, f;
        int i;

        float[] hsv = new float[3];
        float[] rgb = new float[3];

        hsv[0] = h;
        hsv[1] = s;
        hsv[2] = v;

        if (hsv[0] == -1)
        {
            rgb[0] = rgb[1] = rgb[2] = hsv[2];
            return rgb;
        }
        i = (int) (Math.floor(hsv[0]));
        f = hsv[0] - i;
        if (i % 2 == 0)
        {
            f = 1 - f; // if i is even
        }
        m = hsv[2] * (1 - hsv[1]);
        n = hsv[2] * (1 - hsv[1] * f);
        switch (i)
        {
            case 6:
            case 0:
                rgb[0] = hsv[2];
                rgb[1] = n;
                rgb[2] = m;
                break;
            case 1:
                rgb[0] = n;
                rgb[1] = hsv[2];
                rgb[2] = m;
                break;
            case 2:
                rgb[0] = m;
                rgb[1] = hsv[2];
                rgb[2] = n;
                break;
            case 3:
                rgb[0] = m;
                rgb[1] = n;
                rgb[2] = hsv[2];
                break;
            case 4:
                rgb[0] = n;
                rgb[1] = m;
                rgb[2] = hsv[2];
                break;
            case 5:
                rgb[0] = hsv[2];
                rgb[1] = m;
                rgb[2] = n;
                break;
        }

        return rgb;

    }
     
     public static float[] rgbtohsv(float r, float g, float b, float[] color)
     {
        float delta;
        float M = Math.max(r, Math.max(g, b));
        float m = Math.min(r, Math.min(g,b));
        
        color[2] = M;
        
        delta = M - m;
        if( M != 0 )
		color[1] = delta / M;
        else {
		// r = g = b = 0		// s = 0, v is undefined
		color[1] = 0;
		color[0] = -1 ;
		return color;
	}
        if( r == M )
		color[0] = ( g - b ) / delta;
        
         
         return color;
     }
     
    /* public static float[] hsv2rgb(float h, float s, float v, float[] color)
     {
         int i;
         float f, p, q, t;
         
         if(s==0)
         {
             color[0] = color[1] = color[2] = v;
             return color;
         }
         
         h = h/60;
         
         return color;
     }*/

     /**
      * 
      * @param r
      * @param g
      * @param b
      * @return 
      */
    private static float[] rgb2hsv(float r, float g, float b){
        float M = Math.max(r, Math.max(g, b));
        float m = Math.min(r, Math.min(g,b));
        
        float[] color = new float[3];
        
        float d = M - m;
        // value of v
        color[2] = M;
        //value of saturation s
        if (M != 0)
            color[1] = d / M;
        //computation of h
        if (color[1]==0)
             color[0] = 0;
        else {
            if (r==M)
                color[0] = (g-b)/d;
            else if (g==M)
                color[0] = 2 + (b-r)/d;
            else
		color[0] = 4 + ( r - g ) / d;
            
            color[0] = color[0] * 60;
            if (color[0]<0)
                color[0] = color[0] + 360;
        }
        return color;
    }

}