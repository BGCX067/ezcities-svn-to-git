package simulation.app.celluar;

import java.util.ArrayList;

import simulation.obj.LandCell;

public class MultiAgentCAModel {
  
	private static int DEFAULT_SIZE = 200;
	
	static final int ROW = DEFAULT_SIZE; // class constants
    static final int COL = DEFAULT_SIZE;
    static final int SIZE = 8;
    static final int INIT_FPS = 10;
    
    public double[][][] grid;
    public int cellShape = 0;
    public boolean colorCoded = false;
    
    private double[][][] gridCopy;
    private double[][][] rulegrid;
 
    private int min = 2;
    private int max = 4;
    private int hit = 1;
    private int generationCount = 0;
    
    public ArrayList<LandCell> cells = null;
   
    
    public boolean frozen = true;
    
    //three kind of agent
    public enum CAAgent {
        residential, commercial, transition;
    }
    
    public MultiAgentCAModel() { 
    	
    }
    	
    public void init(){
        grid = new double [ROW][COL][3]; // initialize data structures
        gridCopy = new double [ROW][COL][3];
        
        rulegrid = new double[ROW][COL][3];
    
        initGrid(grid);
        initGrid(rulegrid);    
        setStartCondition(grid);
        setStartSingapore(grid, cells);

        
    }
    
    
    private void setStartSingapore(double[][][]grid, ArrayList<LandCell> cells)
    {
    	
    	float size = 800.0f/this.DEFAULT_SIZE;
    	
    	for(LandCell c:cells){
    		float x = c.getx();
    		float y = c.gety();
    		
    		if(x >= 400)
    		{
    			x = 400;
    		}
    		
    		if(y >= 400){
    			y = 400;
    		}
    		
    		if(x<= -400){
    			x = -400;
    		}
    		
    		if(y <= -400){
    			y = -400;
    		}
    		
    		int row = (int) ((x + 400) /size) -1;
    		
    		if(row < 0)
    		{
    			row = 0;
    		}
    		
    		int col = (int) ((y + 300)/size) - 1;
    		
    		if(col < 0){
    			col = 0;
    		}
    		
    		grid[row][col][1] = Integer.parseInt(c.getArea());
    		grid[row][col][2] = c.getUse();
    		
    	}
    }
    
    private void setStartCondition(double[][][] grid2) {
    	
		// TODO Auto-generated method stub
    	grid[127][(COL / 2) -1][0] = 1;
        grid[127][(COL / 2) +1][0] = 1;
        grid[128][(COL / 2) -1][0] = 1;
        grid[128][(COL / 2) +1][0] = 1;
        grid[129][(COL / 2) -1][0] = 1;
        grid[129][(COL / 2)][0] = 1;
        grid[129][(COL / 2) +1][0] = 1;
        
        //adding land use master plan
        
        
        //adding urban structure
		
        
        //adding transportation bus stop
	}

	public int getDefaultSize(){
       return this.DEFAULT_SIZE;
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
    
        total = (grid[y][x][0] != 0) ? -1 : 0;
        for (m = -1; m <= +1; m++) {
            for (n = -1; n <= +1; n++) {
                if (grid[(ROW + (y + m)) % ROW][(COL + (x + n)) % COL][0] != 0) {
                    total++;
                }
            }
        }
        return total;
    }
    
    private void duplicateGrid(double[][][] source, double[][][] dest) {
        int i, j;
        for (i = 0; i < ROW; i++) {
            for (j = 0; j < COL; j++) {
                dest[i][j][0] = source[i][j][0];
                dest[i][j][1] = source[i][j][1];

            }
        }    
    }
    
    public void nextGeneration() {
        int i, j, neighbors;
        initGrid(gridCopy);
        
        for (i = 0; i < ROW; i++) {
            for (j = 0; j < COL; j++) {
             
            	neighbors = calc(i, j);
            
                if (grid[i][j][0] != 0) {
                    if ((neighbors >= min) && (neighbors <= max)) {
                        gridCopy[i][j][0] = neighbors;
                    }
                    
                } else {
                    if (neighbors == hit) {
                        gridCopy[i][j][0] = hit;
                    }
                }
            }
        }
        
        initGrid(grid);
        duplicateGrid(gridCopy, grid);
    }
    
    private void initGrid(double[][][] matrix) {
        int i, j;
        for (i = 0; i < ROW; i++) {
            for (j = 0; j < COL; j++) {
                matrix[i][j][0] = 0;
            }
        }
    }
    
   
    
  
}