package labs.javaline;

import java.io.InputStream;
import java.io.OutputStream;

public class Generator
{
	private InputStream stream;
	private GeneratorSettings settings;
	
	public Generator(InputStream stream, GeneratorSettings settings)
	{
		this.stream = stream;
		this.settings = settings;
	}
	
	public void generate(OutputStream outFile)
	{
		
	}
}
