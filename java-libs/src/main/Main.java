package main;

import java.io.IOException;

public class Main 
{
	public static final boolean SAFE = true;
	
	public static void main(String[] args) throws IOException
	{
		int size;
		byte[] array = new byte[10];
		while (true)
		{
			size = System.in.read(array);
			System.out.println("size: " + size);
			if (array[0] == 'q')
				break;
		}
	}
}
