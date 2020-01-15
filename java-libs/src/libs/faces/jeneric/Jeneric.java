
package libs.faces.jeneric;

import java.util.LinkedList;
import java.util.List;

public class Jeneric
{
	public static <T> List<T> newList()
	{
		return new LinkedList<T>();
	}
}
