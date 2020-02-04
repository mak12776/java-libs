
package libs.bytes;

public class ByteTools
{
	// +++ algorithms +++
	
	// test all and any
	
	public static boolean testAny(byte[] buffer, int start, int end, byte value)
	{
		for (; start < end; start += 1)
			if (buffer[start] == value)
				return true;
		return false;
	}
	
	public static boolean testAll(byte[] buffer, int start, int end, byte value)
	{
		for (; start < end; start += 1)
			if (buffer[start] != value)
				return false;
		return true;
	}
	
	public static boolean testAny(byte[] buffer, int start, int end, ByteTest test)
	{
		for (; start < end; start += 1)
			if (test.test(buffer[start]))
				return true;
		return false;
	}
	
	public static boolean testAll(byte[] buffer, int start, int end, ByteTest test)
	{
		for (; start < end; start += 1)
			if (!test.test(buffer[start]))
				return false;
		return true;
	}
	
	// fill functions
	
	public static void fill(byte[] buffer, int start, int end, byte value)
	{
		for (; start < end; start += 1)
			buffer[start] = value;
	}
	
	// area copy functions
	
	public static void areaCopy(byte[] src, int start, int end, byte[] dest, int offset, int count)
	{
		if (count == 0)
			return;
		
		int length = end - start;
		
		if (length == 1)
		{
			fill(dest, offset, offset + count, src[start]);
			return;
		}
		
		System.arraycopy(src, start, dest, offset, length);
		
		int writeOffset = offset + length;
		int endOffset = offset + (length * count);
		
		while (writeOffset < endOffset)
		{
			System.arraycopy(dest, offset, dest, writeOffset, length);
			writeOffset += length;

			length = writeOffset - offset;
			if (length > endOffset - writeOffset)
				length = endOffset - writeOffset; 
		}
	}
	
	// find test functions
	
	public static int find(byte[] buffer, int start, int end, ByteTest test)
	{
		int index;

		index = start;
		while (index < end)
		{
			if (test.test(buffer[index]))
				return index;
			index += 1;
		}
		return end;
	}
	
	public static int findNot(byte[] buffer, int start, int end, ByteTest test)
	{
		int index;

		index = start;
		while (index < end)
		{
			if (!test.test(buffer[index]))
				return index;
			index += 1;
		}
		return end;
	}
	
	public static int rfind(byte[] buffer, int start, int end, ByteTest test)
	{
		int index;

		index = end;
		while (index > start)
		{
			index -= 1;
			if (test.test(buffer[index]))
				return index;
		}
		return end;
	}

	public static int rfindNot(byte[] buffer, int start, int end, ByteTest test)
	{
		int index;

		index = end;
		while (index > start)
		{
			index -= 1;
			if (!test.test(buffer[index]))
				return index;
		}
		return end;
	}

	// comparison functions

	public static int compare(byte[] buffer1, int offset1, byte[] buffer2, int offset2, int length)
	{
		int diff = 0;
		for (int i = 0; i < length; i += 1)
		{
			diff = buffer2[offset2 + i] - buffer1[offset1 + i];
			if (diff != 0)
				break;
		}
		return diff;
	}

	public static boolean isEqual(byte[] buffer1, int offset1, byte[] buffer2, int offset2, int length)
	{
		return compare(buffer1, offset1, buffer2, offset2, length) == 0;
	}

	// starts with & ends with

	public static boolean startsWith(byte[] buffer, int bufferStart, int bufferEnd, byte[] prefix, int prefixStart, int prefixEnd)
	{
		int bufferLength = bufferEnd - bufferStart;
		int prefixLength = prefixEnd - prefixStart;
		
		return (bufferLength >= prefixLength) && isEqual(buffer, bufferStart, prefix, prefixStart, prefixLength);
	}

	public static boolean endsWith(byte[] buffer, int bufferStart, int bufferEnd, byte[] suffix, int suffixStart, int suffixEnd)
	{
		int bufferLength = bufferEnd - bufferStart;
		int suffixLength = suffixEnd - suffixStart;
		
		return (bufferLength >= suffixLength) && isEqual(buffer, bufferEnd - suffixLength, suffix, suffixStart, suffixLength);
	}

	// search

	public static int lsearch(byte[] source, int sourceStart, int sourceEnd, byte[] buffer, int bufferStart, int bufferEnd)
	{
		int sourceLength = sourceEnd - sourceStart;
		int bufferLength = bufferEnd - bufferStart;

		if (sourceLength >= bufferLength)
		{
			for (int index = sourceStart, end = sourceEnd - bufferLength; index <= end; index += 1)
			{
				if (isEqual(source, index, buffer, bufferStart, bufferLength))
					return index;
			}
		}
		return sourceEnd;
	}

	public static int rsearch(byte[] source, int sourceStart, int sourceEnd, byte[] buffer, int bufferStart, int bufferEnd)
	{			
		int sourceLength = sourceEnd - sourceStart;
		int bufferLength = bufferEnd - bufferStart;

		if (sourceLength >= bufferLength)
		{
			for (int index = sourceEnd - bufferLength; index >= 0; index -= 1)
			{
				if (isEqual(source, index, buffer, bufferStart, bufferLength))
					return index;
			}
		}
		return sourceEnd;
	}
	
	// read functions

	public static long read(byte[] buffer, int offset, int size)
	{
		long value = 0;

		for (int index = 0; index < size; index += 1)
		{
			value <<= 8;
			value = value | ((long) buffer[offset + index] & 0xFF);
		}

		return value;
	}

	public static short readShort(byte[] buffer, int offset)
	{
		return 	(short) (
					((buffer[offset++] & 0xFF) << 8) |
					 (buffer[offset] & 0xFF)
				);
	}

	public static int readInt(byte[] buffer, int offset)
	{
		return 	((buffer[offset++] & 0xFF) << (8 * 3)) |
				((buffer[offset++] & 0xFF) << (8 * 2)) |
				((buffer[offset++] & 0xFF) << (8 * 1)) |
				(buffer[offset++] & 0xFF);
	}

	public static long readLong(byte[] buffer, int offset)
	{
		return 	(( (long) buffer[offset++] & 0xFF ) << (8 * 7)) |
				(( (long) buffer[offset++] & 0xFF ) << (8 * 6)) |
				(( (long) buffer[offset++] & 0xFF ) << (8 * 5)) |
				(( (long) buffer[offset++] & 0xFF ) << (8 * 4)) |
				(( (long) buffer[offset++] & 0xFF ) << (8 * 3)) |
				(( (long) buffer[offset++] & 0xFF ) << (8 * 2)) |
				(( (long) buffer[offset++] & 0xFF ) << (8 * 1)) |
				 ( (long) buffer[offset] & 0xFF);
	}
	
	// write functions

	public static void write(byte[] buffer, int offset, int size, long value)
	{
		for (int index = size - 1; index >= 0; index -= 1)
		{
			buffer[offset + index] = (byte) (value & 0xFF);
			value >>>= 8;
		}
	}

	public static void writeShort(byte[] buffer, int offset, short value)
	{
		buffer[offset++] = (byte) ((value >> 8) & 0xFF);
		buffer[offset] = (byte) (value & 0xFF);
	}

	public static void writeInt(byte[] buffer, int offset, int value)
	{
		buffer[offset++] = (byte) ((value >> (8 * 3)) & 0xFF);
		buffer[offset++] = (byte) ((value >> (8 * 2)) & 0xFF);
		buffer[offset++] = (byte) ((value >> (8 * 1)) & 0xFF);
		buffer[offset] = (byte) (value & 0xFF);
	}

	public static void writeLong(byte[] buffer, int offset, long value)
	{			
		buffer[offset++] = (byte) ((value >> (8 * 7)) & 0xFF);
		buffer[offset++] = (byte) ((value >> (8 * 6)) & 0xFF);
		buffer[offset++] = (byte) ((value >> (8 * 5)) & 0xFF);
		buffer[offset++] = (byte) ((value >> (8 * 4)) & 0xFF);
		buffer[offset++] = (byte) ((value >> (8 * 3)) & 0xFF);
		buffer[offset++] = (byte) ((value >> (8 * 2)) & 0xFF);
		buffer[offset++] = (byte) ((value >> (8 * 1)) & 0xFF);
		buffer[offset] = (byte) (value & 0xFF);
	}
	
}
