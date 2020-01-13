package main;

import java.io.FileInputStream;
import java.io.IOException;

import libs.exceptions.BaseException;
import libs.tools.types.StreamTools;

public class Mains
{
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
