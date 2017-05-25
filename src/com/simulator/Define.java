package com.simulator;

public class Define {
	public static int connection_state = 0;
	public static int timer_state = 0;
	// public static String array_of_data[] = new String[80];

	public static long GPLEV0 = 0;
	public static long GPLEV1 = 0;
	/*
	 * public static char data_in[] = { '0', '0', '0', '0', '0', '0', '0', '0',
	 * '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
	 * '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' };
	 */
	public final static int NUM_GPIO_PINS = 17;
	public static long PinValue[] = new long[NUM_GPIO_PINS];
	public static long PinDirection[] = new long[NUM_GPIO_PINS];
	public static int pinmap[] = { 2, 3, 4, 14, 15, 17, 18, 21, 22, 23, 24, 10, 9, 25, 11, 8, 7 };

	public static int ypos[] = { 146, 187, 231, 319, 357, 396, 492, 536, 579, 231, 275, 319, 402, 448, 536, 581, 628 };
	public static long PINSET = 0; // holds 64-bit value, 0-53 pin values are
	// saved in this variable
	public static long PINDIR = 0; // holds 64-bit value, 0-53 pin directions
	// are saved in this variable
	public static boolean READ = false;

}
