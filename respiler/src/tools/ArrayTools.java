package tools;

import java.lang.reflect.Array;

public class ArrayTools
{
	public static Object[] newArray(Class<?> c, int length)
	{
		Object[] result = (Object[]) Array.newInstance(c, length);
		return result;
	}
}
