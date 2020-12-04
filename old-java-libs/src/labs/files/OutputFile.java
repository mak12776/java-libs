
package labs.files;

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

	public final void write(byte[] buffer) throws IOException
	{
		stream.write(buffer);
	}

	public final void write(byte[] buffer, int offset, int length) throws IOException
	{
		stream.write(buffer, offset, length);
	}

	public final void print() throws IOException
	{
		stream.write(newline.getBytes());
	}

	public final void print(String string) throws IOException
	{
		stream.write(string.getBytes());
		print();
	}
}
