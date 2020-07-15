
package libs.tools.bytes.safe;

import libs.safe.SafeOptions;
import libs.safe.SafeTools;
import libs.tools.bytes.ByteTools;
import libs.types.bytes.ByteTest;

public class SafeByteTools extends ByteTools
{
	public static final boolean SAFE = SafeOptions.get(SafeByteTools.class);

	// ++ safe functions ++

	// test all and any

	public static boolean testAny(byte[] buffer, int start, int end, byte value)
	{
		if (SAFE)
			SafeTools.checkBufferStartEnd(buffer, start, end);

		return ByteTools.testAny(buffer, start, end, value);
	}

	public static boolean testAll(byte[] buffer, int start, int end, byte value)
	{
		if (SAFE)
			SafeTools.checkBufferStartEnd(buffer, start, end);

		return ByteTools.testAll(buffer, start, end, value);
	}

	public static boolean testAny(byte[] buffer, int start, int end, ByteTest test)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, start, end);
			SafeTools.checkNullArgument(test, "test");
		}

		return ByteTools.testAny(buffer, start, end, test);
	}

	public static boolean testAll(byte[] buffer, int start, int end, ByteTest test)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, start, end);
			SafeTools.checkNullArgument(test, "test");
		}

		return ByteTools.testAll(buffer, start, end, test);
	}

	// fill functions

	public static void fill(byte[] buffer, int start, int end, byte value)
	{
		if (SAFE)
			SafeTools.checkBufferStartEnd(buffer, start, end);

		ByteTools.fill(buffer, start, end, value);
	}

	// area copy functions

	public static void areaCopy(byte[] src, int start, int end, byte[] dest, int offset, int count)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(src, start, end);

			// TOOD: check count better
			SafeTools.checkInvalidIndexMinimum(count, 0, "count");
		}

		ByteTools.areaCopy(src, start, end, dest, offset, count);
	}

	// find test functions

	public static int find(byte[] buffer, int start, int end, ByteTest test)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, start, end);
			SafeTools.checkNullArgument(test, "test");
		}

		return ByteTools.find(buffer, start, end, test);
	}

	public static int findNot(byte[] buffer, int start, int end, ByteTest test)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, start, end);
			SafeTools.checkNullArgument(test, "test");
		}

		return ByteTools.findNot(buffer, start, end, test);
	}

	public static int rfind(byte[] buffer, int start, int end, ByteTest test)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, start, end);
			SafeTools.checkNullArgument(test, "test");
		}

		return ByteTools.rfind(buffer, start, end, test);
	}

	public static int rfindNot(byte[] buffer, int start, int end, ByteTest test)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, start, end);
			SafeTools.checkNullArgument(test, "test");
		}

		return ByteTools.rfindNot(buffer, start, end, test);
	}

	// comparison functions

	public static int compare(byte[] buffer1, int offset1, byte[] buffer2, int offset2, int length)
	{
		if (SAFE)
		{
			SafeTools.checkBufferOffsetLength(buffer1, offset1, length, "buffer1");
			SafeTools.checkBufferOffsetLength(buffer2, offset2, length, "buffer2");

		}

		return ByteTools.compare(buffer1, offset1, buffer2, offset2, length);
	}

	public static boolean isEqual(byte[] buffer1, int offset1, byte[] buffer2, int offset2, int length)
	{
		if (SAFE)
		{
			SafeTools.checkBufferOffsetLength(buffer1, offset1, length, "buffer1");
			SafeTools.checkBufferOffsetLength(buffer2, offset2, length, "buffer2");

		}

		return ByteTools.isEqual(buffer1, offset1, buffer2, offset2, length);
	}

	// starts with & ends with

	public static boolean startsWith(byte[] buffer, int bufferStart, int bufferEnd, byte[] prefix, int prefixStart,
			int prefixEnd)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, bufferStart, bufferEnd, "buffer");
			SafeTools.checkBufferStartEnd(prefix, prefixStart, prefixEnd, "prefix");
		}

		return ByteTools.startsWith(buffer, bufferStart, bufferEnd, prefix, prefixStart, prefixEnd);
	}

	public static boolean endsWith(byte[] buffer, int bufferStart, int bufferEnd, byte[] suffix, int suffixStart,
			int suffixEnd)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, bufferStart, bufferEnd, "buffer");
			SafeTools.checkBufferStartEnd(suffix, suffixStart, suffixEnd, "suffix");
		}

		return ByteTools.endsWith(buffer, bufferStart, bufferEnd, suffix, suffixStart, suffixEnd);
	}

	// search

	public static int lsearch(byte[] source, int sourceStart, int sourceEnd, byte[] buffer, int bufferStart,
			int bufferEnd)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, bufferStart, bufferEnd, "buffer");
			SafeTools.checkBufferStartEnd(source, sourceStart, sourceEnd, "source");
		}

		return ByteTools.lsearch(source, sourceStart, sourceEnd, buffer, bufferStart, bufferEnd);
	}

	public static int rsearch(byte[] source, int sourceStart, int sourceEnd, byte[] buffer, int bufferStart,
			int bufferEnd)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, bufferStart, bufferEnd, "buffer");
			SafeTools.checkBufferStartEnd(source, sourceStart, sourceEnd, "source");
		}

		return ByteTools.rsearch(source, sourceStart, sourceEnd, buffer, bufferStart, bufferEnd);
	}

	// read functions

	public static long read(byte[] buffer, int offset, int size)
	{
		if (SAFE)
		{
			SafeTools.checkIntegerBytes(size);
			SafeTools.checkBufferOffsetLength(buffer, offset, size);
		}

		return ByteTools.read(buffer, offset, size);
	}

	public static short readShort(byte[] buffer, int offset)
	{
		if (SAFE)
			SafeTools.checkBufferOffsetLength(buffer, offset, Short.BYTES);

		return ByteTools.readShort(buffer, offset);
	}

	public static int readInt(byte[] buffer, int offset)
	{
		if (SAFE)
			SafeTools.checkBufferOffsetLength(buffer, offset, Integer.BYTES);

		return ByteTools.readInt(buffer, offset);
	}

	public static long readLong(byte[] buffer, int offset)
	{
		if (SAFE)
			SafeTools.checkBufferOffsetLength(buffer, offset, Long.BYTES);

		return ByteTools.readLong(buffer, offset);
	}

	// write functions

	public static void write(byte[] buffer, int offset, int size, long value)
	{
		if (SAFE)
		{
			SafeTools.checkIntegerBytes(size);
			SafeTools.checkBufferOffsetLength(buffer, offset, size);
		}

		ByteTools.write(buffer, offset, size, value);
	}

	public static void writeShort(byte[] buffer, int offset, short value)
	{
		if (SAFE)
			SafeTools.checkBufferOffsetLength(buffer, offset, Short.BYTES);

		ByteTools.writeShort(buffer, offset, value);
	}

	public static void writeInt(byte[] buffer, int offset, int value)
	{
		if (SAFE)
			SafeTools.checkBufferOffsetLength(buffer, offset, Integer.BYTES);

		ByteTools.writeInt(buffer, offset, value);
	}

	public static void writeLong(byte[] buffer, int offset, long value)
	{
		if (SAFE)
			SafeTools.checkBufferOffsetLength(buffer, offset, Long.BYTES);

		ByteTools.writeLong(buffer, offset, value);
	}
}
