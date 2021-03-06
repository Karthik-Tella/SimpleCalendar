import java.util.*;
import java.util.spi.CalendarNameProvider;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.xml.ws.Holder;

/**
 * enum of all the days of a week
 * @author Karthik Tella
 *
 */
enum DAYS{
	Sun, Mon, Tue, Wed, Thu, Fri, Sat;
}

/**
 * View class of the calendar application 
 * @author Karthik Tella
 *
 */
public class MainView {
	//instance variables
	private int state;
	private Model model;
	private GregorianCalendar cal;
	private JFrame frame;
	private JLabel monthLabel = new JLabel();
	private final JLabel eventsLabel = new JLabel();
	private JPanel monthPanel;
	private final JPanel eventView;
	private Date start;
	private Date end;

	public MainView(Model m) {
		//basic initialization 
		this.model = m;
		this.cal = model.getCalendar();
		state = Calendar.DAY_OF_MONTH;
		Color buttonColor = new Color(245, 245, 245);
		Controller controller = new Controller(model, this);
		start = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		end = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, -1);


		//Month view
		monthPanel = new JPanel();
		monthPanel.setLayout(new GridLayout(7,7,5,5));
		monthPanel.setBackground(Color.WHITE);
		drawNavigation(monthPanel);

		JPanel monthView = new JPanel();
		monthView.setBorder(new EmptyBorder(10, 10, 10, 10));
		monthView.setBackground(Color.WHITE);
		monthView.setLayout(new BoxLayout(monthView, BoxLayout.Y_AXIS));

		JButton create = new JButton("Create");
		create.setBackground(new Color(208, 74, 47));
		create.setForeground(Color.WHITE);
		create.setHorizontalAlignment(SwingConstants.LEFT);
		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EventView ev = new EventView(model)	;			
			}
		});

		JPanel headLine = new JPanel();
		headLine.setBackground(Color.WHITE);
		headLine.setLayout(new BoxLayout(headLine, BoxLayout.X_AXIS));
		monthLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		monthLabel.setHorizontalAlignment(SwingConstants.LEFT);
		JPanel innerButton = new JPanel();
		innerButton.setBackground(Color.WHITE);
		innerButton.setLayout(new FlowLayout());
		JButton nextMonth = new JButton(">");
		JButton prevMonth = new JButton("<");
		nextMonth.setBackground(buttonColor);
		prevMonth.setBackground(buttonColor);
		nextMonth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				model.alterMV(1);
			}
		});

		prevMonth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.alterMV(-1);
			}
		});


		innerButton.add(prevMonth);
		innerButton.add(nextMonth);
		headLine.add(monthLabel);
		headLine.add(innerButton);
		monthView.add(create);
		monthView.add(Box.createRigidArea(new Dimension(0, 5)));
		monthView.add(headLine);
		monthView.add(monthPanel);

		eventView = new JPanel();
		eventView.setBackground(Color.white);
		eventView.setLayout(new BorderLayout());
		eventsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dayView();		

		JScrollPane scroll = new JScrollPane();
		scroll.setBackground(Color.white);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.getViewport().add(eventView);
		scroll.setPreferredSize(new Dimension(500, 200));
		scroll.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.getHorizontalScrollBar().setUnitIncrement(16);


		frame = new JFrame();
		frame.setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		//adding components
		frame.add(controller, BorderLayout.NORTH);
		frame.add(monthView, BorderLayout.WEST);
		frame.add(scroll, BorderLayout.EAST);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Adds the navigation view components
	 * @param monthPanel the panel to be modified
	 */
	public void drawNavigation(JPanel monthPanel) {	
		DateFormat df = new SimpleDateFormat("MMMMM yyyy");
		monthLabel.setText(df.format(cal.getTime()));

		for(DAYS d : DAYS.values() ) {
			monthPanel.add(new JLabel(d+""));
		}


		GregorianCalendar tempCal = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		int firstDay = tempCal.get(Calendar.DAY_OF_WEEK);		
		int daysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		tempCal.add(Calendar.MONTH,-1);

		int cont = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cont -= firstDay; 


		for(int i = 1; i <= 42; i++) {
			if(i<firstDay) {
				JLabel jl = new JLabel(cont+i+1+"");
				jl.setForeground(new Color(120,120,120));
				monthPanel.add(jl);
			}
			else if(i>=firstDay+daysOfMonth) {
				int val = i-(firstDay+daysOfMonth)+1;
				JLabel jl = new JLabel(val+"");
				jl.setForeground(new Color(120,120,120));
				monthPanel.add(jl);
			}
			else {
				final JLabel dayLabel = new JLabel(i - firstDay + 1 + "");
				dayLabel.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseClicked(MouseEvent e) {
						int num = Integer.parseInt(dayLabel.getText());
						model.setDay(num);
					}
				});

				int nowSelected = i - firstDay + 1;
				if(nowSelected == cal.get(Calendar.DAY_OF_MONTH)) {
					dayLabel.setBorder(BorderFactory.createLineBorder(Color.blue));
				}
				monthPanel.add(dayLabel);
			}
		}
	}

	/**
	 * Adds the day view components to the calendar app
	 */
	public void dayView() {
		//		Reset
		eventView.removeAll();

		eventsLabel.setText(new SimpleDateFormat("dd MMMM yyyy").format(cal.getTime()));
		ArrayList<JTextField> fieldList = new ArrayList<>();
		JPanel dayHolder = new JPanel(); 
		Border border = BorderFactory.createLineBorder(new Color(241, 241, 241));
		dayHolder.setLayout(new BoxLayout(dayHolder, BoxLayout.PAGE_AXIS));
		for(int i = 0 ; i < 48; i++) {
			JTextField eventField = new JTextField(30);
			eventField.setBackground(Color.WHITE);
			eventField.setEditable(false);
			eventField.setBorder(border); 
			dayHolder.add(eventField);
			fieldList.add(eventField);
		}

		JPanel timePanel = new JPanel();
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.PAGE_AXIS));
		for(int i = 0 ; i < 24;i++) {
			JPanel holder = new JPanel();
			holder.setBackground(Color.white);
			holder.setLayout(new GridLayout(2,1));
			JTextField first = new JTextField(5);
			JTextField half = new JTextField(5);
			first.setBackground(Color.white);
			first.setBorder(border);			
			half.setBackground(Color.white);
			half.setBorder(border);
			String tag = "PM";
			int hour = i;
			if(hour < 12) {
				tag = "AM";
			}
			if (hour == 0) {
				hour += 12;
			}
			if (hour > 12) {
				hour -= 12;
			}
			first.setText(hour + ":00"+ tag );
			first.setEditable(false);
			half.setText(hour + ":30"+ tag );
			half.setEditable(false);
			holder.add(first);
			holder.add(half);
			timePanel.add(holder);
		}
		eventView.add(eventsLabel, BorderLayout.NORTH);
		eventView.add(timePanel, BorderLayout.WEST);
		eventView.add(dayHolder, BorderLayout.CENTER);
		ArrayList<CalendarEvent>  eventList = model.getEvents();
		if(eventList != null) {
			for(CalendarEvent event : eventList) {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

				if(df.format(event.getStart()).equals(df.format(model.getCalendar().getTime()))) {
					DateFormat hourFormat = new SimpleDateFormat("HH");
					int startHour = Integer.parseInt(hourFormat.format(event.getStart()));
					int endHour = Integer.parseInt(hourFormat.format(event.getEnd()));
					DateFormat minFormat = new SimpleDateFormat("mm");
					int startMin = Integer.parseInt(minFormat.format(event.getStart()));
					int endMin = Integer.parseInt(minFormat.format(event.getEnd()));
					int fromPosition = startHour *2;
					int toPosition = endHour*2;
					if(startMin >= 30) {
						fromPosition++;
					}
					if(endMin >= 30) {
						toPosition++;
					}
					for(int i  = fromPosition; i <= toPosition; i++) {
						String preText = fieldList.get(i).getText();
						if(!preText.equals("") || preText != null) {
							fieldList.get(i).setText(preText+ " |" + event.getName());
						}
					}
				}
			}
		}
		eventView.revalidate();
		eventView.repaint();
	}

	/**
	 * Adds the week view component to the calendar app
	 */
	public void weekView() {
		eventView.removeAll();

		Border border = BorderFactory.createLineBorder(new Color(241, 241, 241));
		DateFormat format = new SimpleDateFormat("EEE dd/MM");
		Calendar temp  = new GregorianCalendar();		

		temp.setTime(cal.getTime());
		temp.add(Calendar.DAY_OF_MONTH, 1-temp.get(Calendar.DAY_OF_WEEK));

		String headLine = format.format(temp.getTime());

		temp.add(Calendar.DAY_OF_MONTH, 6);

		headLine += " - " + format.format(temp.getTime());

		eventsLabel.setText(headLine);

		JPanel timePanel = new JPanel();
		timePanel.setBackground(Color.WHITE);
		timePanel.setLayout(new FlowLayout());

		temp.add(Calendar.DAY_OF_MONTH, -6);
		format = new SimpleDateFormat("EEE MM/d");
		for(int i = 0; i < 7; i++) {
			JPanel days = new JPanel();
			days.setBackground(Color.white);
			days.setLayout(new BoxLayout(days, BoxLayout.PAGE_AXIS));
			JTextField header = new JTextField(format.format(temp.getTime()),10);
			header.setFont(new Font("Arial", Font.BOLD, 12));
			header.setBorder(border);
			header.setBackground(Color.white);
			header.setEditable(false);
			days.add(header);
			int counter = 0;
			for(CalendarEvent event : model.getEvents()) {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				if(df.format(event.getStart()).equals(df.format(temp.getTime()))) {
					JTextField eve = new JTextField(event.getName(),10);
					eve.setBackground(Color.white);
					eve.setEditable(false);
					eve.setBorder(border);
					days.add(eve);
					counter++;
				}
			}
			if(counter == 0 ) {
				JTextField eve = new JTextField("No Event",10);
				eve.setForeground(new Color(200,200,200));
				eve.setBackground(Color.white);
				eve.setEditable(false);
				eve.setBorder(border);
				days.add(eve);
				counter++;
			}
			if(counter < 48) {
				for(int j = 0; j < 48 - counter; j++) {
					JTextField eve = new JTextField("-",10);
					eve.setEditable(false);
					eve.setForeground(new Color(200,200,200));
					eve.setBackground(Color.white);
					eve.setBorder(border);
					days.add(eve);
				}
			}
			timePanel.add(days);
			temp.add(Calendar.DAY_OF_MONTH, 1);
		}


		eventView.add(eventsLabel, BorderLayout.NORTH);
		eventView.add(timePanel, BorderLayout.CENTER);
		eventView.revalidate();
		eventView.repaint();
	}

	/**
	 * Adds the month view components to the calendar app
	 */
	public void monthView() {
		eventView.removeAll();

		Border border = BorderFactory.createLineBorder(new Color(241, 241, 241));
		DateFormat format = new SimpleDateFormat("MMMMM YYYY");
		eventsLabel.setText(format.format(cal.getTime()));
		Calendar temp = new GregorianCalendar();
		temp.setTime(cal.getTime());
		temp.set(Calendar.DAY_OF_MONTH, 1);
		int lastDay = temp.getActualMaximum(Calendar.DAY_OF_MONTH);
		JPanel timePanel = new JPanel();
		timePanel.setLayout(new FlowLayout());
		timePanel.setBackground(Color.WHITE);
		format = new SimpleDateFormat("EEE d/MM");
		for(int i = 1; i <= lastDay; i++) {
			JPanel days = new JPanel();
			days.setLayout(new BoxLayout(days, BoxLayout.PAGE_AXIS));
			days.setBackground(Color.WHITE);
			JTextField header = new JTextField(format.format(temp.getTime()), 10);
			header.setBackground(Color.WHITE);
			header.setBorder(border);
			header.setFont(new Font("Arial", Font.BOLD, 12));
			header.setEditable(false);
			days.add(header);
			int counter = 0;
			for(CalendarEvent event : model.getEvents()) {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				if(df.format(event.getStart()).equals(df.format(temp.getTime()))) {
					JTextField eve = new JTextField(event.getName(),10);
					eve.setBackground(Color.white);
					eve.setEditable(false);
					eve.setBorder(border);
					days.add(eve);
					counter++;
				}
			}

			if(counter == 0 ) {
				JTextField eve = new JTextField("No Event",10);
				eve.setForeground(new Color(200,200,200));
				eve.setBackground(Color.white);
				eve.setEditable(false);
				eve.setBorder(border);
				days.add(eve);
				counter++;
			}

			if(counter < 48) {
				for(int j = 0; j < 48 - counter; j++) {
					JTextField eve = new JTextField("-",10);
					eve.setEditable(false);
					eve.setForeground(new Color(200,200,200));
					eve.setBackground(Color.white);
					eve.setBorder(border);
					days.add(eve);
				}
			}
			timePanel.add(days);
			temp.add(Calendar.DAY_OF_MONTH, 1);
		}

		eventView.add(eventsLabel, BorderLayout.NORTH);
		eventView.add(timePanel,BorderLayout.CENTER);
		eventView.revalidate();
		eventView.repaint();
	}


	/**
	 * Adds JLabels of all readable event to the calendar app
	 */
	public void agendaView(Date start, Date end) {
		this.start = start;
		this.end = end;
		System.out.println("in av");
		eventView.removeAll();
		eventsLabel.setText("Agenda");
		DateFormat format = new SimpleDateFormat("EEE MMM d, yyyy");
		DateFormat timeFormat = new SimpleDateFormat("h:mm a"	);
		DateFormat compFormat = new SimpleDateFormat("MM/d/yyyy");
		JPanel holder =  new JPanel();
		holder.setBackground(Color.WHITE);
		holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));
		holder.setBorder(new EmptyBorder(10, 10, 10, 10));

		for(CalendarEvent e : model.getEvents()) {
			if(e.getStart().after(start) && e.getStart().before(end) && e.getEnd().after(start) && e.getEnd().before(end))
			{
				JPanel eventHolder = new JPanel();
				eventHolder.setBackground(Color.white);
				eventHolder.setLayout(new BorderLayout());

				String str = format.format(e.getStart());
				str += "     ";
				str += timeFormat.format(e.getStart()) + " - " + timeFormat.format(e.getEnd());
				str += "     ";
				str += e.getName();
				JLabel eventLabel = new JLabel(str);
				eventLabel.setFont(new Font("Arial", Font.PLAIN, 12));
				holder.add(eventLabel);
			}
		}

		eventView.add(eventsLabel, BorderLayout.NORTH);
		eventView.add(holder, BorderLayout.CENTER);
		eventView.revalidate();
		eventView.repaint();
	}

	/**
	 * Repaints the navigation 
	 */
	public void repaintNavigation() {
		monthPanel.removeAll();
		drawNavigation(monthPanel);
		monthPanel.revalidate();
		monthPanel.repaint();
	}

	/**
	 * Repaints the view
	 */
	public void repaint() {
		repaintNavigation();
		eventView.removeAll();
		switch (state) 
		{
		case Calendar.DAY_OF_MONTH:
			dayView();
			break;
		case Calendar.WEEK_OF_MONTH:
			weekView();
			break;
		case Calendar.MONTH:
			monthView();
			break;
		case 0:
			agendaView(start,end);
			break;

		}
		eventView.revalidate();
		eventView.repaint();

	}

	/**
	 * Accessor: Returns the state of the calendar
	 * @return state of the calendar
	 */
	public int getState() {
		return state;
	}

	/**
	 * Mutator: Sets the state of the calendar
	 * @param state current state of the calendar
	 */
	public void setState(int state) { 
		this.state = state;
	}
}