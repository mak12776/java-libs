package tools.bytes;

import tools.ByteTools;
import tools.types.ByteTest;

public class PackedBytesView implements BytesView
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
	
	public void copyTo(PackedBytesView view)
	{
		view.buffer = buffer;
		view.start = start;
		view.end = end;
	}
	
	public PackedBytesView(byte[] buffer, int start, int end)
	{
		this.set(buffer, start, end);
	}
	
	public PackedBytesView()
	{
		this(null, 0, 0);
	}
	
	public int length()
	{
		return end - start;
	}
	
	@Override
	public boolean equals(Object arg0)
	{
		return (arg0 instanceof PackedBytesView) ? isEqual((PackedBytesView) arg0) : false;
	}
	
	public boolean isEqual(PackedBytesView view)
	{
		return (length() == view.length()) && (ByteTools.isEqual(buffer, start, view.buffer, view.start, length()));
	}
	
	public boolean startsWith(PackedBytesView view)
	{
		return (length() >= view.length()) && ByteTools.isEqual(buffer, start, view.buffer, view.start, view.length());
	}
	
	public boolean endsWith(PackedBytesView view)
	{
		return (length() >= view.length()) && ByteTools.isEqual(buffer, end - view.length(), view.buffer, view.start, view.length());
	}
	
	public int find(ByteTest test)
	{
		int index = start;
		while (index < end)
		{
			if (test.test(buffer[index]))
				return index;
			index += 1;
		}
		return end;
	}
	
	public int rfind(ByteTest test)
	{
		int index = end;
		while (index > start)
		{
			index -= 1;
			if (test.test(buffer[index]))
				return index;
		}
		return end;
	}
	
	public int lstrip(PackedBytesView view)
	{
		if (startsWith(view))
		{
			start += view.length();
		}
		return length();
	}
	
	public int rstrip(PackedBytesView view)
	{
		if (endsWith(view))
		{
			end -= view.length();
		}
		return length();
	}
	
	public int strip(PackedBytesView prefix, PackedBytesView suffix)
	{		
		if (lstrip(prefix) != 0)
		{
			return rstrip(suffix); 
		}
		return 0;
	}
	
	public int lstrip(ByteTest test)
	{
		start = find(test.not());
		return length();
	}
	
	public int rstrip(ByteTest test)
	{
		int index;
		
		index = rfind(test.not());
		end = (index != end) ? index : start;
		return length();
	}
	
	public int strip(ByteTest test)
	{
		if (lstrip(test) != 0)
		{
			return rstrip(test);
		}
		return 0;
	}
}
