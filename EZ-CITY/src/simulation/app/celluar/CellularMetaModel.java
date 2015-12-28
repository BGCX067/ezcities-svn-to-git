package simulation.app.celluar;


import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.Timer;

public class CellularMetaModel {
  
	private static int DEFAULT_SIZE = 256;
	private double[][] m_cellMatrix  = new double[DEFAULT_SIZE][DEFAULT_SIZE]; 
	
	static final int ROW = DEFAULT_SIZE; // class constants
    static final int COL = DEFAULT_SIZE;
    static final int SIZE = 8;
    static final int INIT_FPS = 10;
    
    public double[][] grid;
    public int cellShape = 0;
    public boolean colorCoded = false;
    
    private double[][] gridCopy;
    private int min = 2;
    private int max = 4;
    private int hit = 3;
    private int generationCount = 0;
    
  
    public boolean frozen = true;
    
    public CellularMetaModel() { 
    	  	
    //    timer = new Timer(delay, (ActionListener) this);
     //   timer.setInitialDelay(delay * 10);
     //   timer.setCoalesce(true); // ???
    
        grid = new double [ROW][COL]; // initialize data structures
        gridCopy = new double [ROW][COL];
    
        initGrid(grid);
        grid[127][(COL / 2) -1] = 1;
        grid[127][(COL / 2) +1] = 1;
        grid[128][(COL / 2) -1] = 1;
        grid[128][(COL / 2) +1] = 1;
        grid[129][(COL / 2) -1] = 1;
        grid[129][(COL / 2)] = 1;
        grid[129][(COL / 2) +1] = 1;
        
    }
    
    
    public void update() {
        nextGeneration();
        generationCount++;  
    }
    
    public void startAnimation() {
  //      timer.start();
        frozen = false;
    }
    
    public void stopAnimation() {
  //      timer.stop();
        frozen = true;
    }
    
    private int calc(int y, int x) {
        int m, n, total;
    
        total = (grid[y][x] != 0) ? -1 : 0;
        for (m = -1; m <= +1; m++) {
            for (n = -1; n <= +1; n++) {
                if (grid[(ROW + (y + m)) % ROW][(COL + (x + n)) % COL] != 0) {
                    total++;
                }
            }
        }
        return total;
    }
    
    private void duplicateGrid(double[][] source, double[][] dest) {
        int i, j;
        for (i = 0; i < ROW; i++) {
            for (j = 0; j < COL; j++) {
                dest[i][j] = source[i][j];
            }
        }    
    }
    
    public void nextGeneration() {
        int i, j, neighbors;
        initGrid(gridCopy);
        for (i = 0; i < ROW; i++) {
            for (j = 0; j < COL; j++) {
                neighbors = calc(i, j);
            
                if (grid[i][j] != 0) {
                    if ((neighbors >= min) && (neighbors <= max)) {
                        gridCopy[i][j] = neighbors;
                    }
                } else {
                    if (neighbors == hit) {
                        gridCopy[i][j] = hit;
                    }
                }
            }
        }
        initGrid(grid);
        duplicateGrid(gridCopy, grid);
    }
    
    private void initGrid(double[][] matrix) {
        int i, j;
        for (i = 0; i < ROW; i++) {
            for (j = 0; j < COL; j++) {
                matrix[i][j] = 0;
            }
        }
    }
    
  
}