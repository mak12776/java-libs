
package main;

import labs.faces.cface.CFace;
import labs.faces.cface.CFace.Key;

public class Main
{
	public static void main(String[] args)
	{
		for (Key key : Key.values())
		{
			if (!CFace.keywords.containsKey(key))
				System.err.println(key);
		}
	}
}

