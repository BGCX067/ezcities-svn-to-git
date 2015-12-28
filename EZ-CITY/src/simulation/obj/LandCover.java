package simulation.obj;
import java.nio.ByteBuffer;

import com.jogamp.opengl.util.texture.Texture;

public class LandCover {
	
	
	//There is always a four layers thing.....
	
	/* List of textures */
	public int[] textureIndex = null;
	
	public int[] height = null;
	public int[] width = null;
	
	public int[][] red0 = null;
	public int[][] green0 = null;
	public int[][] blue0 = null;
	
	public int[][] red1 = null;
	public int[][] green1 = null;
	public int[][] blue1 = null;
	

	public int[][] red2 = null;
	public int[][] green2 = null;
	public int[][] blue2 = null;
	
	public int[][] red3 = null;
	public int[][] green3 = null;
	public int[][] blue3 = null;
	
	public int[][] red4 = null;
	public int[][] green4 = null;
	public int[][] blue4 = null;
	
	public Texture[] texturees= null;

	public ByteBuffer[] tbuffer = new ByteBuffer[5];
	
	
	public LandCover(){
		
		textureIndex = new int[5];
		height = new int[5];
		width = new int[5];
	}
	
	
	public static class layer {  
    //define a nested inner class in another inner class 
	    
       
    } 

}


