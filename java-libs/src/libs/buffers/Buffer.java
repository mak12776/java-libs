
package libs.buffers;

import java.io.IOException;
import java.io.InputStream;

import libs.bytes.ByteTools;
import libs.exceptions.BufferIsFullException;
import libs.exceptions.NotEnoughDataException;
import libs.exceptions.UnimplementedCodeException;
import libs.safe.SafeOptions;
import libs.safe.SafeTools;

public class Buffer
{
	public static final boolean SAFE = SafeOptions.get(Buffer.class);
	
	public static final boolean CHECK_INTEGER_BYTES = false;
	public static final boolean CHECK_BUFFER_START_END = false;
	
	public static final boolean CHECK_INDEX_OUT_OF_BOUNDS = true;
	public static final boolean CHECK_INVALID_SIZE = false;
	public static final boolean CHECK_INVALID_LENGTH = false;
	
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
	
	public static Buffer safe(byte[] buffer, int length)
	{
		return new Buffer(buffer, length);
	}
	
	public static Buffer safe(int size)
	{
		return new Buffer(size);
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
	
	// byte index

	public byte get(int index)
	{
		if (SAFE)
			SafeTools.checkIndexOutOfBounds(index, 0, length, "index");

		return buffer[index];
	}
	
	public void set(int index, byte value)
	{
		if (SAFE)
			SafeTools.checkIndexOutOfBounds(index, 0, length, "index");

		buffer[index] = value;
	}
	
	// unsafe byte index
	
	public byte unsafeGet(int index)
	{
		return buffer[index];
	}
	
	public void unsafeSet(int index, byte value)
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
		if (CHECK_BUFFER_START_END)
			SafeTools.checkInvalidIndexMaximum(length, 0, "length");
		
		if (length == 0) 
			return;
		
		if (length >= this.length)
		{
			if (checkEnoughData && length > this.length)
				throw new NotEnoughDataException();
			
			this.length = 0;
			return;
		}
		
		if (fromLeft)
			System.arraycopy(buffer, length, buffer, 0, this.length - length);
		
		this.length -= length;
	}

	// append buffer functions
	
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
	
	public void append(byte[] buffer)
	{
		append(buffer, 0, buffer.length);
	}
	
	// pop buffer functions
	
	public void pop(byte[] buffer, int start, int end)
	{
		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		int bufferLength = end - start;
		
		if (bufferLength > this.length)
			throw new NotEnoughDataException();
		
		System.arraycopy(this.buffer, this.length - 1, buffer, start, bufferLength);
		this.length -= bufferLength;
	}
	
	public void pop(byte[] buffer)
	{
		pop(buffer, 0, buffer.length);
	}
	
	// append integer types
	
	public void append(int size, long value)
	{
		if (CHECK_INTEGER_BYTES)
			SafeTools.checkIntegerBytes(size);

		if (size > this.buffer.length - this.length)
			throw new BufferIsFullException();

		ByteTools.write(this.buffer, this.length, size, value);
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
		if (CHECK_INTEGER_BYTES)
			SafeTools.checkIntegerBytes(size);
		
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
