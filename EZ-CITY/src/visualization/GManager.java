package visualization;


import simulation.obj.Standard;
import data.FileIO.ExcelLoader;
import data.FileIO.ImageLoader;
import data.FileIO.RasterpointLoader;
import data.FileIO.ShapefileLoader;
import data.dataManeger.Scene;
import draw.simpleRender.RenderMan;
import draw.simpleRender.RenderManSmall;

public class GManager {
	
	private Scene m_scene = null;
	public ExcelLoader excel_load = null;
	public RasterpointLoader singapore_load = null;
	public ShapefileLoader shapefile_load = null;
	public ImageLoader CAimage_load = null;
	
	//new we limit the number of the renderMan to 2;
	
	public RenderMan manager1 = null;
	public RenderMan manager2 = null;
	
	public RenderManSmall smanager1 = null;
	public RenderManSmall smanager2 = null;

	public GManager(){};
	
	//load data
	
	//assign data to render managers
	public void assignRenderToCavas(){
		
	}

	public void register(RenderMan renderm, int n) {
		// TODO Auto-generated method stub
		if(n == 1)
		{
			manager1 = renderm;
		}else if(n == 2){
			manager2 = renderm;
		}
		
	}

	public void register(RenderManSmall renderm, int n) {
		// TODO Auto-generated method stub
		if(n == 1)
		{
			smanager1 = renderm;
		}else if(n == 2){
			smanager2 = renderm;
		}
		
	}
	
	public void loadData() {

//		NetworkLoader ld = new NetworkLoader();
		this.m_scene = new Scene();
		
		this.m_scene.bounds.add(Standard.RochorBoundary[0], Standard.RochorBoundary[1], 0);
		this.m_scene.bounds.add(Standard.RochorBoundary[2], Standard.RochorBoundary[3], 0);

	//	this.m_scene = ld.load("F://work//ezcity//trans_data");

		// set up data first

//		if (this.m_scene != null) {
//
//			if (this.excel_load == null) {
//				String path = System.getProperty("user.dir");
//				path += "\\data\\sample.csv";
//				this.excel_load = new ExcelLoader(path);
//
//				this.excel_load.setScene(this.m_scene);
//
//				try {
//					this.excel_load.read();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//		}
//
//		if (this.singapore_load == null) {
//			String path = "\\data\\vectors.xml";
//			this.singapore_load = new RasterpointLoader(path);
//
//			this.singapore_load.setScene(this.m_scene);
//			this.singapore_load.loadArea();
//
//		}

		// load buildings into
		if (this.shapefile_load == null) {
			String path = "F:\\cecile_develpment\\workspace\\EZ-CITY\\data";
			String building_filename = "Rochor_Buildings_1";
			String masterplan_filename = "Rochor_master_raster";

			shapefile_load = new ShapefileLoader();

			this.shapefile_load.setScene(this.m_scene);

			try {
				shapefile_load.loadBuildingShape(path, building_filename);
		//		shapefile_load.loadMasterPlanShape(path, masterplan_filename);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		if(this.CAimage_load == null){
			
			String path = "F:\\cecile_develpment\\workspace\\EZ-CITY\\data";
			String filename = "raster";

			CAimage_load = new ImageLoader();
			
			this.CAimage_load.setScene(m_scene);
			this.CAimage_load.loadMasterplan(path, filename);
		}
		
		m_scene.cleanup();

	}
	
	public void assignData(){
		
		//assign data to individual render, currently we have only manager1 and smanager2 works
		if(manager1 != null){
		manager1.m_scene = this.m_scene;
		manager1.excel_load = this.excel_load;
		manager1.singapore_load = this.singapore_load;
		manager1.shapefile_load = this.shapefile_load;
		}
		
		if(manager2!= null){
			manager2.m_scene = this.m_scene;
			manager2.excel_load = this.excel_load;
			manager2.singapore_load = this.singapore_load;
			manager2.shapefile_load = this.shapefile_load;
		}
		
		if(smanager1!=null){
			smanager1.m_scene = this.m_scene;
			smanager1.excel_load = this.excel_load;
			smanager1.singapore_load = this.singapore_load;
	//		smanager1.shapefile_load = this.shapefile_load;
		}
		
		if(smanager2!=null){
			smanager2.m_scene = this.m_scene;
			smanager2.excel_load = this.excel_load;
			smanager2.singapore_load = this.singapore_load;
	//		smanager1.shapefile_load = this.shapefile_load;
		}
	}
	
	

}
