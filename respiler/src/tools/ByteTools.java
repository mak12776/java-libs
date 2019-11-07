package tools;

public class ByteTools
{
	public static boolean byteIn(byte b, byte[] bytes)
	{
		for (int i = 0; i < bytes.length; i += 1)
		{
			if (bytes[i] == b)
				return true;
		}
		return false;
	}
	
	public static int compareBytes(byte[] buffer, int bufferOffset, byte[] bytes, int bytesOffset, int length)
	{
		int diff = 0;
		for (int i = 0; i < length; i += 1)
		{
			diff = bytes[bytesOffset + i] - buffer[bufferOffset + i];
			if (diff != 0) break;
		}
		return diff;
	}
	
	public static boolean isEqualBytes(byte[] buffer, int bufferOffset, byte[] bytes, int bytesOffset, int length)
	{
		return compareBytes(buffer, bufferOffset, bytes, bytesOffset, length) == 0;
	}
	
}
