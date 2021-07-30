// Jeep and bus transactions

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class JeepBus extends JFrame implements ActionListener {
	JPanel cards = new JPanel();
	JPanel panelA = new JPanel();
	JPanel panelB = new JPanel();
	JPanel panelC = new JPanel();
	JPanel panelD = new JPanel();
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	JPanel panel3 = new JPanel();
	JPanel panel4 = new JPanel();
	JButton btnJeep = new JButton("Jeep");
	JButton btnBus = new JButton("Bus");
	JLabel lblHeaderVehicle = new JLabel();
	JTextField txtPassenger = new JTextField();
	JTextField txtDistance = new JTextField();
	JTextField txtDiscount = new JTextField();
	JButton btnReport = new JButton("Report");
	JLabel lblReport = new JLabel("");
	JButton btnCancel = new JButton("Back/Main");

	Vehicles v = new Vehicles();
	String vehicles = "";

	Color color1 = Color.decode("#B3E6FF");					//blue (background)
	Color color2 = Color.decode("#00BF6F"); 				//green (button)
	Color color3 = Color.decode("#007BFF"); 				//blue (button)
	Color color4 = Color.decode("#FFC107"); 				//orange (button)
	Color color5 = Color.decode("#28A745");
	Color color6 = Color.decode("#DC3545");

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				myGUI();
			}
		});
	}

	private static void myGUI() {
		JeepBus r = new JeepBus("RBA Vehicles Center");
		r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		r.myComponents(r.getContentPane());
		r.setSize(500,350);
		r.setLocationRelativeTo(null);
		r.setVisible(true);
	}

	public JeepBus(String name) {
		super(name);
	}

	private void myComponents(final Container pane) {
		thePanelA();
		thePanel1();
		thePanel2();
		thePanel3();
		thePanel4();

		cards = new JPanel(new CardLayout());
		cards.add(panelA, "panelA");
		cards.add(panelB, "panelB");								// Containing panel1 and panel2
		cards.add(panelC, "panelC");								// Containing panel1 and panel3
		cards.add(panelD, "panelD");								// Containing panel1 and panel4
		cards.setVisible(true);
		pane.add(cards, BorderLayout.CENTER);

		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, "panelA");
	}

	public void actionPerformed(ActionEvent e) {
		cards.setVisible(true);

		if (e.getActionCommand().equals("Start") || e.getActionCommand().equals("Cancel")) {
			thePanelB();

			CardLayout cl = (CardLayout)(cards.getLayout());
			cl.show(cards, "panelB");

			if (e.getActionCommand().equals("Cancel"))
				enablePanel1();
		} else if (e.getActionCommand().equals("Jeep") || e.getActionCommand().equals("Bus")) {
			panelC.setLayout(new BorderLayout(1, 2));
			panelC.add(panel1, BorderLayout.WEST);
			panelC.add(panel3, BorderLayout.EAST);

			CardLayout cl = (CardLayout)(cards.getLayout());
			cl.show(cards, "panelC");

			if (e.getActionCommand().equals("Jeep")) {
				vehicles = "jeep";
				lblHeaderVehicle.setText("Jeep:");
			} else if (e.getActionCommand().equals("Bus")) {
				vehicles = "bus";
				lblHeaderVehicle.setText("Bus:");
			}

			disablePanel1();
		} else if (e.getActionCommand().equals("Enter")) {
			try {
				int passenger = Integer.parseInt(txtPassenger.getText());
				int distance = Integer.parseInt(txtDistance.getText());
				int discount = Integer.parseInt(txtDiscount.getText());
				int regular = passenger - discount;
				int jeepBus = 0, firstKilometers = 0;
				double fee = 0, additional = 0;

				if (passenger >= discount) {
					v.setRegularPassenger(v.getRegularPassenger() + regular);
					v.setDiscountedPassenger(v.getDiscountedPassenger() + discount);
					
					if (vehicles.equalsIgnoreCase("jeep")) {
						do {
							jeepBus++;
							passenger = passenger - 20;
						} while (passenger > 0);

						v.setNumberOfJeep(v.getNumberOfJeep() + jeepBus);

						fee = 8;
						firstKilometers = 4;
						additional = 1.5;
					} else if (vehicles.equalsIgnoreCase("bus")) {
						do {
							jeepBus++;
							passenger = passenger - 60;
						} while (passenger > 0);

						v.setNumberOfBus(v.getNumberOfBus() + jeepBus);

						fee = 10;
						firstKilometers = 5;
						additional = 1.85;
					}

					distance = distance - firstKilometers;
					
					while (distance >= 1) {
						fee = fee + additional;
						distance--;
					}

					double normalFee = fee * regular;
					double discountedFee = (fee - (fee * 0.2)) * discount;
					double totalFee = normalFee + discountedFee;
					v.setTotalTransaction(v.getTotalTransaction() + 1);
					v.setTotalIncome(v.getTotalIncome() + totalFee);

					JOptionPane.showMessageDialog(cards, "Regular passenger/s:   " + regular + "\nDiscounted passenger/s:   " +  discount 
						+ "\nNo. of " + vehicles + " used:   " + jeepBus + "\nTotal fare:   P " + String.format("%.2f", totalFee));

					thePanelB();

					CardLayout cl = (CardLayout)(cards.getLayout());
					cl.show(cards, "panelB");

					vehicles = "";
					enablePanel1();
				} else {
					JOptionPane.showMessageDialog(cards, "ERROR: Discounted passengers exceed the total passengers.");
				}

				txtPassenger.setText("");
				txtDistance.setText("");
				txtDiscount.setText("");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(cards, "ERROR: You must enter a number");
				txtPassenger.setText("");
				txtDistance.setText("");
				txtDiscount.setText("");
			}
		} else if (e.getActionCommand().equals("Report")) {
			if (v.getRegularPassenger() > 0 || v.getDiscountedPassenger() > 0) {
				lblReport.setText(v + "");

				panelD.setLayout(new BorderLayout(1, 2));
				panelD.add(panel1, BorderLayout.WEST);
				panelD.add(panel4, BorderLayout.EAST);

				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, "panelD");

				disablePanel1();
			} else {
				JOptionPane.showMessageDialog(cards, "ERROR: You have not made any transaction yet");
			}
		} 
	}

	// Starting panel
	private void thePanelA() {
		ImageIcon imgIcon = new ImageIcon("RBA Logo.png");
		imgIcon = new ImageIcon(imgIcon.getImage().getScaledInstance(300, 200, java.awt.Image.SCALE_SMOOTH));
		JLabel lblLogo = new JLabel(imgIcon);
		JLabel lblCaption = new JLabel("Welcome to RBA Vehicles Center!");
		JButton btnStart = new JButton("START");

		lblLogo.setBounds(115, 60, 260, 75);
		lblCaption.setBounds(142, 132, 240, 26);
		lblCaption.setFont(new Font("Calibri", 1, 15));

		btnStart.setBounds(185, 180, 120, 50);
		btnStart.setFont(new Font("Calibri", 1, 17));
		btnStart.setFocusable(false);
		btnStart.setBackground(color2);
		btnStart.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnStart.setForeground(Color.white);
		btnStart.setActionCommand("Start");
		btnStart.addActionListener(this);

		panelA.setBackground(color1);
		panelA.setLayout(null);
		panelA.add(lblLogo, BorderLayout.CENTER);
		panelA.add(lblCaption);
		panelA.add(btnStart);
	}
	// Navigation panel
	private void thePanel1() {
		JLabel lblChoose = new JLabel("Choose:");

		lblChoose.setBounds(25, 25, 70, 26);
		lblChoose.setFont(new Font("Calibri", 1, 15));

		btnJeep.setBounds(25, 65, 100, 40);
		btnJeep.setFont(new Font("Calibri", 1, 14));
		btnJeep.setFocusable(false);
		btnJeep.setBackground(color3);
		btnJeep.setForeground(Color.white);
		btnJeep.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnJeep.setActionCommand("Jeep");
		btnJeep.addActionListener(this);

		btnBus.setBounds(25, 120, 100, 40);
		btnBus.setFont(new Font("Calibri", 1, 14));
		btnBus.setFocusable(false);
		btnBus.setBackground(color4);
		btnBus.setForeground(Color.white);
		btnBus.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnBus.setActionCommand("Bus");
		btnBus.addActionListener(this);

		btnReport.setBounds(25, 175, 100, 40);
		btnReport.setFont(new Font("Calibri", 1, 14));
		btnReport.setFocusable(false);
		btnReport.setBackground(color5);
		btnReport.setForeground(Color.white);
		btnReport.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnReport.setActionCommand("Report");
		btnReport.addActionListener(this);

		btnCancel.setBounds(25, 230, 100, 40);
		btnCancel.setFont(new Font("Calibri", 1, 14));
		btnCancel.setFocusable(false);
		btnCancel.setBackground(color6);
		btnCancel.setForeground(Color.white);
		btnCancel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnCancel.setActionCommand("Cancel");
		btnCancel.addActionListener(this);

		panel1.setPreferredSize(new Dimension(150, 350));
		panel1.setBackground(color1);
		panel1.setLayout(null);
		panel1.add(lblChoose);
		panel1.add(btnJeep);
		panel1.add(btnBus);
		panel1.add(btnReport);
		panel1.add(btnCancel);
	}
	// Guidelines panel (main)
	private void thePanel2() {
		JLabel lblHeader = new JLabel("Guidelines:");
		JLabel lblJeepGuide = new JLabel("<html>Jeep: P8.00 for 1st 4 kilometers + P1.50 for suceeding kilometers travelled</html>");
		JLabel lblBusGuide = new JLabel("<html>Bus: P10.00 for 1st 5 kilometers + P1.85 for suceeding kilometers travelled</html>");
		JLabel lblNote = new JLabel("<html>*Note: Report button can only be accessed after having atleast one (1) transaction.</html>");

		lblHeader.setBounds(25, 25, 150, 26);
		lblJeepGuide.setBounds(25, 59, 300, 36);
		lblBusGuide.setBounds(25, 99, 300, 36);
		lblNote.setBounds(25, 149, 300, 36);

		lblHeader.setFont(new Font("Calibri", 1, 15));
		lblJeepGuide.setFont(new Font("Calibri", 1, 14));
		lblBusGuide.setFont(new Font("Calibri", 1, 14));
		lblNote.setFont(new Font("Calibri", 1, 14));

		panel2.setPreferredSize(new Dimension(335, 350));
		panel2.setLayout(null);
		panel2.add(lblHeader);
		panel2.add(lblJeepGuide);
		panel2.add(lblBusGuide);
		panel2.add(lblNote);
	}
	// Jeep and buas panel
	private void thePanel3() {
		JLabel lblPassenger = new JLabel("Total no. of passengers:");
		JLabel lblDistance = new JLabel("Total distance of trip (km):");
		JLabel lblDiscount = new JLabel("Passengers w/ 20% discount:");
		JButton btnEnter = new JButton("Enter");

		lblHeaderVehicle.setBounds(25, 25, 200, 26);
		lblHeaderVehicle.setFont(new Font("Calibri", 1, 15));

		lblPassenger.setBounds(25, 69, 200, 26);
		lblPassenger.setFont(new Font("Calibri", 1, 14));
		txtPassenger.setBounds(210, 65, 100, 35);
		txtPassenger.setMargin(new Insets(0, 7, 0, 7));

		lblDistance.setBounds(25, 113, 200, 26);
		lblDistance.setFont(new Font("Calibri", 1, 14));
		txtDistance.setBounds(210, 110, 100, 35);
		txtDistance.setMargin(new Insets(0, 7, 0, 7));

		lblDiscount.setBounds(25, 158, 200, 26);
		lblDiscount.setFont(new Font("Calibri", 1, 14));
		txtDiscount.setBounds(210, 155, 100, 35);
		txtDiscount.setMargin(new Insets(0, 7, 0, 7));

		btnEnter.setBounds(210, 230, 100, 40);
		btnEnter.setFont(new Font("Calibri", 1, 14));
		btnEnter.setFocusable(false);
		btnEnter.setBackground(color2);
		btnEnter.setForeground(Color.white);
		btnEnter.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnEnter.setActionCommand("Enter");
		btnEnter.addActionListener(this);

		panel3.setPreferredSize(new Dimension(335, 350));
		panel3.setLayout(null);
		panel3.add(lblHeaderVehicle);
		panel3.add(lblPassenger);
		panel3.add(txtPassenger);
		panel3.add(lblDistance);
		panel3.add(txtDistance);
		panel3.add(lblDiscount);
		panel3.add(txtDiscount);
		panel3.add(btnEnter);
	}
	// Printing the report panel
	private void thePanel4() {
		JLabel lblHeader = new JLabel("Report:");

		lblHeader.setBounds(25, 25, 200, 26);
		lblHeader.setFont(new Font("Calibri", 1, 15));

		lblReport.setBounds(25, 60, 300, 300);
		lblReport.setFont(new Font("Calibri", 1, 14));
		lblReport.setVerticalAlignment(JLabel.TOP);

		panel4.setPreferredSize(new Dimension(335, 350));
		panel4.setLayout(null);
		panel4.add(lblHeader);
		panel4.add(lblReport);
	}
	private void thePanelB() {
		panelB.setLayout(new BorderLayout(1, 2));
		panelB.add(panel1, BorderLayout.WEST);
		panelB.add(panel2, BorderLayout.EAST);
	}
	// Disabling navigation buttons excluding Back
	private void disablePanel1() {
		btnJeep.setEnabled(false);
		btnBus.setEnabled(false);
		btnReport.setEnabled(false);
	}
	// Enabling navigation buttons excluding Back
	private void enablePanel1() {
		btnJeep.setEnabled(true);
		btnBus.setEnabled(true);
		btnReport.setEnabled(true);
	}
}

// Storage of data
class Vehicles {
	private int totalTransaction;
	private int regularPassenger;
	private int discountedPassenger;
	private int numberOfJeep;
	private int numberOfBus;
	private double totalIncome;

	public void setTotalTransaction(int i) {
		totalTransaction = i;
	}
	public void setRegularPassenger(int i) {
		regularPassenger = i;
	}
	public void setDiscountedPassenger(int i) {
		discountedPassenger = i;
	}
	public void setNumberOfJeep(int i) {
		numberOfJeep = i;
	}
	public void setNumberOfBus(int i) {
		numberOfBus = i;
	}
	public void setTotalIncome(double d) {
		totalIncome = d;
	}

	public int getTotalTransaction() {
		return totalTransaction;
	}
	public int getRegularPassenger() {
		return regularPassenger;
	}
	public int getDiscountedPassenger() {
		return discountedPassenger;
	}
	public int getNumberOfJeep() {
		return numberOfJeep;
	}
	public int getNumberOfBus() {
		return numberOfBus;
	}
	public double getTotalIncome() {
		return totalIncome;
	}

	public Vehicles() {}

	public String toString() {
		return "<html>Number of transactions:&nbsp;&nbsp;&nbsp;" + getTotalTransaction() +
			"<br>Regular passengers:&nbsp;&nbsp;&nbsp;" + getRegularPassenger() +
			"<br>Discounted passengers:&nbsp;&nbsp;&nbsp;" + getDiscountedPassenger() +
			"<br>Total jeep hired:&nbsp;&nbsp;&nbsp;" + getNumberOfJeep() + 
			"<br>Total bus hired:&nbsp;&nbsp;&nbsp;" + getNumberOfBus() + 
			"<br>Total income:&nbsp;&nbsp;&nbsp;P " + String.format("%.2f", getTotalIncome()) + "</html>";
	}
}