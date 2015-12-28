package simulation.obj;

import java.util.ArrayList;

import check.tools.MBTools;

public class TransitRoute {
	// TransitRoute is consist of Mode, RouteProfile(linked stops), Route(links
	// in the network file)
	// and Departures

	private String mode;
	private String id;
	private ArrayList<String> stops = new ArrayList<String>();
	private ArrayList<String> links = new ArrayList<String>();

	public TransitRoute(String id) {
		this.id = id;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public void addStop(String stopid) {
		if (stops.contains(stopid))
			return;
		stops.add(stopid);
	}

	public void addLink(String linkId) {
		if (links.contains(linkId))
			return;
		links.add(linkId);
	}

	public String getMode() {
		return mode;
	}

	public String getId() {
		return id;
	}
	
	public ArrayList<String> getStops() {
		
		return stops;
	}

	public ArrayList<String> getInterStops(String start, String end) {
		
		int opposite = 0;
		
		ArrayList<String> interstops = new ArrayList<String>();
		
		ArrayList<String> interstopsopp = new ArrayList<String>();
		
		int sp = 0, ep = 0;
		for (int i = 0; i < stops.size(); i++) {
			if (stops.get(i).equals(start))
				sp = i;
			else if (stops.get(i).equals(end))
				ep = i;
		}
		
		if((ep == 0)&&(sp == 0))
		{
			return interstops;
		}
		
		if(ep < sp){
			int temp = ep;
			ep = sp;
			sp = temp;
			
			opposite = 1;
		}

		MBTools.debug(sp +" "+ep, false);
		if (ep >= sp)
			for (int i = sp; i <= ep; i++)
				interstops.add(stops.get(i));

		else {
			for (int i = ep; i < stops.size(); i++)
				interstops.add(stops.get(i));
			for (int i = 0; i <= sp; i++)
				interstops.add(stops.get(i));
		}
		
		
		if(opposite == 1){
			for (int i = interstops.size(); i>0; i--)
			{
				interstopsopp.add(interstops.get(i-1));
			}
			if(interstopsopp.size() == 1){
		    	int cc = 0;
		    	cc = 1;
		    }
		    return interstopsopp;
		
		}
		
		if(interstops.size() == 1){
	    	int cc = 0;
	    	cc = 1;
	    }
		return interstops;
	}

	public void printStops() {
		System.out.print("Stops: ");
		for (int i = 0; i < stops.size(); i++)
			System.out.print(stops.get(i) + " ");
		System.out.println();
	}

	public void printLinks() {
		System.out.print("Links: ");
		for (int i = 0; i < links.size(); i++)
			System.out.print(links.get(i) + " ");
		System.out.println();
	}
}
