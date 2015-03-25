/**
 * @author Mengyuan Huang, Ruijia Wang
 * This is the test code for Version2
 */
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class Version2Test {
	private static final int TIMEOUT = 2000; // 2000ms = 2sec
	private Version2 ver;
	
	@Before
	public void Setup() {
		ver = new Version2(PopulationQuery.parse("CenPop2010.txt"), 10000);
	}
    
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQueryBorder() {
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 100, 500, 1, 1, 100, 500);
        assertEquals("The population should be", 312471327, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQueryEmpty() {
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 100, 500, 50, 100, 50, 100);
        assertEquals("The population should be", 0, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery1() {
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 20, 25, 1, 1, 5, 4);
        assertEquals("The population should be", 1360301, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery2() {
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 20, 25, 1, 12, 9, 25);
        assertEquals("The population should be", 710231, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery3() {
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 20, 25, 9, 1, 20, 13);
        assertEquals("The population should be", 310400795, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery4() {
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 20, 25, 1, 1, 20, 25);
        assertEquals("The population should be", 312471327, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery5() {
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 20, 25, 1, 1, 20, 4);
        assertEquals("The population should be", 36493611, population);
    }

    @Test(timeout = TIMEOUT)
    public void testCalculateQuery6() {
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 20, 25, 9, 1, 11, 25);
        assertEquals("The population should be", 52392739, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery7() {
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 9, 14, 5, 5, 7, 5);
        assertEquals("The population should be", 18820388, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery8() {
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 9, 14, 6, 3, 8, 4);
        assertEquals("The population should be", 105349619, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery9() {
    	ver = new Version2(PopulationQuery.parse("sample.txt"), 2);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 5, 5, 1, 1, 5, 5);
        assertEquals("The population should be", 11841, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery10() {
    	ver = new Version2(PopulationQuery.parse("sample.txt"), 2);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 5, 5, 1, 3, 2, 3);
        assertEquals("The population should be", 0, population);
    }    
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery11() {
    	ver = new Version2(PopulationQuery.parse("sample.txt"), 2);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 5, 5, 1, 1, 2, 3);
        assertEquals("The population should be", 1865, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery12() {
    	ver = new Version2(PopulationQuery.parse("sample.txt"), 2);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 5, 5, 1, 1, 1, 1);
        assertEquals("The population should be", 0, population);
    }
}
