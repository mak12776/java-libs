package olds;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class ByteStream 
{
	public InputStream stream;
	public LinkedBuffer buffer;
	public int index;
	public int bufferSize;
	public boolean EOF;
	
	public ByteStream(InputStream stream, int bufferSize)
	{
		this.stream = stream;
		this.buffer = null;
		this.bufferSize = bufferSize;
	}
	
	public byte nextByte() throws IOException
	{
		if (buffer == null)
		{
			buffer = new LinkedBuffer(bufferSize, null);
			if (buffer.readFileFull(stream) == 0)
			{
				throw new EOFException();
			}
			index = 0;
		}
		else 
		{
			index += 1;
			if (index == buffer.length)
			{
				if (buffer.isFull())
				{
					buffer.next = LinkedBuffer.readFileFull(stream, bufferSize);
					if (buffer.next == null)
					{
						index -= 1;
						throw new EOFException();
					}
					buffer = buffer.next;
					index = 0;
				}
				else
				{
					index -= 1;
					throw new EOFException();
				}
			}
		}
		return buffer.array[index];
	}
	
	public void decIndex()
	{
		index -= 1;
	}
	
	public void setByteAtIndex(byte b)
	{
		buffer.array[index] = b;
	}
}
