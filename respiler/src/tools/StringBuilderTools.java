package tools;

public final class StringBuilderTools 
{
	public static void appendObjects(StringBuilder builder, Object... objects)
	{
		for (int i = 0; i < objects.length; i += 1)
		{
			builder.append(objects[i]);
		}
	}
	
	public static void appendByte(StringBuilder builder, byte b)
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
			builder.append("   ~");
			break;

		default:
			builder.append((char)b);
		}
	}
	
	public static void appendBytes(StringBuilder builder, byte[] array, int start, int end)
	{
		for (int index = start; index < end; index += 1)
		{
			appendByte(builder, array[index]);
		}
	}
}
