package data.dataManeger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;

import simulation.obj.Agent;
import simulation.obj.Bounds;
import simulation.obj.Building;
import simulation.obj.LandCell;
import simulation.obj.Edge;
import simulation.obj.Itinerary;
import simulation.obj.LandCover;
import simulation.obj.Node;
import simulation.obj.Stop;
import simulation.obj.Trajectory;
import simulation.obj.TransitLine;
import simulation.obj.TransitRoute;
import simulation.obj.Trip;

import check.tools.MBTools;
import data.FileIO.QueryTrajectory;
import diewald_shapeFile.files.dbf.DBF_Field;
import diewald_shapeFile.files.shp.shapeTypes.ShpPolygon;
import diewald_shapeFile.files.shp.shapeTypes.ShpShape;
import draw.Geometry.Pnt2D;
import draw.Geometry.Pnt3D;
import draw.Geometry.Poly2DEx;

public final class Scene {
	private final ArrayList<Node> nodes = new ArrayList<Node>();
	private final ArrayList<Edge> links = new ArrayList<Edge>();
	
	/*
	 * database queried out trajectories
	 */
	public ArrayList<Trajectory> m_queriedList = new ArrayList<Trajectory>();

	private final ArrayList<Agent> agents = new ArrayList<Agent>();
	private final ArrayList<Itinerary> plans = new ArrayList<Itinerary>();
	// available in the Singapore data
	private final ArrayList<Stop> stops = new ArrayList<Stop>();
	private final ArrayList<TransitLine> lines = new ArrayList<TransitLine>();
	private ArrayList<Trip> trips = new ArrayList<Trip>();

	private final Map<String, Integer> nodeToIndex = new HashMap<String, Integer>();
	private final Map<String, Integer> linkToIndex = new HashMap<String, Integer>();
	private final Map<String, Integer> stopToIndex = new HashMap<String, Integer>();
	private final Map<String, Integer> linesToIndex = new HashMap<String, Integer>();

	public static final Bounds bounds = new Bounds();
	
	/*
	 * this is for the query
	 */
    private QueryTrajectory m_query = null;
    
    /**
     * cellular automata
     */
	public ArrayList<LandCell> cells = new ArrayList<LandCell>();
	
	/*
	 *  shapefile reader - the buildings
	 */
	
	public ArrayList<Building> buildings = new ArrayList<Building>();
	
	public LandCover landcover = new LandCover();

    
	public void addNode(Attributes attributes) {
		// attributes: id, x, y, type
		String id = attributes.getValue("id");
		String x = attributes.getValue("x");
		String y = attributes.getValue("y");

		if (nodeToIndex.get(id) == null) {
			try {
				float fx = Float.parseFloat(x);
				float fy = Float.parseFloat(y);
				nodeToIndex.put(id, nodes.size());
				nodes.add(new Node(fx, fy));
				nodes.get(nodes.size()-1).setid(id);
				
				//we add point to bound each time
				bounds.add(fx, fy, 0);

			} catch (NumberFormatException e) {
				System.out.println("warning: ignoring node " + id
						+ " (invalid position)");
			}
		} else {
			System.out.println("warning: ignoring node " + id
					+ " (already existing)");
		}
		
	}

	
	
	
	public void addEdge(Attributes attributes) {
		String id = attributes.getValue("id");
		String from = attributes.getValue("from");
		String to = attributes.getValue("to");

		if (id == null)
			return;

		if (linkToIndex.get(id) == null) {
			Integer f = nodeToIndex.get(from);
			Integer t = nodeToIndex.get(to);
			if (f != null && t != null) {
				linkToIndex.put(id, links.size());
				links.add(new Edge(f, t));
			} else if (f == null) {
				System.out.println("warning: ignoring link " + id
						+ " (from node does not exist)");
			} else if (t == null) {
				System.out.println("warning: ignoring link " + id
						+ " (to node does not exist)");
			}
		} else {
			System.out.println("warning: ignoring link " + id
					+ " (already existing)");
		}
	}

	public void addStops(Attributes attributes) {
		String id = attributes.getValue("id");
		String x = attributes.getValue("x");
		String y = attributes.getValue("y");
		String linkRefId = attributes.getValue("linkRefId");

		if (linkToIndex.get(id) == null) {
			try {
				Float fx = Float.parseFloat(x);
				Float fy = Float.parseFloat(y);
				stopToIndex.put(id, stopToIndex.size());
				stops.add(new Stop(fx, fy, linkRefId));
				
				bounds.add(fx, fy, 0);

				
			} catch (NumberFormatException e) {
				System.out.println("warning: ignoring stop " + id
						+ " (invalid position)");
			}
		} else {
			System.out.println("warning: ignoring stop " + id
					+ " (already existing)");
		}
	}

	public void addTransitLines(TransitLine line) {
		String id = line.getId();
		MBTools.debug(id, false);
		if (linesToIndex.get(id) == null) {
			linesToIndex.put(id, linesToIndex.size());
			lines.add(line);
		}
	}

	public void addAgent(Agent agent) {
		agents.add(agent);
	}

	public void cleanup() {
	//	nodeToIndex.clear();
	//	linkToIndex.clear();

		// normalize nodes -> [0..1]
		
		System.out.println(bounds.toString());
		for (Node node : nodes) {
			node.setX((float) bounds.normalizeX(node.getX()));
			node.setY((float) bounds.normalizeY(node.getY()));
	//		System.out.println("normalized x,y = " + node.getX() + "  " + node.getY());
		}

		for (Agent agent : agents)
			for (Itinerary plan : agent.getItinerarys()) {
				for (Trip trip : plan.getTrips()) {
					trip.setDepTime((float) bounds.normalizeZ(trip.getDepTime()));
					trip.setArrTime((float) bounds.normalizeZ(trip.getArrTime()));
					cleanupTripBoundingBox(trip);
				}
			}
		
		for(Building build: buildings){
			for(Pnt3D point: build.getFootprint().getPoints()){
				point.setX((float) bounds.normalizeX(point.getX()));
				point.setY((float) bounds.normalizeY(point.getY()));
				point.setZ((float) bounds.normalizeZ(point.getZ()));


			}
		}
		
		
		for(LandCell c: cells){
			
			c.setx((float) bounds.normalizeX(c.getx()));
			c.setx((float) bounds.normalizeY(c.gety()));
//				point.setX((float) bounds.normalizeX(point.getX()));
//				point.setY((float) bounds.normalizeY(point.getY()));
//				point.setZ((float) bounds.normalizeZ(point.getZ()));

		}

		MBTools.debug(bounds.toString(), true);
	}
	
	
	
	
	public void cleanupTripBoundingBox(Trip trip){
		double min_x = bounds.normalizeX(trip.getBoundingBox()
				.getAttributes()[0]);
		double max_x = bounds.normalizeX(trip.getBoundingBox()
				.getAttributes()[1]);
		double min_y = bounds.normalizeY(trip.getBoundingBox()
				.getAttributes()[2]);
		double max_y = bounds.normalizeY(trip.getBoundingBox()
				.getAttributes()[3]);
		double min_t = bounds.normalizeZ(trip.getBoundingBox()
				.getAttributes()[4]);
		double max_t = bounds.normalizeZ(trip.getBoundingBox()
				.getAttributes()[5]);

		trip.getBoundingBox().set(min_x, min_y, min_t, max_x, max_y,
				max_t);
	}

	public static double unNormalizeZ(float z) {
		return bounds.unNormalizeZ(z);
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public ArrayList<Edge> getLinks() {
		return links;
	}

	public ArrayList<Itinerary> getPlans() {
		return plans;
	}

	public ArrayList<TransitLine> getLines() {
		return lines;
	}

	
	
	// Return the agents ArrayList
	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public ArrayList<Stop> getStops() {
		return stops;
	}

	public Integer getNodeIndex(String node) {
		return nodeToIndex.get(node);
	}

	Integer getLinkIndex(String link) {
		return linkToIndex.get(link);
	}

	public Integer getLineIndex(String line) {
		return linesToIndex.get(line);
	}

	Integer getStopIndex(String stop) {
		return stopToIndex.get(stop);
	}

	public Trip getTrip(int id) {
		return trips.get(id);
	}

	public void addTrip(Trip trip) {
		trips.add(trip);
	}

	public Bounds getBound() {
		return bounds;
	}
	
	/*
	 * database
	 */
	
	public void connectdatabase() throws Exception{
		if(this.m_query == null)
		{
			m_query = new QueryTrajectory();
		}
	}
	
	public void queryAll() throws SQLException, Exception{
		
		if(m_queriedList != null){
			m_queriedList = null;
		}
		m_queriedList = m_query.getAll();
		
	}

	public TransitLine getTransitLine(int index){
		return this.lines.get(index);
	}
	
	public ArrayList<TransitRoute> getTransitLine(String id) {
		// TODO Auto-generated method stub
		
		ArrayList<TransitRoute> selectedroute = null;
		
		for (TransitLine line : lines) {
			
			String busid = line.getId();
			String[] ids = busid.split("_");
			
			if(id.equalsIgnoreCase(ids[0]))
			{
				System.out.println(ids[0]);
				
				selectedroute = line.getRoutes();
				
			}
		}
		return selectedroute;
	}

	public ArrayList<Building> getbuilding() {
		// TODO Auto-generated method stub
		return this.buildings;
	}	
	
}
