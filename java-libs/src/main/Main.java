package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import libs.exceptions.BaseException;
import libs.tools.StreamTools;

public class Main 
{
	public static void main(String[] args) throws IOException
	{
		FileOutputStream stream = new FileOutputStream("BigFile.txt");
		byte[] data = "Hello And Hi\nGood Nice, Thank you\r\n".getBytes();
		for (long size = 0; size < (1024 * 1024 * 1024); size += data.length)
			stream.write(data);
		stream.close();
	}
	
	public static void countLinesMain(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("usage: " + " [PATH]");
			return;
		}
		
		try
		{
			FileInputStream stream = new FileInputStream(args[0]);
			System.out.println("number of lines: " + StreamTools.countLines(512, stream));
		}
		catch (IOException | BaseException e)
		{
			e.printStackTrace();
		}
	}
}
