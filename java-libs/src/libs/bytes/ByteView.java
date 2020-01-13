
package libs.bytes;

import libs.tools.types.ByteTools;
import libs.views.ByteViewInterface;

public class ByteView implements ByteViewInterface
{
	/*
	 * Buffer View
	 */

	public byte[] buffer;

	public int start;
	public int end;

	// ByteViewInterface functions

	@Override
	public void set(byte[] buffer, int start, int end)
	{
		this.buffer = buffer;
		this.start = start;
		this.end = end;
	}

	// constructor

	public ByteView(byte[] buffer, int start, int end)
	{
		this.set(buffer, start, end);
	}

	public ByteView()
	{
		this(null, 0, 0);
	}

	// array creation

	public static ByteView[] newArray(int size, byte[] buffer, int start, int end)
	{
		ByteView[] result;

		result = new ByteView[size];
		for (int i = 0; i < result.length; i += 1)
		{
			result[i] = new ByteView(buffer, start, end);
		}

		return result;
	}

	public static ByteView[] newArray(int size)
	{
		return newArray(size, null, 0, 0);
	}

	public void swap(ByteView view)
	{
		ByteView temp = new ByteView();

		temp.set(buffer, start, end);
		set(view.buffer, view.start, view.end);
		view.set(buffer, start, end);
	}

	// fields functions

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

	// functions

	@Override
	public boolean equals(Object arg0)
	{
		return (arg0 instanceof ByteView) ? isEqual((ByteView) arg0) : false;
	}

	public boolean isEqual(ByteView view)
	{
		return (length() == view.length()) && ByteTools.isEqual(buffer, start, view.buffer, view.start, length());
	}

	public boolean isEqual(BufferViews views, int index)
	{
		return (length() == views.getLength(index))
				&& ByteTools.isEqual(buffer, start, views.buffer, views.getStart(index), views.getEnd(index));
	}

	public boolean isEqual(byte[] buffer)
	{
		return (length() == buffer.length) && ByteTools.isEqual(buffer, start, buffer, 0, buffer.length);
	}

	// starts with functions

	public boolean startsWith(ByteView view)
	{
		return ByteTools.startsWith(buffer, start, end, view.buffer, view.start, view.end);
	}

	public boolean startsWith(BufferViews views, int index)
	{
		return ByteTools.startsWith(buffer, start, end, views.buffer, views.getStart(index), views.getEnd(index));
	}

	public boolean startsWith(byte[] buffer)
	{
		return ByteTools.startsWith(buffer, start, end, buffer, 0, buffer.length);
	}

	// ends with functions

	public boolean endsWith(ByteView view)
	{
		return ByteTools.endsWith(buffer, start, end, view.buffer, view.start, view.end);
	}

	public boolean endsWith(BufferViews views, int index)
	{
		return ByteTools.endsWith(buffer, start, end, views.buffer, views.getStart(index), views.getEnd(index));
	}

	public boolean endsWith(byte[] buffer)
	{
		return ByteTools.endsWith(buffer, start, end, buffer, 0, buffer.length);
	}

	// find test functions

	public int find(ByteTest test)
	{
		return ByteTools.find(buffer, start, end, test);
	}

	public int rfind(ByteTest test)
	{
		return ByteTools.rfind(buffer, start, end, test);
	}

	public int findNot(ByteTest test)
	{
		return ByteTools.findNot(buffer, start, end, test);
	}

	public int rfindNot(ByteTest test)
	{
		return ByteTools.rfindNot(buffer, start, end, test);
	}

	// search functions

	public int search(ByteView view)
	{
		return ByteTools.search(buffer, start, end, view.buffer, view.start, view.end);
	}

	public int lsearch(ByteView view)
	{
		return ByteTools.lsearch(buffer, start, end, view.buffer, view.start, view.end);
	}

	// strip functions

	public boolean lstrip(ByteView view)
	{
		if (startsWith(view))
		{
			start += view.length();
			return true;
		}
		return false;
	}

	public boolean rstrip(ByteView view)
	{
		if (endsWith(view))
		{
			end -= view.length();
			return true;
		}
		return false;
	}

	public boolean strip(ByteView prefix, ByteView suffix)
	{
		if (startsWith(prefix))
		{
			start += prefix.length();
			if (endsWith(suffix))
			{
				end -= suffix.length();
				return true;
			}
			start -= prefix.length();
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

	// split functions

	public boolean lsplit(ByteTest test, ByteViewInterface view)
	{
		int index;

		index = find(test);

		if (index == end)
			return false;

		view.set(buffer, start, index);
		start = index;
		return true;
	}

	public boolean rsplit(ByteTest test, ByteViewInterface view)
	{
		int index;

		index = rfind(test);

		if (index == end)
			return false;

		index += 1;

		view.set(buffer, index, end);
		end = index;
		return true;
	}

	public boolean strip(ByteTest test)
	{
		boolean l = lstrip(test);
		boolean r = rstrip(test);
		return l || r;
	}
}
