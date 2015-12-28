package simulation.obj;

public class LandCell {

	int pointid;    // id of this cell
	float pos_x;    // geographic _x
	float pos_y;    // geographic _y
	int refered_x;  // raw in the matrix
	int refered_y;  // column in the matrix
	
	String area;    //  
	int use;        // land use of this cell
	String heigh;   // height of this cell
    
	// ground features which is on top of this parcel
	String buildingid; // the buildings
	String humanid;    // the humans
	int number_of_human; // the number of human on top of it
	
	public LandCell(){
		
	}
	
	public void setid(int id){
		this.pointid = id;
	}
	
	public void setx(float d){
		this.pos_x = d;
	}
	
	public void sety(float y){
		this.pos_y = y;
	}
	
	public void setArea(String a){
		this.area = a;
	}
	
	/*
	    BUSSINESS_WHITE,
	    BUSSINESS_2_WHITE,
	    BUSSINESS_PARK,
	    BUSSINESS_PARK_WHITE,
	    RESIDENTIAL_INSTITUTION,
	    HEALTH_MEDICAL_CARE,
	    PLACE_OF_WORSHIP,
	    BEACH_AREA,
	    SPORTS_RECREATION,
	    RAILWAY,
	    MASS_RAPID_TRANSIT,
	    LIGHT_RAPID_TRANSIT,
	    UTILITY,
	    CEMETERY,
	    RESERVE_SITE,
	    SPECIAL_USE,
	    PARK_CONNECTOR,
	    PLANNING_AREA_BOUNDARY,
	    PLOT_RATIO_BOUNDARY,
	    UNDERGROUND_ROAD_INFRASTRUCTURE_TUNNEL,
	    MAXIMUM_PERMISSIBLE_PLOT_RATIO,
	    BASE_PLOT_RATIO,
	    MAXIMUM_PERMISSIBLE_WHITE_QUANTUM,
	    MINIMUM_BUSINESS,
	    CONSERVATION_AREA,
	    NATURE_RESEARVE,
	    NATIONAL_PARK,
	    MONUMENT
	 */
	public void setUse(String u){
		
		if(u.equalsIgnoreCase("WHITE")){
			this.use = 1;
		}else if(u.equalsIgnoreCase("HOTEL")){
			this.use = 2;
		}else if(u.equalsIgnoreCase("BUSINESS_2")||u.equalsIgnoreCase("BUSINESS_1")){
			this.use = 3;
		}else if(u.equalsIgnoreCase("CIVIC_COMMUNITY_INSTITUTION")){
			this.use = 4;
		}else if(u.equalsIgnoreCase("RESIDENTIAL")){
			this.use = 5;
		}else if(u.equalsIgnoreCase("EDUCATIONAL INSTITUTION")){
			this.use = 6;
		}else if(u.equalsIgnoreCase("RESIDENTIAL WITH COMMERCIAL AT 1ST STOREY")){
			this.use = 7;
		}else if(u.equalsIgnoreCase("COMMERCIAL")){
			this.use = 8;
		}else if(u.equalsIgnoreCase("OPEN SPACE")){
			this.use = 9;
		}else if(u.equalsIgnoreCase("PARK")){
			this.use = 10;
		}else if(u.equalsIgnoreCase("ROAD")){
			this.use = 11;
		}else if(u.equalsIgnoreCase("COMMERCIAL_RESIDENTIAL")){
			this.use = 12;
		}else if(u.equalsIgnoreCase("WATERBODY")){
			this.use = 13;
		}else if(u.equalsIgnoreCase("PORT AIRPORT")){
			this.use = 14;
		}else if(u.equalsIgnoreCase("TRANSPORT FACILITIES")){
			this.use = 15;
		}else if(u.equalsIgnoreCase("CONSERVATION AREA")){
			this.use = 16;
		}
		
		
		
		
		
		
		else if(u.equalsIgnoreCase("AGRICULTURE")){
			this.use = 7;
		}
		 
	}
	
	public void setHeight(String h){
		this.heigh = h;
	}
	
	public int getid()
	{
		return this.pointid;
	}
	
	public float getx(){
		return this.pos_x;
	}
	
	public float gety(){
		return this.pos_y;
	}
	
	public String getArea(){
		return this.area;
	}
	
	public int getUse(){
		return this.use;
	}
	
	public String getHeight(){
		return this.heigh;
	}
	
}
