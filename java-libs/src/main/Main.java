package main;

public class Main 
{
	public static void main(String[] args)
	{
		System.out.println("Hello World");
		
		byte a = (byte) 0xff;
		
		System.out.println(Integer.toHexString((int)(a & 0xff)));
	}
}
