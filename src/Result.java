/**
 * @author Mengyuan Huang, Ruijia Wang
 * THis is the object that has fields for all the unchanged values
 * passed to ForkJoin tasks in Version4.java
 */

public class Result {
    public int size; 
    public CensusGroup[] data;
    public Rectangle rect;
    public int cutoff;
    public int x;
    public int y;
    
    public Result(CensusGroup[] data, Rectangle rect, int x, int y, int size, int cutoff){
        this.size = size;
        this.data = data;
        this.rect = rect;
        this.cutoff = cutoff;
        this.x = x;
        this.y = y;
    }
}