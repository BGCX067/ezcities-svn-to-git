package simulation.obj;

public class Node {
	private String id = "";
	private float x;
	private float y;
	
	public Node(float x, float y) {
		
		this.x = x;
		this.y = y;
	}

	public String getid(){
		return id;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}

	public void setid(String i){
	
		this.id = i;
	}
	
	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return x + " " + y;
	}
}
