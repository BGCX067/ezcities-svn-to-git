package simulation.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransitLine {
	// transitLine contains transitRoute
	private String id;
	private ArrayList<TransitRoute> routes = new ArrayList<TransitRoute>();
	private Map<String, Integer> routesToIndex = new HashMap<String, Integer>();

	public TransitLine(String id) {
		this.id = id;
	}

	public void addRoutes(TransitRoute route) {
		if (routes.contains(route))
			return;
		routes.add(route);
		routesToIndex.put(route.getId(), routesToIndex.size());
	}

	public String getId() {
		return id;
	}
	
	public Integer getRouteIndex(String routeId){
		return routesToIndex.get(routeId);
	}
	
	public ArrayList<TransitRoute> getRoutes(){
		return routes;
	}
	
	public void printRoutes(){
		System.out.println("Line: "+ id);
		System.out.print("Routes: ");
		for(int i = 0; i < routes.size(); i++)
			System.out.print(routes.get(i).getId() + " ");
		System.out.println();
	}
}
