/**
 * @author Mengyuan Huang, Ruijia Wang
 * 
 * test for version3
 */

import static org.junit.Assert.*;

import org.junit.Test;

public class Version4Test {
	private static final int TIMEOUT = 2000; // 2000ms = 2sec
	private Version4 ver;
	
    @Test(timeout = TIMEOUT)
    public void testFindCorners() {
    	ver = new Version4(PopulationQuery.parse("CenPop2010.txt"), 10000, 100, 500);
        assertEquals("The corners should be","[left=-173.033 right=-65.30086 top=1.8039697 "
                + "bottom=0.31838202]",ver.findCorners().toString());
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQueryBorder() {
    	ver = new Version4(PopulationQuery.parse("CenPop2010.txt"), 10000, 100, 500);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 100, 500, 1, 1, 100, 500);
        assertEquals("The population should be", 312471327, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQueryEmpty() {
    	ver = new Version4(PopulationQuery.parse("CenPop2010.txt"), 10000, 100, 500);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 100, 500, 50, 100, 50, 100);
        assertEquals("The population should be", 0, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery1() {
    	ver = new Version4(PopulationQuery.parse("CenPop2010.txt"), 10000, 20, 25);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 20, 25, 1, 1, 5, 4);
        assertEquals("The population should be", 1360301, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery2() {
    	ver = new Version4(PopulationQuery.parse("CenPop2010.txt"), 10000, 20, 25);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 20, 25, 1, 12, 9, 25);
        assertEquals("The population should be", 710231, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery3() {
    	ver = new Version4(PopulationQuery.parse("CenPop2010.txt"), 10000, 20, 25);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 20, 25, 9, 1, 20, 13);
        assertEquals("The population should be", 310400795, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery4() {
    	ver = new Version4(PopulationQuery.parse("CenPop2010.txt"), 10000, 20, 25);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 20, 25, 1, 1, 20, 25);
        assertEquals("The population should be", 312471327, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery5() {
    	ver = new Version4(PopulationQuery.parse("CenPop2010.txt"), 10000, 20, 25);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 20, 25, 1, 1, 20, 4);
        assertEquals("The population should be", 36493611, population);
    }

    @Test(timeout = TIMEOUT)
    public void testCalculateQuery6() {
    	ver = new Version4(PopulationQuery.parse("CenPop2010.txt"), 10000, 20, 25);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 20, 25, 9, 1, 11, 25);
        assertEquals("The population should be", 52392739, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery7() {
    	ver = new Version4(PopulationQuery.parse("CenPop2010.txt"), 10000, 9, 14);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 9, 14, 5, 5, 7, 5);
        assertEquals("The population should be", 18820388, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery8() {
    	ver = new Version4(PopulationQuery.parse("CenPop2010.txt"), 10000, 9, 14);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 9, 14, 6, 3, 8, 4);
        assertEquals("The population should be", 105349619, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery9() {
    	ver = new Version4(PopulationQuery.parse("sample.txt"), 2, 5, 5);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 5, 5, 1, 1, 5, 5);
        assertEquals("The population should be", 11841, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery10() {
    	ver = new Version4(PopulationQuery.parse("sample.txt"), 2, 5, 5);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 5, 5, 1, 3, 2, 3);
        assertEquals("The population should be", 0, population);
    }    
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery11() {
    	ver = new Version4(PopulationQuery.parse("sample.txt"), 2, 5, 5);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 5, 5, 1, 1, 2, 3);
        assertEquals("The population should be", 1865, population);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCalculateQuery12() {
    	ver = new Version4(PopulationQuery.parse("sample.txt"), 2, 5, 5);
        Rectangle rect = ver.findCorners();
        int population = ver.calculateQuery(rect, 5, 5, 1, 1, 1, 1);
        assertEquals("The population should be", 0, population);
    }
}
