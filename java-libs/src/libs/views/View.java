
package libs.views;

import libs.bytes.ByteViewInterface;

public class View implements ByteViewInterface
{
	public int start;
	public int end;

	// ByteViewInterface functions

	@Override
	public void set(byte[] buffer, int start, int end)
	{
		this.start = start;
		this.end = end;
	}

	// constructor

	public View(int start, int end)
	{
		set(null, start, end);
	}

	public View()
	{
		this(0, 0);
	}

	// fields functions

	public int length()
	{
		return this.end - this.start;
	}

	// array creation

	public static View[] newArray(int size)
	{
		View[] result;

		result = new View[size];
		for (int i = 0; i < result.length; i += 1)
		{
			result[i] = new View();
		}
		return result;
	}
}
