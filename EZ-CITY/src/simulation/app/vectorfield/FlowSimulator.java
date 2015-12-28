package simulation.app.vectorfield;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_2D;
import java.util.Arrays;

import simulation.app.celluar.MultiAgentCAModel;

/**
 *
 * @author michel
 */
public class FlowSimulator {
    
    public final int DIVERGENCE_VELOCITY = 0;
    public final int DIVERGENCE_FORCE = 1;
    private static int DIVERGENCE = 0;
    private static boolean CONSTANT_VELOCITY_FIELD = false;
    
    int winWidth;
    int winHeight;
    
    //size of the simulation grid
    int gridsize;
    //simulation time step
    double dt = 0.4;
    //fluid viscosity
    double visc = 0.001;
    // velocity field at the current (vx,vy) moment and previous (vx0,vy0) moment
    double vx[], vy[];
    double vx0[], vy0[];
    // velocity magnitude
    double vm[];
    //(fx,fy)   = user-controlled simulation forces, steered with the mouse
    double fx[], fy[];
    //force magnitude
    double fm[];
    //smoke density at the current (rho) and previous (rho0) moment
    double rho[], rho0[];
    double d[];
    //determines whether flow is running or not
    boolean paused = false;
    /*
     * FFT object: Computes 2D Discrete Fourier Transform (DFT) of complex and
     * real, double precision data.
     * See: http://incanter.org/docs/parallelcolt/api/edu/emory/mathcs/jtransforms/fft/DoubleFFT_2D.html
     */
    DoubleFFT_2D fft;
    // temporary fields used for FFT filter
    double[] vx0_tmp, vy0_tmp;
    
	private MultiAgentCAModel m_ca;
  
    
    /**
     * Constructor. Initialize the simulator with the given grid size:<br>
     * 1) Stablish the grid size.<br>
     * 2) Call <code>init_simulation</code>.<br>
     * 3) Stablish the panel (new instance of <code>FlowSimulatorPanel</code>).
     * @param gridSize 
     */
    public FlowSimulator(int gridSize, int width, int height) {
      this.gridsize = gridSize;
      this.winWidth = width;
      this.winHeight = height;
      
      init_simulation(gridSize);
    }

 
    /**
     * @param flag which starts or stops the simulator.
     */
    public void setPaused(boolean flag) { paused = flag; }
    /**
     * @return size of the simulation grid
     */
    public int getGridSize() { return gridsize; }
    /**
     * @return simulation time step
     */
    public double getTimeStep() { return dt; }
    /**
     * Sets new simulation time step
     * @param dt , new simulation time step.
     */
    public void setTimeStep(double dt) { this.dt = dt; }
    /**
     * @return fluid viscosity
     */
    public double getViscosity() { return visc; }
    /**
     * Sets new fluid viscosity.
     * @param visc , new fluid viscosity.
     */
    public void setViscosity(double visc) { this.visc = visc; }
    /**
     * @return x-component of the velocity field
     */
    public double[] getVelocityX() { return Arrays.copyOf(vx, vx.length); }
    /**
     * @return y-component of the velocity field
     */
    public double[] getVelocityY() { return Arrays.copyOf(vy, vy.length); }
    
    /**
     * @return density field
     */
    public double[] getDensity() { return Arrays.copyOf(rho, rho.length); }
    /**
     * @return Magnitude of velocity
     */
    public double[] getVelocityMagnitude(){ return Arrays.copyOf( vm, vm.length ); }
    /**
     * Set the smoke density at position (x,y)
     * @param x position
     * @param y position
     * @param smoke density to add at position (x,y)
     */
    public void setDensity(int x, int y, double density) { rho[y * gridsize + x] = density; }

    /**
     * Add a force with direction (dx,dy) at position (x,y)
     * @param x position
     * @param y position
     * @param dx , x direction of the force
     * @param dy , y direction of the force
     */
    public void addForce(int x, int y, double dx, double dy) {
      fx[y * gridsize + x] += dx;
      fy[y * gridsize + x] += dy;
    }
    
    public double[] getForceX() { return Arrays.copyOf(fx, fx.length); };
    
    public double[] getForceY() { return Arrays.copyOf(fy, fy.length); };
    
    
    public double[] getForceMagnitude() { return Arrays.copyOf( fm, fm.length ); }
    
    public double[] getDivergence(){ return Arrays.copyOf(d, d.length); }
    
    public void enableConstantVelocityField( boolean enable ){ CONSTANT_VELOCITY_FIELD = enable; }
    
    /**
     * Sets which divergence to calculate
     * @param option : Velocity or Force (vector field).
     */
    public void setWhichDivergence( int option ){
        switch( option ){
            case DIVERGENCE_VELOCITY:
                DIVERGENCE = DIVERGENCE_VELOCITY;
                break;
            case DIVERGENCE_FORCE:
                DIVERGENCE = DIVERGENCE_FORCE;
                break;
        }
    }
    
    /**
     * Initialize simulation data structures as a function of the grid size 'n'.
     * Although the simulation takes place on a 2D grid, we allocate all data structures as 1D arrays,
     * for compatibility with the FFTW numerical library.<br>
     * 1) Create velocity arrays of dimension 2n(n/(2+1)), with zero values.<br>
     * 2) Create the force and smoke arrays of dimension n*n, with zero values. <br>
     * 3) Create the <code>DoubleFFT_2D</code> object with n as parameters.<br>
     * 4) Create the auxiliary arrays for FFT of dimension 2n*n, with zero values.<br>
     * @param n , grid size.
     */
    private void init_simulation(int n) {
      //Allocate data structures
      int dim = n * 2 * (n / 2 + 1);
      vx = new double[dim];
      vy = new double[dim];
      vm = new double[dim];
      vx0 = new double[dim];
      vy0 = new double[dim];
      //Initialize data structures to 0
      for (int i = 0; i < dim; i++){
         vx[i] = vy[i] = vm[i] = vx0[i] = vy0[i] = 0.0;
      }
      //Allocate force and smoke density
      dim = n * n; 
      fx = new double[dim];
      fy = new double[dim];
      fm = new double[dim];
      d = new double[dim];
      rho = new double[dim];
      rho0 = new double[dim];
      //Prepare Fourier-Transform, of 'n' rows and 'n' columns.
      fft = new DoubleFFT_2D(n, n);
      vx0_tmp = new double[n * 2 * n];
      vy0_tmp = new double[n * 2 * n];
      //Initialize data structures to 0
      for (int i = 0; i < dim; i++){
         fx[i] = fy[i] = fm[i] = rho[i] = rho0[i] = d[i] = 0.0;
      }
    }
    
    /**
     * Adjusts x to a convenient integer.
     * @param x to adjust.
     * @return convenient integer.
     */
    int clamp(double x) {
      return ((x) >= 0.0 ? ((int) (x)) : (-((int) (1 - (x)))));
    }

    /**
     * Solve (compute) one step of the fluid flow simulation
     * @param n
     * @param vx
     * @param vy
     * @param vx0
     * @param vy0
     * @param visc
     * @param dt 
     */
    void solve(int n, double[] vx, double[] vy, double[] vx0, double[] vy0, double visc, double dt) {
      double x, y, x0, y0, f, r, U[] = new double[2], V[] = new double[2], s, t;
      int i, j, i0, j0, i1, j1, idxm1, idxp1, idym1, idyp1;
      for (i = 0; i < n * n; i++) {
    	  
    	  
    	  int row = i/n;
    	  int col = i%n;
    	  
    	  if(m_ca.grid[row][col][2] == 0){
     		 continue; 
     	 }
         vx[i] += dt * vx0[i];
         vx0[i] = vx[i];
         vy[i] += dt * vy0[i];
         vy0[i] = vy[i];
      }
      
      
      for (x = 0.5 / n, i = 0; i < n; i++, x += 1.0 / n) {
         for (y = 0.5 / n, j = 0; j < n; j++, y += 1.0 / n) {
        	 
        	 if(m_ca.grid[i][j][2] == 0){
         		 continue; 
         	 }
        	 
        	 
            x0 = n * (x - dt * vx0[i + n * j]) - 0.5f;
            y0 = n * (y - dt * vy0[i + n * j]) - 0.5f;
            i0 = clamp(x0);
            s = x0 - i0;
            i0 = (n + (i0 % n)) % n;
            i1 = (i0 + 1) % n;
            j0 = clamp(y0);
            t = y0 - j0;
            j0 = (n + (j0 % n)) % n;
            j1 = (j0 + 1) % n;
            vx[i + n * j] = (1 - s) * ((1 - t) * vx0[i0 + n * j0] + t * vx0[i0 + n * j1]) + s * ((1 - t) * vx0[i1 + n * j0] + t * vx0[i1 + n * j1]);
            vy[i + n * j] = (1 - s) * ((1 - t) * vy0[i0 + n * j0] + t * vy0[i0 + n * j1]) + s * ((1 - t) * vy0[i1 + n * j0] + t * vy0[i1 + n * j1]);
         }
      }
      
      
      // copy to temporary complex arrays; imaginary components are 0
      for (int k = 0; k < n; ++k) {
         for (int l = 0; l < n; ++l) {
        	 
        	 if(m_ca.grid[k][l][2] == 0){
         		 continue; 
         	 }
        	 
        	 
            vx0_tmp[2*(k + n * l)] = vx[k + n * l];
            vy0_tmp[2*(k + n * l)] = vy[k + n*l];

            vx0_tmp[2*(k + n * l) + 1] = 0.0;
            vy0_tmp[2*(k + n * l) + 1] = 0.0;
         }
      }
      
      
      fft.complexForward(vx0_tmp);
      fft.complexForward(vy0_tmp);
      
      
      for (i = 0; i < 2*n; i += 2) {
         x = i <= n ? 0.5*i : (0.5*i - n);
         for (j = 0; j < n; j++) {
        	 
//        	 
//        	 if(m_ca.grid[i][j][2] == 0){
//         		 continue; 
//         	 }
        	 
        	 
            y = j <= n / 2 ? (double) j : (double) (j - n);
            r = x * x + y * y;
            if (r == 0.0f) {
               continue;
            }
            f = Math.exp(-r * dt * visc);
            U[0] = vx0_tmp[i + 2 * n * j]; 
            V[0] = vy0_tmp[i + 2 * n * j]; 
            U[1] = vx0_tmp[i + 1 + 2 * n * j]; 
            V[1] = vy0_tmp[i + 1 + 2 * n * j]; 

            vx0_tmp[i + 2 * n * j] = f * ((1 - x * x / r) * U[0] - x * y / r * V[0]);
            vx0_tmp[i + 1 + 2 * n * j] = f * ((1 - x * x / r) * U[1] - x * y / r * V[1]);

            vy0_tmp[i + 2 * n * j] = f * (-y * x / r * U[0] + (1 - y * y / r) * V[0]);
            vy0_tmp[i + 1 + 2*n*j] = f * (-y * x / r * U[1] + (1 - y * y / r) * V[1]);
         }
      }
      fft.complexInverse(vx0_tmp, true);
      fft.complexInverse(vy0_tmp, true);
      
      
      
      // take out real components and calculate velocity magnitude
      for (i = 0; i < n; i++) for (j = 0; j < n; j++) {
    	  
    		 if(m_ca.grid[i][j][2] == 0){
         		 continue; 
         	 }
    		 
          x=vx0_tmp[2*(i + n * j)];
          y=vy0_tmp[2*(i + n * j)];
          vx[i + n * j] = x;
          vy[i + n * j] = y;
          vm[i + n * j] = Math.sqrt(x*x+y*y);    
      }
      
      if( CONSTANT_VELOCITY_FIELD ){ vx[10+31*64]=.020; }
      
      for( i=0; i<n; i++ ) {
    	  for( j=0; j<n; j++ ){
    		  
    			 if(m_ca.grid[i][j][2] == 0){
             		 continue; 
             	 }
    			 
    			 
          //id = j+i*n;
          idxm1 = j+i*n-1; idxp1 = j+i*n+1;
          idym1 = j+i*n-n; idyp1 = j+i*n+n;
          if( j==0 ){idxm1 = i*n+n-1;}
          if( j==n-1 ){idxp1 = i*n;}
          if( i==0 ){idym1 = n*(n-1)+j;}
          if( i==n-1 ){idyp1 = j;}
          switch( DIVERGENCE ){
              case DIVERGENCE_VELOCITY:
                  d[j+i*n] = (vx[idxp1]-vx[idxm1])*0.5 + (vy[idyp1]-vy[idym1])*0.5;
                  //BW: d[i] = (vx[i]-vx[idxp1]) + (vy[i]-vy[idxp1]);
                  break;
              case DIVERGENCE_FORCE:
                  d[j+i*n] = (fx[idxp1]-fx[idxm1])*0.5 + (fy[idyp1]-fy[idym1])*0.5;
                  break;
          }
      }
      
    }
}

    /**
     * This function diffuses matter that has been placed in the velocity field. It's almost identical to the
     * velocity diffusion step in the function above. The input matter densities are in rho0 and the result is written into rho.
     * @param n
     * @param vx , fftw_real
     * @param vy , fftw_real
     * @param rho , fftw_real
     * @param rho0 , fftw_real
     * @param dt 
     */
    void diffuse_matter(int n, double/*fftw_real*/[] vx, double/*fftw_real*/[] vy, double/*fftw_real*/[] rho, double/*fftw_real*/[] rho0, double dt) {
      double/*fftw_real*/ x, y, x0, y0, s, t;
      int i, j, i0, j0, i1, j1;
      for (x = 0.5 / n, i = 0; i < n; i++, x += 1.0 / n) {
         for (y = 0.5 / n, j = 0; j < n; j++, y += 1.0 / n) {
        	 
        	 if(m_ca.grid[i][j][2] == 0){
         		 continue; 
         	 }
        	 
            x0 = n * (x - dt * vx[i + n * j]) - 0.5;
            y0 = n * (y - dt * vy[i + n * j]) - 0.5;
            i0 = clamp(x0);
            s = x0 - i0;
            i0 = (n + (i0 % n)) % n;
            i1 = (i0 + 1) % n;
            j0 = clamp(y0);
            t = y0 - j0;
            j0 = (n + (j0 % n)) % n;
            j1 = (j0 + 1) % n;
            rho[i + n * j] = (1 - s) * ((1 - t) * rho0[i0 + n * j0] + t * rho0[i0 + n * j1]) + s * ((1 - t) * rho0[i1 + n * j0] + t * rho0[i1 + n * j1]);
         }
      }
    }

    /**
     * Copy user-controlled forces to the force vectors that are sent to the solver.
     * Also dampens forces and matter density to get a stable simulation.
     */
    void set_forces() {
      int i;
      for (i = 0; i < gridsize * gridsize; i++) {
    	  
    	  
    	 int row = i / gridsize;
    	 int col = i % gridsize;
    	 
    	 if(m_ca.grid[row][col][2] == 0){
    		 continue;
    		 
    	 }
    	  
         rho0[i] = 0.995f * rho[i];
         fx[i] *= 0.85f;
         fy[i] *= 0.85f;
         vx0[i] = fx[i];
         vy0[i] = fy[i];
         fm[i] = Math.sqrt(fx[i]*fx[i]+fy[i]*fy[i]);
      }
    }

    /**
     * Do one complete cycle of the simulation or only "set forces" for next cycle:<br>
     * 1. Is simulation paused?<br>
     * 2.a <b>No</b>:<br>
     * 2.a.1 call {@code set_forces}<br>
     * 2.a.2 call {@code solve} to attend forces from the user<br>
     * 2.a.3 call {@code diffuse_matter} to compute a new set of velocities<br>
     * 2.b <b>Yes</b>, prepare cycle by only setting matter to diffuse:<br>
     * 2.b.1 call {@code set_forces}<br>
     */
    public void do_one_simulation_step() {
      if (!paused) {
         set_forces();
         solve(gridsize, vx, vy, vx0, vy0, visc, dt);
         diffuse_matter(gridsize, vx, vy, rho, rho0, dt);
      } else set_forces();
    }


	public void blindWithCA(MultiAgentCAModel ca) {
		// TODO Auto-generated method stub
		this.m_ca = ca;
	}

}


