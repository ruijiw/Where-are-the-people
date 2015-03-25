/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT3 PHASE A
 * 
 * This is the second version to process data to find the four
 * corners of the U.S. rectangle and answer the query. This 
 * program will use ForkJoin Framework to answer the query.
 */

import java.util.concurrent.*;

public class Version2 extends Calculator{
	private CensusGroup[] data;
    private int size;
    private final ForkJoinPool fjPool = new ForkJoinPool();
	private int cutoff;
    
	/**
	 * Constructor
	 * @param input file that contains the census-block-group data
	 * @param cutoff the sequential cutoff
	 */
    public Version2(CensusData input, int cutoff){
        data = input.data;
        size = input.data_size;
        this.cutoff = cutoff;
    }
    
    /** {@inheritDoc} */
    public Rectangle findCorners(){
    	return Common.findCorners2(data, size, cutoff);
    }
    
    /** {@inheritDoc} */
    public int calculateQuery(Rectangle rect, int x, int y, int west, int south, int east, 
    		int north){
    	float xUnit = (rect.right - rect.left) / x; 
        float yUnit = (rect.top - rect.bottom) / y;
        float minLong = rect.left + (west - 1) * xUnit;
        float maxLong = rect.left + east * xUnit;
        float minLat = rect.bottom + (south - 1) * yUnit;
        float maxLat = rect.bottom + north * yUnit;       
        Rectangle rect2 =  new Rectangle(minLong, maxLong, maxLat, minLat); 
        return fjPool.invoke(new CalculateQueryVersion2(data, 0, size, rect2, cutoff));
    }
}
