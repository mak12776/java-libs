package libs.types;

import libs.tools.ByteTools;

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
		
		ByteTools.copy(this.buffer, this.length, buffer, start, bufferLength);
		length += bufferLength;
	}
	
	public void append(int size, long value)
	{	
		ByteTools.write(this.buffer, this.length, size, value);
		length += size;
	}
	
	public void appendByte(byte value)
	{
		ByteTools.writeByte(buffer, length, value);
		length += Byte.BYTES;
	}
	
	public void appendShort(short value)
	{
		ByteTools.writeShort(buffer, length, value);
		length += Short.BYTES;
	}
	
	public void appnedInt(int value)
	{
		ByteTools.writeInt(buffer, length, value);
		length += Integer.BYTES;
	}
	
	public void appendLong(long value)
	{
		ByteTools.writeLong(buffer, length, value);
		length += Long.BYTES;
	}
}
