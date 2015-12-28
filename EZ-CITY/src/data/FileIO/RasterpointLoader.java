package data.FileIO;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import data.dataManeger.Scene;

import simulation.obj.Bounds;
import simulation.obj.LandCell;
import simulation.obj.Edge;
import simulation.obj.Node;

public class RasterpointLoader {
	
	
	private SAXParserFactory parserFactory = SAXParserFactory.newInstance();

	String record = "";
	String lineidstatic = "";
	int startedge = 0;
	Node n = null;

	public Scene scene = null;

	String url = "";
	public Bounds bounds = new Bounds();
    public ArrayList<LandCell> cells = null;
    
    String path = "";

  //  scene.cleanup();

	public RasterpointLoader(String path) {
		this.path = path;
	}

	public void loadArea() {

		try {

			SAXParser parser = parserFactory.newSAXParser();
			parser.parse(path, new DefaultHandler() {

				boolean pointid = false;
				boolean pos = false;
				boolean area = false;
				boolean use = false;
				boolean heigh = false;
			

				String lpointid = null;
				String lpos = null;
				String larea = null;
				String luse = null;
				String lheigh = null;
				

				@Override
				public InputSource resolveEntity(String publicId,
						String systemId) throws org.xml.sax.SAXException,
						java.io.IOException {
					System.out.println("Ignoring: " + publicId + ", "
							+ systemId);
					return new InputSource(new java.io.StringReader(""));
				}

				@Override
				public void startElement(String uri, String localName,
						String name, Attributes attributes) throws SAXException {

					if (name.equals("fme:vectors")) {
						// addEdge(attributes);
						// }
						if (startedge == 1) {
							addPoint();
							startedge = 0;
						} else {

						}
					} else if (name.equals("fme:OBJECTID_12")) {
						this.pointid = true;
					} else if (name.equals("fme:grid_code")) {
						this.area = true;
					} else if (name.equals("fme:lu_desc")) {
						this.use = true;
					} else if (name.equals("fme:gpr")) {
						this.heigh = true;
					} else if(name.equals("gml:pos")){
						this.pos = true;
						
						startedge = 1;
					}
				}

				public void addPoint() {

					if (lpointid == null)
						return;

					
					    LandCell c = new LandCell();
					    c.setid(Integer.parseInt(lpointid));
					    
					    String[] pos = lpos.split(" ");
					    c.setx(Float.parseFloat(pos[0]));
					    c.sety(Float.parseFloat(pos[1]));
					    
					    c.setx((float) (scene.getBound().normalizeX(c.getx())*400f));
					    c.sety((float) (scene.getBound().normalizeY(c.gety())*400f + 100));
					    
					    c.setArea(larea);
					    c.setHeight(lheigh);
					    c.setUse(luse);
					    
					    cells.add(c);
					  
				}

				

		
						
				public void characters(char ch[], int start, int length)
						throws SAXException {

					if (pointid) {
						lpointid = new String(ch, start, length);
						pointid = false;
					}

					if (pos) {
						lpos = new String(ch, start, length);
						pos = false;
					}

					if (area) {
						larea = new String(ch, start, length);
						area = false;
					}

					if (use) {
						luse = new String(ch, start, length);
						use = false;
					}

					if (heigh) {
						lheigh = new String(ch, start, length);
						heigh = false;
					}

				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public void setScene(Scene m_scene) {
		// TODO Auto-generated method stub
		this.scene = m_scene;
		this.cells = this.scene.cells;
		
	}

	
}
