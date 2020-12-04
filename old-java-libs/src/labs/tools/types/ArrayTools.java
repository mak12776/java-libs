
package labs.tools.types;

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
}
