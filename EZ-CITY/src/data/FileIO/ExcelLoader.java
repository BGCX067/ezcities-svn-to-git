package data.FileIO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import data.dataManeger.Scene;
import draw.Geometry.Pnt2D;

import simulation.obj.Stay;

public class ExcelLoader {

	private Scene scene = null;

	String filepath;
	
	public ArrayList<Stay> functionpoints = new ArrayList<Stay>();


	public ExcelLoader(String path){
		
		this.filepath = path;
	}
	
	public void read() throws IOException  {
		
		// CSVRead.java
		//Reads a Comma Separated Value file and prints its contents.

		  BufferedReader CSVFile = 
		        new BufferedReader(new FileReader(filepath));

		  String dataRow = CSVFile.readLine(); // Read first line.
		  // The while checks to see if the data is null. If 
		  // it is, we've hit the end of the file. If not, 
		  // process the data.
		  if(dataRow !=null){
			  dataRow = CSVFile.readLine();
		  }else{
			  return;
		  }
		  
		  
		  int i = 0;
		  while (dataRow != null){
			  
		   String[] rs = dataRow.split(",");
		   
		   if(rs.length == 0)
		   {
			   break;
		   }
		   
		   //id	cid	tp	onstop	offstop	ridestart	ridedis	ridetime	fair	transcount	onx	ony	offx	offy	hours	hoursend	dis	function1	function2	EXTRA
		   Stay s1 = new Stay();
		   
		   s1.setTripid(rs[0]);
		   s1.setCid(rs[1]);
		   
		   s1.setTp(Integer.parseInt(rs[2]));
		   s1.setTime(Float.parseFloat(rs[14])/4-1);
		   s1.setDis(Float.parseFloat(rs[6]));
		   s1.setDuration(Float.parseFloat(rs[7]));
		   s1.setCount(0);
		   
		   Pnt2D p1 = new Pnt2D();
		   
		   p1.setX(Float.parseFloat(rs[10]));
		   p1.setY(Float.parseFloat(rs[11]));
		   
		   p1.setX(scene.getBound().normalizeX(Float.parseFloat(rs[10])));
		   p1.setY(scene.getBound().normalizeY(Float.parseFloat(rs[11])));
		   
		   s1.setLocation(p1);
		   s1.setColor(Integer.parseInt(rs[17]));
		   
		   //////////////////////////////////////////////////////////
		   Stay s2 = new Stay();
		   s2.setTripid(rs[0]);
		   s2.setCid(rs[1]);
		   
		   s2.setTp(Integer.parseInt(rs[2]));
		   s2.setTime(Float.parseFloat(rs[15])/4-1);
		   s2.setDis(Float.parseFloat(rs[6]));
		   s2.setDuration(Float.parseFloat(rs[7]));
		   s2.setCount(Integer.parseInt(rs[9]));
		   
		   Pnt2D p2 = new Pnt2D();
		   
		   p2.setX(scene.getBound().normalizeX(Float.parseFloat(rs[12])));
		   p2.setY(scene.getBound().normalizeY(Float.parseFloat(rs[13])));
		   
		   s2.setLocation(p2);
		   s2.setColor(Integer.parseInt(rs[18]));
		   		   
           this.functionpoints.add(s1);
           this.functionpoints.add(s2);
		   
	
		   dataRow = CSVFile.readLine(); // Read next line of data.
		  }
		  // Close the file once all data has been read.
		  CSVFile.close();

		  // End the printout with a blank line.
		  System.out.println();

		} // CSVRead

	public void setScene(Scene m_scene) {
		// TODO Auto-generated method stub
		this.scene  = m_scene;
	}
	
	
}
