package libs.fio;

import java.io.IOException;
import java.io.OutputStream;

public class OutFile
{
	private OutputStream stream;
	private String newline;
	
	// constructors
	
	public OutFile(OutputStream stream, String newline)
	{
		this.stream = stream;
		this.newline = newline;
	}
	
	public OutFile(OutputStream stream)
	{
		this(stream, "\n");
	}
	
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
