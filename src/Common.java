/**
 * @author Mengyuan Huang, Ruijia Wang
 * This is the file to store code shared in different versions.
 */

import java.util.concurrent.ForkJoinPool;


public class Common {
	private final static ForkJoinPool fjPool = new ForkJoinPool();
	
	/**
	 * This is the method shared by Version 1 and Version 3. It will
	 * find the boundaries of the U.S. rectangle and return it.
	 * @param data input data from file.
	 * @param size size of data.
	 * @return rectangle that has the boundaries.
	 */
    public static Rectangle findCorners(CensusGroup[] data, int size){
        float longitude = data[0].longitude;
        float latitude = data[0].latitude;
        Rectangle result = new Rectangle(longitude, longitude, latitude, latitude);
        for(int i = 0; i < size; i++){
            result.top = Math.max(data[i].latitude, result.top);
            result.bottom = Math.min(data[i].latitude, result.bottom);
            result.left = Math.min(data[i].longitude, result.left);
            result.right = Math.max(data[i].longitude, result.right);
        }
        return result;
    }
    
    /**
     * This is the method shared by Version 3 and Version 4. It will
     * calculate the population asked by user query. It will use grid 
     * array to help calculate the total population for user query.
     * The runtime for this method is O(1).
     * @param rect U.S. rectangle
     * @param grid 2-D array that each element holds the total population in the upper-left corner.
     * @param x column number of grid
     * @param y row number of grid
     * @param west
     * @param south
     * @param east
     * @param north
     * @return return population.
     */
    public static int calculateQuery(Rectangle rect,int[][] grid, int x, int y, int west, int south, int east, int north){
        // use the arithmetic trick to calculate population asked by the query.
    	// original = grid[i][j] - grid[i][j + 1] - grid[i - 1][j] + grid[i - 1][j + 1]
    	int upLeft = 0; // grid above the top-right corner
        int swLeft = 0; //grid just left of the bottom left corner
        int aboveNe = 0; //grid above and to the upper-left corner
        if(north < y && west - 2 >= 0 ){
        	upLeft = grid[north][west - 2];
        }
        if(west - 2 >= 0){
        	swLeft = grid[south - 1][west - 2];
        }
        if(north < y){
        	aboveNe = grid[north][east - 1];
        }
        return grid[south - 1][east - 1] - swLeft - aboveNe + upLeft;
    }    
    
    /**
     * help method to build the grid array.
     * @param rect U.S. rectangle
     * @param x column number
     * @param y row number
     * @return grid
     */
    public static int[][] buildGrid(Rectangle rect, int x, int y, int[][] grid){
        //modify the grid so each element holds the total population in the upper-left rectangle.
        for(int i = y - 2; i >= 0; i--){ 
        	grid[i][0] = grid[i + 1][0] + grid[i][0];
        }
        for(int j = 1; j < x; j++){ 
        	grid[y - 1][j] = grid[y-1][j-1] + grid[y - 1][j];
        }
        for(int i = 1; i < x; i++){
        	for(int j = y - 2; j >= 0; j--){
        		grid[j][i] = grid[j][i] + grid[j][i-1] + grid[j + 1][i] - grid[j + 1][i - 1];
        	}
        }
        return grid;
    }
    
    
    /**
     * This is the method shared by Version 2 and Version 4. 
     * It will use parallel algorithm to calculate the boundaries of
     * U.S. rectangle.
     * @param data input data from file.
     * @param size size of input data.
     * @param cutoff sequential cutoff for parallel algorithm
     * @return return rectangle.
     */
    public static Rectangle findCorners2(CensusGroup[] data, int size, int cutoff ){
    	return fjPool.invoke(new FindCornersVersion2(data, 0, size, cutoff));
    }
    
    /**
     * help method to get the correct column number
     * @param rect U.S. rectangle
     * @param longitude longtitude of data[i]
     * @param x column number
     * @return
     */
    public static int getCol(Rectangle rect, float longitude, int x){
        float xUnit = (rect.right - rect.left) / x; 
		int xIndex = 0;
    	if(longitude == rect.right){
			xIndex = x - 1;
		} else {
			xIndex = (int)Math.floor((longitude - rect.left) / xUnit);
		}
    	
    	return xIndex;
    }
    
    /**
     * help method to get the correct row number
     * @param rect U.S. rectangle
     * @param latitude latitude of data[i]
     * @param y row number
     * @return
     */
    public static int getRow(Rectangle rect, float latitude, int y){
        float yUnit = (rect.top - rect.bottom) / y;
		int yIndex = 0;
   		if(latitude == rect.top){
			yIndex = y - 1;
		} else {
			yIndex = (int)Math.floor((latitude - rect.bottom) / yUnit);
		}
    	return yIndex;
    }
}
