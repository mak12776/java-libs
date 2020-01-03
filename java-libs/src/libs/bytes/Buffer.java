package libs.bytes;

import java.io.IOException;
import java.io.InputStream;

import libs.tools.ByteTools;
import libs.tools.SafeTools;

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
	
	// fields functions
	
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
	
	public byte get(int index)
	{
		if (SafeTools.CHECK_INDEX_OUT_OF_BOUNDS)
			SafeTools.checkIndexOutOfBounds(index, 0, length);
		
		return buffer[index];
	}
	
	public boolean isEmpty()
	{
		return (length == 0);
	}
	
	public boolean isFull()
	{
		return (length == buffer.length);
	}
	
	// file reading functions
	
	public int readFile(InputStream stream) throws IOException
	{
		length = stream.read(buffer);
		if (length == -1)
			length = 0;
		return length;
	}
	
	public int readFileFull(InputStream stream) throws IOException
	{
		int readNumber;
		
		length = 0;
		while (length != buffer.length)
		{
			readNumber = stream.read(buffer, length, buffer.length - length);
			if (readNumber == -1)
				return length;
			length += readNumber;
		}
		return length;
	}
	
	public int readFileMore(InputStream stream) throws IOException
	{
		int readNumber;
		
		readNumber = stream.read(buffer, length, buffer.length - length);
		if (readNumber == -1)
			return 0;
		length += readNumber;
		return readNumber;
	}
	
	// append functions
	
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