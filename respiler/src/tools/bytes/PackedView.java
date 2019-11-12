package tools.bytes;

import tools.ByteTools;
import tools.pattern.Pattern;
import tools.types.ByteTest;

public class PackedView implements BytesView
{
	/*
	 * 	Slow Packed Bytes View
	 */
	
	public byte[] buffer;
	
	public int start;
	public int end;
	
	@Override
	public void set(byte[] buffer, int start, int end)
	{
		this.buffer = buffer;
		this.start = start;
		this.end = end;
	}
	
	public void copyTo(PackedView view)
	{
		view.buffer = buffer;
		view.start = start;
		view.end = end;
	}
	
	public PackedView(byte[] buffer, int start, int end)
	{
		this.set(buffer, start, end);
	}
	
	public PackedView()
	{
		this(null, 0, 0);
	}
	
	public static PackedView[] newArray(int size)
	{
		PackedView[] result;
		
		result = new PackedView[size];
		for (int i = 0; i < result.length; i += 1)
		{
			result[i] = new PackedView();
		}
		
		return result;
	}
	
	public int length()
	{
		return end - start;
	}
	
	public boolean isEmpty()
	{
		return (start == end);
	}
	
	@Override
	public boolean equals(Object arg0)
	{
		return (arg0 instanceof PackedView) ? isEqual((PackedView) arg0) : false;
	}
	
	public boolean isEqual(PackedView view)
	{
		return (length() == view.length()) && ByteTools.isEqual(buffer, start, view.buffer, view.start, length());
	}
	
	public boolean match(Pattern pattern)
	{
		return false;
	}
	
	public boolean startsWith(PackedView view)
	{
		return ByteTools.startsWith(buffer, start, end, view.buffer, view.start, view.end);
	}
	
	public boolean endsWith(PackedView view)
	{
		return ByteTools.endsWith(buffer, start, end, view.buffer, view.start, view.end);
	}
	
	public int find(ByteTest test)
	{
		return ByteTools.find(buffer, start, end, test);
	}
	
	public int rfind(ByteTest test)
	{
		return ByteTools.rfind(buffer, start, end, test);
	}
	
	public int search(PackedView view)
	{
		return ByteTools.search(buffer, start, end, view.buffer, view.start, view.end);
	}
	
	public int lsearch(PackedView view)
	{
		return ByteTools.lsearch(buffer, start, end, view.buffer, view.start, view.end);
	}
	
	public boolean lstrip(PackedView view)
	{
		if (startsWith(view))
		{
			start += view.length();
			return true;
		}
		return false;
	}
	
	public boolean rstrip(PackedView view)
	{
		if (endsWith(view))
		{
			end -= view.length();
			return true;
		}
		return false;
	}
	
	public boolean strip(PackedView prefix, PackedView suffix)
	{
		if (
				ByteTools.startsWith(buffer, start, end, prefix.buffer, prefix.start, prefix.end) &&
				ByteTools.endsWith(buffer, start + prefix.length(), end, suffix.buffer, suffix.start, suffix.end))
		{
			start += prefix.length();
			end -= suffix.length();
			return true;
		}
		return false;
	}
	
	public boolean lstrip(ByteTest test)
	{
		int index;
		
		index = find(test.not());
		if (index != end)
		{
			start = index;
			return true;
		}
		return false;
	}
	
	public boolean rstrip(ByteTest test)
	{
		int index;
		
		index = rfind(test.not());
		if (index != end)
		{
			end = index;
			return true;
		}
		return false;
	}
	
	public boolean strip(ByteTest test)
	{
		boolean l = lstrip(test);
		boolean r = rstrip(test);
		return l || r;
	}
}
