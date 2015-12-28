package simulation.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;


import check.tools.MBTools;

public final class TransitSchedule {
	private final ArrayList<Node> nodes = new ArrayList<Node>();
	private final ArrayList<Edge> links = new ArrayList<Edge>();



	private final Map<String, Integer> nodeToIndex = new HashMap<String, Integer>();
	private final Map<String, Integer> linkToIndex = new HashMap<String, Integer>();
	private final Map<String, Integer> stopToIndex = new HashMap<String, Integer>();
	private final Map<String, Integer> linesToIndex = new HashMap<String, Integer>();

	private static final Bounds bounds = new Bounds();

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

	public void cleanup() {
		nodeToIndex.clear();
		linkToIndex.clear();

		// normalize nodes -> [0..1]
		for (Node node : nodes) {
			bounds.add(node.getX(), node.getY(), 0);
		}
		System.out.println(bounds.toString());
		for (Node node : nodes) {
			node.setX((float) bounds.normalizeX(node.getX()));
			node.setY((float) bounds.normalizeY(node.getY()));
		}


		MBTools.debug(bounds.toString(), true);
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


	Integer getNodeIndex(String node) {
		return nodeToIndex.get(node);
	}

	Integer getLinkIndex(String link) {
		return linkToIndex.get(link);
	}

	Integer getLineIndex(String line) {
		return linesToIndex.get(line);
	}

	Integer getStopIndex(String stop) {
		return stopToIndex.get(stop);
	}

	

}
