package emarsys.duedate.core;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import emarsys.duedate.model.TimeUnit;

/**
 * Due date calculator, emarsys homework.
 *
 * @author almge
 *
 */
public class DueDateCalculator {

	protected static final int LAST_WORKHOUR = 17;
	protected static final int FIRST_WORKHOUR = 9;
	protected static final int WORKDAY_LENGTH = LAST_WORKHOUR - FIRST_WORKHOUR;

	protected static final int FIRST_WORKDAY = 2;
	protected static final int WEEK_LENGTH = 5;

	protected static final int FIRST_WORKWEEK = 1;
	protected static final int YEAR_LENGTH = 52;

	/**
	 * Define time unit list in incrementing order. For example if the turnaround
	 * time was given in minutes, a new element like "new TimeUnit(45, 15,
	 * Calendar.MINUTE)" should be added to the head of the list and this would be
	 * able to calculate dueDate for a situation where only the last 45 minutes in
	 * every (work) hour was considered worktime.
	 */
	protected static List<TimeUnit> unitList = new LinkedList<TimeUnit>();
	static {
		// unitList.add(new TimeUnit(45, 15, Calendar.MINUTE));
		unitList.add(new TimeUnit(WORKDAY_LENGTH, FIRST_WORKHOUR, Calendar.HOUR_OF_DAY));
		unitList.add(new TimeUnit(WEEK_LENGTH, FIRST_WORKDAY, Calendar.DAY_OF_WEEK));
		unitList.add(new TimeUnit(YEAR_LENGTH, FIRST_WORKWEEK, Calendar.WEEK_OF_YEAR));
		unitList.add(new TimeUnit(Integer.MAX_VALUE, 0, Calendar.YEAR));
	}

	public static void main(final String[] args) {
	}

	public static Date calculateDueDate(final Date submitDate, final int turnaroundTime) {
		final Queue<TimeUnit> unitQueue = new LinkedList<TimeUnit>(unitList);

		return processNextQueueItem(getDueDateCalendar(submitDate), turnaroundTime, unitQueue);
	}

	protected static Calendar getDueDateCalendar(final Date submitDate) {
		final Calendar dueDate = Calendar.getInstance();
		dueDate.setTime(submitDate);
		return dueDate;
	}

	/**
	 * Processes time unit queue items recursively. Once the queue is empty it
	 * returns the final date value.
	 *
	 * @param time
	 *            - Calendar instance to modify time for.
	 * @param turnaroundTime
	 *            - turnaround time to calculate incrementing amount from. It's unit
	 *            is expected to be the current queue item's Calendar type
	 *            (HOUR_OF_DAY, DAY_OF_THE_WEEK, etc...).
	 * @param unitQueue
	 *            - unit queue to process elements from.
	 * @return
	 */
	protected static Date processNextQueueItem(final Calendar time, final int turnaroundTime,
			final Queue<TimeUnit> unitQueue) {

		if (unitQueue.peek() != null) {
			final TimeUnit unit = unitQueue.poll();

			final int overflow = setNewUnitValue(time, calculateUnitIncrement(turnaroundTime, unit), unit);

			return processNextQueueItem(time, calculateNewTurnaroundTime(turnaroundTime, unit, overflow), unitQueue);
		} else {
			return time.getTime();
		}
	}

	protected static int calculateNewTurnaroundTime(final int turnaroundTime, final TimeUnit unit, final int overflow) {
		return turnaroundTime / unit.LENGTH + overflow;
	}

	/**
	 * Updates the given Calendar's value in unit.TYPE units by unitIncrementAmount
	 * and returns the overflow.
	 *
	 * @param time
	 *            - Calendar instance to modify time for.
	 * @param unitIncrementAmount
	 *            - increment amount for current unit.
	 * @param unit
	 *            - parameters for processed time unit.
	 * @return
	 */
	protected static int setNewUnitValue(final Calendar time, final int unitIncrementAmount, final TimeUnit unit) {
		final int incrementedValue = calculateUnitRelativeValue(time, unit) + unitIncrementAmount;

		time.set(unit.TYPE, calculateNewUnitValue(unit, incrementedValue));

		return calculateOverflow(unit, incrementedValue);
	}

	protected static int calculateOverflow(final TimeUnit unit, final int incrementedValue) {
		return incrementedValue / unit.LENGTH;
	}

	protected static int calculateNewUnitValue(final TimeUnit unit, final int incrementedValue) {
		return incrementedValue % unit.LENGTH + unit.START;
	}

	protected static int calculateUnitIncrement(final int turnaroundTime, final TimeUnit unit) {
		return turnaroundTime % unit.LENGTH;
	}

	protected static int calculateUnitRelativeValue(final Calendar time, final TimeUnit unit) {
		return time.get(unit.TYPE) - unit.START;
	}

}
