
package libs.buffers;

import java.io.IOException;
import java.io.InputStream;

import libs.bytes.ByteTools;
import libs.exceptions.BufferIsFullException;
import libs.exceptions.NotEnoughDataException;
import libs.io.ByteIO;
import libs.tools.SafeTools;

public class Buffer
{
	public static final boolean CHECK_INDEX_OUT_OF_BOUNDS = true;
	public static final boolean CHECK_INVALID_SIZE = false;
	public static final boolean CHECK_INTEGER_BYTES = true;
	public static final boolean CHECK_BUFFER_START_END = false;
	
	private byte[] buffer;
	private int length;

	public Buffer(byte[] buffer, int length)
	{
		this.buffer = buffer;
		this.length = length;
	}

	public Buffer(int size)
	{
		if (CHECK_INVALID_SIZE)
			SafeTools.checkNegativeZeroIndex(size, "size");
		
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
		if (CHECK_INDEX_OUT_OF_BOUNDS)
			SafeTools.checkIndexOutOfBounds(index, 0, length, "index");

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
		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		int bufferLength = end - start;

		if (bufferLength > this.buffer.length - this.length)
			throw new BufferIsFullException();

		System.arraycopy(buffer, start, this.buffer, this.length, bufferLength);
		length += bufferLength;
	}

	public void append(int size, long value)
	{
		if (CHECK_INTEGER_BYTES)
			SafeTools.checkIntegerBytes(size);

		if (size > this.buffer.length - this.length)
			throw new BufferIsFullException();

		ByteIO.write(this.buffer, this.length, size, value);
		length += size;
	}

	public void appendByte(byte value)
	{
		if (Byte.BYTES > this.buffer.length - this.length)
			throw new BufferIsFullException();

		ByteIO.writeByte(buffer, length, value);
		length += Byte.BYTES;
	}

	public void appendShort(short value)
	{
		if (Short.BYTES > this.buffer.length - this.length)
			throw new BufferIsFullException();

		ByteIO.writeShort(buffer, length, value);
		length += Short.BYTES;
	}

	public void appendInt(int value)
	{
		if (Integer.BYTES > this.buffer.length - this.length)
			throw new BufferIsFullException();

		ByteIO.writeInt(buffer, length, value);
		length += Integer.BYTES;
	}

	public void appendLong(long value)
	{
		if (Long.BYTES > this.buffer.length - this.length)
			throw new BufferIsFullException();

		ByteIO.writeLong(buffer, length, value);
		length += Long.BYTES;
	}
	
	// pop functions
	
	public void pop(byte[] array, int start, int end)
	{
		
	}
	
	public long pop(int size)
	{
		if (CHECK_INTEGER_BYTES)
			SafeTools.checkIntegerBytes(size);
		
		if (size > this.length)
			throw new NotEnoughDataException();
		
		length -= size;
		return ByteIO.read(buffer, length, size);
	}
	
	public byte popByte()
	{
		if (Byte.BYTES > this.length)
			throw new NotEnoughDataException();
		
		length -= Byte.BYTES;
		return ByteIO.readByte(buffer, length);
	}
	
	public short popShort()
	{
		if (Short.BYTES > this.length)
			throw new NotEnoughDataException();
		
		length -= Short.BYTES;
		return ByteIO.readShort(buffer, length);
	}
	
	public int popInt()
	{
		if (Integer.BYTES > this.length)
			throw new NotEnoughDataException();
		
		length -= Integer.BYTES;
		return ByteIO.readInt(buffer, length);
	}
	
	public long popLong()
	{
		if (Long.BYTES > this.length)
			throw new NotEnoughDataException();
		
		length -= Long.BYTES;
		return ByteIO.readLong(buffer, length);
	}
}
