
package libs.views;

import libs.interfaces.BufferViewInterface;

public class View implements BufferViewInterface
{
	public int start;
	public int end;

	// constructor

	public View(int start, int end)
	{
		this.start = start;
		this.end = end;
	}

	public View()
	{
		this(0, 0);
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
	
	// ByteViewInterface functions

	@Override
	public void set(byte[] buffer, int start, int end)
	{
		this.start = start;
		this.end = end;
	}

	// fields functions

	public int length()
	{
		return this.end - this.start;
	}
}
