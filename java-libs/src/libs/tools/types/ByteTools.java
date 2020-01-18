
package libs.tools.types;

import libs.bytes.ByteTest;
import libs.tools.SafeTools;

public class ByteTools
{
	public static final boolean CHECK_BUFFER_START_END = false;
	public static final boolean CHECK_BUFFER_OFFSET_LENGTH = false;
	
	// +++ algorithms +++

	// test all and any

	public static boolean testAny(byte[] buffer, int start, int end, ByteTest test)
	{
		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		for (; start < end; start += 1)
			if (test.test(buffer[start]))
				return true;
		return false;
	}

	public static boolean testAny(byte[] buffer, int start, int end, byte value)
	{
		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		for (; start < end; start += 1)
			if (buffer[start] == value)
				return true;
		return false;
	}

	public static boolean testAll(byte[] buffer, int start, int end, ByteTest test)
	{
		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		for (; start < end; start += 1)
			if (!test.test(buffer[start]))
				return false;
		return true;
	}

	public static boolean testAll(byte[] buffer, int start, int end, byte value)
	{
		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		for (; start < end; start += 1)
			if (buffer[start] != value)
				return false;
		return true;
	}
	
	// fill functions
	
	public static void fill(byte[] buffer, int start, int end, byte value)
	{
		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		for (; start < end; start += 1)
			buffer[start] = value;
	}

	// find test functions

	public static int find(byte[] buffer, int start, int end, ByteTest test)
	{
		int index;

		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);

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

		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);

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

		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);

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

		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);

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

	public static int compare(byte[] buffer, int bufferOffset, byte[] bytes, int bytesOffset, int length)
	{
		if (CHECK_BUFFER_OFFSET_LENGTH)
		{
			SafeTools.checkBufferOffsetLength(buffer, bufferOffset, length, "buffer");
			SafeTools.checkBufferOffsetLength(bytes, bytesOffset, length, "bytes");
		}
		
		int diff = 0;
		for (int i = 0; i < length; i += 1)
		{
			diff = bytes[bytesOffset + i] - buffer[bufferOffset + i];
			if (diff != 0)
				break;
		}
		return diff;
	}

	public static boolean isEqual(byte[] buffer, int bufferOffset, byte[] bytes, int bytesOffset, int length)
	{
		if (CHECK_BUFFER_OFFSET_LENGTH)
		{
			SafeTools.checkBufferOffsetLength(buffer, bufferOffset, length, "buffer");
			SafeTools.checkBufferOffsetLength(bytes, bytesOffset, length, "bytes");
		}
		
		return compare(buffer, bufferOffset, bytes, bytesOffset, length) == 0;
	}

	public static void copy(byte[] destination, int destinationOffset, byte[] source, int sourceOffset, int length)
	{
		if (CHECK_BUFFER_OFFSET_LENGTH)
		{
			SafeTools.checkBufferOffsetLength(destination, destinationOffset, length, "destination");;
			SafeTools.checkBufferOffsetLength(source, sourceOffset, length, "source");;
		}
		
		System.arraycopy(source, sourceOffset, destination, destinationOffset, length);
	}

	// starts with & ends with

	public static boolean startsWith(byte[] buffer, int bufferStart, int bufferEnd, byte[] bytes, int bytesStart,
			int bytesEnd)
	{
		if (CHECK_BUFFER_START_END)
		{
			SafeTools.checkBufferStartEnd(buffer, bufferStart, bufferEnd, "buffer");
			SafeTools.checkBufferStartEnd(bytes, bytesStart, bytesEnd, "bytes");
		}
		
		int bufferLength = bufferEnd - bufferStart;
		int bytesLength = bytesEnd - bytesStart;
		
		return (bufferLength >= bytesLength) && isEqual(buffer, bufferStart, bytes, bytesStart, bytesLength);
	}

	public static boolean endsWith(byte[] buffer, int bufferStart, int bufferEnd, byte[] bytes, int bytesStart,
			int bytesEnd)
	{
		if (CHECK_BUFFER_START_END)
		{
			SafeTools.checkBufferStartEnd(buffer, bufferStart, bufferEnd, "buffer");
			SafeTools.checkBufferStartEnd(bytes, bytesStart, bytesEnd, "bytes");
		}
		
		int bufferLength = bufferEnd - bufferStart;
		int bytesLength = bytesEnd - bytesStart;
		
		return (bufferLength >= bytesLength)
				&& isEqual(buffer, bufferEnd - bytesLength, bytes, bytesStart, bytesLength);
	}

	// search

	public static int search(byte[] buffer, int bufferStart, int bufferEnd, byte[] bytes, int bytesStart, int bytesEnd)
	{
		if (CHECK_BUFFER_START_END)
		{
			SafeTools.checkBufferStartEnd(buffer, bufferStart, bufferEnd, "buffer");
			SafeTools.checkBufferStartEnd(bytes, bytesStart, bytesEnd, "bytes");
		}
		
		int bufferLength = bufferEnd - bufferStart;
		int bytesLength = bytesEnd - bytesStart;

		if (bufferLength >= bytesLength)
		{
			for (int index = bufferStart, end = bufferEnd - bytesLength; index <= end; index += 1)
			{
				if (isEqual(buffer, index, bytes, bytesStart, bytesLength))
					return index;
			}
		}
		return bufferEnd;
	}

	public static int lsearch(byte[] buffer, int bufferStart, int bufferEnd, byte[] bytes, int bytesStart, int bytesEnd)
	{
		if (CHECK_BUFFER_START_END)
		{
			SafeTools.checkBufferStartEnd(buffer, bufferStart, bufferEnd, "buffer");
			SafeTools.checkBufferStartEnd(bytes, bytesStart, bytesEnd, "bytes");
		}
		
		int bufferLength = bufferEnd - bufferStart;
		int bytesLength = bytesEnd - bytesStart;

		if (bufferLength >= bytesLength)
		{
			for (int index = bufferEnd - bytesLength; index >= 0; index -= 1)
			{
				if (isEqual(buffer, index, bytes, bytesStart, bytesLength))
					return index;
			}
		}
		return bufferEnd;
	}
}
