
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The class represents a Calendar event
 * @author Karthik Tella
 *
 */
public class CalendarEvent implements Comparable<CalendarEvent> {
	//instance variables
	private String task;
	private Date start;
	private Date end;
	
	/**
	 * Constructor of the class 
	 * @param task Event Description 
	 * @param start Start time of the event 
	 * @param end End time of the event 
	 */
	public CalendarEvent(String task, Date start, Date end) {
		this.task = task;
		this.start = start;
		this.end = end;
	}
	
	/**
	 * Returns the String representation of a calendar event
	 * @return event description 
	 */
	public String toString() {
		return this.task;
	}
	
	@Override
	/**
	 * Compares two events for any time conflicts
	 * @param that CalendarEvent to be be compared to 
	 * @return 0->equality -1->comes before 1->comes after 
	 */
	public int compareTo(CalendarEvent that) {
		if(this.start.before(that.start) && this.end.before(that.start))
			return -1;
		else if(this.start.after(that.end) && this.end.after(that.end))
			return 1;
		else
			return 0;
	}
	
	/**
	 * Checks if the event input is legal 
	 * @return legality of the event 
	 */
	public boolean isLegal() {
		if(this.start.after(end))
			return false;
		return true;
	}
	
	/**
	 * Returns the event description
	 * @return event description
	 */
	public String getName() {
		return task;
	}

	/**
	 * Returns the start time of the event 
	 * @return
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * Returns the end time of the event 
	 * @return
	 */
	public Date getEnd() {
		return end;
	}
	
//	/**
//	 * Returns the day of the month of at which the event occurs
//	 * @param i 0-> Start state 1-> End state
//	 * @return Day of month on which the event occurs
//	 */
//	public int getDay(int i) {
//		DateFormat format = new SimpleDateFormat("dd");
//		if(i == 0) {
//			return Integer.parseInt(format.format(start));
//		}
//		return Integer.parseInt(format.format(end));
//	}

	/**
	 * Converts the event into a String which can be parsed back for saving it into persistent memory
	 * @return String representation of the evnet 
	 */
	public String eventPrint() {
		String str;
		DateFormat format = new SimpleDateFormat("MM/dd/yyyyhh:mmaa");
		str = format.format(start);
		str += "-";
		str += format.format(end);
		str += "|";
		str += task;
		return str;	
	}
	
	
}


