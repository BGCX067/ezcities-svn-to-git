package simulation.obj;

import java.util.ArrayList;
import java.util.Iterator;
import data.dataManeger.Scene;
import draw.Geometry.Pnt3D;

public class Agent {
	
	public String agentID;
	private ArrayList<Itinerary> itinerary;
	
	private Pnt3D temploc = null;
	
	private String inBuilding = null;
	
    public Agent(){
    }
    
	public Agent(String agentID) {
		this.agentID = agentID;
	}
	
	public void setAgentId(String id){
	   this.agentID = id;	
	}
	
	public String getAgentId(){
		return this.agentID;
	}
	
	public void addItinerary(Itinerary iti) {
		itinerary.add(iti);
	}
	
	public ArrayList<Itinerary> getItinerarys() {
		return itinerary;
	}
	
	public Pnt3D getTemploc() {
		return temploc;
	}

	public void setTemploc(Pnt3D temploc) {
		this.temploc = temploc;
	}
	
	public String getInBuilding() {
		return inBuilding;
	}

	public void setInBuilding(String inBuilding) {
		this.inBuilding = inBuilding;
	}

	//returns the position of the agent at a certain time
	public Pnt3D getPosition(Scene scene, float t) {
		Pnt3D loc = null;
		boolean found = false;
		for (Iterator<Itinerary> it = itinerary.iterator(); it.hasNext() && !found;) {
			Itinerary plan = (Itinerary) it.next();
			loc = plan.getPosition(scene,t);
			if(loc != null){
				found = true;
			}
		}
		
		if(loc!=null){
		return new Pnt3D(loc.getX(),loc.getY(),loc.getZ());
		}
		return null;
	}


	

	
}
