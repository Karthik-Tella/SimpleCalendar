

import java.util.*;

/**
 * This is the model of the project
 * which has data
 * @author Team3
 *
 */
public class Model {
	private GregorianCalendar cal;
	private MainView view;
	private Date today;
	private ArrayList<CalendarEvent> events;
	private CalendarSaver saver;
	
	/**
	 * Model contains calendar, views,
	 * events, and saver.
	 */
	public Model() {
		cal = new GregorianCalendar();
		view = new MainView(this);
		today = cal.getTime();
		events = new ArrayList<>();
		saver = new CalendarSaver(this);
		saver.load();
		view.repaint();
	}
	
	/**
	 * Return the GregorianCalendar being used
	 * @return cal the calendar instance variable
	 */
	public GregorianCalendar getCalendar() {
		return cal;
	}
	
	/**
	 * Increments the calendar date by a day
	 * 
	 */
	public void setNext(int inp) {
		cal.add(inp, +1);
		view.repaint();
	}
	
	/**
	 * Decreases the calendar date by a day
	 */
	public void setPrev(int inp) {
		cal.add(inp, -1);
		view.repaint();
	}
	
	/**
	 * Sets the day of month of the instance calendar 
	 * @param num day to which the calendar is to be set to
	 */
	public void setDay(int num) {
		cal.set(Calendar.DAY_OF_MONTH, num);
		view.repaint();
	}
	
	/**
	 * Sets the month view of the instance calendar
	 * @param num day to which the calendar is to be set to
	 */
	public void alterMV(int num) {
		cal.add(Calendar.MONTH, num);
		view.repaintNavigation();
	}
	
	/**
	 * Resets the Today view.
	 */
	public void reset() {
		cal.setTime(today);
		view.repaint();
	}
	
	/**
	 * Returns a list of all the calendar events
	 * @return a list of all calendar events
	 */
	public ArrayList<CalendarEvent> getEvents(){
		return events;
	}	
	
	/**
	 * Takes in a new event and updates the view
	 * @param event new event that is to be added to the view 
	 */
	public void create(CalendarEvent event) {
		events.add(event);
		saver.add();
		Collections.sort(events);
		view.repaint();
		for(CalendarEvent e : events) {
			System.out.println(e);	
		}
	}
	
	/**
	 * Adds a new event to the list of all events
	 * @param event the event to be added to the list of all events
	 */
	public void add(CalendarEvent event) {
		events.add(event);
	}
	
	/**
	 * Strategy helper method 
	 * @param sav the saver class to be used
	 */
	public void save(Saver sav) {
		this.saver.setSaver(sav);
		this.saver.add();
	}
	



}
