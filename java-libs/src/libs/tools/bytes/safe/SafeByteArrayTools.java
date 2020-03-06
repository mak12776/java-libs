package libs.tools.bytes.safe;

import libs.safe.SafeOptions;
import libs.safe.SafeTools;
import libs.tools.bytes.ByteArrayTools;

public class SafeByteArrayTools
{
	public static final boolean SAFE = SafeOptions.get(ByteArrayTools.class);

	public static byte[] slice(byte[] buffer, int start, int end)
	{
		if (SAFE)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		return ByteArrayTools.slice(buffer, start, end);
	}
	
	public static byte[] extend(byte[] buffer, int size)
	{
		if (SAFE)
		{
			SafeTools.checkNullArgument(buffer, "buffer");
			SafeTools.checkNegativeZeroIndex(size, "size");
		}
		
		return ByteArrayTools.extend(buffer, size);
	}
	
	public static byte[] extendLeft(byte[] buffer, int size)
	{
		if (SAFE)
		{
			SafeTools.checkNullArgument(buffer, "buffer");
			SafeTools.checkNegativeZeroIndex(size, "size");
		}
		
		return ByteArrayTools.extendLeft(buffer, size);
	}
	
	public static byte[] concatenate(byte[] buffer1, int start1, int end1, byte[] buffer2, int start2, int end2)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer1, start1, end1);
			SafeTools.checkBufferStartEnd(buffer2, start2, end2);
		}
		
		return ByteArrayTools.concatenate(buffer1, start1, end1, buffer2, start2, end2);
	}
	
	public static byte[] concatenate(byte[] buffer1, byte[] buffer2)
	{
		if (SAFE)
		{
			SafeTools.checkNullArgument(buffer1, "buffer1");
			SafeTools.checkNullArgument(buffer2, "buffer2");
		}
		
		return ByteArrayTools.concatenate(buffer1, buffer2);
	}
}
