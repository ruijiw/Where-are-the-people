/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT3 PHASE A
 * 
 * This is the first version to process data to find the four corners of the 
 * U.S. rectangle using sequential O(n) algorithm and answer query using O(n)
 * traversal.
 */

public class Version1 extends Calculator{
    private CensusGroup[] data;
    private int size;
    
    /**
	 * Constructor
	 * @param input file that contains the census-block-group data
	 */
    public Version1(CensusData input){
        data = input.data;
        size = input.data_size;
    }
    
    /** {@inheritDoc} */
    public Rectangle findCorners(){
        return Common.findCorners(data, size);
    }
    
    /** {@inheritDoc} */
    public int calculateQuery(Rectangle rect, int x, int y, int west, int south, int east, int north){
    	float xUnit = (rect.right - rect.left) / x; 
        float yUnit = (rect.top - rect.bottom) / y;
        
        int population = 0;
        float minLong = rect.left + (west - 1) * xUnit;
        float maxLong = rect.left + east * xUnit;
        float minLat = rect.bottom + (south - 1) * yUnit;
        float maxLat = rect.bottom + north * yUnit;

        for(int i = 0; i < size; i++){
            CensusGroup pos = data[i];
            if(pos.latitude >= minLat && pos.longitude >= minLong && pos.latitude <= maxLat 
            		&& pos.longitude <= maxLong){
                population += pos.population;
            }
        }
        return population;
    }   
}
