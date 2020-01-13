
package libs.tools.types;

public class StringTools
{
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

	public static String toHex(int value)
	{
		return Integer.toHexString(value);
	}

	public static String toHex(long value)
	{
		return Long.toHexString(value);
	}
}
