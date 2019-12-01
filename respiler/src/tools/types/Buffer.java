package tools.types;

import tools.BytesTools;
import tools.SafeTools;
import tools.exceptions.BufferFullException;

public class Buffer
{	
	private byte[] buffer;
	private int length;
	
	public Buffer(byte[] buffer, int length)
	{
		this.buffer = buffer;
		this.length = length;
	}
	
	public Buffer(int size)
	{
		this.buffer = new byte[size];
		this.length = 0;
	}
	
	public byte[] getBytes()
	{
		return buffer;
	}
	
	public int size()
	{
		return buffer.length;
	}
	
	public int length()
	{
		return length;
	}
	
	public boolean isEmpty()
	{
		return (length == 0);
	}
	
	public boolean isFull()
	{
		return (length == buffer.length);
	}
	
	public void append(byte[] buffer, int start, int end)
	{
		int bufferLength = end - start;
		
		BytesTools.copy(this.buffer, length, buffer, start, bufferLength);
		length += bufferLength;
	}
	
	public void append(int size, long value)
	{	
		BytesTools.write(buffer, length, size, value);
		length += size;
	}
}
