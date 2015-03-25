/**
  *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT3 PHASE A
 * 
 * This class help Version2 calculate the query.
 */

import java.util.concurrent.*;

@SuppressWarnings("serial")
public class CalculateQueryVersion2 extends RecursiveTask<Integer>{
    private int lo;
    private int hi;
    private CensusGroup[] data;
    private Rectangle rect;
    private int cutoff;

    /**
     * Construct a new CalculateGridSecondVersion2
     * @param data array that contains census-block-group data
     * @param low lower bound
     * @param high upper bound
     * @param rect query rectangle
     * @param cutoff the sequential cutoff
     */
    public CalculateQueryVersion2(CensusGroup[] data, int low, int high, Rectangle rect, int cutoff){
        lo = low;
        hi = high;
        this.data = data;
        this.rect = rect;
        this.cutoff = cutoff;
        
    }
    
    /**
     * Compute the population of the query rectangle
     * @return an integer that represent the population of the query rectangle
     */
    @Override
    protected Integer compute(){
        if (hi - lo <= cutoff){
            int population = 0;
            for (int i = lo; i < hi ;i++){
                if (data[i].longitude >= rect.left && data[i].longitude <= rect.right &&
                    data[i].latitude <= rect.top && data[i].latitude >= rect.bottom) {
                    population += data[i].population;
                }
            }
            return population;
        }else{
            CalculateQueryVersion2 left = new CalculateQueryVersion2(data, lo, (lo+hi)/2, rect, cutoff);
            CalculateQueryVersion2 right = new CalculateQueryVersion2(data, (lo+hi)/2, hi, rect, cutoff);
            left.fork();
            int rightAns = right.compute();
            int leftAns = left.join();
            return rightAns + leftAns;
        }
    }
}
