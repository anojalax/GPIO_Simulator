
package com.simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Gpio extends JFrame {

	interface RPi_ModelB {
		// Raspberry Pi GPIO mapping
		public static final int GPIO_2 = 2; // Pin 3
		public static final int GPIO_3 = 3; // Pin 5
		public static final int GPIO_4 = 4; // Pin 7
		public static final int GPIO_7 = 7; // Pin 26
		public static final int GPIO_8 = 8; // Pin 24
		public static final int GPIO_9 = 9; // Pin 21
		public static final int GPIO_10 = 10; // Pin 19
		public static final int GPIO_11 = 11; // Pin 23
		public static final int GPIO_14 = 14; // Pin 8
		public static final int GPIO_15 = 15; // Pin 10
		public static final int GPIO_17 = 17; // Pin 11
		public static final int GPIO_18 = 18; // Pin 12
		public static final int GPIO_22 = 22; // Pin 15
		public static final int GPIO_23 = 23; // Pin 16
		public static final int GPIO_24 = 24; // Pin 18
		public static final int GPIO_25 = 25; // Pin 22
		public static final int GPIO_21 = 27; // Pin 13

		// public static final int GPIO_5 = 5; // Pin 29
		// public static final int GPIO_6 = 6; // Pin 31
		// public static final int GPIO_12 = 12; // Pin 32
		// public static final int GPIO_13 = 13; // Pin 33
		// public static final int GPIO_16 = 16; // Pin 36
		// public static final int GPIO_19 = 19; // Pin 35
		// public static final int GPIO_20 = 20; // Pin 38
		// public static final int GPIO_21 = 21; // Pin 40
		// public static final int GPIO_26 = 26; // Pin 37
	}

	// #define CHECKBIT(ADDRESS,BIT) (ADDRESS & (1<<BIT))
	static void SETBIT(int BIT) {
		Define.PINSET |= (1 << BIT);
	}

	static void CLEARBIT(int BIT) {
		Define.PINSET &= ~(1 << BIT);
	}

	private JPanel contentPane;

	//final JButton buttons = new JButton[5];

	final SteelCheckBox swt[] = new SteelCheckBox[17];
	/*
	 * static SteelCheckBox swt[2] = new SteelCheckBox(); static SteelCheckBox
	 * swt[3] = new SteelCheckBox(); static SteelCheckBox swt[4] = new
	 * SteelCheckBox(); static SteelCheckBox swt[17] = new SteelCheckBox();
	 * static SteelCheckBox swt[21] = new SteelCheckBox(); static SteelCheckBox
	 * swt[22] = new SteelCheckBox(); static SteelCheckBox swt[10] = new
	 * SteelCheckBox(); static SteelCheckBox swt[9] = new SteelCheckBox();
	 * static SteelCheckBox swt[11] = new SteelCheckBox(); static SteelCheckBox
	 * swt[14] = new SteelCheckBox(); static SteelCheckBox swt[15] = new
	 * SteelCheckBox(); static SteelCheckBox swt[18] = new SteelCheckBox();
	 * static SteelCheckBox swt[23] = new SteelCheckBox(); static SteelCheckBox
	 * swt[24] = new SteelCheckBox(); static SteelCheckBox swt[8] = new
	 * SteelCheckBox(); static SteelCheckBox swt[7] = new SteelCheckBox();
	 * static SteelCheckBox swt[25] = new SteelCheckBox();
	 */

	int flag = 0;

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawRect(100, 90, 120, 100);
	}

	// JButton led[] = new JButton[17];

	JButton led2 = new RoundButton();
	JButton led3 = new RoundButton();
	JButton led4 = new RoundButton();
	JButton led17 = new RoundButton();
	JButton led21 = new RoundButton();
	JButton led22 = new RoundButton();
	JButton led10 = new RoundButton();
	JButton led9 = new RoundButton();
	JButton led11 = new RoundButton();
	JButton led14 = new RoundButton();
	JButton led15 = new RoundButton();
	JButton led18 = new RoundButton();
	JButton led23 = new RoundButton();
	JButton led24 = new RoundButton();
	JButton led25 = new RoundButton();
	JButton led8 = new RoundButton();
	JButton led7 = new RoundButton();

	// static TCP_Connection conn=new TCP_Connection();
	public static ServerSocket s = null;
	public static Socket incoming = null;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		Gpio gpio = new Gpio();
		gpio.setVisible(true);
		System.out.println("proceeding with tcp_connection");

		// establish server socket
		s = new ServerSocket(8988);
		// wait for incoming connection
		incoming = s.accept();
		System.out.println("client is connected");

		java.io.InputStream inps = incoming.getInputStream();

		Scanner in = new Scanner(inps);
		OutputStream outs;
		outs = incoming.getOutputStream();

		final PrintWriter out = new PrintWriter(outs, true);

		boolean done = false;

		while (!done && in.hasNextLine()) {

			// read data from Socket
			// System.out.println("main thread");
			String line = in.nextLine();

			if (line.trim().equalsIgnoreCase("exit")) {
				done = true;
			}

			if (line != null) {

				// System.out.println("line length" + line.length());
				System.out.println("\n string before parsing...." + line);

				System.out.println("Echo: " + evaluate(line));

				// System.out.println(Define.GPLEV0 + "......" + Define.GPLEV1);

				if (Define.READ == true) {

					System.out.println("client is sending: " + Define.PINSET);
					String str = Long.toString(Define.PINSET);
					out.println(str);
					out.println("\n");
					Define.READ = false;

				}

			}

		}
		// System.out.println("im here");

		try {
			incoming.close();
			incoming = null;
			s.close();
			s = null;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String evaluate(String json) {
		int pin = 0;

		long PINSET = 0;

		// parse input JSON text
		try {
			JSONObject obj = (JSONObject) JSONValue.parse(new String(json));
			Define.PINDIR = ((Long) JSONValue.parse(obj.get("PINDIR").toString()));
			Define.PINSET = ((Long) JSONValue.parse(obj.get("PINSET").toString()));
			Define.READ = (Boolean) JSONValue.parse(obj.get("READ").toString());

			System.out.println("PINDIR:" + Define.PINDIR);
			System.out.println("PINSET:" + PINSET);

			// Update pin Direction and values for all GPIO Pins 0-53
			for (pin = 0; pin < 54; pin++) {
				Define.PinDirection[pin] = Define.PINDIR << pin & 1; // this
				// line
				// is
				// not
				// required.
				// Start
				// using
				// Define.PINDIR
				// itself
				/*
				 * if (PINSET >> pin == 1) Define.PINSET = PINSET | (1 << pin);
				 * //set bit else Define.PINSET = PINSET & ~(1 << pin); // clear
				 * bit
				 */
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return Define.PINSET + " " + Define.PINDIR + " " + Define.READ;
	}

	/**
	 * Create the frame.
	 */
	public void itemStateChanged(ItemEvent itemEvent) {
		int state = itemEvent.getStateChange();
		if (state == ItemEvent.SELECTED) {
			// swt[2].setText("SWT ON");

			SETBIT(2);
			// blinking();

		} else {
			// swt[2].setText("SWT OFF");
			CLEARBIT(2);
			// //Define.GPLEV0[2] = '0';
			led2.setBackground(Color.GREEN);

		}

	}

	public Gpio() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		final JPanel panel = new JPanel();
		// blinking();
		timerISR();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.setLayout(null);
		JLabel lblGpioSimulator = new JLabel("GPIO SIMULATOR");
		lblGpioSimulator.setFont(new Font("Dialog", Font.BOLD, 26));
		lblGpioSimulator.setBounds(296, 12, 260, 27);
		panel.add(lblGpioSimulator);

		JLabel lblv = new JLabel("3V3");
		lblv.setFont(new Font("Dialog", Font.BOLD, 22));
		lblv.setBounds(269, 96, 54, 27);
		panel.add(lblv);

		/*
		 * for(int i = 0; i < 17; i++) { led[i] = new
		 * JButton(String.valueOf(i)); }
		 */

		JButton switch0 = new JButton("1");
		switch0.setFont(new Font("Dialog", Font.BOLD, 18));
		switch0.setBounds(335, 95, 70, 32);
		switch0.setBackground(Color.orange);
		panel.add(switch0);

		JButton button_24 = new JButton("2");
		button_24.setFont(new Font("Dialog", Font.BOLD, 18));
		button_24.setBounds(431, 96, 72, 32);
		button_24.setBackground(Color.RED);
		panel.add(button_24);

		JLabel lblv_1 = new JLabel("5V");
		lblv_1.setFont(new Font("Dialog", Font.BOLD, 22));
		lblv_1.setBounds(521, 97, 70, 25);
		panel.add(lblv_1);

	//final SteelCheckBox swt[] = new SteelCheckBox[32];
		/*final JButton buttons = new JButton[5];
		String[] names = {"One", "Two", "Three", "Four", "Five"};
		for (int i = 0; i < buttons.length' i++) {
		buttons[i] = new JButton(names[i]);
		buttons[i].addActionListener(new ActionListener(
		public void actionPerformed(ActionEvent e) {
			//Do something here
		});
	   	);
		}*/
		//String[] names = {"One", "Two", "Three", "Four", "Five"};

		for (int i = 0; i < Define.NUM_GPIO_PINS; i++) {
			swt[i] = new SteelCheckBox();
			swt[i].setFont(new Font("Dialog", Font.BOLD, 10));
			swt[i].setSize(125, 33);
			swt[i].setBorder(new RoundedBorder(30));
			swt[i].setColored(true);

			if (i < 10)
				swt[i].setLocation(94, Define.ypos[i]);
			else
				swt[i].setLocation(661, Define.ypos[i]);

			panel.add(swt[i]);
//			swt[i].addItemListener((ItemListener) this);
		}

		JLabel lblGpio = new JLabel("GPIO2");
		lblGpio.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio.setBounds(253, 147, 86, 27);
		panel.add(lblGpio);

		JLabel lblGpio_1 = new JLabel("GPIO3");
		lblGpio_1.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio_1.setBounds(253, 186, 86, 27);
		panel.add(lblGpio_1);

		JButton button = new JButton("3");
		button.setFont(new Font("Dialog", Font.BOLD, 18));
		button.setBounds(335, 139, 70, 32);
		button.setForeground(Color.white);
		button.setBackground(Color.gray);
		panel.add(button);

		JButton button_1 = new JButton("5");
		button_1.setFont(new Font("Dialog", Font.BOLD, 18));
		button_1.setBounds(335, 185, 70, 32);
		button_1.setForeground(Color.white);
		button_1.setBackground(Color.gray);
		panel.add(button_1);

		JButton button_2 = new JButton("7");
		button_2.setFont(new Font("Dialog", Font.BOLD, 18));
		button_2.setBounds(335, 229, 70, 32);
		button_2.setForeground(Color.white);
		button_2.setBackground(Color.gray);
		panel.add(button_2);

		JButton button_3 = new JButton("9");
		button_3.setFont(new Font("Dialog", Font.BOLD, 18));
		button_3.setBounds(335, 273, 70, 32);
		button_3.setForeground(Color.white);
		button_3.setBackground(Color.BLACK);
		panel.add(button_3);

		JButton button_4 = new JButton("11");
		button_4.setFont(new Font("Dialog", Font.BOLD, 18));
		button_4.setBounds(335, 317, 70, 32);
		button_4.setForeground(Color.white);
		button_4.setBackground(Color.GRAY);
		panel.add(button_4);

		JButton button_5 = new JButton("13");
		button_5.setFont(new Font("Dialog", Font.BOLD, 18));
		button_5.setBounds(335, 358, 70, 32);
		button_5.setForeground(Color.white);
		button_5.setBackground(Color.gray);
		panel.add(button_5);

		JButton button_6 = new JButton("15");
		button_6.setFont(new Font("Dialog", Font.BOLD, 18));
		button_6.setBounds(335, 402, 70, 32);
		button_6.setForeground(Color.white);
		button_6.setBackground(Color.gray);
		panel.add(button_6);

		JButton button_7 = new JButton("17");
		button_7.setFont(new Font("Dialog", Font.BOLD, 18));
		button_7.setBounds(335, 446, 70, 32);
		button_7.setBackground(Color.ORANGE);
		panel.add(button_7);

		JButton button_8 = new JButton("19");
		button_8.setFont(new Font("Dialog", Font.BOLD, 18));
		button_8.setBounds(335, 490, 70, 32);
		button_8.setForeground(Color.white);
		button_8.setBackground(Color.gray);
		panel.add(button_8);

		JButton button_9 = new JButton("21");
		button_9.setFont(new Font("Dialog", Font.BOLD, 18));
		button_9.setBounds(335, 534, 70, 32);
		button_9.setForeground(Color.white);
		button_9.setBackground(Color.gray);
		panel.add(button_9);

		JButton button_10 = new JButton("23");
		button_10.setFont(new Font("Dialog", Font.BOLD, 18));
		button_10.setBounds(335, 577, 70, 32);
		button_10.setForeground(Color.white);
		button_10.setBackground(Color.gray);
		panel.add(button_10);

		JButton button_11 = new JButton("25");
		button_11.setFont(new Font("Dialog", Font.BOLD, 18));
		button_11.setBounds(335, 626, 70, 32);
		button_11.setBackground(Color.black);
		button_11.setForeground(Color.white);

		panel.add(button_11);

		JButton button_12 = new JButton("4");
		button_12.setFont(new Font("Dialog", Font.BOLD, 18));
		button_12.setBounds(433, 140, 70, 32);
		button_12.setBackground(Color.red);
		panel.add(button_12);

		JButton button_13 = new JButton("6");
		button_13.setFont(new Font("Dialog", Font.BOLD, 18));
		button_13.setBounds(433, 185, 70, 32);
		button_13.setForeground(Color.white);
		button_13.setBackground(Color.black);
		panel.add(button_13);

		JButton button_14 = new JButton("8");
		button_14.setFont(new Font("Dialog", Font.BOLD, 18));
		button_14.setBounds(433, 229, 70, 32);
		button_14.setBackground(Color.gray);
		button_14.setForeground(Color.white);
		panel.add(button_14);

		JButton button_15 = new JButton("10");
		button_15.setFont(new Font("Dialog", Font.BOLD, 18));
		button_15.setBounds(433, 273, 70, 32);
		button_15.setBackground(Color.gray);
		button_15.setForeground(Color.white);

		panel.add(button_15);

		JButton button_16 = new JButton("12");
		button_16.setFont(new Font("Dialog", Font.BOLD, 18));
		button_16.setBounds(433, 317, 70, 32);
		button_16.setForeground(Color.white);
		button_16.setBackground(Color.gray);
		panel.add(button_16);

		JButton button_17 = new JButton("14");
		button_17.setFont(new Font("Dialog", Font.BOLD, 18));
		button_17.setBounds(433, 358, 70, 32);
		button_17.setForeground(Color.white);
		button_17.setBackground(Color.black);
		panel.add(button_17);

		JButton button_18 = new JButton("16");

		button_18.setFont(new Font("Dialog", Font.BOLD, 18));
		button_18.setBounds(433, 402, 70, 32);
		button_18.setForeground(Color.white);
		button_18.setBackground(Color.gray);
		panel.add(button_18);

		JButton button_19 = new JButton("18");
		button_19.setFont(new Font("Dialog", Font.BOLD, 18));
		button_19.setBounds(433, 446, 70, 32);
		button_19.setForeground(Color.white);
		button_19.setBackground(Color.gray);
		panel.add(button_19);

		JButton button_20 = new JButton("20");
		button_20.setFont(new Font("Dialog", Font.BOLD, 18));
		button_20.setBounds(433, 490, 70, 32);
		button_20.setForeground(Color.white);
		button_20.setBackground(Color.black);
		panel.add(button_20);

		JButton button_21 = new JButton("22");
		button_21.setFont(new Font("Dialog", Font.BOLD, 18));
		button_21.setBounds(431, 534, 70, 32);
		button_21.setForeground(Color.white);
		button_21.setBackground(Color.gray);
		panel.add(button_21);

		JButton button_22 = new JButton("24");
		button_22.setFont(new Font("Dialog", Font.BOLD, 18));
		button_22.setBounds(433, 577, 70, 32);
		button_22.setForeground(Color.white);
		button_22.setBackground(Color.gray);
		panel.add(button_22);

		JButton button_23 = new JButton("26");
		button_23.setFont(new Font("Dialog", Font.BOLD, 18));
		button_23.setBounds(433, 626, 70, 32);
		button_23.setForeground(Color.white);
		button_23.setBackground(Color.gray);
		panel.add(button_23);

		JLabel lblGpio_2 = new JLabel("GPIO4");
		lblGpio_2.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio_2.setBounds(253, 230, 86, 27);
		panel.add(lblGpio_2);

		JLabel lblGnd = new JLabel("GND");
		lblGnd.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGnd.setBounds(269, 274, 86, 27);
		panel.add(lblGnd);

		JLabel lblGpio_3 = new JLabel("GPIO17");
		lblGpio_3.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio_3.setBounds(237, 318, 96, 27);
		panel.add(lblGpio_3);

		JLabel lblGpio_4 = new JLabel("GPIO21");
		lblGpio_4.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio_4.setBounds(237, 359, 102, 27);
		panel.add(lblGpio_4);

		JLabel lblGpio_5 = new JLabel("GPIO22");
		lblGpio_5.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio_5.setBounds(237, 402, 102, 27);
		panel.add(lblGpio_5);

		JLabel lblv_2 = new JLabel("3V3");
		lblv_2.setFont(new Font("Dialog", Font.BOLD, 22));
		lblv_2.setBounds(283, 447, 86, 27);
		panel.add(lblv_2);

		JLabel lblGpio_6 = new JLabel("GPIO10");
		lblGpio_6.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio_6.setBounds(237, 490, 102, 27);
		panel.add(lblGpio_6);

		JLabel lblGpio_7 = new JLabel("GPIO9");
		lblGpio_7.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio_7.setBounds(253, 535, 86, 27);
		panel.add(lblGpio_7);

		JLabel lblGpio_8 = new JLabel("GPIO11");
		lblGpio_8.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio_8.setBounds(233, 578, 106, 27);
		panel.add(lblGpio_8);

		JLabel lblGnd_1 = new JLabel("GND");
		lblGnd_1.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGnd_1.setBounds(269, 628, 70, 25);
		panel.add(lblGnd_1);

		JLabel lblv_3 = new JLabel("5V");
		lblv_3.setFont(new Font("Dialog", Font.BOLD, 22));
		lblv_3.setBounds(521, 147, 86, 27);
		panel.add(lblv_3);

		JLabel lblGnd_2 = new JLabel("GND");
		lblGnd_2.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGnd_2.setBounds(519, 186, 86, 27);
		panel.add(lblGnd_2);

		JLabel lblGpio_9 = new JLabel("GPIO14");
		lblGpio_9.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio_9.setBounds(519, 235, 96, 27);
		panel.add(lblGpio_9);

		JLabel lblGpio_10 = new JLabel("GPIO15");
		lblGpio_10.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio_10.setBounds(519, 279, 96, 27);
		panel.add(lblGpio_10);

		JLabel lblGpio_11 = new JLabel("GPIO18");
		lblGpio_11.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio_11.setBounds(519, 319, 96, 27);
		panel.add(lblGpio_11);

		JLabel lblGnd_3 = new JLabel("GND");
		lblGnd_3.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGnd_3.setBounds(521, 359, 86, 27);
		panel.add(lblGnd_3);

		JLabel lblGpio_12 = new JLabel("GPIO23");
		lblGpio_12.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio_12.setBounds(521, 403, 101, 27);
		panel.add(lblGpio_12);

		JLabel lblGpio_13 = new JLabel("GPIO24");
		lblGpio_13.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio_13.setBounds(521, 447, 96, 27);
		panel.add(lblGpio_13);

		JLabel lblGnd_4 = new JLabel("GND");
		lblGnd_4.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGnd_4.setBounds(521, 491, 86, 27);
		panel.add(lblGnd_4);

		JLabel lblGpio_14 = new JLabel("GPIO25");
		lblGpio_14.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio_14.setBounds(521, 535, 96, 27);
		panel.add(lblGpio_14);

		JLabel lblGpio_15 = new JLabel("GPIO8");
		lblGpio_15.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio_15.setBounds(521, 578, 86, 27);
		panel.add(lblGpio_15);

		JLabel lblGpio_16 = new JLabel("GPIO7");
		lblGpio_16.setFont(new Font("Dialog", Font.BOLD, 22));
		lblGpio_16.setBounds(521, 627, 86, 27);
		panel.add(lblGpio_16);

		// led2.setBorder(new RoundedBorder(40));
		led2.setBounds(94, 146, 30, 30);
		led2.setBackground(Color.gray);
		panel.add(led2);

		led3.setBounds(94, 187, 30, 30);
		led3.setBackground(Color.gray);
		panel.add(led3);

		led4.setBounds(94, 231, 30, 30);
		led4.setBackground(Color.gray);
		panel.add(led4);
		led17.setBounds(94, 319, 30, 30);
		led17.setBackground(Color.gray);
		panel.add(led17);
		led21.setBounds(94, 359, 30, 30);
		led21.setBackground(Color.gray);
		panel.add(led21);
		led22.setBounds(94, 396, 30, 30);
		led22.setBackground(Color.gray);
		panel.add(led22);
		led10.setBounds(94, 492, 30, 30);
		led10.setBackground(Color.gray);
		panel.add(led10);
		led9.setBounds(94, 536, 30, 30);
		led9.setBackground(Color.gray);
		panel.add(led9);
		led11.setBounds(94, 579, 30, 30);
		led11.setBackground(Color.gray);
		panel.add(led11);

		led14.setBounds(661, 231, 30, 30);
		led14.setBackground(Color.gray);
		panel.add(led14);

		led15.setBounds(661, 275, 30, 30);
		led15.setBackground(Color.gray);
		panel.add(led15);
		led18.setBounds(661, 319, 30, 30);
		led18.setBackground(Color.gray);
		panel.add(led18);
		led23.setBounds(661, 402, 30, 30);
		led23.setBackground(Color.gray);
		panel.add(led23);
		led24.setBounds(661, 448, 30, 30);
		led24.setBackground(Color.gray);
		panel.add(led24);
		led25.setBounds(661, 536, 30, 30);
		led25.setBackground(Color.gray);
		panel.add(led25);
		led8.setBounds(661, 581, 30, 30);
		led8.setBackground(Color.gray);
		panel.add(led8);
		led7.setBounds(661, 628, 30, 30);
		led7.setBackground(Color.gray);
		panel.add(led7);

	}

	private static final int TIMER_DELAY = 3000;
	private static final int MAX_TIME = 10000;
	private static final String READY = "ready";

	public void timerISR() {

		Timer actionTimer = new Timer(TIMER_DELAY, new ActionListener() {
			private int count = 0;
			private boolean on = false;

			public void actionPerformed(ActionEvent e) {
				if (count * TIMER_DELAY >= MAX_TIME) {
					((Timer) e.getSource()).stop();
					firePropertyChange(READY, READY, null);

				} else {

					if (Define.PinDirection[4] == 1)// pin is output
					{
						led4.setVisible(true);// led should be visible
						swt[4].setVisible(false); // Switch invisible
						if ((Define.PINSET >> 4 & 1) == 1)
							led4.setBackground(Color.RED);// led should glow
							// continuous BRIGHT
							// RED
						else
							led4.setBackground(Color.GRAY); // led should
						// display in GRAY

					} else // pin is input
					{
						led4.setVisible(false); // invisible led
						swt[4].setVisible(true);// visible switch
						// if(swt[4].isSelected())
						if ((Define.PINSET >> 4 & 1) == 1)
							swt[4].setSelected(true); // default not pressed/ON
						else
							swt[4].setSelected(false);
						// swt[4].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}

					if (Define.PinDirection[7] == 1)// pin is output
					{
						led7.setVisible(true);// led should be visible
						swt[7].setVisible(false); // Switch invisible
						if (Define.PinValue[7] == 1) {
							// led should glow continuous BRIGHT RED
							led7.setBackground(Color.RED);
						} else {
							// led should display in pale RED
							led7.setBackground(Color.GRAY);

						}

					} else // pin is input
					{
						led7.setVisible(false); // invisible led
						swt[7].setVisible(true);// visible switch
						swt[7].setSelected(false); // default not pressed/ON
						// swt[7].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}

					if (Define.PinDirection[10] == 1)// pin is output
					{
						led10.setVisible(true);// led should be visible
						swt[10].setVisible(false); // Switch invisible
						if (Define.PinValue[10] == 1) {
							// led should glow continuous BRIGHT RED
							led10.setBackground(Color.RED);
						} else {
							// led should display in pale RED
							led10.setBackground(Color.GRAY);
						}
					} else // pin is input
					{
						led10.setVisible(false); // invisible led
						swt[10].setVisible(true);// visible switch
						swt[10].setSelected(false); // default not pressed/ON
						// swt[10].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}
					if (Define.PinDirection[2] == 1)// pin is output
					{
						led2.setVisible(true);// led should be visible
						swt[2].setVisible(false); // Switch invisible
						if (Define.PinValue[2] == 1) {
							// led should glow continuous BRIGHT RED
							led2.setBackground(Color.RED);
						} else {
							// led should display in pale RED
							led2.setBackground(Color.GRAY);
						}
					} else // pin is input
					{
						led2.setVisible(false); // invisible led
						swt[2].setVisible(true);// visible switch
						swt[2].setSelected(false); // default not pressed/ON
						// swt[2].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}
					if (Define.PinDirection[3] == 1)// pin is output
					{
						led3.setVisible(true);// led should be visible
						swt[3].setVisible(false); // Switch invisible
						if (Define.PinValue[3] == 1) {
							// led should glow continuous BRIGHT RED
							led3.setBackground(Color.RED);
						} else {
							// led should display in pale RED
							led3.setBackground(Color.GRAY);
						}
					} else // pin is input
					{
						led3.setVisible(false); // invisible led
						swt[3].setVisible(true);// visible switch
						swt[3].setSelected(false); // default not pressed/ON
						// swt[3].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}
					if (Define.PinDirection[17] == 1)// pin is output
					{
						led17.setVisible(true);// led should be visible
						swt[17].setVisible(false); // Switch invisible
						if (Define.PinValue[17] == 1) {
							// led should glow continuous BRIGHT RED
							led17.setBackground(Color.RED);
						} else {
							// led should display in pale RED
							led17.setBackground(Color.GRAY);
						}
					} else // pin is input
					{
						led17.setVisible(false); // invisible led
						swt[17].setVisible(true);// visible switch
						swt[17].setSelected(false); // default not pressed/ON
						// swt[17].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}
					if (Define.PinDirection[21] == 1)// pin is output
					{
						led21.setVisible(true);// led should be visible
						swt[21].setVisible(false); // Switch invisible
						if (Define.PinValue[21] == 1) {
							// led should glow continuous BRIGHT RED
							led21.setBackground(Color.RED);
						} else {
							// led should display in pale RED
							led21.setBackground(Color.GRAY);
						}
					} else // pin is input
					{
						led21.setVisible(false); // invisible led
						swt[21].setVisible(true);// visible switch
						swt[21].setSelected(false); // default not pressed/ON
						// swt[21].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}
					if (Define.PinDirection[22] == 1)// pin is output
					{
						led22.setVisible(true);// led should be visible
						swt[22].setVisible(false); // Switch invisible
						if (Define.PinValue[22] == 1) {
							// led should glow continuous BRIGHT RED
							led22.setBackground(Color.RED);
						} else {
							// led should display in pale RED
							led22.setBackground(Color.GRAY);
						}
					} else // pin is input
					{
						led22.setVisible(false); // invisible led
						swt[22].setVisible(true);// visible switch
						swt[22].setSelected(false); // default not pressed/ON
						// swt[22].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}
					if (Define.PinDirection[9] == 1)// pin is output
					{
						led9.setVisible(true);// led should be visible
						swt[9].setVisible(false); // Switch invisible
						if (Define.PinValue[9] == 1) {
							// led should glow continuous BRIGHT RED
							led9.setBackground(Color.RED);
						} else {
							// led should display in pale RED
							led9.setBackground(Color.GRAY);
						}
					} else // pin is input
					{
						led9.setVisible(false); // invisible led
						swt[9].setVisible(true);// visible switch
						swt[9].setSelected(false); // default not pressed/ON
						// swt[9].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}
					if (Define.PinDirection[11] == 1)// pin is output
					{
						led11.setVisible(true);// led should be visible
						swt[11].setVisible(false); // Switch invisible
						if (Define.PinValue[11] == 1) {
							// led should glow continuous BRIGHT RED
							led11.setBackground(Color.RED);
						} else {
							// led should display in pale RED
							led11.setBackground(Color.GRAY);
						}
					} else // pin is input
					{
						led11.setVisible(false); // invisible led
						swt[11].setVisible(true);// visible switch
						swt[11].setSelected(false); // default not pressed/ON
						// swt[11].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}
					if (Define.PinDirection[14] == 1)// pin is output
					{
						led14.setVisible(true);// led should be visible
						swt[14].setVisible(false); // Switch invisible
						if (Define.PinValue[14] == 1) {
							// led should glow continuous BRIGHT RED
							led14.setBackground(Color.RED);
						} else {
							// led should display in pale RED
							led14.setBackground(Color.GRAY);
						}
					} else // pin is input
					{
						led14.setVisible(false); // invisible led
						swt[14].setVisible(true);// visible switch
						swt[14].setSelected(false); // default not pressed/ON
						// swt[14].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}
					if (Define.PinDirection[15] == 1)// pin is output
					{
						led15.setVisible(true);// led should be visible
						swt[15].setVisible(false); // Switch invisible
						if (Define.PinValue[15] == 1) {
							// led should glow continuous BRIGHT RED
							led15.setBackground(Color.RED);
						} else {
							// led should display in pale RED
							led15.setBackground(Color.GRAY);
						}
					} else // pin is input
					{
						led15.setVisible(false); // invisible led
						swt[15].setVisible(true);// visible switch
						swt[15].setSelected(false); // default not pressed/ON
						// swt[15].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}
					if (Define.PinDirection[18] == 1)// pin is output
					{
						led18.setVisible(true);// led should be visible
						swt[18].setVisible(false); // Switch invisible
						if (Define.PinValue[18] == 1) {
							// led should glow continuous BRIGHT RED
							led18.setBackground(Color.RED);
						} else {
							// led should display in pale RED
							led18.setBackground(Color.GRAY);
						}
					} else // pin is input
					{
						led18.setVisible(false); // invisible led
						swt[18].setVisible(true);// visible switch
						swt[18].setSelected(false); // default not pressed/ON
						// swt[18].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}
					if (Define.PinDirection[23] == 1)// pin is output
					{
						led23.setVisible(true);// led should be visible
						swt[23].setVisible(false); // Switch invisible
						if (Define.PinValue[23] == 1) {
							// led should glow continuous BRIGHT RED
							led23.setBackground(Color.RED);
						} else {
							// led should display in pale RED
							led23.setBackground(Color.GRAY);
						}
					} else // pin is input
					{
						led23.setVisible(false); // invisible led
						swt[23].setVisible(true);// visible switch
						swt[23].setSelected(false); // default not pressed/ON
						// swt[23].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}
					if (Define.PinDirection[24] == 1)// pin is output
					{
						led24.setVisible(true);// led should be visible
						swt[24].setVisible(false); // Switch invisible
						if (Define.PinValue[24] == 1) {
							// led should glow continuous BRIGHT RED
							led24.setBackground(Color.RED);
						} else {
							// led should display in pale RED
							led24.setBackground(Color.GRAY);
						}
					} else // pin is input
					{
						led24.setVisible(false); // invisible led
						swt[24].setVisible(true);// visible switch
						swt[24].setSelected(false); // default not pressed/ON
						// swt[24].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}
					if (Define.PinDirection[8] == 1)// pin is output
					{
						led8.setVisible(true);// led should be visible
						swt[8].setVisible(false); // Switch invisible
						if (Define.PinValue[8] == 1) {
							// led should glow continuous BRIGHT RED
							led8.setBackground(Color.RED);
						} else {
							// led should display in pale RED
							led8.setBackground(Color.GRAY);
						}
					} else // pin is input
					{
						led8.setVisible(false); // invisible led
						swt[8].setVisible(true);// visible switch
						swt[8].setSelected(false); // default not pressed/ON
						// swt[8].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}

					// System.out.println(Define.PinDirection[25]+"................pin
					// dir");
					if (Define.PinDirection[25] == 1)// pin is output
					{
						led25.setVisible(true);// led should be visible
						swt[25].setVisible(false); // Switch invisible
						if (Define.PinValue[25] == 1) {
							// led should glow continuous BRIGHT RED
							led25.setBackground(Color.RED);
						} else {
							// led should display in pale RED
							led25.setBackground(Color.GRAY);

						}

					} else // pin is input
					{
						led25.setVisible(false); // invisible led
						swt[25].setVisible(true);// visible switch
						swt[25].setSelected(false); // default not pressed/ON
						// swt[25].setText("SWT OFF"); // initialize as Switch
						// is
						// OFF
					}
					on = !on;
					count++;
				}
			}
		});
		actionTimer.start();
	}

	/*
	 * private static final int TIMER_DELAY = 500; private static final int
	 * MAX_TIME = 2000; private static final String READY = "ready";
	 *
	 * public void blinking() { test.setOpaque(true); Timer blinkTimer = new
	 * Timer(TIMER_DELAY, new ActionListener() { private int count = 0; private
	 * boolean on = false;
	 *
	 * public void actionPerformed(ActionEvent e) { if (count * TIMER_DELAY >=
	 * MAX_TIME) { test.setBackground(null); ((Timer) e.getSource()).stop();
	 *
	 * // !!! firePropertyChange(READY, READY, null);
	 *
	 * } else { test.setBackground(on ? Color.RED : null); on = !on; count++; }
	 * } }); blinkTimer.start(); }
	 */
	public static class RoundedBorder implements Border {

		private int radius;

		RoundedBorder(int radius) {
			this.radius = radius;
		}

		public Insets getBorderInsets(Component c) {
			return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
		}

		public boolean isBorderOpaque() {
			return true;
		}

		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
		}

	}
}