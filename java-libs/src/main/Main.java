
package main;

import java.io.IOException;

public class Main
{
	public static int nextIndex(int index)
	{
		System.out.println("nextIndex(" + index + ");");
		return index;
	}
	
	public static void main(String[] args) throws IOException
	{
		int[] array = new int[100];
		array[nextIndex(0)] = array[nextIndex(1)];
	}
}
