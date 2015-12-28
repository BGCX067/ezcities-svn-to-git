package draw.simpleRender;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class StateManager {

	int m_drawMethod = 1;  //1-vbo nodes

	int m_textureType = 3; //1-texture 2-material 3-color
	
	int m_transparent = 1; //0-disable 1-enable 2-special- with alpha
	
	int m_cullface = 0; //0-cullback 1-culldisable 2-cullfront
	
	static int m_depth = 1; // 0-disable 1-enable
	
	static int m_blend = 0; //0-disable 1- enable
	
	static int m_pushpop = 0;

	public final static void pushMatrix(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glPushMatrix();
		
		m_pushpop ++;
	}
	
	public final static void popMatrix(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glPopMatrix();
		
		m_pushpop --;
	}

	public final static void disableBlend(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glDisable(GL2.GL_BLEND);
		
		m_blend = 0;
	}

	public final static void enableBlend(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE);
		
		m_blend = 1;

	}

	public static void enableDepthTest(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(GL2.GL_DEPTH_TEST);
		
		m_depth = 1;
	}
	
	public final static void disableDepthTest(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glDisable(GL2.GL_DEPTH_TEST);
		
		m_depth = 1;

	}
	
	
	

	
	
}
