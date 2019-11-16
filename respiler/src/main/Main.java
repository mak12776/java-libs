package main;

import java.io.IOException;

import tools.machines.StringMachine;

public class Main 
{
	public static final boolean SAFE = true;
	
	public static void main(String[] args) throws IOException
	{
		for (StringMachine.InstCodes inst: StringMachine.InstCodes.values())
		{
			System.out.println(inst + " " +inst.byteCode);
		}
	}
}
