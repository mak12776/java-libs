package writers;

import java.io.OutputStream;

public class Writer
{
	public OutputStream file;
	
	public Writer(OutputStream file)
	{
		this.file = file;
	}
}
