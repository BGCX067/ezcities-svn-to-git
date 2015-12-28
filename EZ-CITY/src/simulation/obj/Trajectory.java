package simulation.obj;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import draw.simpleRender.StateManager;


public class Trajectory {

	// for scenegraphy
	private int m_id; // identity of different trajectory
	
	private int m_mapid; // the map which it belongs to
	
	// for the database
	
	private String m_jid; // varchar(16) , 
	private String m_cid;  // varchar(20) not null, 
	private String m_passtype; // varchar(32) not null, 

	private String m_travelmode; // varchar(8), 
	private String m_srvnum;  // varchar(8), 
	private int m_bugregnum; //int, 
	private int m_direction;  // int, 
	private String m_boardstop; // varchar(32), 
	private String m_alighstop; // varchar(32), 
//	private Date m_ridedate; // varchar(32), 
//	private Time m_ridestarttime; // varchar(32), 
	private int m_ridestarttime;
	private float m_ridedis; // float not null, 
	private float m_ridetime; // float not null, 
	private float m_farpaid; // float not null, 
	private int m_transfernum;  // int );
	private double m_Onx;
	private double m_Ony;
	
	
//	private ArrayList<Trajectory> m_forwardT = new ArrayList<Trajectory>();
//	private ArrayList<Trajectory> m_backwardT = new ArrayList<Trajectory>();
	
	
 	
	/**
	create table test.d7(
	jid varchar(16) , 
	cid varchar(20) not null, 
	passtype varchar(32) not null, 
	travelmode varchar(8), 
	srvnum varchar(8), 
	bugregnum int, 
	direction int, 
	boardstop varchar(32), 
	alighstop varchar(32), 
	ridedate varchar(32), 
	ridestarttime varchar(32), 
	ridedis float not null, 
	ridetime float not null, 
	farpaid float not null, 
	transfernum int );
	*/
	/*
	 * rendering state, enable different rendering method
	 */
    StateManager m_rds = new StateManager();
    
    
	public Trajectory(){
		
	}
	
	public Trajectory copy(){
		Trajectory t = new Trajectory();
		
		t.setJid(m_jid);
		t.setCid(m_cid);
		t.setPasstype(m_passtype);
		t.setTravelmode(m_travelmode);
		t.setSrvnum(m_srvnum);
		t.setBugregnum(m_bugregnum);
		t.setDirection(m_direction);
		t.setBoardstop(m_boardstop);
		t.setAlighstop(m_alighstop);
	//	t.setRidedate(m_ridedate);
		t.setRidestarttime(m_ridestarttime);
		t.setRidedis(m_ridedis);
		t.setRidetime(m_ridetime);
		t.setFarpaid(m_farpaid);
		t.setTransfernum(m_transfernum);
		
		return t;
	}
	
	public void setMid(int id){
		this.m_id = id;
	}
	
	public int getMide(){
		return this.m_id;
	}
	
	public void setMapid(int mapid){
		this.m_mapid = mapid;		
	}
	
	public int getMapid(){
		return this.m_mapid;
	}
	
	public void setJid(String jid){
		this.m_jid = jid;
	}
	
	public String getJid(){
		return this.m_jid;
	}
	
	public void setCid(String cid){
		this.m_cid = cid;
	}
	
	public String getCid(){
		return this.m_cid;
	}
	
	public void setPasstype(String passtype){
		this.m_passtype = passtype;
	}
	
	public String getPasstype(){
		return this.m_passtype;
	}
	
	public void setTravelmode(String travelmode){
		this.m_travelmode = travelmode;
	}
	

	public String getTravelmode(){
		return this.m_travelmode;
	}
	
	public void setSrvnum(String transitline){
		this.m_srvnum = transitline;
	}
	
	public String getSrvnum(){
		return this.m_srvnum;
	}
	
	public void setBugregnum(int bugregnum){
		this.m_bugregnum = bugregnum;
	}
	
	public int getBugregnum(){
		return this.m_bugregnum;
	}
	
	public void setDirection(int direction){
		this.m_direction = direction;
	}
	
	public int getDirection(){
		return this.m_direction;
	}
	
	public void setBoardstop(String boardstop){
		this.m_boardstop = boardstop;
	}
	
	public String getBoardstop(){
		return this.m_boardstop;
	}
	
	public void setAlighstop(String alighstop){
		this.m_alighstop = alighstop;
	}
	
	public String getAlighstop(){
		return this.m_alighstop;
	}
	
//	public void setRidedate(Date ridedate){
//		this.m_ridedate = ridedate;
//	}
//	
//	public Date getRidedate(){
//		return this.m_ridedate;
//	}
//	
//	public void setRidestarttime(Time ridestarttime){
//		this.m_ridestarttime = ridestarttime;
//	}
//	
//	public Time getRideStarttime(){
//		return this.m_ridestarttime;
//	}
	
	public void setRidestarttime(int ridestarttime){
	this.m_ridestarttime = ridestarttime;
    }

     public int getRideStarttime(){
	return this.m_ridestarttime;
     }
	
	public void setRidedis(float ridedis){
		this.m_ridedis = ridedis;
	}
	
	public float getRidedis(){
		return this.m_ridedis;
	}
	
	public void setRidetime(float ridetime){
		this.m_ridetime = ridetime;
	}
	
	public float getRidetime(){
		return this.m_ridetime;
	}
	
	public void setFarpaid(float farpaid){
		this.m_farpaid = farpaid;
	}
	
	public float getFarpaid(){
		return this.m_farpaid;
	}
	
	public void setTransfernum(int transfernum){
		this.m_transfernum = transfernum;
	}
	
	public int getTransfernum(){
		return this.m_transfernum;
	}
	
	public void setOnX(double x){
		this.m_Onx = x;
	}
	
	public double getOnX(){
		return this.m_Onx;
	}
	
	public void setOnY(double y){
		this.m_Ony = y;
	}
	
	
	public double getOnY(){
		return this.m_Ony;
	}
	
//	public ArrayList<Trajectory> getForward(){
//		return this.m_forwardT;
//	}
//	
//
//	public ArrayList<Trajectory> getBackward(){
//		return this.m_backwardT;
//	}
//	
	
	public void filterOutBus(){
		
	} 
	
	
	
	
	
}
