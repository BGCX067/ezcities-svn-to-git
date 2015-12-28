package draw.simpleRender;

import static javax.media.opengl.GL.GL_LINEAR;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;
import static javax.media.opengl.GL2.GL_QUADS;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.gl2.GLUgl2;
import javax.vecmath.Vector3f;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;

import data.dataManeger.Scene;
import draw.Geometry.Ln3D;
import draw.Geometry.Pnt3D;
import draw.Geometry.Rec3D;
import draw.Geometry.Symbol;
import simulation.app.celluar.CellularMetaModel;
import simulation.app.celluar.MultiAgentCAModel;
import simulation.obj.LandCover;

public class CellularMetaRenderer extends RendererBase {

	protected Scene scene = null;

	public int Lod_level = 4;

	public LandCover m_landcover;

	public MultiAgentCAModel m_ca;

	private Rec3D select = new Rec3D(-0.02, -0.02, 0, 0.02, 0.02, 0);

	// private int texture;

	/* List of textures */
	private int[] textures = new int[5];

	private int index = 0;

	private int m_drawmode = 1; // 0-image_land use, 1 - grid_landuse, 2 -
								// density map 3 - height_map

	// control the size of the cell
	private int m_lodlevel;

	private Symbol m_draw = new Symbol();

	GLUgl2 glu = new GLUgl2();
	GLUT glut;
	GL2 gl;

	// Texture image flips vertically. Shall use TextureCoords class to retrieve
	// the
	// top, bottom, left and right coordinates.
	private float textureTop, textureBottom, textureLeft, textureRight;

	// for rendering

	public CellularMetaRenderer() {
		// TODO Auto-generated constructor stub
		this.m_ca = new MultiAgentCAModel();
		// this.initMatrix();
		this.enableVisible();
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setLandCover(LandCover lc) {
		this.m_landcover = lc;
	}

	public int getLodLevel() {
		return this.m_lodlevel;
	}

	// ----------------------------state of the cells
	// -------------------------------------//

	// ------------------------since here is the drawable stuff that we have to
	// do---------//

	// called by Renderer
	@Override
	public void update(GLAutoDrawable drawable) {
		this.drawable = drawable;
		gl = drawable.getGL().getGL2();

		if (!m_initialized) {
			prepareTexture(drawable);
			// init(drawable);
			m_initialized = true;
		} else {
			// m_ca.update();
		}
	}

	// called by renderer
	@Override
	public void drawScene(GLAutoDrawable drawable) {

		this.drawable = drawable;
		gl = drawable.getGL().getGL2();
		// TODO Auto-generated method stub

		// draw grid
		// gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

		switch (m_drawmode) {
		
		case 0:
			drawImageGrid(drawable);
			break;
		case 1:
			drawGrid(drawable);
			break;
			
		case 3: 
			draw3DGrid(drawable);
			break;

		default:
			drawImageGrid(drawable);
			break;
		}

	}

	public void setDrawMode(int drawMode) {

		this.m_drawmode = drawMode;
	}

	public int getDrawMode() {
		return m_drawmode;
	}
	
	public void drawGrid(GLAutoDrawable drawable) {
		this.drawable = drawable;
		gl = drawable.getGL().getGL2();

		StateManager.pushMatrix(drawable);

		StateManager.enableBlend(drawable);
	
		int row = m_landcover.width[this.Lod_level];
		int col = m_landcover.height[this.Lod_level];
		
		double intervel = 2.0/row;
		
		for(int i = 0 ; i <col; i++){
			for(int j = 0 ; j < row; j++){
				
				Rec3D r= new Rec3D(new Pnt3D(j*intervel - 1, 1- i*intervel ,0), new Pnt3D(j*intervel + intervel - 1, 1- i*intervel - intervel,0.f));
				double[] color = new double[4];
				if(this.Lod_level == 4){
					color[0] = m_landcover.red4[i][j]*1.0/255;
					color[1] = m_landcover.green4[i][j]*1.0/255;
					color[2] = m_landcover.blue4[i][j]*1.0/255;
					color[3] = 0.2f;
				}else if(this.Lod_level ==3){
					color[0] = m_landcover.red3[i][j]*1.0/255;
					color[1] = m_landcover.green3[i][j]*1.0/255;
					color[2] = m_landcover.blue3[i][j]*1.0/255;
					color[3] = 0.2f;
				}else if(this.Lod_level == 2) {
					color[0] = m_landcover.red2[i][j]*1.0/255;
					color[1] = m_landcover.green2[i][j]*1.0/255;
					color[2] = m_landcover.blue2[i][j]*1.0/255;
					color[3] = 0.2f;
				}else if (this.Lod_level == 1){
					color[0] = m_landcover.red1[i][j]*1.0/255;
					color[1] = m_landcover.green1[i][j]*1.0/255;
					color[2] = m_landcover.blue1[i][j]*1.0/255;
					color[3] = 0.2f;
				}else if (this.Lod_level == 0){
					color[0] = m_landcover.red0[i][j]*1.0/255;
					color[1] = m_landcover.green0[i][j]*1.0/255;
					color[2] = m_landcover.blue0[i][j]*1.0/255;
					color[3] = 0.2f;
				}
				
				gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_LINE);	
				m_draw.drawRec2DEx(r, color, drawable);	
			}
		}
		

//		gl.glColor4f(0.5f, 0.5f, 0.5f, 0.2f);
//
//		m_draw.drawGrid2D(this.m_ca.grid, this.m_ca.getDefaultSize(), drawable);
		


		StateManager.disableBlend(drawable);
		
		StateManager.popMatrix(drawable);

	}
	
	public void draw3DGrid(GLAutoDrawable drawable) {
		this.drawable = drawable;
		gl = drawable.getGL().getGL2();

		StateManager.enableBlend(drawable);

		gl.glColor4f(0.5f, 0.5f, 0.5f, 0.4f);

		m_draw.drawGrid2D(this.m_ca.grid, this.m_ca.getDefaultSize(), drawable);

		StateManager.disableBlend(drawable);
	}

	public void drawImageGrid(GLAutoDrawable drawable) {

		gl = drawable.getGL().getGL2();

		gl.glPushMatrix();
		
		gl.glPushAttrib(GL2.GL_COLOR_MATERIAL_FACE);

		gl.glDisable(GL2.GL_COLOR_MATERIAL);

		
		gl.glDisable(GL2.GL_TEXTURE_2D);

		gl.glEnable(GL2.GL_TEXTURE_2D);

		Texture tempt = null;

		if (tempt == null) {

			try {

				File img = new File(
						"F:\\cecile_develpment\\workspace\\EZ-CITY\\data\\raster_32m.bmp");

				tempt = TextureIO.newTexture(img, false);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Use linear filter for texture if image is larger than the original
		// texture
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		// Use linear filter for texture if image is smaller than the original
		// texture
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

		// Texture image flips vertically. Shall use TextureCoords class to
		// retrieve
		// the top, bottom, left and right coordinates, instead of using 0.0f
		// and 1.0f.
		TextureCoords textureCoords = tempt.getImageTexCoords();
		textureTop = textureCoords.top();
		textureBottom = textureCoords.bottom();
		textureLeft = textureCoords.left();
		textureRight = textureCoords.right();

		// Enables this texture's target in the current GL context's state.
		tempt.enable(gl); // same as gl.glEnable(texture.getTarget());
		// gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE,
		// GL.GL_REPLACE);
		// Binds this texture to the current GL context.
		tempt.bind(gl); // same as gl.glBindTexture(texture.getTarget(),
						// texture.getTextureObject());

		gl.glBegin(GL_QUADS);

		// Front Face
		gl.glTexCoord2f(textureLeft, textureBottom);
		gl.glVertex3f(-1.0f, -1.0f, 0.0f); // bottom-left of the texture and
											// quad
		gl.glTexCoord2f(textureRight, textureBottom);
		gl.glVertex3f(1.0f, -1.0f, 0.0f); // bottom-right of the texture and
											// quad
		gl.glTexCoord2f(textureRight, textureTop);
		gl.glVertex3f(1.0f, 1.0f, 0.0f); // top-right of the texture and quad

		gl.glTexCoord2f(textureLeft, textureTop);
		gl.glVertex3f(-1.0f, 1.0f, 0.0f); // top-left of the texture and quad

		tempt.disable(gl); // same as gl.glEnable(texture.getTarget());
		gl.glDisable(GL2.GL_TEXTURE_2D);
		
		gl.glEnable(GL2.GL_COLOR_MATERIAL);

		gl.glPopAttrib();

		gl.glPopMatrix();

	}

	public void prepareTexture(GLAutoDrawable drawable) {

		gl = drawable.getGL().getGL2();
		GLUgl2 glu = new GLUgl2();

		gl.glEnable(GL2.GL_TEXTURE_2D);

		this.textures[0] = genTexture(gl);

		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures[0]);

		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER,
				GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER,
				GL2.GL_LINEAR);

		glu.gluBuild2DMipmaps(textures[0], GL2.GL_RGB8, m_landcover.width[0],
				m_landcover.height[0], GL2.GL_RGB8, GL2.GL_BYTE,
				m_landcover.tbuffer[0]);

		// makeRGBTexture(gl,glu, m_landcover.width[0], m_landcover.height[0],
		// m_landcover.tbuffer[0], textures[0], true );

		this.textures[1] = genTexture(gl);
		// makeRGBTexture(gl,glu, m_landcover.width[1], m_landcover.height[1],
		// m_landcover.tbuffer[1], textures[1], true );
		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures[1]);

		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER,
				GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER,
				GL2.GL_LINEAR);

		int yes = glu.gluBuild2DMipmaps(textures[1], GL2.GL_RGB8,
				m_landcover.width[1], m_landcover.height[1], GL2.GL_RGB8,
				GL2.GL_BYTE, m_landcover.tbuffer[1]);

		System.out.println("yes ----------------------" + yes);

		this.textures[2] = genTexture(gl);
		// makeRGBTexture(gl,glu, m_landcover.width[2], m_landcover.height[2],
		// m_landcover.tbuffer[2], textures[2], true );
		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures[2]);

		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER,
				GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER,
				GL2.GL_LINEAR);
		glu.gluBuild2DMipmaps(textures[2], GL2.GL_RGB8, m_landcover.width[2],
				m_landcover.height[2], GL2.GL_RGB8, GL2.GL_BYTE,
				m_landcover.tbuffer[2]);

		// gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER,
		// GL2.GL_LINEAR);
		// gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER,
		// GL2.GL_LINEAR);
		// gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S,
		// GL2.GL_CLAMP_TO_EDGE);
		// gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T,
		// GL2.GL_CLAMP_TO_EDGE);
		//
		// // no error before this
		// gl.glTexImage2D( textures[2],
		// 0,
		// GL2.GL_RGB8,
		// m_landcover.width[2],
		// m_landcover.height[2],
		// 0,
		// GL2.GL_BGR,
		// GL2.GL_BYTE,
		// m_landcover.tbuffer[2]
		// );

		this.textures[3] = genTexture(gl);
		// makeRGBTexture(gl,glu, m_landcover.width[3], m_landcover.height[3],
		// m_landcover.tbuffer[3], textures[3], true );
		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures[3]);

		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER,
				GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER,
				GL2.GL_LINEAR);
		glu.gluBuild2DMipmaps(textures[3], GL2.GL_RGB8, m_landcover.width[3],
				m_landcover.height[3], GL2.GL_RGB8, GL2.GL_BYTE,
				m_landcover.tbuffer[3]);

		this.textures[4] = genTexture(gl);
		// makeRGBTexture(gl,glu, m_landcover.width[4],m_landcover.height[4],
		// m_landcover.tbuffer[4], textures[4], true );
		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures[4]);
		glu.gluBuild2DMipmaps(textures[4], GL2.GL_RGB8, m_landcover.width[4],
				m_landcover.height[4], GL2.GL_RGB8, GL2.GL_BYTE,
				m_landcover.tbuffer[4]);

		gl.glDisable(GL2.GL_TEXTURE_2D);

	}

	private void makeRGBTexture(GL2 gl, GLU glu, int width, int height,
			Buffer tbuffer, int target, boolean mipmapped) {

		gl.glBindTexture(GL2.GL_TEXTURE_2D, target);

		if (mipmapped) {

			glu.gluBuild2DMipmaps(target, GL2.GL_RGB8, width, height,
					GL2.GL_RGB8, GL2.GL_BYTE, tbuffer);
		} else {

			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER,
					GL2.GL_LINEAR);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER,
					GL2.GL_LINEAR);
			gl.glTexImage2D(target, 0, GL2.GL_RGB, width, height, 0,
					GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE, tbuffer);
		}
	}

	private int genTexture(GL2 gl) {

		final int[] tmp = new int[1];
		// 创建纹理
		gl.glGenTextures(1, tmp, 0);
		return tmp[0];
	}

	@Override
	void loadData() {
		// TODO Auto-generated method stub

	}

	public void init(GLAutoDrawable drawable) {

		if (m_ca == null) {
			m_initialized = false;
			return;
		}

	}


	
	public int pick(float[] position) {
		// TODO Auto-generated method stub
		int N = this.m_ca.getDefaultSize();

		Pnt3D p = new Pnt3D();
		Pnt3D point = new Pnt3D(position[6], position[7], position[8]);
		
		
		int row = m_landcover.width[4];
		int col = m_landcover.height[4];
		
		double intervel = 2.0/row;
		double half_inter = 1.0/row;

		
		
		for(int i = 0 ; i < col; i++){
			for(int j = 0 ; j < row; j++){
				
				p.set(j * intervel + half_inter -1, 1- i * intervel - half_inter , 0);
				
				if (p.distance(point) < 0.01) {
					select.setRec(p.getX() - half_inter, p.getY() - half_inter, 0, p.getX()
							+ half_inter, p.getY() + half_inter, 0);

					return 1;

				}
			}
		}
	

		return 0;
	}

	@Override
	void drawSelect(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		double[] color = { 1.0, 0, 0, 1 };
		gl.glPointSize(2.0f);

		this.m_draw.drawRec2DEx(select, color, drawable);

	}

	public void autoLOD(float scaling) {
		// TODO Auto-generated method stub
		if(scaling <1.5){
			this.Lod_level = 4;
		}else if((scaling >1.5)&&(scaling <=2.5)){
			this.Lod_level = 3;
		}else if((scaling >2.5)&&(scaling <=4.0)){
			this.Lod_level = 2;
		}else if((scaling >4.0)&&(scaling <=6.5)){
			this.Lod_level = 1;
		}else{
			this.Lod_level = 0;
		}
	}

}
