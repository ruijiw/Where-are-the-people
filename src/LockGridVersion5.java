/**
  *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT3 PHASE B
 * 
 * This class help Version5 build a grid of locks.
 */

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.*;

@SuppressWarnings("serial")
public class LockGridVersion5 extends RecursiveAction {
	private static final int SEQUENTIAL_CUTOFF = 100;
    private int lo, hi;
    private ReentrantLock[][] lockGrid;

    /**
     * Construct a new LockGridVersion5
     * @param low lower bound
     * @param high upper bound
     * @param lockGrid 2-D array that each element holds a lock
     */     
    public LockGridVersion5(int low, int high, ReentrantLock[][] lockGrid) {
        lo = low;
        hi = high;
        this.lockGrid = lockGrid;
    }

    /**
     * Compute the grid of locks
     */
    @Override
    protected void compute() {
        if (hi - lo <= SEQUENTIAL_CUTOFF) {
        	for (int i = lo; i < hi; i++) {
        		lockGrid[i / lockGrid[0].length][i % lockGrid[0].length] = new ReentrantLock();
            }
        } else {
        	LockGridVersion5 left = new LockGridVersion5(lo, (hi + lo) / 2, lockGrid);
        	LockGridVersion5 right = new LockGridVersion5((hi + lo) / 2, hi, lockGrid);
			left.fork();
    		right.compute();
    		left.join();
        }
    }
}
