package libs.types;

import libs.bytes.BytesView;

public class View implements BytesView
{
	public int start;
	public int end;
	
	public View(int start, int end)
	{
		set(null, start, end);
	}
	
	public View()
	{
		this(0, 0);
	}
	
	public int length()
	{
		return this.end - this.start;
	}
	
	@Override
	public void set(byte[] buffer, int start, int end)
	{
		this.start = start;
		this.end = end;
	}
	
	public void copyTo(View view)
	{
		view.start = start;
		view.end = end;
	}
	
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
