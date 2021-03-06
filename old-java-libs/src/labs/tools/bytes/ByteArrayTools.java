
package labs.tools.bytes;

import labs.tools.MathTools;
import labs.tools.types.ArrayTools;

public class ByteArrayTools
{
	// +++ algorithms +++

	public static byte[] slice(byte[] buffer, int start, int end)
	{
		int length = end - start;

		byte[] newArray = new byte[length];
		System.arraycopy(buffer, start, newArray, 0, length);

		return newArray;
	}

	private static byte[] resize(byte[] buffer, int size, int offset)
	{
		byte[] newArray = new byte[size];
		System.arraycopy(buffer, 0, newArray, offset, MathTools.min(buffer.length, size));

		return newArray;
	}

	public static byte[] extend(byte[] buffer, int size)
	{
		return resize(buffer, buffer.length + size, 0);
	}

	public static byte[] extendLeft(byte[] buffer, int size)
	{
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

	public static byte[] concatenate(byte[] buffer1, int start1, int end1, byte[] buffer2, int start2, int end2)
	{
		int length1 = end1 - start1;
		int length2 = end2 - start2;

		byte[] result = new byte[length1 + length2];
		System.arraycopy(buffer1, start1, result, 0, length1);
		System.arraycopy(buffer2, start2, result, length1, length2);

		return result;
	}

	public static byte[] joinSep(byte[] sep, byte[]... arrays)
	{
		int length;
		byte[] newArray;

		if (sep.length == 0)
			return join(arrays);

		length = ArrayTools.sumArrayLengths(arrays);

		if (length == 0)
			return null;

		length += (arrays.length - 1) * sep.length;

		newArray = new byte[length];

		System.arraycopy(arrays[0], 0, newArray, 0, arrays[0].length);
		length = arrays[0].length;

		for (int i = 1; i < arrays.length; i += 1)
		{
			System.arraycopy(sep, 0, newArray, length, sep.length);
			length += sep.length;

			System.arraycopy(arrays[i], 0, newArray, length, arrays[i].length);
			length += arrays[i].length;
		}

		return newArray;
	}

	public static byte[] join(byte[]... arrays)
	{
		byte[] result;
		int length;

		length = ArrayTools.sumArrayLengths(arrays);

		if (length == 0)
			return null;

		result = new byte[length];

		length = 0;
		for (int i = 0; i < arrays.length; i += 1)
		{
			System.arraycopy(arrays[i], 0, result, length, arrays[i].length);
			length += arrays[i].length;
		}

		return result;
	}
}
