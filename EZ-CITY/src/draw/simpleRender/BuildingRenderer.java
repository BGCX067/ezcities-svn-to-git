package draw.simpleRender;

import java.util.ArrayList;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import data.dataManeger.Scene;
import draw.Geometry.Ln3D;
import draw.Geometry.Pnt3D;

import simulation.obj.Building;

public class BuildingRenderer extends RendererBase {
	
	protected Scene scene = null;
	
	public ArrayList<Building> buildings = null;

	public Building select = null;

	
	GL2 gl;
	
	public BuildingRenderer() {
		// TODO Auto-generated constructor stub
		this.enableVisible();
		m_renderType = Shapefile_RENDERER;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	//------------------------since here is the drawable stuff that we have to do---------//

	// called by Renderer
	@Override
	public void update(GLAutoDrawable drawable) {
		this.drawable = drawable;
		gl = drawable.getGL().getGL2();

		if (!m_initialized) {
			init(drawable);
			m_initialized = true;
		}
	}

	// called by renderer
	@Override
	public void drawScene(GLAutoDrawable drawable) {
		
		this.drawable = drawable;
		gl = drawable.getGL().getGL2();
		// TODO Auto-generated method stub
		
		int n = buildings.size();

		gl.glPushMatrix();

//		// FIRST PASS: render all agents in light gray
//		gl.glColor4f(0, 1, 0, 0);
//		StateManager.enableBlend(drawable);
//		StateManager.disableDepthTest(drawable);
//       // this.renderAgentDisplayList();
//        StateManager.disableBlend(drawable);
		
		double[] color = {0.8,0.8,0.8, 1.0f};

		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_FILL);
		
		StateManager.enableBlend(drawable);
		StateManager.enableDepthTest(drawable);
		
		StateManager.disableBlend(drawable);

		
		
		for(int i = 0 ; i < n; i ++)					
		{
//	        this.m_drawtool.drawPoly3DEx(buildings.get(i).getFootprint().getTriPoints(), 0 ,color, drawable);

		    this.m_drawtool.drawPoly2DEx(buildings.get(i).getFootprint().getPoints(), buildings.get(i).getHeight(), color, drawable);
		    
//	        this.m_drawtool.drawPoly3DEx(buildings.get(i).getFootprint().getTriPoints(),buildings.get(i).getHeight(), color, drawable);

		}
		StateManager.disableDepthTest(drawable);

		gl.glPopMatrix();
		
        StateManager.disableBlend(drawable);
	}
	
	
	
	
	
	public void init(GLAutoDrawable drawable) {
		
		if (scene == null) {
			m_initialized = false;
			return;
		}		
	}
	


	@Override
	void loadData() {
		// TODO Auto-generated method stub
		buildings = scene.getbuilding(); // get the shapefile

		
	}

	public void pick(float[] position) {
		// TODO Auto-generated method stub
		//now we do it in a wrong way
	//	Ln3D line = new Ln3D(position[0],position[1],position[2],position[3],position[4],position[5]);
		
		Pnt3D point = new Pnt3D(position[6],position[7],position[8]);

		for(Building b: buildings){
			if(b.getFootprint().Is_point_in_poly(point.getX(), point.getY()) == 0)
			{
					
					select = b;
					return;
				
			}
		}
		
		
		
//		for(Building b: buildings){
//			if(b.getFootprint().Is_point_in_poly(position[0], position[1]) == 1){
//				select = b;
//				break;
//			}
//			
//		}
	}

	@Override
	void drawSelect(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		if(select != null){
			
			double[] color = {1.0,0.0,0.0,1.0};

			m_drawtool.drawPoly2DEx(select.getFootprint().getPoints(), select.getHeight(), color, drawable);
		}
	}
}
