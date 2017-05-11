package com.simulator;

import java.awt.List;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class Client {
	public static InputStream instream;
	public static OutputStream outstream;

	public static void main(String[] args) {

	try {
	SocketAddress sa = new InetSocketAddress("localhost", 8988);
	Socket sock = new Socket();
	sock.connect(sa, 10 * 1000);
	instream = sock.getInputStream();
	outstream = sock.getOutputStream();

	int i = 0;
	byte[] b = new byte[1];
	PrintWriter out = new PrintWriter(outstream, true);
	
	String s = getJsonObject();
	out.println(s);
	while ((i = instream.read()) != 10) {
	b[0] = (byte) i;
	System.out.print(new String(b));
	}
	System.out.println();

	instream.close();
	out.close();
	outstream.close();
	sock.close();

	} catch (UnknownHostException e) {
	e.printStackTrace();
	} catch (IOException e) {
	e.printStackTrace();
	}
	}

	public static String getJsonObject() {

		JSONObject ob1 = new JSONObject();
		ob1.put("PinNo", new Integer(13));
		ob1.put("PinVal", new Boolean(true));
		ob1.put("PinDir", "output");
		
		JSONObject ob2 = new JSONObject(); 
		ob2.put("PinNo", new Integer(3));
		ob2.put("PinVal", new Boolean(true));
		ob2.put("PinDir", "input");
		
		JSONArray PinDetails=new JSONArray();
		PinDetails.add(ob1);
		PinDetails.add(ob2);
		
		
	JSONObject obj = new JSONObject();
	
	obj.put("PinDetails", PinDetails);
	System.out.println("client: " + obj);

	return obj.toString();
	}
}
