
package labs.tools.types;

public class StringTools
{
	// algorithms

	public static String strip(String string, String chars)
	{
		int start = 0;
		int end = string.length();

		while (start < end)
		{
			if (chars.indexOf(string.charAt(start)) == -1)
				break;
			start += 1;
		}

		while (end > start)
		{
			end -= 1;
			if (chars.indexOf(string.charAt(end)) == -1)
				break;
		}

		return null;
	}

	// misc

	public static String joinObject(Object... objects)
	{
		StringBuilder builder = new StringBuilder();
		StringBuilderTools.appendObjects(builder, objects);
		return builder.toString();
	}

	public static String byteArrayToString(byte[] array, int start, int end)
	{
		StringBuilder builder = new StringBuilder();
		StringBuilderTools.appendBytes(builder, array, start, end);
		return builder.toString();
	}

	public static String byteArrayToString(byte[] array, int start, int end, Object prefix, Object suffix)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(prefix);
		StringBuilderTools.appendBytes(builder, array, start, end);
		builder.append(suffix);
		return builder.toString();
	}

	// toHex functions

	public static String toHex(int value)
	{
		return Integer.toHexString(value);
	}

	public static String toHex(long value)
	{
		return Long.toHexString(value);
	}
	
	public static boolean startsWtih(String string, String prefix)
	{
		if (prefix.length() > string.length())
			return false;
		for (int index = 0; index < prefix.length(); index += 1)
			if (string.charAt(index) != prefix.charAt(index)) 
				return false;
		return true;
	}
	
	public static boolean endsWith(String string, String suffix)
	{
		if (suffix.length() > string.length())
			return false;
		int stringIndex = string.length() - suffix.length();
		for (int index = 0; index < suffix.length(); index += 1)
			if (string.charAt(stringIndex + index) != suffix.charAt(index)) 
				return false;
		return true;
	}
}
