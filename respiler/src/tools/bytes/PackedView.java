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
	
	public void copyTo(BytesView view)
	{
		view.set(buffer, start, end);
	}
	
	public void swap(PackedView view)
	{
		PackedView temp = new PackedView();
		
		temp.set(buffer, start, end);
		set(view.buffer, view.start, view.end);
		view.set(buffer, start, end);
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
	
	public boolean isNotEmpty()
	{
		return (start != end);
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
	
	public boolean isEqual(BufferUnpackedViews views, int index)
	{
		return (length() == views.getLength(index)) && BytesTools.isEqual(buffer, start, views.buffer, views.getStart(index), views.getEnd(index));
	}
	
	public boolean isEqual(byte[] buffer)
	{
		return (length() == buffer.length) && BytesTools.isEqual(buffer, start, buffer, 0, buffer.length);
	}
	
	public boolean startsWith(PackedView view)
	{
		return BytesTools.startsWith(buffer, start, end, view.buffer, view.start, view.end);
	}
	
	public boolean startsWith(BufferUnpackedViews views, int index)
	{
		return BytesTools.startsWith(buffer, start, end, views.buffer, views.getStart(index), views.getEnd(index));
	}
	
	public boolean startsWith(byte[] buffer)
	{
		return BytesTools.startsWith(buffer, start, end, buffer, 0, buffer.length);
	}
	
	public boolean endsWith(PackedView view)
	{
		return BytesTools.endsWith(buffer, start, end, view.buffer, view.start, view.end);
	}
	
	public boolean endsWith(BufferUnpackedViews views, int index)
	{
		return BytesTools.endsWith(buffer, start, end, views.buffer, views.getStart(index), views.getEnd(index));
	}
	
	public boolean endsWith(byte[] buffer)
	{
		return BytesTools.endsWith(buffer, start, end, buffer, 0, buffer.length);
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
	
	public boolean lstrip(ByteTest test)
	{
		int index;
		
		index = findNot(test);
		if (index == start)
			return false;
		
		start = index;
		return true;
	}
	
	public boolean rstrip(ByteTest test)
	{
		int index;
		
		index = rfindNot(test);
		if (index == end)
		{
			end = start;
			return true;
		}
		
		index += 1;
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
	
	public boolean rsplit(ByteTest test, BytesView view)
	{
		int index;
		
		index = find(test);
		
		if (index == start)
			return false;
		
		view.set(buffer, start, index);
		start = index;
		return true;
	}
	
	public boolean split(ByteTest test, BytesView view)
	{
		int index;
		
		index = rfind(test);
		if (index == end)
		{
			view.set(buffer, start, end);
			end = start;
			return true;
		}
		
		index += 1;
		if (index != end)
		{
			view.set(buffer, index, end);
			end = index;
			return true;
		}
		
		return false;
	}
}
