
package libs.buffers.immutable;

import java.io.IOException;
import java.io.InputStream;

import libs.bytes.ByteTools;
import libs.exceptions.BufferIsFullException;
import libs.exceptions.NotEnoughDataException;

public class Buffer
{
	protected byte[] buffer;
	protected int length;

	// constructors
	
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
	
	protected Buffer() { }

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
	
	// remaining
	
	public int remaining()
	{
		return buffer.length - length;
	}
	
	// get and set byte at index

	public byte get(int index)
	{
		return buffer[index];
	}
	
	public void set(int index, byte value)
	{
		buffer[index] = value;
	}
	
	// members test functions
	
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
	
	// clear & delete functions
	
	public void clear()
	{
		this.length = 0;
	}
	
	public void delete(int length, final boolean checkEnoughData, final boolean fromLeft)
	{
		if (length == 0) 
			return;
		
		if (length >= this.length)
		{
			if (checkEnoughData)
			{
				if (length > this.length)
					throw new NotEnoughDataException();
			}
			
			this.length = 0;
			return;
		}
		
		if (fromLeft)
			System.arraycopy(buffer, length, buffer, 0, this.length - length);
		
		this.length -= length;
	}
	
	// fill functions
	
	public void fill(byte value)
	{
		ByteTools.fill(buffer, 0, length, value);
	}
	
	public void fillAll(byte value)
	{
		ByteTools.fill(buffer, 0, buffer.length, value);
		length = buffer.length;
	}

	// append functions
	
	public void append(byte[] buffer, int start, int end)
	{
		int length = end - start;

		if (length > this.buffer.length - this.length)
			throw new BufferIsFullException();

		System.arraycopy(buffer, start, this.buffer, this.length, length);
		this.length += length;
	}
	
	public void append(byte[] buffer)
	{
		append(buffer, 0, buffer.length);
	}
	
	// pop buffer functions
	
	public void pop(byte[] buffer, int start, int end)
	{
		int length = end - start;
		
		if (length > this.length)
			throw new NotEnoughDataException();
		
		System.arraycopy(this.buffer, this.length - length, buffer, start, length);
		this.length -= length;
	}
	
	public void pop(byte[] buffer)
	{		
		pop(buffer, 0, buffer.length);
	}
	
	
	// ++ append and pop integer types ++
	
	// TODO: may be this functions can be removed.
	
	// append integer types
	
	public void append(int size, long value)
	{
		if (size > this.buffer.length - this.length)
			throw new BufferIsFullException();

		ByteTools.write(buffer, length, size, value);
		length += size;
	}

	public void appendByte(byte value)
	{
		if (Byte.BYTES > this.buffer.length - this.length)
			throw new BufferIsFullException();

		buffer[length] = value;
		length += Byte.BYTES;
	}

	public void appendShort(short value)
	{
		if (Short.BYTES > this.buffer.length - this.length)
			throw new BufferIsFullException();

		ByteTools.writeShort(buffer, length, value);
		length += Short.BYTES;
	}

	public void appendInt(int value)
	{
		if (Integer.BYTES > this.buffer.length - this.length)
			throw new BufferIsFullException();

		ByteTools.writeInt(buffer, length, value);
		length += Integer.BYTES;
	}

	public void appendLong(long value)
	{
		if (Long.BYTES > this.buffer.length - this.length)
			throw new BufferIsFullException();

		ByteTools.writeLong(buffer, length, value);
		length += Long.BYTES;
	}
	
	// pop integer types
	
	public long pop(int size)
	{
		if (size > this.length)
			throw new NotEnoughDataException();
		
		length -= size;
		return ByteTools.read(buffer, length, size);
	}
	
	public byte popByte()
	{
		if (Byte.BYTES > this.length)
			throw new NotEnoughDataException();
		
		length -= Byte.BYTES;
		return buffer[length];
	}
	
	public short popShort()
	{
		if (Short.BYTES > this.length)
			throw new NotEnoughDataException();
		
		length -= Short.BYTES;
		return ByteTools.readShort(buffer, length);
	}
	
	public int popInt()
	{
		if (Integer.BYTES > this.length)
			throw new NotEnoughDataException();
		
		length -= Integer.BYTES;
		return ByteTools.readInt(buffer, length);
	}
	
	public long popLong()
	{
		if (Long.BYTES > this.length)
			throw new NotEnoughDataException();
		
		length -= Long.BYTES;
		return ByteTools.readLong(buffer, length);
	}
}
