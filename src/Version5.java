/**
 * @author Mengyuan Huang, Ruijia Wang
 * CSE 332 
 * PROJECT3 PHASE B
 * 
 * This is the fifth version to process data to find the four corners
 * of U.S. rectangle and answer the query. This program will build a 
 * shared grid in parallel which is protected by locks.
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantLock;


public class Version5 extends Calculator {
	private CensusGroup[] data;
    private int size;
    private final ForkJoinPool fjPool = new ForkJoinPool();
    private int cutoff;
    private int[][] grid;
    public static final int THREADS = 4;
    
    /**
   	 * Construct a new Version5
   	 * @param input file that contains the census-block-group data
   	 * @param x the number of columns
   	 * @param y the number of rows
   	 * @param cutoff the sequential cutoff
   	 */
    public Version5(CensusData input, int cutoff, int x, int y){
        data = input.data;
        size = input.data_size;
        this.cutoff = cutoff;
        grid = new int[y][x];
    }
    
    /** {@inheritDoc} */
    public Rectangle findCorners(){
    	Rectangle rect = Common.findCorners2(data, size, cutoff);
    	int y = grid.length;
    	int x = grid[0].length;
        Result pack = new Result(data, rect, x, y, size, cutoff);
        // create a grid of locks
        ReentrantLock[][] lockGrid = new ReentrantLock[y][x];
        fjPool.invoke(new LockGridVersion5(0, x * y, lockGrid));
        
        BuildGridVersion5[] ts = new BuildGridVersion5[THREADS];

        // divide the input array and start all of the Threads
        for (int i = 0; i < THREADS; i++) {
            ts[i] = new BuildGridVersion5((i * size) / THREADS, ((i + 1) * size) / THREADS, pack, grid, lockGrid);
            ts[i].start();
        }
        
        // join all of the Threads after computation completes
        for (int i = 0; i < THREADS; i++) {
        	try {
        		ts[i].join();
    		} catch (InterruptedException e) {
    			System.out.println("The processing has been interrupted.");
                System.exit(1);
    		}
        }
      
        grid = Common.buildGrid(rect, x, y, grid);
        return rect;        
    }
    
    /** {@inheritDoc} */
    public int calculateQuery(Rectangle rect, int x, int y, int west, int south, int east, 
    		int north){
        return Common.calculateQuery(rect, grid, x, y, west, south, east, north);
    }    
}
