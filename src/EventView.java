import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * The following class displays the input view when a new event is to be created 
 * @author Karthik Tella
 *
 */
public class EventView {
	//instance variables
	private final Model model;

	/**
	 * Constructor of the class 
	 * @param model The model on which operates
	 */
	public EventView(Model model) {
		this.model = model;

		final JFrame frame = new JFrame();
		JPanel container = new JPanel();
		container.setBackground(Color.white);

		final JTextField task = new JTextField("Enter Task Here...");

		GregorianCalendar cal = model.getCalendar();
		DateFormat format = new SimpleDateFormat("M/d/yyyy");
		final JTextField date = new JTextField(format.format(cal.getTime()));

		GregorianCalendar g  = new GregorianCalendar();
		format = new SimpleDateFormat("hh:mmaa");
		final JTextField start = new JTextField(format.format(g.getTime()));

		g.add(Calendar.MINUTE, 90);
		final JTextField end = new JTextField(format.format(g.getTime()));

		JButton create = new JButton("Create");
		JButton cancel = new JButton("Cancel");
		create.setBackground(new Color(64, 132, 242));
		create.setForeground(Color.white);
		cancel.setBackground(new Color(245, 245, 245));
		

		container.add(task);
		container.add(date);
		container.add(start);
		container.add(end);
		container.add(create);
		container.add(cancel);

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nameInp = task.getText();
				String dateInp = date.getText();
				String startInp = start.getText();
				String endInp = end.getText();
				DateFormat form = new SimpleDateFormat("MM/dd/yyyyhh:mmaa");


				try {
					Date startOut = form.parse(dateInp + startInp);
					Date endOut = form.parse(dateInp + endInp);
					CalendarEvent event  =  new CalendarEvent(nameInp, startOut, endOut);
					boolean check = false;
					if(!event.isLegal()) {
						JOptionPane.showMessageDialog(frame, "Invalid end time, please re-enter yout data");
						check = true;
					}

					for(CalendarEvent e1 : model.getEvents()) {
						if(event.compareTo(e1)==0) {
							JOptionPane.showMessageDialog(frame, "Calendar event clash");
							check = true;
							break;
						}
					}

					if(!check) {
						model.create(event);
						check = false;
					}

					frame.dispose();

				}
				catch(ParseException excep) {
					excep.printStackTrace();
				}
			}
		});

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(container);
		frame.pack();
		frame.setVisible(true);
	}


	/**
	 * Stubbed main method
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
