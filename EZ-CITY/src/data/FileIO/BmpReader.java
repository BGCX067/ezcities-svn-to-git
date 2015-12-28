package data.FileIO;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import com.jogamp.opengl.util.texture.Texture;


public class BmpReader {

	public int width;

	public int height;

	int[][] red;

	int[][] green;
	int[][] blue;

	public byte[] read;

	public ByteBuffer readbuffer = null;

	public BufferedImage image = null;
	
	public Texture texture = null;

	public void init(String path) {
		try {

			java.io.FileInputStream fin = new java.io.FileInputStream(path);

			// java.io.DataInputStream bis = new java.io.DataInputStream(fin);

			java.io.BufferedInputStream bis = new java.io.BufferedInputStream(
					fin);

			byte[] array1 = new byte[14];
			bis.read(array1, 0, 14);

			byte[] array2 = new byte[40];
			bis.read(array2, 0, 40);

			width = ChangeInt(array2, 7);
			height = ChangeInt(array2, 11);

			getInf(bis);

			fin.close();
			bis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initBuffer(String path) {
		try {

			java.io.FileInputStream fin = new java.io.FileInputStream(path);

			// java.io.DataInputStream bis = new java.io.DataInputStream(fin);

			java.io.BufferedInputStream bis = new java.io.BufferedInputStream(
					fin);

			byte[] array1 = new byte[14];
			bis.read(array1, 0, 14);

			byte[] array2 = new byte[40];
			bis.read(array2, 0, 40);

			width = ChangeInt(array2, 7);
			height = ChangeInt(array2, 11);

			getInf(bis);

			fin.close();
			bis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	/**
	 * translate to int
	 * 
	 * @param array2
	 * @param start
	 * @return
	 */
	public int ChangeInt(byte[] array2, int start) {

		int i = (int) ((array2[start] & 0xff) << 24)
				| ((array2[start - 1] & 0xff) << 16)
				| ((array2[start - 2] & 0xff) << 8)
				| (array2[start - 3] & 0xff);
		return i;
	}

	/**
	 * get int array
	 * 
	 * @param dis
	 * 
	 */
	public void getInf(java.io.BufferedInputStream bis) {

		red = new int[height][width];
		green = new int[height][width];
		blue = new int[height][width];

		read = new byte[height * width * 3];
		// readbuffer = ByteBuffer.allocate(height*width*3*4);

		int skip_width = 0;
		int m = width * 3 % 4;
		if (m != 0) {
			skip_width = 4 - m;
		}

		int inbuffer = 0;

		for (int i = height - 1; i >= 0; i--) {
			for (int j = 0; j < width; j++) {
				try {

					blue[i][j] = bis.read();
					green[i][j] = bis.read();
					red[i][j] = bis.read();

					read[inbuffer] = (byte)blue[i][j];
					read[inbuffer + 1] = (byte)green[i][j];
					read[inbuffer + 2] = (byte)red[i][j];
					inbuffer += 3;

					if (j == 0) {
						bis.skip(skip_width);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static byte[] int2byte(int res) {

		byte[] targets = new byte[4];

		targets[0] = (byte) (res & 0xff);// 最低位
		targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
		targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
		targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
		return targets;
	}

}
