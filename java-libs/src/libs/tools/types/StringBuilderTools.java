
package libs.tools.types;

public final class StringBuilderTools
{
	public static void appendObjects(StringBuilder builder, Object... objects)
	{
		for (int i = 0; i < objects.length; i += 1)
		{
			builder.append(objects[i]);
		}
	}

	public static void appendReprByte(StringBuilder builder, byte b)
	{
		switch (b)
		{
		case '\n':
			builder.append("\\n");
			break;
		case '\r':
			builder.append("\\r");
			break;
		case '\t':
			builder.append("\\t");
			break;
		default:
			builder.append((char) b);
		}
	}

	public static void appendReprBytes(StringBuilder builder, byte[] array, int start, int end)
	{
		for (int index = start; index < end; index += 1)
			appendReprByte(builder, array[index]);
	}

	public static void appendBytes(StringBuilder builder, byte[] array, int start, int end)
	{
		for (int index = start; index < end; index += 1)
			builder.append((char) array[index]);
	}
}
