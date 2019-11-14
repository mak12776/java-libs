package tools.bytes;

import tools.BytesTools;
import tools.StringBuilderTools;

public class BufferUnpackedViews
{
	public byte[] buffer;
	public UnpackedView[] views;
	

	public String getLineString(int index)
	{
		StringBuilder builder = new StringBuilder();
		StringBuilderTools.appendBytes(builder, buffer, views[index].start, views[index].end);
		return builder.toString();
	}
	
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
	
	public void copyViewTo(int index, BytesView view)
	{
		view.set(buffer, views[index].start, views[index].end);
	}
	
	public BufferUnpackedViews(byte[] buffer, UnpackedView[] views)
	{
		this.buffer = buffer;
		this.views = views;
	}
	
	public static BufferUnpackedViews from(String... stringsArray)
	{
		byte[][] bytesArray;
		
		bytesArray = new byte[stringsArray.length][];
		
		for (int i = 0; i < stringsArray.length; i += 1)
		{
			bytesArray[i] = stringsArray[i].getBytes();
		}
		
		return from(bytesArray);
	}
	
	public static BufferUnpackedViews from(byte[]... bytesArray)
	{
		BufferUnpackedViews result;
		int length;
		
		length = 0;
		for (int i = 0; i < bytesArray.length; i += 1)
		{
			length += bytesArray[i].length;
		}
		
		if (length == 0)
			return null;
		
		result = new BufferUnpackedViews(null, null);
		
		result.buffer = new byte[length];
		result.views = new UnpackedView[bytesArray.length];
		
		length = 0;
		for (int i = 0; i < bytesArray.length; i += 1)
		{
			BytesTools.copy(result.buffer, length, bytesArray[i], 0, bytesArray[i].length);
			
			result.views[i].start = length;
			length += bytesArray[i].length;
			result.views[i].end = length;
		}
		
		return result;
	}
}
