/**
  *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT3 PHASE B
 * 
 * This class help Version5 build a shared grid in parallel which is protected by locks.
 */

import java.util.concurrent.locks.ReentrantLock;

public class BuildGridVersion5 extends java.lang.Thread{
	private int lo;
    private int hi;
    private Result pack;
    private int[][] grid;
    private ReentrantLock[][] lockGrid;
    
    /**
     * Construct a new BuildGridVersion5
     * @param low lower bound
     * @param high upper bound
     * @param pack a objects that contains the fields for unchanging values
     * @param grid 2-D array that each element holds the population in the corresponding location.
     * @param lockGrid 2-D array that each element holds a lock
     */
    BuildGridVersion5(int low, int high, Result pack, int[][] grid, ReentrantLock[][] lockGrid) {
        lo = low;
        hi = high;
        this.pack = pack;
        this.grid = grid;
        this.lockGrid = lockGrid;
    }
    
    /**
     * Compute the grid
     */
    @Override
    public void run() {
        for (int k = lo ; k < hi; k++) {
            int j = Common.getCol(pack.rect, pack.data[k].longitude, pack.x);
            int i = Common.getRow(pack.rect, pack.data[k].latitude, pack.y);
            synchronized(lockGrid[i][j]){
            	grid[i][j] += pack.data[k].population;
            }
        }           
    }
}
