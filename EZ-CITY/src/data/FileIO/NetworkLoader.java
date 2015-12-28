package data.FileIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import simulation.obj.TransitLine;
import simulation.obj.TransitRoute;

import check.tools.MBTools;
import data.dataManeger.Scene;


public class NetworkLoader {
	private SAXParserFactory parserFactory = SAXParserFactory.newInstance();
	
	String record = "";
	String lineidstatic = "";

	// General: Agent > Itinerary > Trips > Path
	// MatSim: Person > Plans > Routes > Nodes

	public Scene load(String basePath) {
		Scene scene = new Scene();

	//	loadNetwork(scene, basePath + "/output_network.xml");
		loadTransitSchedule(scene, basePath + "/transitSchedule.xml");
		// load facilities etc...

		scene.cleanup();

//		System.out.println("info: loaded network (" + scene.getNodes().size()
//				+ " nodes, " + scene.getLinks().size() + " links, "
//				+ scene.getAgents().size() + " agents)");

		return scene;
	}

	
	private void loadNetwork(final Scene scene, String path) {
		try {
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse(path, new DefaultHandler() {
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
					if (name.equals("node")) {
						scene.addNode(attributes);
					} else if (name.equals("link")) {
						scene.addEdge(attributes);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void loadTransitSchedule(final Scene scene, String path) {
		try {
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse(path, new DefaultHandler() {
				@Override
				public InputSource resolveEntity(String publicId,
						String systemId) throws org.xml.sax.SAXException,
						java.io.IOException {
					System.out.println("Ignoring: " + publicId + ", "
							+ systemId);
					return new InputSource(new java.io.StringReader(""));
				}

				TransitLine line = null;
				TransitRoute route = null;

				public void startElement(String uri, String localName,
						String name, Attributes attributes) throws SAXException {
					if (name.equals("stopFacility"))
						scene.addNode(attributes);
					if (name.equals("transitLine"))
						line = new TransitLine(attributes.getValue("id"));

					if (name.equals("transitRoute")) {
						MBTools.debug("route " + attributes.getValue("id"),
								false);
						route = new TransitRoute(attributes.getValue("id"));
					}

					if (name.equals("stop")) {
						String stopid = attributes.getValue("refId");
						if (route != null)
							route.addStop(stopid);
					}

					if (name.equals("link")) {
						String refid = attributes.getValue("refId");
						if (route != null)
							route.addLink(refid);
					}
				}

				public void endElement(String uri, String localName, String name)
						throws SAXException {
					if (name.equals("transitRoute"))
						if (line != null && route != null)
							line.addRoutes(route);

					if (name.equals("transitLine"))
						if (line != null)
							scene.addTransitLines(line);
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void modifyTxt(String path) {
		File f = new File("F:\\t1.txt");
		if(f.exists()){
			System.out.print("yes");
		}
		
		
		
	}

	public void writeTxt(String path) throws IOException {
		
		File f = new File("F:\\buslocation.txt");
		if (f.exists()) {
			System.out.print("yes");
		} else {
			System.out.print("wrong");
			f.createNewFile();// �?存在则创建
		}
		try {
			
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse(path, new DefaultHandler() {
				@Override
				public InputSource resolveEntity(String publicId,
						String systemId) throws org.xml.sax.SAXException,
						java.io.IOException {
					System.out.println("Ignoring: " + publicId + ", "
							+ systemId);
					return new InputSource(new java.io.StringReader(""));
				}


				public void startElement(String uri, String localName,
						String name, Attributes attributes) throws SAXException {
					if (name.equals("stopFacility")){
						
					   String id = attributes.getValue("id");
					   String x = attributes.getValue("x");
					   String y = attributes.getValue("y");
					   
					   record += id + "," + x +"," + y + "\r\n";
					   
					}
				}

				public void endElement(String uri, String localName, String name)
						throws SAXException {
					
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 BufferedWriter output = new BufferedWriter(new FileWriter(f));
		 output.write(record);
		 output.close();
	}
	
public void writeRoutes(String path) throws IOException {
		
		File f = new File("F:\\123.txt");
		if (f.exists()) {
			System.out.print("yes");
		} else {
			System.out.print("wrong");
			f.createNewFile();// �?存在则创建
		}
		try {
			SAXParser parser = parserFactory.newSAXParser();
			parser.parse(path, new DefaultHandler() {
				@Override
				public InputSource resolveEntity(String publicId,
						String systemId) throws org.xml.sax.SAXException,
						java.io.IOException {
					System.out.println("Ignoring: " + publicId + ", "
							+ systemId);
					return new InputSource(new java.io.StringReader(""));
				}


				public void startElement(String uri, String localName,
						String name, Attributes attributes) throws SAXException {
					
				
					
					if (name.equals("transitLine")){
				//   	line = new TransitLine(attributes.getValue("id"));
					  
					    String lineid = attributes.getValue("id");
					    lineidstatic = lineid;
					}
					if (name.equals("transitRoute")) {
						MBTools.debug("route " + attributes.getValue("id"),
								false);
				//		route = new TransitRoute(attributes.getValue("id"));
						
						String routeid = attributes.getValue("id");
						record += "\r\n" + lineidstatic + "," + routeid;
						
					
					}

					if (name.equals("stop")) {
						String stopid = attributes.getValue("refId");
				         //if (route != null)
					//		route.addStop(stopid);
						    record += "," + stopid;
					}

				}

				public void endElement(String uri, String localName, String name)
						throws SAXException {
					
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 BufferedWriter output = new BufferedWriter(new FileWriter(f));
		 output.write(record);
		 output.close();
	}


	public static int getTime(String time) {
		int h = Integer.parseInt(time.substring(0, 2));
		int m = Integer.parseInt(time.substring(3, 5));
		int s = Integer.parseInt(time.substring(6));
		int timeInSec = 3600 * h + 60 * m + s;
		if (timeInSec > 3600 * 24)
			timeInSec -= 3600 * 24;
		return timeInSec;
	}
}
