
package libs.buffers;

import libs.bytes.ByteTools;
import libs.tools.types.StringBuilderTools;
import libs.views.View;

public class BufferViews
{
	public byte[] buffer;
	public View[] views;

	public BufferViews(byte[] buffer, View[] views)
	{
		this.buffer = buffer;
		this.views = views;
	}

	public static BufferViews from(String... stringsArray)
	{
		byte[][] bytesArray;

		bytesArray = new byte[stringsArray.length][];

		for (int i = 0; i < stringsArray.length; i += 1)
		{
			bytesArray[i] = stringsArray[i].getBytes();
		}

		return from(bytesArray);
	}

	public static BufferViews from(byte[]... bytesArray)
	{
		BufferViews result;
		int length;

		length = 0;
		for (int i = 0; i < bytesArray.length; i += 1)
		{
			length += bytesArray[i].length;
		}

		if (length == 0)
			return null;

		result = new BufferViews(null, null);

		result.buffer = new byte[length];
		result.views = new View[bytesArray.length];

		length = 0;
		for (int i = 0; i < bytesArray.length; i += 1)
		{
			System.arraycopy(bytesArray[i], 0, result.buffer, length, bytesArray[i].length);

			result.views[i].start = length;
			length += bytesArray[i].length;
			result.views[i].end = length;
		}

		return result;
	}

	// fields functions

	public int getLength(int index)
	{
		return views[index].length();
	}

	public int getStart(int index)
	{
		return views[index].start;
	}

	public int getEnd(int index)
	{
		return views[index].end;
	}

	public int getViewsLength()
	{
		return views.length;
	}

	public void copyViewTo(int index, BufferViewInterface view)
	{
		view.set(buffer, views[index].start, views[index].end);
	}

	public String getLineString(int index)
	{
		StringBuilder builder = new StringBuilder();
		StringBuilderTools.appendBytes(builder, buffer, views[index].start, views[index].end);
		return builder.toString();
	}
}
