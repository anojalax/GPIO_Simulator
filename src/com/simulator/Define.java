package com.simulator;

import java.awt.Color;

public class Define {
	public static int connection_state = 0;
	public static int timer_state = 0;
	public final static int PORT_NUMBER=8988;
	public final static int NUM_GPIO_PINS = 17;
	public final static int NUM_PINS = 26;
	public static int pinmap[] = { 2, 3, 4, 17, 21, 22, 10, 9, 11, 14, 15, 18, 23, 24, 25, 8, 7 };
	public static int ypos[] = { 146, 187, 231, 319, 357, 396, 492, 536, 579, 231, 275, 319, 402, 448, 536, 581, 628 };
	public static long PINSET = 0; // holds 64-bit value, 0-53 pin values are
	// saved in this variable
	public static long PINDIR = 0; // holds 64-bit value, 0-53 pin directions
	// are saved in this variable
	public static long PUD0 = 0;
	public static long PUD1 = 0;
	public static boolean READ = false;
	public static long PIN_LED = 0; // Stores Pin Level of LED; Received from
									// Client(QEMU)
	public static long PIN_SWT = 0; // Stores Pin Level of Switch; Send to
									// Client(QEMU)

	final static Color[] pincolor = new Color[] { Color.orange, Color.red, Color.gray, Color.red,
			Color.gray, /* pin 1 to 5 */
			Color.darkGray, Color.gray, Color.gray, Color.darkGray,
			Color.gray, /* pin 6 to 10 */
			Color.gray, Color.gray, Color.gray, Color.darkGray,
			Color.gray, /* pin 11 to 15 */
			Color.gray, Color.orange, Color.gray, Color.gray,
			Color.darkGray, /* pin 16 to 20 */
			Color.gray, Color.gray, Color.gray, Color.gray, Color.darkGray,
			Color.gray }; /* pin 21 to 26 */
	public static String PinLabels[] = { "3V3", "5V", "GPIO2", "5V", "GPIO3", "GND", "GPIO4", "GPIO14", "GND", "GPIO15",
			"GPIO17", "GPIO18", "GPIO21", "GND", "GPIO22", "GPIO23", "3V3", "GPIO24", "GPIO10", "GND", "GPIO9",
			"GPIO25", "GPIO11", "GPIO8", "GND", "GPIO7" };
}