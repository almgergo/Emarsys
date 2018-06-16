package emarsys.duedate.test;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import emarsys.duedate.core.DueDateCalculator;

/**
 * due date calculator test cases.
 *
 * @author almge
 *
 */
public class DuedateCalculatorTest {

	@Test
	public void calculatorTest() {
		final Time startTime = new Time(2018, 11, 31, 10, 20);
		final Time expectedTime = new Time(2019, 0, 11, 9, 20);

		final int turnaroundTime = 71;

		this.runTest(startTime, expectedTime, turnaroundTime);
	}

	@Test
	public void calculatorTest2() {
		final Time startTime = new Time(2018, 05, 15, 16, 59);
		final Time expectedTime = new Time(2019, 05, 18, 9, 59);

		final int turnaroundTime = 2081;

		this.runTest(startTime, expectedTime, turnaroundTime);
	}

	private void runTest(final Time startTime, final Time expectedTime, final int turnaroundTime) {
		final Calendar now = this.getCalendarAtTime(startTime);
		final Calendar expected = this.getCalendarAtTime(expectedTime);

		// System.out.println(DueDateCalculator.calculateDueDate(now.getTime(),
		// turnaroundTime));
		Assert.assertEquals(DueDateCalculator.calculateDueDate(now.getTime(), turnaroundTime).getTime(),
				expected.getTimeInMillis());
	}

	private Calendar getCalendarAtTime(final Time startTime) {
		final Calendar now = Calendar.getInstance();
		now.set(startTime.year, startTime.month, startTime.day, startTime.hour, startTime.minute, 0);
		now.set(Calendar.MILLISECOND, 0);
		return now;
	}

	private class Time {
		int year;
		int month;
		int day;

		public Time(final int year, final int month, final int day, final int hour, final int minute) {
			super();
			this.year = year;
			this.month = month;
			this.day = day;
			this.hour = hour;
			this.minute = minute;
		}

		int hour;
		int minute;
	}
}
