package libs.tools;

public class SafeTools
{
	public static final boolean CHECK_INTEGER_BITS = true;
	
	public static void checkIntegerBits(final int bits)
	{
		if ((bits != Byte.SIZE) && (bits != Short.SIZE) && (bits != Integer.SIZE) && (bits != Long.SIZE))
			throw new IllegalArgumentException("invalid size: " + bits);
	}
	
	public static final boolean CHECK_INTEGER_BYTES = true;
	
	public static void checkIntegerBytes(final int bytes)
	{
		if ((bytes != Byte.BYTES) && (bytes != Short.BYTES) && (bytes != Integer.BYTES) && (bytes != Long.BYTES))
			throw new IllegalArgumentException("invalid integer bytes: " + bytes);
	}
	
	public static final boolean CHECK_ARRAY_SIZE_BYTES = false;
	
	public static void checkArraySizeBytes(final int bytes)
	{
		if ((bytes != Byte.BYTES) && (bytes != Short.BYTES) && (bytes != Integer.BYTES))
			throw new IllegalArgumentException("invalid array size bytes: " + bytes);
	}
	
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
