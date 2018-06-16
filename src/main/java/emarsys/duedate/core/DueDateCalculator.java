package emarsys.duedate.core;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import emarsys.duedate.model.TimeUnitParameters;

/**
 * Due date calculator, emarsys homework.
 * @author almge
 *
 */
public class DueDateCalculator {

//	private static final int WORK_WEEK_LENGTH = 5;
//	private static final int DAY_LENGTH = 24;
	
	private static final int WORKDAY_END = 17;
	private static final int WORKDAY_START = 9;
	private static final int WORKDAY_LENGTH = WORKDAY_END - WORKDAY_START;
	
	private static final int WEEK_START= 2;
	private static final int WEEK_END= 6;
	private static final int WEEK_LENGTH= WEEK_END - WEEK_START;
	
//	private static final int FREE_TIME_LENGTH = DAY_LENGTH-WORKDAY_LENGTH;

	private static Map<String, TimeUnitParameters> parameterMap = new HashMap();
	{
		parameterMap.put("DAY", new TimeUnitParameters(WORKDAY_LENGTH, WORKDAY_START, WORKDAY_END));
		parameterMap.put("WEEK", new TimeUnitParameters(WEEK_LENGTH, WEEK_START, WEEK_END));
	}
	
	public static void main(String[] args) {
		
		final int turnaroundTime = 10;
		final Date submitDate = new Date();
		
		System.out.println(
				calculateDueDate(submitDate, turnaroundTime)
		);

	}

	private static Date calculateDueDate(Date submitDate, int turnaroundTime) {
		Calendar dueDate = Calendar.getInstance();
		dueDate.setTime(submitDate);
		
		add(	
			dueDate, 
			Calendar.HOUR_OF_DAY, 
			parameterMap.get("DAY")
		);
		
		
//		int dayCount = turnaroundTime / WORKDAY_LENGTH;
//		int weekCount = dayCount % WORK_WEEK_LENGTH;
//		int remHours = turnaroundTime % WORKDAY_LENGTH;
//		
//		dueDate.add(Calendar.DAY_OF_YEAR, dayCount);
//		dueDate.add(Calendar.HOUR_OF_DAY, remHours);
//		
//		// If the hour at the end of increment is equals or larger than the end workhour, the most likely we are out of worktime for the day (assuming we count seconds or milliseconds)
//		if (dueDate.get(Calendar.HOUR_OF_DAY) == WORKDAY_END_HOUR && dueDate.get(Calendar.MINUTE) == 0) {
//			
//		}
//		if (dueDate.get(Calendar.HOUR_OF_DAY) >= WORKDAY_END_HOUR) {
//			
//		}
		
		return dueDate.getTime();
	}
	
	private static int add(Calendar time, int type, int shiftAmount, TimeUnitParameters parameters) {
		int startValue = time.get(type) - parameters.START; 
		int shiftedValue = startValue + shiftAmount;
		
		int remValue = shiftedValue / parameters.LENGTH;
		int newValue = shiftedValue % parameters.LENGTH;
		
		time.set(type, newValue + parameters.START);
		
		return remValue;
	}

	
}
