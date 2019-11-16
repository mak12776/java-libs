package main;

import java.io.IOException;

import tools.machines.StringMachine;

public class Main 
{
	public static final boolean SAFE = true;
	
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
	
	public static void main(String[] args) throws IOException
	{
		short a = 0x1D1F;
		byte b = 1;
		
		printHex(a);
		printHex((long)a << 8);
		printHex((long)a << 16);
		printHex((long)a << 24);
		printHex((long)a << 28);
		printHex((long)a << 0);
	}
}
