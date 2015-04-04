/**
 * 
 * @author Mengyuan Huang, Ruijia Wang
 * This is the timing class for write-up problem 6 and 7.
 *
 */
public class Timing1 {
	public static final int NUM_TESTS = 40;
	public static final int NUM_WARMUP = 20;
	public static final int CUTOFF = 40000;
	
	public static void main(String[] args) {
		double time = getAverageRuntime(args);
		System.out.println(time);
	}
	  public static double getAverageRuntime(String[] args) {
		    double totalTime = 0;
		    Calculator ver = new Version1(PopulationQuery.parse(args[0]));
		    // for version2: Calculator ver = new Version2(PopulationQuery.parse(args[0]), CUTOFF);
		    // Calculator ver = new Version3(PopulationQuery.parse(args[0]), 100, 500);
		    // for problem 7, using Version4 and Version5, change the grid size. Keep cutoff the same.
		    for (int i=0; i<NUM_TESTS; i++) {
		      long startTime = System.currentTimeMillis();
		      ver.findCorners();
		      long endTime = System.currentTimeMillis();
		      if (NUM_WARMUP <= i) {                    // Throw away first NUM_WARMUP runs to exclude JVM warmup
		        totalTime += (endTime - startTime);
		      }
		    }
		    return totalTime / (NUM_TESTS - NUM_WARMUP);  // Return average runtime.
		  }
}
