package libs.fio;

import java.io.IOException;
import java.io.OutputStream;

public class OutputFile
{
	protected OutputStream stream;
	protected String newline;
	
	// constructors
	
	public OutputFile(OutputStream stream, String newline)
	{
		this.stream = stream;
		this.newline = newline;
	}
	
	public OutputFile(OutputStream stream)
	{
		this(stream, "\n");
	}
	
	// functions
	
	public void write(byte[] buffer) throws IOException
	{
		stream.write(buffer);
	}
	
	public void write(byte[] buffer, int offset, int length) throws IOException
	{
		stream.write(buffer, offset, length);
	}
	
	public void print() throws IOException
	{
		stream.write(newline.getBytes());
	}
	
	public void print(String string) throws IOException
	{
		stream.write(string.getBytes());
		print();
	}
}
