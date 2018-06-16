package emarsys.duedate.model;

public class TimeUnit {
	public final int LENGTH;
	public final int START;
	public final int TYPE;
	
	public TimeUnit(int length, int firstValue, int type) {
		this.LENGTH = length;
		this.START = firstValue;
		this.TYPE = type;
	}
}
