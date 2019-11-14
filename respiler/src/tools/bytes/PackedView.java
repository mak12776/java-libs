package tools.bytes;

import tools.BytesTools;
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
		return (length() == view.length()) && BytesTools.isEqual(buffer, start, view.buffer, view.start, length());
	}
	
	public boolean startsWith(PackedView view)
	{
		return BytesTools.startsWith(buffer, start, end, view.buffer, view.start, view.end);
	}
	
	public boolean endsWith(PackedView view)
	{
		return BytesTools.endsWith(buffer, start, end, view.buffer, view.start, view.end);
	}
	
	public int find(ByteTest test)
	{
		return BytesTools.find(buffer, start, end, test);
	}
	
	public int rfind(ByteTest test)
	{
		return BytesTools.rfind(buffer, start, end, test);
	}
	
	public int findNot(ByteTest test)
	{
		return BytesTools.findNot(buffer, start, end, test);
	}
	
	public int rfindNot(ByteTest test)
	{
		return BytesTools.rfindNot(buffer, start, end, test);
	}
	
	public int search(PackedView view)
	{
		return BytesTools.search(buffer, start, end, view.buffer, view.start, view.end);
	}
	
	public int lsearch(PackedView view)
	{
		return BytesTools.lsearch(buffer, start, end, view.buffer, view.start, view.end);
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
				BytesTools.startsWith(buffer, start, end, prefix.buffer, prefix.start, prefix.end) &&
				BytesTools.endsWith(buffer, start + prefix.length(), end, suffix.buffer, suffix.start, suffix.end))
		{
			start += prefix.length();
			end -= suffix.length();
			return true;
		}
		return false;
	}
	
	public void split(ByteTest test, BytesView view)
	{
		int index;
		
		index = BytesTools.findNot(buffer, start, end, test);
		
		if (index == end)
			index = start;
		
		view.set(buffer, start, index);
		start = index;
	}
	
	public void rsplit(ByteTest test, BytesView view)
	{
		int index;
		
		index = BytesTools.rfindNot(buffer, start, end, test);
		
		view.set(buffer, index, end);
		end = index;
	}
	
	public boolean lstrip(ByteTest test)
	{
		int index;
		
		index = findNot(test);
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
		
		index = rfindNot(test);
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
