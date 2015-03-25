/**
 * @author Mengyuan Huang, Ruijia Wang
 * CSE 332 
 * PROJECT3 PHASE B
 * 
 * This is the forth version to process data to find the four corners
 * of U.S. rectangle and answer the query. This program will use
 * ForkJoin Framework to build the grid in parallel.
 */

import java.util.concurrent.ForkJoinPool;

public class Version4 extends Calculator {
    private CensusGroup[] data;
    private int size;
    public static final ForkJoinPool fjPool = new ForkJoinPool();
    private int cutoff;
    private int[][] grid;
    
    /**
   	 * Construct a new Version4
   	 * @param input file that contains the census-block-group data
   	 * @param x the number of columns
   	 * @param y the number of rows
   	 * @param cutoff the sequential cutoff
   	 */
    public Version4(CensusData input, int cutoff, int x, int y){
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
        grid = fjPool.invoke(new BuildGridVersion4(0, size, pack));
        grid = Common.buildGrid(rect, x, y, grid);
        return rect;        
    }
    
    /** {@inheritDoc} */
    public int calculateQuery(Rectangle rect, int x, int y, int west, int south, int east, 
    		int north){
        return Common.calculateQuery(rect, grid, x, y, west, south, east, north);
    }    
    
}
