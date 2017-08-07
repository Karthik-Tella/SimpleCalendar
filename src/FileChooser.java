

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * To Choose Correct Event File
 */
public class FileChooser {
	private Model model;
	
	/**
	 * FileChoose Class
	 */
	public FileChooser(Model m ){
		this.model = m;
		JFileChooser jFileChooser = new JFileChooser();
		//jFileChooser.setCurrentDirectory(new File("/User/karth"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
		jFileChooser.setFileFilter(filter);
		jFileChooser.addChoosableFileFilter(xmlfilter);


		int result = jFileChooser.showOpenDialog(new JFrame());


		if (result == JFileChooser.APPROVE_OPTION) {
			File file = jFileChooser.getSelectedFile();
			System.out.println("Selected file: " + file.getAbsolutePath());
			if(file.exists()) {
				try {
					FileReader fr = new FileReader(file);
					BufferedReader br = new BufferedReader(fr);
					String line = br.readLine();
					while(line != null){
						parseAndAdd(line);
						line = br.readLine();
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				} 	
			}
			else
				System.out.println("No file");

		}
	}

	/**
	 * Parse the file and add events
	 */
	public void parseAndAdd(String str) {
		Calendar temp = new GregorianCalendar();
		String[] events = str.split(";");
		String name = events[0];
		int year = Integer.parseInt(events[1]);
		int startMonth = Integer.parseInt(events[2]);
		int endMonth = Integer.parseInt(events[3]);
		String daysPatter = events[4];
		int start = Integer.parseInt(events[5]);
		int end = Integer.parseInt(events[6]);

		for(int i = startMonth; i <= endMonth; i++) {
			temp.set(year, i-1, 1);
			int lastDay = temp.getActualMaximum(Calendar.DAY_OF_MONTH);
			for(int j = 1; j <= lastDay; j++) {
				temp.set(Calendar.DAY_OF_MONTH, j);
				if(daysPatter.contains(dOW(temp.getTime()))) {
					temp.set(Calendar.HOUR_OF_DAY, start);
					Date startTime = temp.getTime();

					temp.set(Calendar.HOUR_OF_DAY, end);
					Date endTime = temp.getTime();

					CalendarEvent event = new CalendarEvent(name, startTime, endTime);
					boolean check = false;
					if(!event.isLegal()) {
						JOptionPane.showMessageDialog(null,"Invalid end time, please re-enter yout data");
						check = true;
					}

					for(CalendarEvent e1 : model.getEvents()) {
						if(event.compareTo(e1)==0) {
							JOptionPane.showMessageDialog(null, "Calendar event clash");
							check = true;
							break;
						}
					}
					//Comment to ignore collisions
					if(!check)
						model.add(event);
				}
			}
		}
	}

	/**
	 * Check the date info
	 */
	private String dOW(Date date) {
		DateFormat format = new SimpleDateFormat("E");
		String str = format.format(date);
		String end = "";
		switch (str) {
		case "Sun":
			end = "S";
			break;
		case "Mon":
			end = "M";
			break;
		case "Tue":
			end = "T";
			break;
		case "Wed":
			end = "W";
			break;
		case "Thu":
			end = "H";
			break;
		case "Fri":
			end = "F";
			break;
		case "Sat":
			end = "A";
			break;
		default:
			System.out.println("Conversion error!");
			break;
		}
		return end;
	}
}