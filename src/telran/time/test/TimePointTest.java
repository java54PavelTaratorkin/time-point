package telran.time.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import telran.employees.Manager;
import telran.time.*;
import telran.util.Arrays;

class TimePointTest {

	@Test
	void testBetween() {
		TimePoint point1 = new TimePoint(10, TimeUnit.HOUR);
		TimePoint point2 = new TimePoint(3600 * 20, TimeUnit.SECOND);
		
		TimePoint point3 = TimeUnit.MINUTE.between(point1, point2);
		assertEquals(600, point3.getAmount());
		assertEquals(TimeUnit.MINUTE, point3.getTimeUnit());
		
		TimePoint point4 = TimeUnit.SECOND.between(point1, point2);
		assertEquals(36000, point4.getAmount());
		assertEquals(TimeUnit.SECOND, point4.getTimeUnit());
		
		TimePoint point5 = new TimePoint(10, TimeUnit.HOUR);
		TimePoint pointBetween = point5.getTimeUnit().between(point1, point2);
		assertEquals(10, pointBetween.getAmount());
		assertEquals(TimeUnit.HOUR, pointBetween.getTimeUnit());
	}
	
	@Test
	void convertTest() {
		TimePoint timePoint = new TimePoint(10, TimeUnit.HOUR);
		TimePoint point1Actual = timePoint.convert(TimeUnit.SECOND);
		assertEquals(36000, point1Actual.getAmount());
	}
	
	@Test
	void plusAdjusterTest() {
		TimePoint timePoint1 = new TimePoint(10, TimeUnit.HOUR);
		TimePoint timePoint2 = new TimePoint(60, TimeUnit.MINUTE);
		TimePoint actual = timePoint2.with(new PlusAdjuster(timePoint1));
		assertEquals(660, actual.getAmount());
		assertEquals(TimeUnit.MINUTE, actual.getTimeUnit());
	}
	
	@Test
	void timePointEqualsTest() {
		TimePoint timePoint1 = new TimePoint(10, TimeUnit.HOUR);
		TimePoint timePoint2 = new TimePoint(600, TimeUnit.MINUTE);	
		TimePoint timePoint3 = new TimePoint(660, TimeUnit.MINUTE);
		TimePoint timePoint4 = null;
        TimePoint[] points1 = {
                new TimePoint(15, TimeUnit.HOUR),
                new TimePoint(20, TimeUnit.SECOND),
                new TimePoint(300, TimeUnit.SECOND)
        };
		
        TimePoint[] points2 = {
                new TimePoint(15, TimeUnit.HOUR),
                new TimePoint(20, TimeUnit.SECOND),
                new TimePoint(5, TimeUnit.MINUTE)
        };
        
        assertArrayEquals(points1, points2);
        assertEquals(null, timePoint4);        
		assertEquals (timePoint2, timePoint1);
		
		assertNotEquals (timePoint1, timePoint3);
		assertNotEquals (timePoint3, timePoint2);
		
		Manager manager = new Manager(101, 2000, "PM", 0.5F);
		assertFalse(timePoint1.equals(manager));
	}
	
	@Test
	void timePointCompareToTest() {
        TimePoint[] pointsActual = {
                new TimePoint(15, TimeUnit.HOUR),
                new TimePoint(20, TimeUnit.SECOND),
                new TimePoint(5, TimeUnit.MINUTE)
        };
		
        TimePoint[] pointsExpected = {
        		new TimePoint(20, TimeUnit.SECOND),
                new TimePoint(5, TimeUnit.MINUTE),
                new TimePoint(15, TimeUnit.HOUR)                
        };
        Arrays.bubbleSort(pointsActual, Comparator.naturalOrder());
        assertArrayEquals(pointsExpected, pointsActual);
	}
	
	@Test
	void futureProximityAdjusterTest() {
        TimePoint timePoint1 = new TimePoint(15, TimeUnit.HOUR);
        TimePoint timePoint2 = new TimePoint(5, TimeUnit.MINUTE);
        TimePoint timePoint3 = new TimePoint(4, TimeUnit.MINUTE);
        TimePoint timePoint4 = new TimePoint(17, TimeUnit.HOUR);
        TimePoint timePoint5 = new TimePoint(19, TimeUnit.HOUR);
        TimePoint[] points1 = {
                new TimePoint(15, TimeUnit.HOUR),
                new TimePoint(20, TimeUnit.SECOND),
                new TimePoint(19, TimeUnit.HOUR),
                new TimePoint(20, TimeUnit.SECOND),
                new TimePoint(19, TimeUnit.HOUR),
                new TimePoint(5, TimeUnit.MINUTE),
                new TimePoint(15, TimeUnit.HOUR),
                new TimePoint(2, TimeUnit.MINUTE)
        };        
        
		FutureProximityAdjuster futureProximityAdjuster1 = new FutureProximityAdjuster(points1);
       
        assertEquals(new TimePoint(19, TimeUnit.HOUR), 
        		timePoint1.with(futureProximityAdjuster1));
        assertEquals(new TimePoint(15, TimeUnit.HOUR), 
        		timePoint2.with(futureProximityAdjuster1));
        assertEquals(new TimePoint(5, TimeUnit.MINUTE), 
        		timePoint3.with(futureProximityAdjuster1));
        assertEquals(new TimePoint(19, TimeUnit.HOUR), 
        		timePoint4.with(futureProximityAdjuster1));
        assertEquals(null, 
        		timePoint5.with(futureProximityAdjuster1));

        assertThrowsExactly(NullPointerException.class, 
        		() -> new TimePoint(5, TimeUnit.MINUTE).with(new FutureProximityAdjuster(null)));
	}

}
