package draw.simpleRender;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;


public class SceneRender extends RendererBase{

	public SceneRender(GL2 gl) {
	}

	
	public void drawAxes(GL2 gl){

	
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glDisable(GL2.GL_LIGHTING);
		gl.glLineWidth(1);
		gl.glBegin(GL2.GL_LINES);

			gl.glColor3d(1.0, 0.0, 0.0);
			gl.glVertex3d(0.0, 0.0, 0.0);
			gl.glVertex3d(0.5, 0.0, 0.0);
	
			gl.glColor3d(0.0, 1.0, 0.0);
			gl.glVertex3d(0.0, 0.0, 0.0);
			gl.glVertex3d(0.0, 0.5, 0.0);
	
			gl.glColor3d(0.0, 0.0, 1.0);
			gl.glVertex3d(0.0, 0.0, 0.0);
			gl.glVertex3d(0.0, 0.0, 0.5);

		gl.glEnd();
		gl.glEnable(GL2.GL_LIGHTING);
		
		//glFlush();

	}
	

@Override
void update(GLAutoDrawable drawable) {
	// TODO Auto-generated method stub
	
}

@Override
void drawScene(GLAutoDrawable drawable) {
	// TODO Auto-generated method stub
	GL2 gl = drawable.getGL().getGL2();
	drawAxes(gl);
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
