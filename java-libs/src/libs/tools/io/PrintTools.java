
package libs.tools.io;

import java.io.PrintStream;

public class PrintTools
{
	public static PrintStream outputStream = System.out;

	public static void print(long a)
	{
		outputStream.println("long: " + a);
	}

	public static void print(int a)
	{
		outputStream.println("int: " + a);
	}

	public static void printBinary(int a)
	{
		outputStream.println("int: " + Integer.toBinaryString(a));
	}

	public static void printBinary(long a)
	{
		outputStream.println("long: " + Long.toBinaryString(a));
	}

	public static void printHex(byte b)
	{
		outputStream.println("byte: " + Integer.toHexString(b & 0xFF));
	}

	public static void printHex(short a)
	{
		outputStream.println("short: " + Integer.toHexString(a & 0xFFFF));
	}

	public static void printHex(int a)
	{
		outputStream.println("int: " + Integer.toHexString(a));
	}

	public static void printHex(long a)
	{
		outputStream.println("long: " + Long.toHexString(a));
	}
	
	public static void print(String string)
	{
		outputStream.println(string);
	}
	
	public static void printSeparator(int width, char ch)
	{
		for (int i = 0; i < width; i += 1)
			System.out.write(ch);
		System.out.println();
	}
	
	public static void printSeparator(int width)
	{
		printSeparator(width, '-');
	}
	
	public static void printSeparator()
	{
		printSeparator(80);
	}
}
