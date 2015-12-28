package simulation.obj;

public class Activity {
	private int posNodeId;
	private float startTime;
	private float endTime;
	private int actType;
		
	public Activity(int nodeId, float start, float end, int type){
		posNodeId = nodeId;
		startTime = start;	
		endTime = end;
		actType = type;
	}
	
	public int getPos(){
		return posNodeId;
	}
	
	public int getActType(){
		return actType;
	}
	
	public float getStartTime(){
		return startTime;
	}
	
	public float getEndTime(){
		return endTime;
	}
	
	public void setActType(int type){
		actType = type;
	}
	
	public void setStartTime(float time){
		startTime = time;
	}
	
	public void setEndTime(float time){
		endTime = time;
	}
}
