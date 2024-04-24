package telran.time;

public class TimePoint implements Comparable<TimePoint>{
	int amount;
	TimeUnit timeUnit;
	public TimePoint(int amount, TimeUnit timeUnit) {
		this.amount = amount;
		this.timeUnit = timeUnit;
	}
	public int getAmount() {
		return amount;
	}
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}
	public TimePoint convert(TimeUnit unit) {
		//returns new TimePoint with a given TimeUnit
		int newTimeAmount = amount * timeUnit.getValue() / unit.getValue();
		return new TimePoint(newTimeAmount, unit);
	}
	public TimePoint with(TimePointAdjuster adjuster) {
		//returns new TimePoint based on any TimePointAdjuster
		return adjuster.adjust(this);
	}
	@Override
	public int compareTo(TimePoint o) {
		int thisValue = this.amount * this.timeUnit.getValue();
		int otherValue = o.amount * o.timeUnit.getValue();
		return Integer.compare(thisValue, otherValue);
	}
	@Override
	public boolean equals(Object obj) {
		boolean res = false;
		if (obj != null && obj instanceof TimePoint) {
			res = this.compareTo((TimePoint)obj) == 0;
		}
		return res;
	}
	
}
