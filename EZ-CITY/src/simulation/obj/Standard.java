package simulation.obj;

public final class Standard {
	
	
    public final static int Commercial_and_Retail = 1;
    public final static int Mixed_use = 2;
    public final static int Business = 3;
    public final static int Green_Recreation_Open_Space = 4;
    public final static int Planning = 5;
    public final static int Residential = 6;
    public final static int Transport = 7;
    public final static int Government_administration = 8;
    public final static int Community = 9;
    public final static int public_Utility = 10;
    
    public final static int[][] Functioncolor = {
    	
    	{80, 225, 230},
    	{235, 27, 250},
    	{222, 81, 4},
    	{0, 0, 0},
    	{77, 40, 59},
    	{0, 0, 0},
    	{11, 97, 217},
    	{230, 208, 99},
    	{230, 188, 218},
    	{194, 29, 120}
    };
    
    public final static double[] RochorBoundary = {27950.6990,29225.8071,33788.6990,34467.8071}; // left, top, right,down
    
    /*
     * defined mixed use type 
     */
    //   
    //  Main Street residential/commercial - two to three-story buildings with residential units above and commercial units on the ground floor facing the street
    public final static int residential_commercial = 1;   
    //Urban residential/commercial - multi-story residential buildings with commercial and civic uses on ground floor
    public final static int residential_civil = 2;  // car_parking
    //Office convenience - office buildings with small retail and service uses oriented to the office workers
    public final static int office_retail = 3;
    //Office/residential - multi-family residential units within office buliding(s)
    public final static int office_residential = 4;
    //Shopping mall conversion - residential and/or office units added (adjacent) to an existing standalone shopping mall
    public final static int residential_office_to_shop = 5;
    //Live/work - residents can operate small businesses on the ground floor of the building where they live
    public final static int residential_combine_work = 6;
    //Studio/light industrial - residents may operate studios or small workshops in the building where they live
    public final static int industrial_residential = 7;
    //    Hotel/residence - mix hotel space and high-end multi-family residential
    public final static int hotel_residential = 8;
    //     Single-family detached home district with standalone shopping center
    public final static int home_shop = 9;
    //using the second floor as the storage building and the upper as office
    public final static int office_storage_commercial = 10;
    
    
    /*
     * define for trip purpose
     */
    public final static int HOME = 0;
    public final static int WORK = 1;
    public final static int EDUCATION = 2;
    public final static int LEISURE = 3;
    public final static int SHOP = 4;
    public final static int DEFAULT = 5;

    
    public final static int getLanduse(int[] color){
    	int landuse = -1;
    	
    	for(int j  = 0 ; j < 10; j ++){
    		if((color[0] == Functioncolor[j][0])&&(color[1] == Functioncolor[j][1])&&(color[2] == Functioncolor[j][2])){
    			
    			return j+1;
    		}
    	}
    	return landuse;
    }
    
    public final static int[] getColor(int landuse){
    	
    	return Functioncolor[landuse-1];
    }
    

	static enum  standardmasterplan {  
		
		RESIDENTIAL,
		RESIDENTIAL_WITH_COMMERCIAL_AT_1ST_STOREY, // 88
		COMMERCIAL_RESEIDENTIAL, //
	    COMMERCIAL,
	    HOTEL,
	    WHITE,
	    BUSSINESS_1,
	    BUSSINESS_WHITE,
	    BUSSINESS_2,
	    BUSSINESS_2_WHITE,
	    BUSSINESS_PARK,
	    BUSSINESS_PARK_WHITE,
	    RESIDENTIAL_INSTITUTION,
	    HEALTH_MEDICAL_CARE,
	    EDUCATION_INSTITUTION,
	    PLACE_OF_WORSHIP,
	    CIVIC_COMMUNITY_INSTITUTION,
	    OPEN_SPACE,
	    PARK,
	    BEACH_AREA,
	    SPORTS_RECREATION,
	    WATERBODY,
	    ROAD,
	    TRANSPORT_FACILITIES,
	    RAILWAY,
	    MASS_RAPID_TRANSIT,
	    LIGHT_RAPID_TRANSIT,
	    UTILITY,
	    CEMETERY,
	    AGRICULTURE,
	    PORT_AIRPORT,
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
	    
	}  
}
