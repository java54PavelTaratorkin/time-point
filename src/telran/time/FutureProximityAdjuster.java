package telran.time;

import java.util.Comparator;

import telran.util.Arrays;

public class FutureProximityAdjuster implements TimePointAdjuster{
	TimePoint[] timePoints;
	
	public FutureProximityAdjuster(TimePoint[] points) {
		if (points == null) {
			throw new NullPointerException();
		}
		this.timePoints = Arrays.copy(points);
		Arrays.bubbleSort(this.timePoints);

	}
	
	@Override
	public TimePoint adjust(TimePoint point) {
		// getting copy of TimePoints[] and deleting from it all elements 
		// that match point.
		TimePoint[] timePointsCopy = Arrays.copy(timePoints);
		timePointsCopy = Arrays.removeIf(timePointsCopy, t -> t.equals(point));
		
		// searching closest insertion index to the point using binary search.
		int index = Arrays.binarySearch(timePointsCopy, point, Comparator.naturalOrder());
		
		TimePoint closeTimePoint = null;
		
		// assigning another value to return TimePoint only if it is lower than filtered
		// arrays length based on binary search logic.
		if ((-index - 1) < timePointsCopy.length) {
			closeTimePoint = timePointsCopy[-index - 1];
		}
		
		return closeTimePoint;
	}

}
