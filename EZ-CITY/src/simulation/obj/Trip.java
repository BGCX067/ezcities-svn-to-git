package simulation.obj;

import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.vecmath.Vector3f;

import check.tools.MBTools;
import data.dataManeger.Scene;
import draw.Geometry.BndBox;
import draw.Geometry.Pnt3D;

public class Trip{
	Scene scene;
	private String tripid;
	private int[] nodes;
	private float tdep;
	private float tarr;
	private float dis; // distance of the trip
	private float fare; // payment of the trip
	private Node depNode;
	private Node arrNode;
	private ArrayList<Node> path = new ArrayList<Node>();
	private BndBox bb = new BndBox();
	private int tripType; // 0 for fromHome
							// 1 for toHome
							// 2 for others

	private boolean movingFurther;
	private boolean isPT;

	// 20110905 CSC: added flag for cylinder containment
	// not very nice but lightweight for now.
	boolean containmentFlag = false;

	//each trip has unique id in the array list of scene
	private String tripId;
	private String transitlineid;
	private int busid;
	

	public Trip(){
		
	}
	
	public Trip(Scene scene){
		this.scene = scene;
	}
	
	
	
	public Trip(Scene scene, float tdep, float tarr, ArrayList<String> nodes) {
		this.scene = scene;
		this.tdep = tdep;
		this.tarr = tarr;
		this.nodes = new int[nodes.size()];

		for (int i = 0; i < nodes.size(); ++i) {
			MBTools.debug(nodes.get(i), false);
			this.nodes[i] = scene.getNodeIndex(nodes.get(i));
		}

		depNode = new Node(scene.getNodes().get(this.nodes[0]).getX(), scene
				.getNodes().get(this.nodes[0]).getY());

		arrNode = new Node(scene.getNodes().get(this.nodes[nodes.size() - 1])
				.getX(), scene.getNodes().get(this.nodes[nodes.size() - 1])
				.getY());
	}

	public Pnt3D getLocation(Scene scene, float t) {
		if (t > tarr || t < tdep)
			return null;
		float delta = (tarr - tdep) / (nodes.length - 1);
		float currt = tdep;
		for (int i = 0; i < nodes.length; i++) {
			if (t >= currt && t <= currt + delta) {
				return new Pnt3D(scene.getNodes().get(nodes[i]).getX(), scene
						.getNodes().get(nodes[i]).getY(), t);
			}
			currt += delta;
		}

		return null;
	}

	public Pnt3D getLocation(Scene scene, int index) {
		if (index >= nodes.length) {
			return null;
		}

		float delta = (tarr - tdep) / (nodes.length - 1);
		Node node = scene.getNodes().get(nodes[index]);
		return new Pnt3D(node.getX(), node.getY(), tdep + delta * index);

	}
	
	public void setPT(boolean isPT){
		this.isPT = isPT;
	}
	
	public boolean getPT(){
		return isPT;
	}

	public void setType(int type) {
		tripType = type;
	}

	public int getType() {
		return tripType;
	}

	public void leaveCenter(Scene scene, Node node) {
		Node dep = scene.getNodes().get(nodes[0]);
		Node arr = scene.getNodes().get(nodes[nodes.length - 1]);
		double depDis = Math.pow(node.getX() - dep.getX(), 2)
				+ Math.pow(node.getY() - dep.getY(), 2);
		double arrDis = Math.pow(node.getX() - arr.getX(), 2)
				+ Math.pow(node.getY() - arr.getY(), 2);

		if (arrDis > depDis)
			movingFurther = true;
		else
			movingFurther = false;
	}

	public boolean movFarCenter() {
		return movingFurther;
	}

	public int[] getNodes() {
		return nodes;
	}
	
	public ArrayList<Node> getPath(){
		return this.path;
	}

	public float getDepTime() {
		return tdep;
	}

	public void setDepTime(float tdep) {
		this.tdep = tdep;
	}

	public float getArrTime() {
		return tarr;
	}

	public void setArrTime(float tarr) {
		this.tarr = tarr;
	}

	public Node getDepNode() {
		return depNode;
	}

	public Pnt3D getDepPoint() {
		MBTools.debug("Dep X: "+depNode.getX()+" Y: "+depNode.getY() +" t: "+getDepTime(), false);
		return new Pnt3D(depNode.getX(), depNode.getY(), getDepTime());
	}
	
	public Node getArrNode(){
		return arrNode;
	}
	
	public int setArrNode(String offStop){
		System.out.println(offStop);
		
		if(Integer.parseInt(offStop)<10000){
			offStop = "0"+offStop;
		}
		if(scene.getNodeIndex(offStop) == null)
		{
			return 0;
		}
		this.arrNode = scene.getNodes().get(scene.getNodeIndex(offStop));
		setNodes();
		return 1;
	}
	
	public int setDepNode(String onStop){
		
		if(Integer.parseInt(onStop)<10000){
			onStop = "0"+onStop;
		}
		if(scene.getNodeIndex(onStop) == null)
		{
		     return 0;	
		}
		this.depNode = scene.getNodes().get(scene.getNodeIndex(onStop));
		return 1;
	}
	
	private void setNodes()
	{
		//get the route and the between nodes
	//	TransitRoute route = Scene.getRout(transitlineid, busid);
		
		
		TransitLine line = scene.getLines().get(scene.getLineIndex(this.transitlineid));
		
		
		ArrayList<TransitRoute> routes = line.getRoutes();
		ArrayList<String> stops = null;
		for(TransitRoute r : routes)
		{
			stops = r.getInterStops(this.depNode.getid(), this.arrNode.getid());
		    if(stops.size()>0){
		    	
		    	for(int i = 0 ; i < stops.size() ; i++)
				{
					
					path.add(scene.getNodes().get(scene.getNodeIndex(stops.get(i))));
				
				}	
		    	break;
		    }
		}
		
		
	}
	
	public void setTransitlineid(String id)
	{
		this.transitlineid = id;
	}
	
	public String getTransitlineid(){
		return this.transitlineid;
	}
	
	public void setBusid(int id){
		this.busid = id;
	}
	
	public int getBusid(){
		return this.busid;
	}
	
	public void setDis(float dis){
		this.dis  = dis;
	}
	
	public float getDis(){
		return this.dis;
	}
	
	public void setFair(float fair){
		this.fare = fair;
	}
	
	public float getFair()
	{
		return this.fare;
	}
	
	public Pnt3D getArrPoint(){
		MBTools.debug("Arr X: "+arrNode.getX()+" Y: "+arrNode.getY() +" t: "+getArrTime(), false);
		return new Pnt3D(arrNode.getX(), arrNode.getY(), getArrTime());
	}

	public Pnt3D getBoundedDepPoint(Scene scene) {
		float x = scene.getNodes().get(nodes[0]).getX();
		float y = scene.getNodes().get(nodes[0]).getY();
		float z = this.getDepTime();
		return new Pnt3D(x, y, z);
	}
	
	public Pnt3D getBoundededArrPoint(Scene scene) {
		float x = scene.getNodes().get(nodes[nodes.length - 1]).getX();
		float y = scene.getNodes().get(nodes[nodes.length - 1]).getY();
		float z = this.getArrTime();
		return new Pnt3D(x, y, z);
	}


	public BndBox getBoundingBox() {
		return bb;
	}

	// always build the bounding box before get it
	public void buildBox(Scene scene) {
		float delta = (tarr - tdep) / (nodes.length - 1);
		Node depNode = scene.getNodes().get(nodes[0]);
		bb = new BndBox(depNode.getX(), depNode.getY(), tdep);
		for (int i = 0; i < nodes.length; i++) {
			Node node = scene.getNodes().get(nodes[i]);
			bb.add(node.getX(), node.getY(), tdep + delta * i);
		}
	}


	public boolean intersects(BndBox bb) {
		return false;
	}


	public boolean contains(BndBox bb) {
		return false;
	}

	public void flag() {
		containmentFlag = true;
	}

	public boolean isFlagged() {
		return containmentFlag;
	}

	public void unFlag() {
		containmentFlag = false;

	}

	public Pnt3D getLocation2(Scene scene, float t) {
		// System.out.println("" + this + " t = " + t);
		if (t > tarr || t < tdep)
			return null;
		float delta = (tarr - tdep) / (nodes.length - 1);
		float currt = tdep;
		for (int i = 0; i < nodes.length - 1; i++) {
			// System.out.print("Trip: " + this + " currt = " + currt + " t=" +
			// t);
			if (t >= currt && t <= currt + delta) {
				float nodeT = t - currt;
				float percT = MBTools.map(nodeT, 0, delta, 0, 1);

				Node n1 = scene.getNodes().get(nodes[i]);
				Node n2 = scene.getNodes().get(nodes[i + 1]);
				Vector3f v1 = new Vector3f(n1.getX(), n1.getY(), 0f);
				Vector3f v2 = new Vector3f(n2.getX(), n2.getY(), 0f);
				v2.sub(v1);
				v2.scale(percT);
				v1.add(v2);
				// System.out.println("percT: " + percT + " n1: " + n1.getX() +
				// "," + n1.getY() + " n2: " + n2.getX() + "," + n2.getY() +
				// " v1: " + v1.x + "," + v1.y);
				return new Pnt3D(v1.x, v1.y, t);
			}
			currt += delta;
		}
		return null;
	}

	public void setTripId(String id) {
		tripId = id;
	}

	public String getTripId() {
		return tripId;
	}


	public void render(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		int[] intNodes = this.getNodes();
		gl.glBegin(GL.GL_LINE_STRIP);
		for (int i = 0; i < intNodes.length; i++) {
			Pnt3D loc = this.getLocation(scene, i);
			if (loc == null) {
				System.out.println("loc == null at index " + i);
			}
			gl.glVertex3d(loc.getX(), loc.getY(), loc.getZ());
		}
		gl.glEnd();
		
	}

}
