package labs.javaline;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class JavaLine
{
	public static void generate(InputStream inStream, OutputStream outStream, GeneratorSettings settings)
	{
		Scanner scanner;
		String line;
		String stripedLine;
		
		scanner = new Scanner(inStream);
		
		while (scanner.hasNext())
		{
			line = scanner.nextLine();
			stripedLine = line;
		}
	}
}
