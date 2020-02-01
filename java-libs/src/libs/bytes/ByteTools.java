
package libs.bytes;

import libs.safe.SafeOptions;
import libs.safe.SafeTools;

public class ByteTools
{
	public static final boolean SAFE = SafeOptions.get(ByteTools.class);
	
	public static class Unsafe
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
	}
	
	// ++ safe functions ++
	
	// test all and any
	
	public static boolean testAny(byte[] buffer, int start, int end, byte value)
	{
		if (SAFE)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		return Unsafe.testAny(buffer, start, end, value);
	}
	
	public static boolean testAll(byte[] buffer, int start, int end, byte value)
	{
		if (SAFE)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		return Unsafe.testAll(buffer, start, end, value);
	}
	
	public static boolean testAny(byte[] buffer, int start, int end, ByteTest test)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, start, end);
			SafeTools.checkNullArgument(test, "test");
		}	
		
		return Unsafe.testAny(buffer, start, end, test);
	}
	
	public static boolean testAll(byte[] buffer, int start, int end, ByteTest test)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, start, end);
			SafeTools.checkNullArgument(test, "test");
		}
		
		return Unsafe.testAll(buffer, start, end, test);
	}
	
	// fill functions
	
	public static void fill(byte[] buffer, int start, int end, byte value)
	{
		if (SAFE)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		Unsafe.fill(buffer, start, end, value);
	}
	
	// area copy functions
	
	public static void areaCopy(byte[] src, int start, int end, byte[] dest, int offset, int count)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(src, start, end);
			
			// TOOD: check count better
			SafeTools.checkInvalidIndexMinimum(count, 0, "count");;
		}
		
		Unsafe.areaCopy(src, start, end, dest, offset, count);
	}
	
	// find test functions
	
	public static int find(byte[] buffer, int start, int end, ByteTest test)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, start, end);
			SafeTools.checkNullArgument(test, "test");
		}
		
		return Unsafe.find(buffer, start, end, test);
	}
	
	public static int findNot(byte[] buffer, int start, int end, ByteTest test)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, start, end);
			SafeTools.checkNullArgument(test, "test");
		}
		
		return Unsafe.findNot(buffer, start, end, test);
	}
	
	public static int rfind(byte[] buffer, int start, int end, ByteTest test)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, start, end);
			SafeTools.checkNullArgument(test, "test");
		}
		
		return Unsafe.rfind(buffer, start, end, test);
	}

	public static int rfindNot(byte[] buffer, int start, int end, ByteTest test)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, start, end);
			SafeTools.checkNullArgument(test, "test");
		}
		
		return Unsafe.rfindNot(buffer, start, end, test);
	}
	
	// comparison functions

	public static int compare(byte[] buffer1, int offset1, byte[] buffer2, int offset2, int length)
	{
		if (SAFE)
		{
			SafeTools.checkBufferOffsetLength(buffer1, offset1, length, "buffer1");
			SafeTools.checkBufferOffsetLength(buffer2, offset2, length, "buffer2");
			
		}
		
		return Unsafe.compare(buffer1, offset1, buffer2, offset2, length);
	}

	public static boolean isEqual(byte[] buffer1, int offset1, byte[] buffer2, int offset2, int length)
	{
		if (SAFE)
		{
			SafeTools.checkBufferOffsetLength(buffer1, offset1, length, "buffer1");
			SafeTools.checkBufferOffsetLength(buffer2, offset2, length, "buffer2");
			
		}
		
		return Unsafe.isEqual(buffer1, offset1, buffer2, offset2, length);
	}

	// starts with & ends with

	public static boolean startsWith(byte[] buffer, int bufferStart, int bufferEnd, byte[] prefix, int prefixStart, int prefixEnd)
	{
		if (SAFE)
		{			
			SafeTools.checkBufferStartEnd(buffer, bufferStart, bufferEnd, "buffer");
			SafeTools.checkBufferStartEnd(prefix, prefixStart, prefixEnd, "prefix");
		}
		
		return Unsafe.startsWith(buffer, bufferStart, bufferEnd, prefix, prefixStart, prefixEnd);
	}

	public static boolean endsWith(byte[] buffer, int bufferStart, int bufferEnd, byte[] suffix, int suffixStart, int suffixEnd)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, bufferStart, bufferEnd, "buffer");
			SafeTools.checkBufferStartEnd(suffix, suffixStart, suffixEnd, "suffix");
		}
		
		return Unsafe.endsWith(buffer, bufferStart, bufferEnd, suffix, suffixStart, suffixEnd);
	}

	// search

	public static int lsearch(byte[] source, int sourceStart, int sourceEnd, byte[] buffer, int bufferStart, int bufferEnd)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, bufferStart, bufferEnd, "buffer");
			SafeTools.checkBufferStartEnd(source, sourceStart, sourceEnd, "source");
		}
		
		return Unsafe.lsearch(source, sourceStart, sourceEnd, buffer, bufferStart, bufferEnd);
	}

	public static int rsearch(byte[] source, int sourceStart, int sourceEnd, byte[] buffer, int bufferStart, int bufferEnd)
	{	
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, bufferStart, bufferEnd, "buffer");
			SafeTools.checkBufferStartEnd(source, sourceStart, sourceEnd, "source");
		}
		
		return Unsafe.rsearch(source, sourceStart, sourceEnd, buffer, bufferStart, bufferEnd);
	}
}
