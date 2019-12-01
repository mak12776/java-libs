package tools.bytes;

public class UnpackedView implements BytesView
{
	public int start;
	public int end;
	
	public UnpackedView(int start, int end)
	{
		set(null, start, end);
	}
	
	public UnpackedView()
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
	
	public void copyTo(UnpackedView view)
	{
		view.start = start;
		view.end = end;
	}
	
	public static UnpackedView[] newArray(int size)
	{
		UnpackedView[] result;
		
		result = new UnpackedView[size];
		for (int i = 0; i < result.length; i += 1)
		{
			result[i] = new UnpackedView();
		}
		return result;
	}
}
