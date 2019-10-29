package types;

public final class Tools 
{
	public static String joinObject(Object... objects)
	{
		StringBuffer builder = new StringBuffer();
		for (int i = 0; i < objects.length; i += 1)
		{
			builder.append(objects[i]);
		}
		return builder.toString();
	}
	
	public static void appendObjects(StringBuilder builder, Object... objects)
	{
		for (int i = 0; i < objects.length; i += 1)
		{
			builder.append(objects[i]);
		}
	}
	
	public static String byteArrayToString(byte[] array, Object prefix, Object suffix)
	{
		StringBuffer builder = new StringBuffer();
		
		builder.append(prefix);
		for (int i = 0; i < array.length; i += 1)
		{
			char ch = (char)array[i];
			if (ch == '\n')
			{
				builder.append("\\n");
			}
			else
			{
				builder.append(ch);
			}
		}
		builder.append(suffix);
		
		return builder.toString();
	}
	
	public static String byteArrayToString(byte[] array)
	{
		StringBuffer builder = new StringBuffer();
		for (int i = 0; i < array.length; i += 1)
		{
			char ch = (char)array[i];
			if (ch == '\n')
			{
				builder.append("\\n");
			}
			else
			{
				builder.append(ch);
			}
		}
		return builder.toString();
	}
}
