package draw.control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.media.opengl.awt.GLCanvas;
import javax.swing.SwingUtilities;

import draw.simpleRender.RenderMan;

public class MyMouseListener implements MouseListener {	
	public RenderMan ov;

	
	public MyMouseListener(RenderMan ov) {
		// TODO Auto-generated method stub		
		this.ov = ov;
	}

	public void mouseClicked(MouseEvent e) {
		GLCanvas can = (GLCanvas) e.getComponent();
		
		/* Dieses Vorgehen erzeugt einen totalen Absturz bzw. Freeze der Applikation
		GLContext context = can.getContext();
		context.makeCurrent();
		GL gl = context.getGL();
		*/ 
		
		if (SwingUtilities.isRightMouseButton(e)) {
		//	ov.reset();			
			can.display();
		} else if (SwingUtilities.isLeftMouseButton(e)) {	
			ov.PICKED = true;
			ov.setLastPickPoint(e.getPoint());
			can.display();
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			System.out.println("panning ist false");
		}
	}

	public void mousePressed(MouseEvent e) {

		if (SwingUtilities.isLeftMouseButton(e)) {
			ov.startDrag(e.getPoint());			
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			ov.startMove(e.getPoint());
		}
	}

}



