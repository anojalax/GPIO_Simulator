package com.simulator;



import java.awt.BorderLayout;

import java.util.ArrayList;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.simulator.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;


import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

public class Gpio extends JFrame {
	static JToggleButton inout3 = new JToggleButton("IN");
	static JToggleButton inout4 = new JToggleButton("IN");
	static JToggleButton inout17 = new JToggleButton("IN");
	static JToggleButton inout21 = new JToggleButton("IN");
	static JToggleButton inout22 = new JToggleButton("IN");
	static JToggleButton inout10 = new JToggleButton("IN");
	static JToggleButton inout9 = new JToggleButton("IN");
	static JToggleButton inout11 = new JToggleButton("IN");
	static JToggleButton inout14 = new JToggleButton("IN");
	static JToggleButton inout15 = new JToggleButton("IN");
	static JToggleButton inout18 = new JToggleButton("IN");
	static JToggleButton inout23 = new JToggleButton("IN");
	static JToggleButton inout24 = new JToggleButton("IN");
	static JToggleButton inout25 = new JToggleButton("IN");
	static JToggleButton inout8 = new JToggleButton("IN");
	static JToggleButton inout7 = new JToggleButton("IN");

	 
	
	private JPanel contentPane;
	static SteelCheckBox switch2 = new SteelCheckBox();
	static SteelCheckBox switch3 = new SteelCheckBox();
	static SteelCheckBox switch4 = new SteelCheckBox();
	static SteelCheckBox switch17 = new SteelCheckBox();
	static SteelCheckBox switch21= new SteelCheckBox();
	static SteelCheckBox switch22= new SteelCheckBox();
	static SteelCheckBox switch10= new SteelCheckBox();
	static SteelCheckBox switch9= new SteelCheckBox();
	static SteelCheckBox switch11= new SteelCheckBox();
	static SteelCheckBox switch14= new SteelCheckBox();
	static SteelCheckBox switch15= new SteelCheckBox();
	static SteelCheckBox switch18= new SteelCheckBox();
	static SteelCheckBox switch23= new SteelCheckBox();
	static SteelCheckBox switch24= new SteelCheckBox();
	static SteelCheckBox switch8= new SteelCheckBox();
	static SteelCheckBox switch7= new SteelCheckBox();
	static SteelCheckBox switch25= new SteelCheckBox();
	
	int flag=0;
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		Graphics2D g2d=(Graphics2D) g;

		g2d.drawRect(100, 90, 120, 100);
	}
	JButton led2 = new RoundButton();
	JButton led3 = new RoundButton();
	JButton led4 = new RoundButton();
	JButton led17 = new RoundButton();
	JButton led21= new RoundButton();
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
	

	//static TCP_Connection conn=new TCP_Connection();
	public static ServerSocket s = null;
	public static Socket incoming = null;

	public static void main(String[] args) throws IOException {
		
		Gpio gpio = new Gpio();
		gpio.setVisible(true);
		System.out.println("proceeding with tcp_connection");
		
		// establish server socket
		s = new ServerSocket(8988);
		// wait for incoming connection
		incoming = s.accept();
		System.out.println("client is connected");

		InputStream inps = incoming.getInputStream();
		
		Scanner in = new Scanner(inps);
		OutputStream outs;
		outs = incoming.getOutputStream();
    	
    	final PrintWriter out = new PrintWriter(outs, true);
    	final String GPLEV0=new String(Define.GPLEV0);
    	
		/*Thread t = new Thread() {
			  public void run() {
				  
				 System.out.println("inside thread");
	            	out.println(GPLEV0);
	            	 System.out.println("data sent");
			 }
			};
			t.start();*/
		
		
		//PrintWriter out = new PrintWriter(outs, true);

				boolean done = false;
				
				
				
		while (!done && in.hasNextLine()) {

			// read data from Socket
			 System.out.println("main thread");
			String line = in.nextLine();
			
			if (line.trim().equalsIgnoreCase("exit")) 
			{
				done = true;
			}
			//else
				//Define.array_of_data[i]=line;
			
			
			//System.out.println("Data Received:" +line+"array data"+i+Define.array_of_data[0]);
			if(line!=null){
				
				System.out.println("line length"+line.length());
				
						System.out.println("Echo: " + evaluate(line));
				}
			
				}
		
			//out.println(GPLEV0);
			
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


		long GPFSEL0=0;   /* 0x00 Function Select Pins 0-9   */
		long GPFSEL1=0;   /* 0x04 Function Select Pins 10-19 */
		long GPFSEL2=0;   /* 0x08 Function Select Pins 20-29 */
		long GPFSEL3=0;   /* 0x0c Function Select Pins 30-39 */
		long GPFSEL4=0;   /* 0x10 Function Select Pins 40-49 */
		long GPFSEL5=0;   /* 0x14 Function Select Pins 50-53 */
		long PINSET0=0; /* Derived output state for pins  0-31 based on SET and CLR registers */
		long PINSET1=0; /* Derived output state for pins  32-53 based on SET and CLR registers */


		/*long PinNum=0;
		boolean PinVal=true;
		boolean PinDir=true;*/
		System.out.println("\n string before parsing...."+json);
		// parse input JSON text
		try {
			JSONObject obj = (JSONObject) JSONValue.parse(new String(json));
			//PinNum=(Long) JSONValue.parse(obj.get("PinNum").toString());
			GPFSEL0 = (Long) JSONValue.parse(obj.get("GPFSEL0").toString());
			GPFSEL1 = (Long) JSONValue.parse(obj.get("GPFSEL1").toString());
			GPFSEL2 = (Long) JSONValue.parse(obj.get("GPFSEL2").toString());
			GPFSEL3 = (Long) JSONValue.parse(obj.get("GPFSEL3").toString());
			GPFSEL4 = (Long) JSONValue.parse(obj.get("GPFSEL4").toString());
			GPFSEL5 = (Long) JSONValue.parse(obj.get("GPFSEL5").toString());
			PINSET0 = (Long) JSONValue.parse(obj.get("PINSET0").toString());
			PINSET1 = (Long) JSONValue.parse(obj.get("PINSET1").toString());

			System.out.println("GPFSEL0:"+GPFSEL0 +"GPFSEL1: "+GPFSEL1+"GPFSEL2:" +GPFSEL2+"GPFSEL3:"+GPFSEL3 +"GPFSEL4: "+GPFSEL4+"GPFSEL5:" +GPFSEL5);
			System.out.println("PINSET0:"+PINSET0 +"PINSET1: "+PINSET1);

			/*if(PinVal==true)
			{ 
				Define.PinValue[(int) PinNum]=1;
			}else
				Define.PinValue[(int) PinNum]=0;
			
			if(PinDir==true)
			{ 
				Define.PinDirection[(int) PinNum]=1;
			}else
				Define.PinDirection[(int) PinNum]=0;*/
			
		}
		catch(Exception e){
			System.out.println(e);
		}

		//return PinNum+""+PinVal+""+PinDir;	}
		return GPFSEL0+""+GPFSEL1+""+GPFSEL2;	}

	/**
	 * Create the frame.
	 */
	public Gpio()
	{



		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);


		JPanel panel = new JPanel();
		//blinking();
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
		lblv.setBounds(269, 96, 70, 27);
		panel.add(lblv);

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
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					//inout2.setText("OUT");

				} else
				{
					//inout2.setText("IN");
				}
			}
		};

		ItemListener itemListener3 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					inout3.setText("OUT");

				} else
				{
					inout3.setText("IN");
				}
			}
		};
		inout3.addItemListener(itemListener3);
		ItemListener itemListener4 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					inout4.setText("OUT");

				} else
				{
					inout4.setText("IN");
				}
			}
		};
		inout4.addItemListener(itemListener4);
		ItemListener itemListener17 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					inout17.setText("OUT");

				} else
				{
					inout17.setText("IN");
				}
			}
		};
		inout17.addItemListener(itemListener17);
		ItemListener itemListener21 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					inout21.setText("OUT");

				} else
				{
					inout21.setText("IN");
				}
			}
		};
		inout21.addItemListener(itemListener21);
		ItemListener itemListener22 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					inout22.setText("OUT");

				} else
				{
					inout22.setText("IN");
				}
			}
		};
		inout22.addItemListener(itemListener22);
		ItemListener itemListener10 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					inout10.setText("OUT");

				} else
				{
					inout10.setText("IN");
				}
			}
		};
		inout10.addItemListener(itemListener10);
		ItemListener itemListener9 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					inout9.setText("OUT");

				} else
				{
					inout9.setText("IN");
				}
			}
		};
		inout9.addItemListener(itemListener);
		ItemListener itemListener11 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					inout11.setText("OUT");

				} else
				{
					inout11.setText("IN");
				}
			}
		};
		inout11.addItemListener(itemListener11);
		ItemListener itemListener14 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					inout14.setText("OUT");

				} else
				{
					inout14.setText("IN");
				}
			}
		};
		inout14.addItemListener(itemListener14);
		ItemListener itemListener15 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					inout15.setText("OUT");

				} else
				{
					inout15.setText("IN");
				}
			}
		};
		inout15.addItemListener(itemListener15);
		ItemListener itemListener18 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					inout18.setText("OUT");

				} else
				{
					inout18.setText("IN");
				}
			}
		};
		inout18.addItemListener(itemListener);
		ItemListener itemListener23 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					inout23.setText("OUT");

				} else
				{
					inout23.setText("IN");
				}
			}
		};
		inout23.addItemListener(itemListener23);
		ItemListener itemListener24 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					inout24.setText("OUT");

				} else
				{
					inout24.setText("IN");
				}
			}
		};
		inout24.addItemListener(itemListener24);
		ItemListener itemListener25 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
				//	inout2.setText("OUT");

				} else
				{
				//	inout2.setText("IN");
				}
			}
		};
		inout25.addItemListener(itemListener25);

		ItemListener itemListener8 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					//inout8.setText("OUT");

				} else
				{
					//inout8.setText("IN");
				}
			}
		};
		inout8.addItemListener(itemListener8);

		ItemListener itemListener7 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					//inout7.setText("OUT");

				} else
				{
					//inout7.setText("IN");
				}
			}
		};
		inout7.addItemListener(itemListener7);

		ItemListener itemListener1 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch2.setText("SWT ON");
					Define.GPLEV0[2]='1';
					//blinking();

				} else {
					switch2.setText("SWT OFF");
					Define.GPLEV0[2]='0';
					led2.setBackground(Color.GREEN);

				}
			}
		};
		switch2.addItemListener(itemListener1);

		ItemListener itemListener2 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch3.setText("SWT ON");
					Define.GPLEV0[3]='1';

				} else {
					switch3.setText("SWT OFF");
					Define.GPLEV0[3]='0';

				}
			}
		};
		switch3.addItemListener(itemListener2);

		ItemListener itemListeners4 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch4.setText("SWT ON");
					Define.GPLEV0[4]='1';
				} else {
					switch4.setText("SWT OFF");
					Define.GPLEV0[4]='0';
					
				}
			}
		};

		switch4.addItemListener(itemListeners4);
		//
		ItemListener itemListeners17 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch17.setText("SWT ON");
					Define.GPLEV0[17]='1';

				} else {
					switch17.setText("SWT OFF");
					Define.GPLEV0[17]='0';

				}
			}
		};
		switch17.addItemListener(itemListeners17);
		ItemListener itemListeners21 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch21.setText("SWT ON");
					Define.GPLEV0[21]='1';

				} else {
					switch21.setText("SWT OFF");
					Define.GPLEV0[21]='0';

				}
			}
		};
		switch21.addItemListener(itemListeners21);
		ItemListener itemListeners22 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch22.setText("SWT ON");
					Define.GPLEV0[22]='1';

				} else {
					switch22.setText("SWT OFF");
					Define.GPLEV0[22]='0';

				}
			}
		};
		switch22.addItemListener(itemListeners22);
		ItemListener itemListeners10 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch10.setText("SWT ON");
					Define.GPLEV0[10]='1';
				}
					else{
						Define.GPLEV0[6]='0';
						switch10.setText("SWT OFF");
				}
			}
		};
		switch10.addItemListener(itemListeners10);
		ItemListener itemListeners9 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch9.setText("SWT ON");
					Define.GPLEV0[9]='1';

				} else {
					switch9.setText("SWT OFF");
					Define.GPLEV0[9]='0';

				}
			}
		};
		switch9.addItemListener(itemListeners9);
		ItemListener itemListeners11 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch11.setText("SWT ON");
					Define.GPLEV0[11]='1';

				} else {
					switch11.setText("SWT OFF");
					Define.GPLEV0[11]='0';

				}
			}
		};
		switch11.addItemListener(itemListeners11);
		ItemListener itemListeners14 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch14.setText("SWT ON");
					Define.GPLEV0[14]='1';

				} else {
					switch14.setText("SWT OFF");
					Define.GPLEV0[14]='0';

				}
			}
		};
		switch14.addItemListener(itemListeners14);
		ItemListener itemListeners15 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch15.setText("SWT ON");
					Define.GPLEV0[15]='1';

				} else {
					switch15.setText("SWT OFF");
					Define.GPLEV0[15]='0';

				}
			}
		};
		switch15.addItemListener(itemListeners15);
		ItemListener itemListeners18 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch18.setText("SWT ON");
					Define.GPLEV0[18]='1';

				} else {
					switch18.setText("SWT OFF");
					Define.GPLEV0[18]='0';

				}
			}
		};
		switch18.addItemListener(itemListeners18);
		ItemListener itemListeners23 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch23.setText("SWT ON");
					Define.GPLEV0[23]='1';

				} else {
					switch23.setText("SWT OFF");
					Define.GPLEV0[23]='0';

				}
			}
		};
		switch23.addItemListener(itemListeners23);
		ItemListener itemListeners24 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch24.setText("SWT ON");
					Define.GPLEV0[24]='1';

				} else {
					switch24.setText("SWT OFF");
					Define.GPLEV0[24]='0';

				}
			}
		};
		
		switch24.addItemListener(itemListeners24);
		ItemListener itemListeners25 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch25.setText("SWT ON");
					Define.GPLEV0[25]='1';

				} else {
					switch25.setText("SWT OFF");
					Define.GPLEV0[25]='0';

				}
			}
		};
		switch25.addItemListener(itemListeners25);
		ItemListener itemListeners8 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch8.setText("SWT ON");
					Define.GPLEV0[8]='1';

				} else {
					switch8.setText("SWT OFF");
					Define.GPLEV0[8]='0';

				}
			}
		};
		switch8.addItemListener(itemListeners8);
		ItemListener itemListeners7 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int state = itemEvent.getStateChange();
				if (state == ItemEvent.SELECTED) {
					switch7.setText("SWT ON");
					Define.GPLEV0[7]='1';
					
				} else {
					switch7.setText("SWT OFF");
					Define.GPLEV0[7]='0';
					}
			}
		};

		switch7.addItemListener(itemListeners7);




		switch2.setFont(new Font("Dialog", Font.BOLD, 10));
		//switch2.setText("SWT OFF");
		switch2.setBounds(94, 146, 125, 37);
	//	switch2.setBorder(new RoundedBorder(30)); //10 is the radius
		//  led7.setBackground(Color.RED);
		switch2.setVisible(true);
		switch2.setColored(true);
		panel.add(switch2);

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


		switch3.setFont(new Font("Dialog", Font.BOLD, 10));
		switch3.setBounds(94, 187, 125, 33);
		switch3.setBorder(new RoundedBorder(30));
		switch3.setColored(true);
		panel.add(switch3);

		switch4.setFont(new Font("Dialog", Font.BOLD, 10));
		switch4.setBounds(94, 231, 125, 33);
		switch4.setBorder(new RoundedBorder(30));
		switch4.setColored(true);
		panel.add(switch4);

		switch17.setFont(new Font("Dialog", Font.BOLD, 10));
		switch17.setBounds(94, 319, 125, 33);
		switch17.setBorder(new RoundedBorder(30));
		switch17.setColored(true);
		panel.add(switch17);

		switch21.setFont(new Font("Dialog", Font.BOLD, 10));
		switch21.setBounds(94, 357, 125, 33);
		switch21.setBorder(new RoundedBorder(30));
		switch21.setColored(true);
		panel.add(switch21);

		switch22.setFont(new Font("Dialog", Font.BOLD, 10));
		switch22.setBounds(94, 396, 125, 33);
		switch22.setBorder(new RoundedBorder(30));
		switch22.setColored(true);
		panel.add(switch22);

		switch10.setFont(new Font("Dialog", Font.BOLD, 10));
		switch10.setBounds(94, 492, 125, 33);
		switch10.setBorder(new RoundedBorder(30));
		switch10.setColored(true);
		panel.add(switch10);

		switch9.setFont(new Font("Dialog", Font.BOLD, 10));
		switch9.setBounds(94, 536, 125, 33);
		switch9.setBorder(new RoundedBorder(30));
		switch9.setColored(true);
		panel.add(switch9);

		switch11.setFont(new Font("Dialog", Font.BOLD, 10));
		switch11.setBounds(94, 579, 125, 33);
		switch11.setBorder(new RoundedBorder(30));
		switch11.setColored(true);
		panel.add(switch11);

		switch14.setFont(new Font("Dialog", Font.BOLD, 10));
		switch14.setBorder(new RoundedBorder(30));
		switch14.setColored(true);
		switch14.setBounds(661, 231, 125, 33);
		panel.add(switch14);

		switch15.setFont(new Font("Dialog", Font.BOLD, 10));
		switch15.setBounds(661, 275, 125, 33);
		switch15.setBorder(new RoundedBorder(30));
		switch15.setColored(true);
		panel.add(switch15);

		switch18.setFont(new Font("Dialog", Font.BOLD, 10));
		switch18.setBounds(661, 319, 125, 33);
		switch18.setBorder(new RoundedBorder(30));
		switch18.setColored(true);
		panel.add(switch18);

		switch23.setFont(new Font("Dialog", Font.BOLD, 10));
		switch23.setBounds(661, 402, 125, 33);
		switch23.setBorder(new RoundedBorder(30));
		switch23.setColored(true);
		panel.add(switch23);

		switch24.setFont(new Font("Dialog", Font.BOLD, 10));
		switch24.setBounds(661, 448, 125, 33);
		switch24.setBorder(new RoundedBorder(30));
		switch24.setColored(true);
		panel.add(switch24);

		switch25.setFont(new Font("Dialog", Font.BOLD, 10));
		switch25.setBounds(661, 536, 125, 33);
		switch25.setBorder(new RoundedBorder(30));
		switch25.setColored(true);
		panel.add(switch25);

		switch8.setFont(new Font("Dialog", Font.BOLD, 10));
		switch8.setBorder(new RoundedBorder(30));
		switch8.setBounds(661, 581, 125, 33);
		panel.add(switch8);
		switch8.setColored(true);
		
		switch7.setFont(new Font("Dialog", Font.BOLD, 10));
		switch7.setBorder(new RoundedBorder(30));
		switch7.setBounds(661, 628, 125, 33);
		panel.add(switch7);
		switch7.setColored(true);

				
		
		//led2.setBorder(new RoundedBorder(40));
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
	            	
	            	
	            	if(Define.PinDirection[4]==1)//pin is output
	            	{
	            		led4.setVisible(true);// led should be visible
	            		switch4.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[4]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led4.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led4.setBackground(new Color(229,0,0));
	            	    	
	            	    }
	            	 
	            	}
	            		else  //pin is input
	            		{
	            			led4.setVisible(false); //invisible led
	            			switch4.setVisible(true);//visible switch
	            			switch4.setSelected(false); //default not pressed/ON
	            			switch4.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	
	            	if(Define.PinDirection[7]==1)//pin is output
	            	{
	            		led7.setVisible(true);// led should be visible
	            		switch7.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[7]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led7.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led7.setBackground(new Color(229,0,0));
	            	    	
	            	    }
	            	 
	            	}
	            		else  //pin is input
	            		{
	            			led7.setVisible(false); //invisible led
	            			switch7.setVisible(true);//visible switch
	            			switch7.setSelected(false); //default not pressed/ON
	            			switch7.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	
	            	
	            	if(Define.PinDirection[10]==1)//pin is output
	            	{
	            		led10.setVisible(true);// led should be visible
	            		switch10.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[10]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led10.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led10.setBackground(new Color(255, 150, 170));
	            	    }
	            	 }else  //pin is input
	            		{
	            			led10.setVisible(false); //invisible led
	            			switch10.setVisible(true);//visible switch
	            			switch10.setSelected(false); //default not pressed/ON
	            			switch10.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	if(Define.PinDirection[2]==1)//pin is output
	            	{
	            		led2.setVisible(true);// led should be visible
	            		switch2.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[2]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led2.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led2.setBackground(new Color(255, 150, 170));
	            	    }
	            	 }else  //pin is input
	            		{
	            			led2.setVisible(false); //invisible led
	            			switch2.setVisible(true);//visible switch
	            			switch2.setSelected(false); //default not pressed/ON
	            			switch2.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	if(Define.PinDirection[3]==1)//pin is output
	            	{
	            		led3.setVisible(true);// led should be visible
	            		switch3.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[3]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led3.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led3.setBackground(new Color(255, 150, 170));
	            	    }
	            	 }else  //pin is input
	            		{
	            			led3.setVisible(false); //invisible led
	            			switch3.setVisible(true);//visible switch
	            			switch3.setSelected(false); //default not pressed/ON
	            			switch3.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	if(Define.PinDirection[17]==1)//pin is output
	            	{
	            		led17.setVisible(true);// led should be visible
	            		switch17.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[17]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led17.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led17.setBackground(new Color(255, 150, 170));
	            	    }
	            	 }else  //pin is input
	            		{
	            			led17.setVisible(false); //invisible led
	            			switch17.setVisible(true);//visible switch
	            			switch17.setSelected(false); //default not pressed/ON
	            			switch17.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	if(Define.PinDirection[21]==1)//pin is output
	            	{
	            		led21.setVisible(true);// led should be visible
	            		switch21.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[21]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led21.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led21.setBackground(new Color(255, 150, 170));
	            	    }
	            	 }else  //pin is input
	            		{
	            			led21.setVisible(false); //invisible led
	            			switch21.setVisible(true);//visible switch
	            			switch21.setSelected(false); //default not pressed/ON
	            			switch21.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	if(Define.PinDirection[22]==1)//pin is output
	            	{
	            		led22.setVisible(true);// led should be visible
	            		switch22.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[22]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led22.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led22.setBackground(new Color(255, 150, 170));
	            	    }
	            	 }else  //pin is input
	            		{
	            			led22.setVisible(false); //invisible led
	            			switch22.setVisible(true);//visible switch
	            			switch22.setSelected(false); //default not pressed/ON
	            			switch22.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	if(Define.PinDirection[9]==1)//pin is output
	            	{
	            		led9.setVisible(true);// led should be visible
	            		switch9.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[9]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led9.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led9.setBackground(new Color(255, 150, 170));
	            	    }
	            	 }else  //pin is input
	            		{
	            			led9.setVisible(false); //invisible led
	            			switch9.setVisible(true);//visible switch
	            			switch9.setSelected(false); //default not pressed/ON
	            			switch9.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	if(Define.PinDirection[11]==1)//pin is output
	            	{
	            		led11.setVisible(true);// led should be visible
	            		switch11.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[11]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led11.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led11.setBackground(new Color(255, 150, 170));
	            	    }
	            	 }else  //pin is input
	            		{
	            			led11.setVisible(false); //invisible led
	            			switch11.setVisible(true);//visible switch
	            			switch11.setSelected(false); //default not pressed/ON
	            			switch11.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	if(Define.PinDirection[14]==1)//pin is output
	            	{
	            		led14.setVisible(true);// led should be visible
	            		switch14.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[14]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led14.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led14.setBackground(new Color(255, 150, 170));
	            	    }
	            	 }else  //pin is input
	            		{
	            			led14.setVisible(false); //invisible led
	            			switch14.setVisible(true);//visible switch
	            			switch14.setSelected(false); //default not pressed/ON
	            			switch14.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	if(Define.PinDirection[15]==1)//pin is output
	            	{
	            		led15.setVisible(true);// led should be visible
	            		switch15.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[15]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led15.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led15.setBackground(new Color(255, 150, 170));
	            	    }
	            	 }else  //pin is input
	            		{
	            			led15.setVisible(false); //invisible led
	            			switch15.setVisible(true);//visible switch
	            			switch15.setSelected(false); //default not pressed/ON
	            			switch15.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	if(Define.PinDirection[18]==1)//pin is output
	            	{
	            		led18.setVisible(true);// led should be visible
	            		switch18.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[18]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led18.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led18.setBackground(new Color(255, 150, 170));
	            	    }
	            	 }else  //pin is input
	            		{
	            			led18.setVisible(false); //invisible led
	            			switch18.setVisible(true);//visible switch
	            			switch18.setSelected(false); //default not pressed/ON
	            			switch18.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	if(Define.PinDirection[23]==1)//pin is output
	            	{
	            		led23.setVisible(true);// led should be visible
	            		switch23.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[23]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led23.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led23.setBackground(new Color(255, 150, 170));
	            	    }
	            	 }else  //pin is input
	            		{
	            			led23.setVisible(false); //invisible led
	            			switch23.setVisible(true);//visible switch
	            			switch23.setSelected(false); //default not pressed/ON
	            			switch23.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	if(Define.PinDirection[24]==1)//pin is output
	            	{
	            		led24.setVisible(true);// led should be visible
	            		switch24.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[24]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led24.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led24.setBackground(new Color(255, 150, 170));
	            	    }
	            	 }else  //pin is input
	            		{
	            			led24.setVisible(false); //invisible led
	            			switch24.setVisible(true);//visible switch
	            			switch24.setSelected(false); //default not pressed/ON
	            			switch24.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	if(Define.PinDirection[8]==1)//pin is output
	            	{
	            		led8.setVisible(true);// led should be visible
	            		switch8.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[8]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led8.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led8.setBackground(new Color(255, 150, 170));
	            	    }
	            	 }else  //pin is input
	            		{
	            			led8.setVisible(false); //invisible led
	            			switch8.setVisible(true);//visible switch
	            			switch8.setSelected(false); //default not pressed/ON
	            			switch8.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	
	            	
	            	//System.out.println(Define.PinDirection[25]+"................pin dir");
	            	if(Define.PinDirection[25]==1)//pin is output
	            	{
	            		led25.setVisible(true);// led should be visible
	            		switch25.setVisible(false); // Switch invisible
	            	    if(Define.PinValue[25]==1)
	            	    {
	            	    	//led should glow continuous BRIGHT RED
	            	    	led25.setBackground(Color.RED);
	            	    }
	            	    else
	            	    {
	            	    	//led should display in pale RED
	            	    	led25.setBackground(new Color(255, 150, 170));
	            	    		
	            	    }
	            	 
	            	}
	            		else  //pin is input
	            		{
	            			led25.setVisible(false); //invisible led
	            			switch25.setVisible(true);//visible switch
	            			switch25.setSelected(false); //default not pressed/ON
	            			switch25.setText("SWT OFF"); //initialize as Switch is OFF
	            		}
	            	         on = !on;
	               count++;
	            }
	         }
	      });
	     actionTimer.start();
	    }

	
	
	
	
	/* private static final int TIMER_DELAY = 500;
	   private static final int MAX_TIME = 2000;
	   private static final String READY = "ready";
	   
	public void blinking() {
		 test.setOpaque(true);
	      Timer blinkTimer = new Timer(TIMER_DELAY, new ActionListener() {
	         private int count = 0;
	         private boolean on = false;

	         public void actionPerformed(ActionEvent e) {
	            if (count * TIMER_DELAY >= MAX_TIME) {
	               test.setBackground(null);
	               ((Timer) e.getSource()).stop();

	               // !!!
	               firePropertyChange(READY, READY, null);
	               
	            } else {
	               test.setBackground(on ? Color.RED : null);
	               on = !on;
	               count++;
	            }
	         }
	      });
	      blinkTimer.start();
	    }*/
	public static class RoundedBorder implements Border {

		private int radius;


		RoundedBorder(int radius) {
			this.radius = radius;
		}


		public Insets getBorderInsets(Component c) {
			return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
		}


		public boolean isBorderOpaque() {
			return true;
		}


		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			g.drawRoundRect(x, y, width-1, height-1, radius, radius);
		}
		
	}
}