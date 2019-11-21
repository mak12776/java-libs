package main;

import java.io.IOException;
import tools.machines.UltraMachine;

public class Main 
{
	public static final boolean SAFE = true;
	
	public static void main(String[] args) throws IOException
	{
		UltraMachine machine = new UltraMachine(1024);
		machine.run();
	}
}
