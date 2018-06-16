package emarsys.duedate.model;

public class TimeUnitParameters {
	public final int LENGTH;
	public final int START;
	public final int END;
	
	public TimeUnitParameters(int length, int firstValue, int lastValue) {
		this.LENGTH = length;
		this.START = firstValue;
		this.END = lastValue;
	}
}
