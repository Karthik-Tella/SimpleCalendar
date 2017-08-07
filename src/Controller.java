import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 * Controller file to exchange data between 
 * Model and View
 */
public class Controller extends JPanel {
	private Model model;
	private MainView view;
	public Controller(final Model model,final MainView view) {
		this.model = model;

		//Button initialization
		JButton today = new JButton("Today");
		JButton prevButton = new JButton("<");
		JButton nextButton = new JButton(">");
		JButton dayButton = new JButton("Day");
		JButton weekButton = new JButton("Week");
		JButton monthButton = new JButton("Month");
		JButton agendaButton = new JButton("Agenda");
		JButton fromFile = new JButton("From File");
		JToggleButton saveXML = new JToggleButton("Save as (.xml)");

		UIManager.put("ToggleButton.select", new Color(64, 132, 242));
		SwingUtilities.updateComponentTreeUI(saveXML);
		

		//toggle functionality
		saveXML.addItemListener(new ItemListener() {
			
			/**
			 *  Check if ItemStateChange to determine 
			 *  save as txt or xml 
			 */
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED){
					saveXML.setForeground(Color.white);
					System.out.println("xml");
					model.save(new XmlSaver());

				} 
				else if(e.getStateChange()==ItemEvent.DESELECTED){
					saveXML.setForeground(Color.BLACK);
					System.out.println("txt");
					model.save(new TxtSaver());
				}
			}
		});


		//Button functionality
		today.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.reset();				
			}
		});

		prevButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(view.getState() != 0)
					model.setPrev(view.getState());
				else
					model.setPrev(Calendar.DAY_OF_MONTH);
			}
		});


		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(view.getState() != 0)
					model.setNext(view.getState());
				else
					model.setNext(Calendar.DAY_OF_MONTH);
			}
		});

		dayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.setState(Calendar.DAY_OF_MONTH); 
				view.dayView();
			}
		});

		weekButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.setState(Calendar.WEEK_OF_MONTH);
				view.weekView();
			}
		});

		monthButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.setState(Calendar.MONTH);
				view.monthView();
			}
		});

		agendaButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.setState(0);
				view.agendaView();
			}
		});

		fromFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileChooser fc = new FileChooser(model);
				repaint();
			}
		});


		//Button style
		Color buttonColor = new Color(245, 245, 245);
		today.setBackground(buttonColor);
		prevButton.setBackground(buttonColor);
		nextButton.setBackground(buttonColor);
		dayButton.setBackground(buttonColor);
		weekButton.setBackground(buttonColor);
		monthButton.setBackground(buttonColor);
		agendaButton.setBackground(buttonColor);
		fromFile.setBackground(buttonColor);
		saveXML.setBackground(buttonColor);

		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(today);
		this.add(Box.createRigidArea(new Dimension(5,0)));
		this.add(prevButton);
		this.add(nextButton);
		this.add(Box.createRigidArea(new Dimension(25,0)));
		this.add(dayButton);
		this.add(weekButton);
		this.add(monthButton);
		this.add(agendaButton);
		this.add(Box.createRigidArea(new Dimension(10,0)));
		this.add(fromFile);
		this.add(Box.createRigidArea(new Dimension(5,0)));
		this.add(saveXML);
	}

	/**
	 * Access Model from MainView
	 */
	public MainView getModel() {
		return view;
	}
}
