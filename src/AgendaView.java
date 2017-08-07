
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * The following class opens the agenda section prompt 
 * @author Karthik Tella
 *
 */
public class AgendaView {
	//instance variables
	private Model model;
	private Calendar cal;
	private JFrame frame;
	private Date start;
	private Date end;


	/**
	 * Constructor of the class
	 * @param model Model from which calendar state used 
	 */
	public AgendaView(Model model) {
		this.model = model;
		cal = model.getCalendar();

		frame = new JFrame();
		JPanel container = new JPanel();
		container.setBorder(new EmptyBorder(5,5,5,5));
		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());

		JPanel timeHolder = new JPanel();
		timeHolder.setBackground(Color.white);
		timeHolder.setLayout(new GridLayout(0, 2, 5, 5));

		JLabel rangeLabel = new JLabel("Enter a calendar range.");
		rangeLabel.setBackground(Color.white);
		rangeLabel.setBorder(new EmptyBorder(5,5,5,5));

		JLabel startLabel = new JLabel("Start date:");
		JLabel endLabel = new JLabel("End date:");
		startLabel.setBackground(Color.white);
		endLabel.setBackground(Color.white);

		final DateFormat format = new SimpleDateFormat("MM/d/yyyy");
		Calendar temp = new GregorianCalendar();
		temp.setTime(cal.getTime());
		JTextField startField = new JTextField(format.format(temp.getTime()), 5);
		temp.add(Calendar.DAY_OF_MONTH, 4);
		JTextField endField = new JTextField(format.format(temp.getTime()), 5);

		startField.setBackground(Color.white);
		endField.setBackground(Color.white);
		startField.setFont(new Font("Arial", Font.PLAIN, 12));
		endField.setFont(new Font("Arial", Font.PLAIN, 12));


		JButton select = new JButton("Select");
		JButton cancel = new JButton("Cancel");
		select.setBackground(new Color(64, 132, 242));
		select.setForeground(Color.white);
		cancel.setBackground(new Color(245, 245, 245));

		timeHolder.add(startLabel);
		timeHolder.add(startField);
		timeHolder.add(endLabel);
		timeHolder.add(endField);
		timeHolder.add(select);
		timeHolder.add(cancel);

		container.add(rangeLabel, BorderLayout.NORTH);
		container.add(timeHolder, BorderLayout.CENTER);

		frame.add(container);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
//		frame.setSize(new Dimension(250,250));
		frame.setResizable(false);
		frame.setVisible(true);

		select.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					start = format.parse(startField.getText());
					end = format.parse(endField.getText());
					if(start.before(end)) {
						model.av(start, end);
						frame.dispose();
					}
					else {
						JOptionPane.showMessageDialog(frame, "Invalid calendar range.");
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
			}
		});

	}

}
