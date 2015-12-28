package draw.control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.awt.GLCanvas;
import javax.swing.SwingUtilities;

import draw.simpleRender.RenderMan;

public class MyMouseMotionListener implements MouseMotionListener {
	
	public RenderMan ov;

	public MyMouseMotionListener(RenderMan ov) {
		this.ov = ov;
	}

	public void mouseMoved(MouseEvent arg0) {
	}

	public void mouseDragged(MouseEvent e) {

		GLCanvas can = (GLCanvas) e.getComponent();
		
		
		

		if (SwingUtilities.isLeftMouseButton(e)) {
			ov.drag(e.getPoint());	
			can.display();
		}
		
		if (SwingUtilities.isRightMouseButton(e)) {
			ov.move(e.getPoint());	
			can.display();
		}
	}
}
