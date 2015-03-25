/**
  *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT3 PHASE B
 * 
 * This class help BuildGridVersion4 add the grids from right and left in parallel.
 */

import java.util.concurrent.*;

@SuppressWarnings("serial")
public class AddGridVersion4 extends RecursiveAction{
	private int lo;
    private int hi;
    private int x;
    private int[][] grid1;
    private int[][] grid2;
    private int[][] res;
    public static final int CUTOFF = 100;
    
    /**
     * Construct a new BuildGridVersion4
     * @param low lower bound
     * @param high upper bound
     * @param grid1 the grid from right
     * @param grid2 the grid from left
     * @param res the combined grid
     * @param pack a objects that contains the fields for unchanging values
     */
    AddGridVersion4(int low, int high, int[][] grid1, int[][] grid2, int[][] res, int x) {
		lo = low;
		hi = high;
		this.grid1 = grid1;
		this.grid2 = grid2;
		this.res = res;
		this.x = x;
	}
	
	// add the grid from left and right
    @Override
	protected void compute() {
		if (hi - lo < CUTOFF) {
			for (int i = lo; i < hi; i++) {
				res[i / x][i % x] = grid1[i / x][i % x] + grid2[i / x][i % x];
			}
		} else {
			AddGridVersion4 left = new AddGridVersion4(lo, (hi + lo) / 2, grid1, grid2, res, x);
			AddGridVersion4 right = new AddGridVersion4((hi + lo) / 2, hi, grid1, grid2, res, x);
			left.fork();
    		right.compute();
    		left.join();
		}
	}
}
