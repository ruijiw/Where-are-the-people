/**
 * 
 * @author Mengyuan Huang, Ruijia Wang
 * This is the timing class for write-up problem 8
 * 
 */
public class Timing8 {
	public static final int NUM_TESTS = 40;
	public static final int NUM_WARMUP = 20;
	public static final int CUTOFF = 10000;
	
	public static void main(String[] args) {
		double time = getAverageRuntime(args);
		System.out.println(time);
	}
	  public static double getAverageRuntime(String[] args) {
		    double totalTime = 0;
		    // change version here.
		      Calculator ver = new Version4(PopulationQuery.parse(args[0]), CUTOFF, 100, 500);
			    long start = System.currentTimeMillis();
			    Rectangle rect = ver.findCorners(); //time the findCorners() once.
			    long end = System.currentTimeMillis();
			    totalTime += end - start;
		    for (int i=0; i<NUM_TESTS; i++) {
		      long startTime = System.currentTimeMillis();
		      for(int j = 0; j < 100;j++){
		    	  ver.calculateQuery(rect, 100, 500, 1, 2, 10, 10);
		      }
		      long endTime = System.currentTimeMillis();
		      if (NUM_WARMUP <= i) {                    // Throw away first NUM_WARMUP runs to exclude JVM warmup
		        totalTime += (endTime - startTime);
		      }
		    }
		    return totalTime / (NUM_TESTS - NUM_WARMUP);  // Return average runtime.
		  }
}