package main;

import java.io.IOException;

import libs.exceptions.BaseException;
import libs.tools.SystemTools;
import libs.tools.io.PrintTools;
import libs.tools.io.StreamTools;

public class CompressMain
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("usage: " + SystemTools.getProgramName() + " [FILE]");
			return;
		}
		
		byte[] buffer = null;
		
		try
		{
			buffer = StreamTools.readFile(args[0]);
		}
		catch (BaseException | IOException e)
		{
			e.printStackTrace();
			return;
		}
		
		System.out.println("File Name: " + args[0]);
		System.out.println("File Size: " + buffer.length);
		PrintTools.printSeparator(10);
		
		
	}
}
