/**
  *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT3 PHASE B
 * 
 * This class help Version4 build the grid in parallel.
 */

import java.util.concurrent.*;

@SuppressWarnings("serial")
public class BuildGridVersion4 extends RecursiveTask<int[][]> {
	private int lo;
    private int hi;
    private Result pack;
    
    /**
     * Construct a new BuildGridVersion4
     * @param low lower bound
     * @param high upper bound
     * @param pack a objects that contains the fields for unchanging values
     */
    BuildGridVersion4(int low, int high, Result pack) {
		lo = low;
		hi = high;
		this.pack = pack;
	}
	
    /**
     * Compute the grid
     * @return an grid that represent the population
     */
    @Override
	protected int[][] compute() {
		if (hi - lo < pack.cutoff) {
			int[][] grid = new int[pack.y][pack.x];
			for (int k = lo ; k < hi; k++) {
				int j = Common.getCol(pack.rect, pack.data[k].longitude, pack.x);
				int i = Common.getRow(pack.rect, pack.data[k].latitude, pack.y);
				grid[i][j] += pack.data[k].population;
			}
			return grid;
 		} else {
 			BuildGridVersion4 left = new BuildGridVersion4(lo, (hi + lo) / 2, pack);
 			BuildGridVersion4 right = new BuildGridVersion4((hi + lo) / 2, hi, pack);
 			left.fork();
 			int[][] rightAns = right.compute();
 			int[][] leftAns = left.join();
			
 			// use parallel map to add the grid from left and right			
 			int[][] combineAns = new int[pack.y][pack.x];
 			Version4.fjPool.invoke(new AddGridVersion4(0, pack.x * pack.y, rightAns, leftAns, combineAns, pack.x));
 			return combineAns;
 		}
	}
}
