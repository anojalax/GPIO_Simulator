package com.simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Gpio extends JFrame implements ItemListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	final static JButton led[] = new RoundButton[Define.NUM_PINS];
	final static SteelCheckBox swt[] = new SteelCheckBox[Define.NUM_PINS];
	final JButton pin[] = new JButton[Define.NUM_PINS];
	final JLabel pinlabel[] = new JLabel[Define.NUM_PINS];
	final static JLabel pull_label[] = new JLabel[Define.NUM_PINS];
	static String abs_path = "/home/anoja/GPIO_Simulator";
	static long CHECKBIT(long REG, int BIT) {
		return (REG >> BIT) & 1;
	}

	public static ServerSocket s = null;
	public static Socket incoming = null;

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawRect(100, 90, 120, 100);
	}

	public static void main(String[] args) throws IOException {
		Gpio gpio = new Gpio();
		gpio.setVisible(true);
		s = new ServerSocket(8988); /* Establish server socket*/
		System.out.println("Server created @ 8988, Waiting for client request");
		/* wait for incoming connection */
		incoming = s.accept();
		System.out.println("Client is connected");
		java.io.InputStream inps = incoming.getInputStream();
		Scanner in = new Scanner(inps);
		OutputStream outs;
		outs = incoming.getOutputStream();
		final PrintWriter out = new PrintWriter(outs, true);
		boolean done = false;

		while (!done && in.hasNextLine()) {
			/* read data from Socket*/
			String line = in.nextLine();
			if (line.trim().equalsIgnoreCase("exit"))
				done = true;

			if (line != null) {
				evaluate(line);
				/* If Client requested for Pin Status*/
				if (Define.READ == true) {
					String str = Long.toString(Define.PIN_SWT);
					out.println(str);
					Define.READ = false;
				}
			}
		}
		try {
			incoming.close();
			incoming = null;
			in.close();
			s.close();
			s = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String evaluate(String json) {
		long PINDIR = 0;
		long PUD0=0, PUD1=0;
		/* Parse input JSON text */
		try {
			JSONObject obj = (JSONObject) JSONValue.parse(new String(json));
			PINDIR = ((Long) JSONValue.parse(obj.get("PINDIR").toString()));
			Define.PINSET = ((Long) JSONValue.parse(obj.get("PINSET").toString()));
			PUD0 = (Long) JSONValue.parse(obj.get("PUD0").toString());
			PUD1 = (Long) JSONValue.parse(obj.get("PUD1").toString());
			Define.READ = (Boolean) JSONValue.parse(obj.get("READ").toString());

			//System.out.println("Data fom QEMU : PUD0:" + PUD0 +" PUD1:" + PUD1);
			if (Define.READ)
				return "Client requested PIN_SWT";

			// Update pin Direction and values for all GPIO Pins 0-53
			if (PINDIR != Define.PINDIR) {
				Define.PINDIR = PINDIR;
				for (int i = 0; i < Define.NUM_GPIO_PINS; i++) {
					/*If Pin is Input*/
					if (CHECKBIT(Define.PINDIR, Define.pinmap[i]) == 0) {
						/* invisible led symbol and switch visible*/
						led[Define.pinmap[i]].setVisible(false);
						swt[Define.pinmap[i]].setVisible(true);
					} else /* If pin is output*/
					{  /* led should be visible & Switch invisible*/
						led[Define.pinmap[i]].setVisible(true);
						swt[Define.pinmap[i]].setVisible(false);
						if (CHECKBIT(Define.PIN_LED, Define.pinmap[i]) == 0) /*led display in GRAY*/
							led[Define.pinmap[i]].setBackground(Color.GRAY);
						else /* led should glow continuous BRIGHT RED*/
							led[Define.pinmap[i]].setBackground(Color.RED);
					}
					led[Define.pinmap[i]].revalidate();
					swt[Define.pinmap[i]].revalidate();
				}
			}

			/* for pull up and pull down images */
			if (PUD0 != Define.PUD0 || PUD1!= Define.PUD1){
				Define.PUD0 = PUD0;
				Define.PUD1 = PUD1;
				int p = 0;
				File pulldownimage = new File(abs_path + "/down-comp.jpg");
				File pullupimage = new File(abs_path + "/up-comp.jpg");
				Image pulldownim = ImageIO.read(pulldownimage);
				Image pullupim = ImageIO.read(pullupimage);
				ImageIcon pulldown = new ImageIcon(pulldownim);
				ImageIcon pullup = new ImageIcon(pullupim);

				for (int pin = 0; pin < Define.NUM_GPIO_PINS; pin++) {
					if (Define.pinmap[pin] < 32)
						p = (int) ((Define.PUD0 >> (2 * Define.pinmap[pin])) & 0x3);
					else if (Define.pinmap[pin] >= 32 & Define.pinmap[pin] < 54)
						p = (int) ((Define.PUD1 >> (2 * Define.pinmap[pin])) & 0x3);
					System.out.println("\n pin: " + Define.pinmap[pin] + "  p value: " + p);
					if (p == 1) /*p value = 0x01 is set for pulldown */
						pull_label[Define.pinmap[pin]].setIcon(pulldown);
					else if (p == 2) /*p value = 0x01 is set for pullup */
						pull_label[Define.pinmap[pin]].setIcon(pullup);
					else   /*p value = 0x00 is set for no pullup o pulldown */
						pull_label[Define.pinmap[pin]].setIcon(null);//no image
				}
			}
			update_led_status();
		} catch (Exception e) {
			System.out.println("Evaluate Error " + e);
		}
		return Define.PIN_LED + " * " + Define.PINDIR + " * " + Define.READ;
	}

	public static void update_led_status()  {
		for (int pin = 0; pin < Define.NUM_GPIO_PINS; pin++) {
			/* Check if the Direction is Output*/
			if ((Define.PINDIR & (1 << Define.pinmap[pin])) != 0) {
				/* Update LED Status in UI*/
				if ((Define.PINSET & (1 << Define.pinmap[pin])) == 0) {
					/* Clear Pin led display in GRAY*/
					Define.PIN_LED &= ~(1 << Define.pinmap[pin]);
					led[Define.pinmap[pin]].setBackground(Color.GRAY);
				} else {
					/*Set Pin led should glow continuous BRIGHT RED*/
					Define.PIN_LED |= (1 << Define.pinmap[pin]);
					led[Define.pinmap[pin]].setBackground(Color.RED);
				}
				led[Define.pinmap[pin]].repaint();
				led[Define.pinmap[pin]].revalidate();
			}
		}


	}

	public void itemStateChanged(ItemEvent e) {
		for (int j = 0; j < Define.NUM_GPIO_PINS; j++) {
			if (e.getSource() == swt[Define.pinmap[j]]) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					Define.PIN_SWT |= (1 << Define.pinmap[j]);
				else
					Define.PIN_SWT &= ~(1 << Define.pinmap[j]);
			}
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
		timerISR();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.setLayout(null);

		JLabel lblGpioSimulator = new JLabel("GPIO SIMULATOR");
		lblGpioSimulator.setFont(new Font("Dialog", Font.BOLD, 26));
		lblGpioSimulator.setBounds(296, 12, 260, 27);
		panel.add(lblGpioSimulator);

		for (int i = 0; i < Define.pinmap.length; i++) {
			swt[Define.pinmap[i]] = new SteelCheckBox();
			swt[Define.pinmap[i]].setFont(new Font("Dialog", Font.BOLD, 10));
			swt[Define.pinmap[i]].setSize(70, 30); // 125, 33);
			swt[Define.pinmap[i]].setBorder(new RoundedBorder(30));
			swt[Define.pinmap[i]].setColored(true);
			led[Define.pinmap[i]] = new RoundButton();
			led[Define.pinmap[i]].setSize(30, 30);
			led[Define.pinmap[i]].setBackground(Color.gray);

			/*To add pull up and pull down in labels*/
			pull_label[Define.pinmap[i]] = new JLabel();
			pull_label[Define.pinmap[i]].setSize(30, 30);

			if (i < 9) {
				swt[Define.pinmap[i]].setLocation(94, Define.ypos[i]);
				led[Define.pinmap[i]].setLocation(94, Define.ypos[i]);
				pull_label[Define.pinmap[i]].setLocation(180, Define.ypos[i]);//200
			} else {
				swt[Define.pinmap[i]].setLocation(661, Define.ypos[i]);
				led[Define.pinmap[i]].setLocation(661, Define.ypos[i]);
				pull_label[Define.pinmap[i]].setLocation(640, Define.ypos[i]);  //770
			}
			panel.add(swt[Define.pinmap[i]]);
			panel.add(led[Define.pinmap[i]]);
			panel.add(pull_label[Define.pinmap[i]]);
			swt[Define.pinmap[i]].addItemListener(this);

		}

		for (int i = 0; i < Define.NUM_PINS; i++) {
			pin[i] = new JButton(String.valueOf(i));
			pin[i].setFont(new Font("Dialog", Font.BOLD, 18));
			pin[i].setBounds((i % 2 == 0) ? 335 : 433, 96 + ((i / 2) * 44), 70, 32);
			pin[i].setBackground(Define.pincolor[i]);
			panel.add(pin[i]);
			pinlabel[i] = new JLabel(Define.PinLabels[i]);
			pinlabel[i].setFont(new Font("Dialog", Font.BOLD, 22));
			if (i % 2 == 0)
				pinlabel[i].setHorizontalAlignment(SwingConstants.RIGHT);
			else
				pinlabel[i].setHorizontalAlignment(SwingConstants.LEFT);
			pinlabel[i].setBounds((i % 2 == 0) ? 233 : 510, 96 + ((i / 2) * 44), 96, 27);
			panel.add(pinlabel[i]);
		}

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
					update_led_status();
					on = !on;
					count++;
				}
			}
		});
		actionTimer.start();
	}

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