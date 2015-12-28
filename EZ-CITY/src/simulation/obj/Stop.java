package simulation.obj;

public class Stop {
	private float x;
	private float y;
	private String linkRef;
	
	public Stop(float x, float y, String linkRef) {
		this.x = x;
		this.y = y;
		this.linkRef = linkRef;
	}

	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public String getLinkRef(){
		return linkRef;
	}
	
	@Override
	public String toString() {
		return x + " " + y + " "+linkRef;
	}
}
