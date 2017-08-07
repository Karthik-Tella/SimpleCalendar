import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JOptionPane;
/**
 * The following class assists in storing and loading events from persistent memory
 * @author Karthik Tella
 *
 */
public class CalendarSaver {
	//instance variables
	private Model model;
	private Saver saver;

	/**
	 * Constructor of the class
	 * Assign the model on which the class operates
	 * @param model The model on which the class is based 
	 */
	public CalendarSaver(Model model) {
		this.model = model;
		saver = new TxtSaver();

	}

	/**
	 * Loads events from persistent memory and adds them to the list of all the events in the model
	 */
	public void load() {
		System.out.println("Loading...");
		File file = new File("C:\\Users\\karth\\Desktop\\summer 17\\cs 151\\Project\\GoogleCalendar\\src\\events.txt");
		ArrayList<String> list = new ArrayList<>();
		if(file.exists()) {
			try {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String line = br.readLine();

				while(line != null){
					System.out.println(line);
					list.add(line);
					line = br.readLine();
				}
				br.close();
				fr.close();
				for(String str : list) {
					model.add(this.parse(str));
				}
				System.out.println("Loading complete!");
			}
			catch (Exception e) {
				e.printStackTrace();
			} 	
		}
		else
			System.out.println("No file");
		
	}

	/**
	 * Converts a String read from persistent memory into a CalendarEvent
	 * @param str String read from persistent memory
	 * @return Calendar event represented by the string
	 */
	public CalendarEvent parse(String str) {
		DateFormat format = new SimpleDateFormat("MM/dd/yyyyhh:mmaa");
		int split = 0;
		String start = "";
		String end = "";
		String name = "";
		Date startDate = null;
		Date endDate = null;
		for(int i = 0 ; i < str.length(); i++) {
			if(str.charAt(i) == '-') {
				start = str.substring(0, i);
				split  = i + 1;
			}
			if(str.charAt(i) == '|') {
				end = str.substring(split, i);
				name = str.substring(i+1, str.length());
			}
		}
		try {
			startDate = format.parse(start);
			endDate = format.parse(end);

		} 
		catch (ParseException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new CalendarEvent(name, startDate, endDate);
	}

	/**
	 * The following method is called on quitting the calendar 
	 * Saves all the events stored in the model into persistent memory
	 */
	public void add() {
		String tag = saver.getExtension();
//		JOptionPane.showMessageDialog(null, "Now saving into events"  + tag);
		File file = new File("C:\\Users\\karth\\Desktop\\summer 17\\cs 151\\Project\\GoogleCalendar\\src\\events"+tag);		
		if (!file.exists()) {
			try {
				file.createNewFile();
				System.out.println("file created");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
			System.out.println("file exists");
		try {
			FileWriter fw = new FileWriter(file.getAbsolutePath());
			BufferedWriter bw = new BufferedWriter(fw);
			for(CalendarEvent e: model.getEvents()){
				bw.write(e.eventPrint() + System.getProperty("line.separator"));
			}
			bw.close();
			fw.close();
			System.out.println("Events Saved to Memory!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setSaver(Saver saver) {
		this.saver = saver;
	}
}
