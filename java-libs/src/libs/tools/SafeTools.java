
package libs.tools;

public class SafeTools
{
	/* SafeTools options exist in:
	 * 		Buffer
	 * 		BufferQueue
	 */
	
	// *** variables ***
	
	public static final boolean CHECK_INTEGER_BITS = true;
	public static final boolean CHECK_INTEGER_BYTES = true;

	// *** functions ***
	
	// check integer bits

	public static void checkIntegerBits(final int bits)
	{
		if ((bits != Byte.SIZE) && (bits != Short.SIZE) && (bits != Integer.SIZE) && (bits != Long.SIZE))
			throw new IllegalArgumentException("invalid integer bits: " + bits);
	}

	// check integer bytes

	public static void checkIntegerBytes(final int bytes)
	{
		if ((bytes != Byte.BYTES) && (bytes != Short.BYTES) && (bytes != Integer.BYTES) && (bytes != Long.BYTES))
			throw new IllegalArgumentException("invalid integer bytes: " + bytes);
	}

	// check array index

	public static void checkArrayIndexBytes(final int bytes)
	{
		if ((bytes != Byte.BYTES) && (bytes != Short.BYTES) && (bytes != Integer.BYTES))
			throw new IllegalArgumentException("invalid array index bytes: " + bytes);
	}
	
	public static void checkArrayIndexBits(final int bits)
	{
		if ((bits != Byte.SIZE) && (bits != Short.SIZE) && (bits != Integer.SIZE))
			throw new IllegalArgumentException("invalid array index bytes: " + bits);
	}
	
	// index out of bounds
	
	public static void checkIndexOutOfBounds(final int index, final int min, final int max, String name)
	{
		if ((index < min) || (index >= max))
			throw new IndexOutOfBoundsException(name + " is out of bounds: " + index);
	}
	
	// invalid index
	
	public static void checkInvalidIndex(final int index, final int min, final int max, String name)
	{
		if ((index < min) || (index >= max))
			throw new IllegalArgumentException("invalid " + name + ": " + index);
	}
	
	public static void checkInvalidIndexMinimum(final int index, final int min, String name)
	{
		if ((index < min))
			throw new IllegalArgumentException("invalid " + name + ": " + index);
	}
	
	public static void checkInvalidIndexMaximum(final int index, final int max, String name)
	{
		if ((index >= max))
			throw new IllegalArgumentException("invalid " + name + ": " + index);
	}
	
	public static void checkNegativeZeroIndex(final int size, String name)
	{
		if (size <= 0)
			throw new IllegalArgumentException("negative or zero " + name + ": " + size);
	}

	// buffer index

	public static void checkBufferIndex(byte[] buffer, int index, final String name)
	{
		if ((index < 0) || (index > buffer.length))
			throw new IllegalArgumentException("invalid " + name + ": " + index);
	}
	
	// buffer start & end index
	
	public static void checkBufferStartEnd(byte[] buffer, int start, int end, final String bufferName)
	{
		if ((start < 0) || (start >= buffer.length))
			throw new IllegalArgumentException("invalid buffer start index: " + start);
		
		if ((end < 0) || (end >= buffer.length))
			throw new IllegalArgumentException("invalid buffer end index: " + end);
		
		if (end < start)
			throw new IllegalArgumentException(
					bufferName + " end index must be greater than " + bufferName + " start index" +
					", start: " + start + ", end: " + end);
	}
	
	public static void checkBufferStartEnd(byte[] buffer, int start, int end)
	{
		checkBufferStartEnd(buffer, start, end, "buffer");
	}
	
	// buffer offset & length
	
	public static void checkBufferOffsetLength(byte[] buffer, int offset, int length, final String bufferName)
	{
		if ((offset < 0) || (offset >= buffer.length))
			throw new IllegalArgumentException("invalid " + bufferName + " offset: " + offset);
		
		if ((length < 0) || (offset + length > buffer.length))
			throw new IllegalArgumentException("invalid " + bufferName +  " length: " + length);
	}
	
	public static void checkBufferOffsetLength(byte[] buffer, int offset, int length)
	{
		checkBufferOffsetLength(buffer, offset, length, "buffer");
	}
	
	// buffer equal length
	
	public static void checkEqualBufferLength(byte[] buffer1, byte[] buffer2, final String bufferName1, final String bufferName2)
	{
		if (buffer1.length != buffer2.length)
			throw new IllegalArgumentException("length of " + bufferName1 + " & " + bufferName2 + " must be equal.");
	}
}

