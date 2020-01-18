
package libs.tools.types;

public class ArrayTools
{
	@SafeVarargs
	public static <T> int sumArrayLengths(T[]... arrays)
	{
		int total = 0;

		for (int index = 0; index < arrays.length; index += 1)
			total += arrays[index].length;

		return total;
	}

	public static byte[] newArray(int size, byte value)
	{
		byte[] result;

		result = new byte[size];
		for (int i = 0; i < size; i += 1)
			result[i] = value;

		return result;
	}

	public static short[] newArray(int size, short value)
	{
		short[] result;

		result = new short[size];
		for (int i = 0; i < size; i += 1)
			result[i] = value;

		return result;
	}

	public static int[] newArray(int size, int value)
	{
		int[] result;

		result = new int[size];
		for (int i = 0; i < size; i += 1)
			result[i] = value;

		return result;
	}

	public static double[] newArray(int size, double value)
	{
		double[] result;

		result = new double[size];
		for (int i = 0; i < size; i += 1)
			result[i] = value;

		return result;
	}
}
