/**
 *  @author Mengyuan Hunang, Ruijia Wang
 * CSE 332 
 * PROJECT3 PHASE A
 * 
 * This class help Version2 find the corners of the rectangle.
 */

import java.util.concurrent.*;

@SuppressWarnings("serial")
public class FindCornersVersion2 extends RecursiveTask<Rectangle> {
	private CensusGroup[] data;
	private int hi; 
	private int lo;
	private float top; 
	private float bottom; 
	private float left; 
	private float right;
	private int cutoff;
	
	/**
	 * Construct a new FindCornersVersion2
	 * @param data array that contains census-block-group data
	 * @param low lower bound
	 * @param high upper bound
	 * @param cutoff the sequential cutoff
	 */
	public FindCornersVersion2(CensusGroup[] data, int low, int high, int cutoff) {
		this.data = data;
		hi = high; 
		lo = low;
		top = data[0].latitude; 
		bottom = data[0].latitude;
		left = data[0].longitude; 
		right = data[0].longitude;
		this.cutoff = cutoff;
	}
	/**
	 * Compute the corners of the rectangle
	 * @return an rectangle of us
	 */
	@Override
	protected Rectangle compute() {
		if (hi-lo <= cutoff) {
			for (int i = lo ; i < hi; i++){
				top = Math.max(data[i].latitude, top);
	            bottom = Math.min(data[i].latitude, bottom);
	            left = Math.min(data[i].longitude, left);
	            right = Math.max(data[i].longitude, right);
			}
			return new Rectangle(left, right, top, bottom);
		} else {
			FindCornersVersion2 left = new FindCornersVersion2(data, lo, (hi+lo)/2, cutoff);
			FindCornersVersion2 right = new FindCornersVersion2(data, (hi+lo)/2, hi, cutoff);
			left.fork();
			Rectangle rightAns = right.compute();
			Rectangle leftAns = left.join();
			float left1 = Math.min(rightAns.left, leftAns.left);
			float right1 = Math.max(rightAns.right, leftAns.right);
			float top1 = Math.max(rightAns.top, leftAns.top);
			float bottom1 = Math.min(rightAns.bottom, leftAns.bottom);
			return new Rectangle(left1, right1, top1, bottom1);
		}
	}
}
