package simulation.obj;

import draw.Geometry.Pnt2D;
import draw.Geometry.Pnt3D;

public class Stay {

	String tripid;
	String cid;
	Pnt3D spatialloc = new Pnt3D();
	Pnt2D location = new Pnt2D();
	float time = 0.f;
	int start = 0; // 0- start 1-end 
	float dis = 0.f;
	float duration = 0.f;
	int count = 0;
    int tp = 0; // people type
    
    int colorcod = 0;

	
	public Stay(){	
		
	}
	
	public void setLocation(Pnt2D point){
		this.location = point;
		
		this.spatialloc.setX((float) point.getX());
		this.spatialloc.setY((float) point.getY());
		this.spatialloc.setZ(time);
		
	}
	
	public Pnt2D getLocation(){
		return this.location;
	}
	
	public void setSpatialloc(Pnt3D point){
		this.spatialloc = point;
	}
	
	public Pnt3D getSpatialloc(){
		return this.spatialloc;
	}
	
	public void setTime(float t){
		this.time = t;
	}
	
	public float getTime(){
		return this.time;
	}
	
	public void setStart(int s){
		this.start = s;
	}
	
	public int getStart(){
		return this.start;
	}
	
	public void setDis(float d){
		this.dis = d;
	}
	
	public float getDis(){
		return this.dis;
	}
	
	public void setDuration(float d){
		this.duration = d;
	}
	
	public float getDuration(){
		return this.duration;
	}
	
	public void setCount(int c){
		this.count = c;
	}
	
	public float getCount(){
		return this.count;
	}
	
	public void setTripid(String t){
		this.tripid = t;
	}
	
	public String getTripid(){
		return this.tripid;
	}
	
	public void setCid(String t){
		this.cid = t;
	}
	
	public String getCid(){
		return this.cid;
	}
	
	public void setTp(int t){
		this.tp = t;
	}
	
	public int getTp(){
		return this.tp;
	}
	
	
	public void setColor(int c){
		this.colorcod = c;
	     
		 double r = (c/100)*1.0/10;
		 double g = ((c - (r*1000))/10)*1.0/10;
		 double b = (c - (r*1000) - g*100)*1.0/10;
		 
		
		this.spatialloc.SetColor(r, g, b);
	}
	
	public int getColor(){
		return this.colorcod;
	}

	
}
