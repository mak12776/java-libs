package olds;

import java.io.FileInputStream;
import java.io.IOException;

public class Tests 
{

	public static void test(String[] args) throws IOException
	{
		args = new String[] {"code.rest"};
		for (int i = 0; i < args.length; i += 1)
		{
			FileInputStream stream = new FileInputStream(args[i]);
			LineStream lineStream = new LineStream(new ByteStream(stream, 8192));
			
			Line line = lineStream.nextLine();
			while (line != null)
			{
				System.out.println(line.number + ": [" + line.getLine().replace("\n", "\\n").replace("\t", "\\t") +"]");
				line = lineStream.nextLine();
			}
		}
	}
}
