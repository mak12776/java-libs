package olds;

import java.io.IOException;
import java.io.InputStream;

public class LinkedBufferStream 
{
	public InputStream stream;
	public LinkedBuffer buffer;
	public int bufferSize;
	
	public LinkedBufferStream(InputStream stream, int bufferSize)
	{
		this.stream = stream;
		this.buffer = null;
		this.bufferSize = bufferSize;
	}
	
	public LinkedBuffer nextBuffer() throws IOException
	{
		LinkedBuffer next;
		if (buffer == null)
		{
			buffer = new LinkedBuffer(bufferSize, null);
			buffer.readFileFull(stream);
		}
		else
		{
			next = new LinkedBuffer(bufferSize, null);
			if (next.readFileFull(stream) == 0)
			{
				return null;
			}
			buffer = buffer.next = next;
		}
		return buffer;
	}
}
