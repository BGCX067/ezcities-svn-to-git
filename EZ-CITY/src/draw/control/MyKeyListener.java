package draw.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.opengl.awt.GLCanvas;

import draw.simpleRender.RenderMan;

public class MyKeyListener implements KeyListener {

	
	private RenderMan ov;

	public MyKeyListener(RenderMan ov) {
		this.ov = ov;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		GLCanvas can = (GLCanvas) e.getComponent();

		switch (c) {
		case java.awt.event.KeyEvent.VK_Q:
			System.exit(0);
			break;
		case java.awt.event.KeyEvent.VK_BACK_SPACE:
			ov.scaling = 1.0f;
			break;
			
	    // just temporally set it like this
		case java.awt.event.KeyEvent.VK_1:
			ov.m_tripTreeRender.resolution = ov.m_tripTreeRender.resolution * 2.0f;
			System.out.print(ov.m_tripTreeRender.resolution);

			break;
			
		case java.awt.event.KeyEvent.VK_2:
			ov.m_tripTreeRender.resolution = ov.m_tripTreeRender.resolution * 0.5f;
			break;
			
		default:
			System.out.print("key typed - default\nKey: " + e.getKeyCode());
			break;
		}
		can.display();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO implement		
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
