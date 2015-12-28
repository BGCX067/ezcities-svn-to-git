package simulation.obj;

import java.util.ArrayList;
import java.util.Iterator;

import check.tools.MBTools;
import data.dataManeger.Scene;
import draw.Geometry.Pnt3D;

public class Itinerary {
	
	
	private boolean isPT;

	private ArrayList<Activity> acts = new ArrayList<Activity>();
	private ArrayList<Trip> trips = new ArrayList<Trip>();
	private ArrayList<Integer> actType = new ArrayList<Integer>();

	public boolean isSelected; // Boolean that shows that the plan is or is not
								// selected
	private String agentID; // ID of the Person that carried out this Itinerary

	public Itinerary(String agentID, boolean isSelected) {
		// Add the ID of the Agent implementing this Itinerary
		this.agentID = agentID;
		this.isSelected = isSelected;
	}
	
	public void setPT(boolean isPT){
		this.isPT = isPT;
	}
	
	public boolean isPT(){
		return isPT;
	}

	
	//add trip from the outside
//	public void addType(String type) {
//		String[] split = type.split("_");
//
//		if (split[0].equals("home"))
//			actType.add(HOME);
//		else if (split[0].equals("work"))
//			actType.add(WORK);
//		else if (split[0].equals("education"))
//			actType.add(EDUCATION);
//		else if (split[0].equals("leisure"))
//			actType.add(LEISURE);
//		else if (split[0].equals("shop"))
//			actType.add(SHOP);
//		else
//			actType.add(DEFAULT);
//	}
	
	public ArrayList<Integer> getActType() {
		return actType;
	}

	public void setTripType() {
		for (int i = 0; i < trips.size(); i++) {
			if (i < actType.size() && actType.get(i) == 0)
				trips.get(i).setType(0);
			else if (i + 1 < actType.size() && actType.get(i + 1) == 0)
				trips.get(i).setType(1);
			else
				trips.get(i).setType(2);
		}
	}

	public void setActs() {
		for (int i = 0; i < trips.size() - 1; i++) {
			if (i + 1 < actType.size())
				acts.add(new Activity(trips.get(i + 1).getNodes()[0], trips
						.get(i).getDepTime(), trips.get(i + 1).getArrTime(),
						actType.get(i + 1)));
		}
	}

	public ArrayList<Activity> getActs() {
		return acts;
	}

	public void addTrips(Scene scene, int tdep, int tarr, char[] ch, int start,
			int length) {
		String s = String.copyValueOf(ch, start, length);
		if (s.contains("kti"))
			return;
		ArrayList<String> nodes = new ArrayList<String>();

		MBTools.debug(s, false);
		// Simulation data
		if (!isPT){
			s = s.replaceAll("cl", "");
			s = s.replaceAll("fl", "");
			for (String n : s.split("\\s+")) {
				if (!n.isEmpty())
					nodes.add(n);
			}
		}
		// Singapore Data
		else {
			s = s.replaceAll("\n", "");
			s = s.replaceAll("\t", "");
			s = s.replaceAll(" ", "");
			MBTools.debug("dep time " + tdep + " arr time " + tarr, false);

			String[] n = s.split("===");

			if (n.length == 1)
				return;
			TransitLine line = null;
			if (scene.getLines() != null)
				line = scene.getLines().get(scene.getLineIndex(n[2]));
			TransitRoute route = line.getRoutes().get(line.getRouteIndex(n[3]));

			ArrayList<String> interstops = route.getInterStops(n[1], n[4]);
			for (int i = 0; i < interstops.size(); i++) {
				nodes.add(interstops.get(i));
			}
		}
		
		if (!nodes.isEmpty()) {
			Trip trip = new Trip(scene, tdep, tarr, nodes);
			trip.setPT(isPT);
			trips.add(trip);
		}
	}

	public ArrayList<Trip> getTrips() {
		return trips;
	}

	// Return the ID of the Agent implementing this Plan
	public String getAgentID() {
		return agentID;
	}

	public Pnt3D getPosition(Scene scene, float t) {
		Pnt3D loc = null;
		boolean found = false;
		for (Iterator<Trip> it = trips.iterator(); it.hasNext() && !found;) {
			Trip trip = (Trip) it.next();
			loc = trip.getLocation2(scene, t);
			if (loc != null) {
				found = true;
			}
		}
		return loc;
	}
}
