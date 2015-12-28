package data.FileIO;

import java.awt.Color;


public class BmpWriter {
	
	private Color[][] pointArray;

	int width;


	int height;

	public BmpWriter(Color[][] pointArray) {
		this.pointArray = pointArray;
		this.width = pointArray.length;
		this.height = pointArray[0].length;
		this.write();
	}

	
	public void write() {
		try {
			// 创建输出流文件对象
			String path = "F:\\cecile_develpment\\workspace\\EZ-CITY\\data";

			java.io.FileOutputStream fos = new java.io.FileOutputStream(path +"\\masterplan.bmp");
			java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);

			int bfType = 0x424d; 
			int bfSize = 54 + width * height * 3;//
			int bfReserved1 = 0;// 
			int bfReserved2 = 0;//
			int bfOffBits = 54;// 

			dos.writeShort(bfType); //
			dos.write(changeByte(bfSize),0,4); //
			dos.write(changeByte(bfReserved1),0,2);// 
			dos.write(changeByte(bfReserved2),0,2);// 
			dos.write(changeByte(bfOffBits),0,4);// 

			int biSize = 40;//
			int biWidth = width;// 
			int biHeight = height;// 
			int biPlanes = 1; // 
			int biBitcount = 24;
			int biCompression = 0;
			int biSizeImage = width * height;
			int biXPelsPerMeter = 0;
			int biYPelsPerMeter = 0;
			int biClrUsed = 0;
			int biClrImportant = 0;
			
		
			dos.write(changeByte(biSize),0,4);
			dos.write(changeByte(biWidth),0,4);
			dos.write(changeByte(biHeight),0,4);
			dos.write(changeByte(biPlanes),0,2);
			dos.write(changeByte(biBitcount),0,2);
			dos.write(changeByte(biCompression),0,4);
			dos.write(changeByte(biSizeImage),0,4);
			dos.write(changeByte(biXPelsPerMeter),0,4);
			dos.write(changeByte(biYPelsPerMeter),0,4);
			dos.write(changeByte(biClrUsed),0,4);
			dos.write(changeByte(biClrImportant),0,4);

			
			for (int i = height - 1; i >= 0; i--) {
				for (int j = 0; j < width; j++) {
					int red = pointArray[i][j].getRed();
					int green = pointArray[i][j].getGreen();
					int blue = pointArray[i][j].getBlue();
					byte[] red1 = changeByte(red);
					byte[] green1 = changeByte(green);
					byte[] blue1 = changeByte(blue);
					dos.write(blue1,0,1);
					dos.write(green1,0,1);
					dos.write(red1,0,1);
				}
			}
			dos.flush();
			dos.close();
			fos.close();
			System.out.println("success!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public byte[] changeByte(int data){
		byte b4 = (byte)((data)>>24);
		byte b3 = (byte)(((data)<<8)>>24);
		byte b2= (byte)(((data)<<16)>>24);
		byte b1 = (byte)(((data)<<24)>>24);
		byte[] bytes = {b1,b2,b3,b4};
		return bytes;
	}
}
