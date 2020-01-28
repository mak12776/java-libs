package labs.javaline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class JavaLine
{
	public static void generate(InputStream inputStream, OutputStream outputStream, GeneratorSettings settings) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		while ((line = reader.readLine()) != null)
		{
			
		}
	}
}
