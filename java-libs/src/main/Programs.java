
package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import libs.exceptions.BaseException;
import libs.files.JavaOutputFile;
import libs.tools.io.LinesTools;
import libs.types.oop.buffers.Buffer;

public class Programs
{
	public static void oldMain(String[] args)
	{
		int j = 0;
		for (int i = 0; i < 1_000_000; i += 1)
			j = i * 100;

		LinkedList<Boolean> tests = new LinkedList<Boolean>();
		Random random = new Random();

		for (int i = 0; i < 1_000; i += 1)
			tests.add(random.nextInt() % 2 == 0);

		byte flags = 0;
		int flagsShift = 5;

		long time1 = System.nanoTime();

		for (int i = 0; i < tests.size(); i += 1)
		{
			if (tests.get(i))
				flags |= 1 << flagsShift;
			else
				flags &= ~(1 << flagsShift);
		}

		long time2 = System.nanoTime();

		for (int index = 0; index < tests.size(); index += 1)
		{
			flags = (byte) ((flags & ~(1 << flagsShift)) | ((tests.get(index) ? 1 : 0) << flagsShift));
		}

		long time3 = System.nanoTime();

		System.out.println(time2 - time1);
		System.out.println(time3 - time2);
	}

	public static void sumNumbers(String[] args)
	{
		if (args.length != 1)
			System.out.println("usage: sum [PATH]");

		try
		{
			FileInputStream stream = new FileInputStream(args[0]);
			Buffer buffer = new Buffer(1024);
		} catch (IOException e)
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
			System.out.println("number of lines: " + LinesTools.countLines(stream, 512));
		} catch (IOException | BaseException e)
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
