
package main;

import libs.bytes.ByteTools;

public class Main
{
	private static final int STEP = 100;
	
	public static void check1(byte[] src, byte[] dest)
	{
		for (int i = 0; i < STEP; i += 1)
			System.arraycopy(src, 0, dest, 0, src.length);
	}
	
	public static void check2(byte[] src, byte[] dest)
	{
		for (int i = 0; i < STEP; i += 1)
			ByteTools.copy(dest, 0, src, 0, src.length);
	}
	
	public static void main(String[] args)
	{
		int size = 1024 * 100;
		
		byte[] src = new byte[size];
		byte[] dest = new byte[size];		
		
		long time1 = System.nanoTime();
		check1(src, dest);
		long time2 = System.nanoTime();
		check2(src, dest);
		long time3 = System.nanoTime();
		
		System.out.println(time2 - time1);
		System.out.println(time3 - time2);
	}
}
