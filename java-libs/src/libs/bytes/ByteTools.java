
package libs.bytes;

import libs.safe.SafeTools;

public class ByteTools
{
	public static final boolean CHECK_BUFFER_START_END = false;
	public static final boolean CHECK_BUFFER_OFFSET_LENGTH = false;
	
	public static final boolean CHECK_INVALID_COUNT = true;
	public static final boolean CHECK_NULL_ARGUMENT = true;
	
	public static final boolean SAFE = true;
	
	// +++ algorithms +++

	// test all and any
	
	public static boolean testAny(byte[] buffer, int start, int end, byte value)
	{
		if (SAFE)
		{
			SafeTools.checkNullArgument(buffer, "buffer");
			SafeTools.checkBufferStartEnd(buffer, start, end);
		}
		
		return testAnyUnsafe(buffer, start, end, value);
	}
	
	public static boolean testAnyUnsafe(byte[] buffer, int start, int end, byte value)
	{
		for (; start < end; start += 1)
			if (buffer[start] == value)
				return true;
		return false;
	}
	
	public static boolean testAll(byte[] buffer, int start, int end, byte value)
	{
		if (SAFE)
		{
			SafeTools.checkNullArgument(buffer, "buffer");
			SafeTools.checkBufferStartEnd(buffer, start, end);
		}
		
		return testAllUnsafe(buffer, start, end, value);
	}
	
	public static boolean testAllUnsafe(byte[] buffer, int start, int end, byte value)
	{
		for (; start < end; start += 1)
			if (buffer[start] != value)
				return false;
		return true;
	}
	
	public static boolean testAny(byte[] buffer, int start, int end, ByteTest test)
	{
		if (SAFE)
		{
			SafeTools.checkNullArgument(buffer, "buffer");
			SafeTools.checkBufferStartEnd(buffer, start, end);
			SafeTools.checkNullArgument(test, "test");
		}	
		
		return testAnyUnsafe(buffer, start, end, test);
	}
	
	public static boolean testAnyUnsafe(byte[] buffer, int start, int end, ByteTest test)
	{
		for (; start < end; start += 1)
			if (test.test(buffer[start]))
				return true;
		return false;
	}
	
	public static boolean testAll(byte[] buffer, int start, int end, ByteTest test)
	{
		if (SAFE)
		{
			SafeTools.checkNullArgument(buffer, "buffer");
			SafeTools.checkBufferStartEnd(buffer, start, end);
			SafeTools.checkNullArgument(test, "test");
		}
		
		return testAllUnsafe(buffer, start, end, test);
	}
	
	public static boolean testAllUnsafe(byte[] buffer, int start, int end, ByteTest test)
	{
		for (; start < end; start += 1)
			if (!test.test(buffer[start]))
				return false;
		return true;
	}
	
	// fill functions
	
	public static void fill(byte[] buffer, int start, int end, byte value)
	{
		if (SAFE)
		{
			SafeTools.checkNullArgument(buffer, "buffer");
			SafeTools.checkBufferStartEnd(buffer, start, end);
		}
		
		for (; start < end; start += 1)
			buffer[start] = value;
	}
	
	// area copy functions
	
	public static void areaCopy(byte[] src, int start, int end, byte[] dest, int offset, int count)
	{
		if (SAFE)
		{
			SafeTools.checkNullArgument(src, "src");
			SafeTools.checkNullArgument(dest, "dest");
			
			SafeTools.checkBufferStartEnd(src, start, end);
			SafeTools.checkInvalidIndexMinimum(count, 0, "count");;
		}
		
		
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
		
		return (bufferLength >= bytesLength) && isEqual(buffer, bufferEnd - bytesLength, bytes, bytesStart, bytesLength);
	}

	// search

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
			for (int index = bufferStart, end = bufferEnd - bytesLength; index <= end; index += 1)
			{
				if (isEqual(buffer, index, bytes, bytesStart, bytesLength))
					return index;
			}
		}
		return bufferEnd;
	}

	public static int rsearch(byte[] buffer, int bufferStart, int bufferEnd, byte[] bytes, int bytesStart, int bytesEnd)
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
