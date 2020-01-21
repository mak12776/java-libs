
package libs.tools.others;

public class PrintTools
{
	public static void print(long a)
	{
		System.out.println("long: " + a);
	}

	public static void print(int a)
	{
		System.out.println("int: " + a);
	}

	public static void printBinary(int a)
	{
		System.out.println("int: " + Integer.toBinaryString(a));
	}

	public static void printBinary(long a)
	{
		System.out.println("long: " + Long.toBinaryString(a));
	}

	public static void printHex(byte b)
	{
		System.out.println("byte: " + Integer.toHexString(b & 0xFF));
	}

	public static void printHex(short a)
	{
		System.out.println("short: " + Integer.toHexString(a & 0xFFFF));
	}

	public static void printHex(int a)
	{
		System.out.println("int: " + Integer.toHexString(a));
	}

	public static void printHex(long a)
	{
		System.out.println("long: " + Long.toHexString(a));
	}

	public static void print(String string)
	{
		System.out.println(string);
	}
}
