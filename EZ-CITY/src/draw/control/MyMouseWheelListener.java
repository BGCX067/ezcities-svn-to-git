package draw.control;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.media.opengl.awt.GLCanvas;

import draw.simpleRender.RenderMan;


public class MyMouseWheelListener implements MouseWheelListener {

	private RenderMan ov = null;

	public MyMouseWheelListener(RenderMan ov) {
		this.ov = ov;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		if (e.getWheelRotation() < 0) {
			ov.scaling -= 0.1;
		} else {
			ov.scaling += 0.1;
		}
		GLCanvas can = (GLCanvas) (e.getComponent());
		can.display();
		
		System.out.println("scalling  :" + ov.scaling);
	}

}
