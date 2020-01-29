
package main;

import java.io.FileInputStream;
import java.io.IOException;

import libs.buffers.Buffer;
import libs.exceptions.BaseException;
import libs.io.JavaOutputFile;
import libs.io.LinesTools;
import libs.io.IOTools;

public class Programs
{
	public static void sumNumbers(String[] args)
	{
		if (args.length != 1)
			System.out.println("usage: sum [PATH]");
		
		try
		{
			FileInputStream stream = new FileInputStream(args[0]);
			Buffer buffer = new Buffer(1024);
			
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
			System.out.println("number of lines: " + LinesTools.countLines(512, stream));
		} 
		catch (IOException | BaseException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void writeCode(String[] args) throws IOException
	{
		String className = "Machine";
		String safeVariableName = "SAFE";
		
		JavaOutputFile file = new JavaOutputFile(System.out);
		
		file.print("// Hello World");
		file.print();
		file.print("public class " + className);
		file.printLeftBrace();
		file.print("private static final boolean " + safeVariableName + " = false;");
		
		file.print();
		file.print("private byte[][] buffers;");
		file.print("private int[] pointers;");
		file.print("private boolean test;");
		file.print();
		
		file.print("// Hello World");
		file.print("// I'm some one in the middle of world.");
		file.print("// I can be any one, and any can be me.");
		
		file.printRightBrace();
	}
}
