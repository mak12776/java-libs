
package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import libs.tools.Tools;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		long a = 293184213;
		long b = 2328;
		
		System.out.println((double)a / (double)b);
		System.out.println(a / b + ", " + (double)(a % b) / (double)b);
		System.out.println((double)(a / b) + (double)(a % b) / (double)b);
		
		System.exit(0);
		
		long total = allocateBuffers(1 << 20, 2000);
		System.out.println("total allocated array: " + Tools.formatSize(total) + " (" + total + ")");
	}
	
	
	public static long allocateBuffers(int bufferSize, int maxBufferNumber)
	{
		ArrayList<byte[]> arrayOfBytes = new ArrayList<byte[]>(maxBufferNumber);
		
		long total = 0;
		byte[] buffer;
		
		while (true)
		{
			try
			{
				buffer = new byte[bufferSize];
				arrayOfBytes.add(buffer);
				Arrays.fill(buffer, (byte) 0);
			}
			catch (OutOfMemoryError e)
			{
				System.out.println("catch OutOfMemoryError");
				break;
			}
			total += bufferSize;
		}
		
		arrayOfBytes = null;
		System.gc();
		
		return total;
	}
}
