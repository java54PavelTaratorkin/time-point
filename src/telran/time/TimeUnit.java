package telran.time;

public enum TimeUnit {
HOUR(3600), MINUTE(60), SECOND(1);
	int value;
	private TimeUnit(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public TimePoint between(TimePoint point1, TimePoint point2) {
		//TODO
		
		return null;
	}
}
