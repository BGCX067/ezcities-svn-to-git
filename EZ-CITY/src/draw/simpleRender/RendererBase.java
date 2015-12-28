package draw.simpleRender;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import draw.Geometry.Symbol;


public abstract class RendererBase{

	protected GLAutoDrawable drawable;
	
	private boolean visible = false;
	
	protected boolean m_initialized = false;
	
	protected Symbol m_drawtool = new Symbol(); // draw tool
	
	private int m_tag; // reserved
	protected int m_renderType; 
		
    public final static int CA_RENDERER = 0;
    public final static int QuadTree_RENDERER = 1;
    public final static int Transport_RENDERER = 2;
    public final static int Shapefile_RENDERER = 2;


	public int getRenderType(){
		return this.m_renderType;
	}
	
	public int getRenderTag(){
		return this.m_tag;
	}
	
	public void setRenderTag(int _tag){
		this.m_tag  = _tag;
	}

	protected void begin(int mode) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glBegin(mode);
	}
	
	protected void end() {
		GL2 gl = drawable.getGL().getGL2();
		gl.glEnd();
	}

	public void enableVisible(){
		this.visible = true;
	}
	
	public void disableVisible(){
		this.visible = false;
	}
	
	public boolean isVisible(){
		return this.visible;
	}
	
	abstract void update(GLAutoDrawable drawable);

	public void draw(GLAutoDrawable drawable){
		drawScene(drawable);
		drawSelect(drawable);
	}
	
    abstract void drawScene(GLAutoDrawable drawable);
	abstract void drawSelect(GLAutoDrawable drawable);
	
	abstract void loadData();
	
}
