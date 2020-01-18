package libs.tools.types;

import libs.tools.MathTools;
import libs.tools.SafeTools;

public class ByteArrayTools
{
	// +++ algorithms +++
	
	public static final boolean CHECK_ZERO_NEGATIVE_SIZE = false; 
	
	public static byte[] resize(byte[] buffer, int size, int offset)
	{
		if (CHECK_ZERO_NEGATIVE_SIZE)
			SafeTools.checkNegativeZeroIndex(size, "size");
		
		byte[] newArray = new byte[size];
		System.arraycopy(buffer, 0, newArray, offset, MathTools.min(buffer.length, size));
		
		return newArray;
	}
	
	public static byte[] extend(byte[] buffer, int size)
	{
		if (CHECK_ZERO_NEGATIVE_SIZE)
			SafeTools.checkNegativeZeroIndex(size, "size");
		
		return resize(buffer, buffer.length + size, 0);
	}
	
	public static byte[] extendLeft(byte[] buffer, int size)
	{
		if (CHECK_ZERO_NEGATIVE_SIZE)
			SafeTools.checkNegativeZeroIndex(size, "size");
		
		return resize(buffer, buffer.length + size, size);
	}
	
	public static byte[] concatenate(byte[] buffer1, byte[] buffer2)
	{
		int length = buffer1.length + buffer2.length;
		
		byte[] newArray = new byte[length];
		System.arraycopy(buffer1, 0, newArray, 0, buffer1.length);
		System.arraycopy(buffer2, 0, newArray, buffer1.length, buffer2.length);
		
		return newArray;
	}
	
	// copy bytes
	
	public static byte[] copy(byte[] buffer, int start, int end)
	{
		int length = end - start;
		
		byte[] newArray = new byte[length];
		System.arraycopy(buffer, start, newArray, 0, length);
		
		return newArray;
	}
}
