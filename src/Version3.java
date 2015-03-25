/**
 * @author Mengyuan Huang, Ruijia Wang
 * CSE 332 
 * PROJECT3 PHASE B
 * 
 * This is the third version to process data to find the four corners
 * of U.S. rectangle and answer the query. It will perform additional 
 * preprocessing so that each query can be answered in O(1) time.
 */
public class Version3 extends Calculator {
    private int size;
    private CensusGroup[] data;
    private int[][] grid;
    
    
    /**
	 * Construct a new Version3
	 * @param input file that contains the census-block-group data
	 * @param x the number of columns
	 * @param y the number of rows
	 */
    public Version3(CensusData input, int x, int y){
        size = input.data_size;
        data = input.data;
        grid = new int[y][x];
    }
    
    /** {@inheritDoc} */
    public Rectangle findCorners(){
    	Rectangle rect = Common.findCorners(data, size);
    	//build the grid.
    	int y = grid.length;
    	int x = grid[0].length;
        for(int i = 0; i <  size; i++){
        	//find the correct column and row number to store population in data[i]
        	int column = Common.getCol(rect, data[i].longitude, x); 
        	int row = Common.getRow(rect, data[i].latitude, y);
       		grid[row][column] += data[i].population;
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
