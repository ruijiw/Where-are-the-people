/**
 * @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT3 PHASE A
 * 
 * an interface for all the versions.
 */
public abstract class Calculator {
	
	/**
	 * Find the four corners of the rectangle.
	 * @return rectangle that include the four corners
	 */
    public abstract Rectangle findCorners();
    
    /**
	 * Calculate the population in the rectangle.
	 * @param rect large rectangle of the map
	 * @param x how many columns
	 * @param y how many rows
	 * @param west left border of the query rectangle
	 * @param east right border of the query rectangle
	 * @param south bottom border of the query rectangle
	 * @param north upper border of the query rectangle
	 * @return population in the query rectangle
	 */
    public abstract int calculateQuery(Rectangle rect, int x, int y, int north, int south, int west, int east);
}