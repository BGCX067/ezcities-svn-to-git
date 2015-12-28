package data.FileIO;

import java.nio.ByteBuffer;

import data.dataManeger.Scene;



public class ImageLoader {

	private Scene scene = null;
	
	String name_LOD0 = "_8m";
	String name_LOD1 = "_16m";
	String name_LOD2 = "_32m";
	String name_LOD3 = "_64m";
	String name_LOD4 = "_128m";
	String name_LOD5 = "_256m";
	String name_LOD6 = "_256m";
	
	private BmpReader LOD0readers = null;
	private BmpReader LOD1readers = null;
	private BmpReader LOD2readers = null;
	private BmpReader LOD3readers = null;
	private BmpReader LOD4readers = null;

	public void setScene(Scene _s) {
		scene = _s;
	}
	
	
	public ImageLoader(){}



	public void loadMasterplan(String path, String filename) {
		// TODO Auto-generated method stub
		if(this.LOD0readers == null){
			LOD0readers = new BmpReader();
			LOD0readers.initBuffer(path+"\\" + filename+name_LOD0 +".bmp");
			scene.landcover.height[0] = LOD0readers.height;
			scene.landcover.width[0] = LOD0readers.width;
			scene.landcover.tbuffer[0] = ByteBuffer.wrap(LOD0readers.read);
	//		scene.landcover.texturees[0] = LOD0readers.texture;

		}
		
		if(this.LOD1readers == null){
			LOD1readers = new BmpReader();
			LOD1readers.initBuffer(path+"\\"+ filename + name_LOD1 + ".bmp");
			scene.landcover.height[1] = LOD1readers.height;
			scene.landcover.width[1] = LOD1readers.width;
			scene.landcover.tbuffer[1] = ByteBuffer.wrap(LOD1readers.read);
	//		scene.landcover.texturees[1] = LOD1readers.texture;
			
			scene.landcover.blue1 = LOD1readers.blue;
			scene.landcover.red1 = LOD1readers.red;
			scene.landcover.green1 = LOD1readers.green;
			

		}
		
		if(this.LOD2readers == null){
			LOD2readers = new BmpReader();
			LOD2readers.initBuffer(path+"\\"+ filename + name_LOD2 + ".bmp");
			scene.landcover.height[2] = LOD2readers.height;
			scene.landcover.width[2] = LOD2readers.width;
			scene.landcover.tbuffer[2] = ByteBuffer.wrap(LOD2readers.read);
	//		scene.landcover.texturees[2] = LOD2readers.texture;
			
			scene.landcover.blue2 = LOD2readers.blue;
			scene.landcover.red2 = LOD2readers.red;
			scene.landcover.green2 = LOD2readers.green;

		}
		
		if(this.LOD3readers == null){
			LOD3readers = new BmpReader();
	//		LOD3readers.initBuffer(path+"\\"+ filename + name_LOD3 + ".bmp");
			LOD3readers.initBuffer(path+"\\"+ filename + name_LOD3 + ".bmp");
			scene.landcover.height[3] = LOD3readers.height;
			scene.landcover.width[3] = LOD3readers.width;
			scene.landcover.tbuffer[3] = ByteBuffer.wrap(LOD3readers.read); 
			
			scene.landcover.blue3 = LOD3readers.blue;
			scene.landcover.red3 = LOD3readers.red;
			scene.landcover.green3 = LOD3readers.green;
			
			
	//		scene.landcover.texturees[3] = LOD3readers.texture;

		}
		
		if(this.LOD4readers == null){
			LOD4readers = new BmpReader();
			LOD4readers.initBuffer(path+"\\" + filename + name_LOD4 + ".bmp");
			scene.landcover.height[4] = LOD4readers.height;
			scene.landcover.width[4] = LOD4readers.width;
			scene.landcover.tbuffer[4] = ByteBuffer.wrap(LOD4readers.read);
	//		scene.landcover.texturees[4] = LOD4readers.texture;
			
			scene.landcover.blue4 = LOD4readers.blue;
			scene.landcover.red4 = LOD4readers.red;
			scene.landcover.green4 = LOD4readers.green;


		}
		
	
		
		
	}
	


}
