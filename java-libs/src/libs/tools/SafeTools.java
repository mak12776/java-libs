package libs.tools;

import libs.tools.types.StringTools;

public class SafeTools
{
	// check integer bits
	
	public static final boolean CHECK_INTEGER_BITS = true;
	
	public static void checkIntegerBits(final int bits)
	{
		if ((bits != Byte.SIZE) && (bits != Short.SIZE) && (bits != Integer.SIZE) && (bits != Long.SIZE))
			throw new IllegalArgumentException("invalid integer bits: " + bits);
	}
	
	// check integer bytes
	
	public static final boolean CHECK_INTEGER_BYTES = true;
	
	public static void checkIntegerBytes(final int bytes)
	{
		if ((bytes != Byte.BYTES) && (bytes != Short.BYTES) && (bytes != Integer.BYTES) && (bytes != Long.BYTES))
			throw new IllegalArgumentException("invalid integer bytes: " + bytes);
	}
	
	// check array size bytes
	
	public static final boolean CHECK_ARRAY_SIZE_BYTES = false;
	
	public static void checkArraySizeBytes(final int bytes)
	{
		if ((bytes != Byte.BYTES) && (bytes != Short.BYTES) && (bytes != Integer.BYTES))
			throw new IllegalArgumentException("invalid array size bytes: " + bytes);
	}
	
	// index out of bounds
	
	public static final boolean CHECK_INDEX_OUT_OF_BOUNDS = true;
	
	public static void checkIndexOutOfBounds(final int index, final int max)
	{
		if (index >= max)
			throw new IndexOutOfBoundsException("index is out of bounds: " + index);
	}
	
	public static void checkIndexOutOfBounds(final int index, final int min, final int max)
	{
		if ((index < min) || (index >= max))
			throw new IndexOutOfBoundsException("index is out of bounds: " + index);
	}
	
	// size
	
	public static final boolean CHECK_INVALID_SIZE = true;
	
	public static void checkInvalidSize(final int size, int max)
	{
		if (size >= max)
			throw new IllegalArgumentException("invalid size: " + size);
	}
	
	public static void checkInvalidSize(final int size, int min, int max)
	{
		if ((size < min) || (size >= max))
			throw new IllegalArgumentException("invalid size: " + size);
	}
	
	// buffer index
	
	public static final boolean CHECK_BUFFER_START_END = false;
	
	private static void checkBufferIndex(byte[] buffer, int index, String name)
	{
		if ((index < 0) || (index > buffer.length))
			throw new IllegalArgumentException("invalid " + name + ": " + index);
	}
	
	public static void checkBufferStartEnd(byte[] buffer, int start, int end)
	{
		checkBufferIndex(buffer, start, "start");
		checkBufferIndex(buffer, start, "end");
		if (start > end)
			throw new IllegalArgumentException(StringTools.joinObject("start is greater than end: start = ", start, ", end = ", end));
	}
}
