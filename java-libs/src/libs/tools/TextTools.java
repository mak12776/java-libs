package libs.tools;

public class TextTools
{
	public static byte[] newSpaceLine(int size)
	{
		byte[] result;

		result = new byte[size];

		size -= 1;
		for (int index = 0; index < size; index += 1)
		{
			result[index] = ' ';
		}
		result[size] = '\n';

		return result;
	}
}
