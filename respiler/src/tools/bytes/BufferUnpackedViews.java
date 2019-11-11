package tools.bytes;

import tools.ByteTools;
import tools.StringBuilderTools;

public class BufferUnpackedViews
{
	public byte[] buffer;
	public UnpackedView[] views;
	
	public BufferUnpackedViews(byte[] buffer, UnpackedView[] views)
	{
		this.buffer = buffer;
		this.views = views;
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
			ByteTools.copy(result.buffer, length, bytesArray[i], 0, bytesArray[i].length);
			
			result.views[i].start = length;
			length += bytesArray[i].length;
			result.views[i].end = length;
		}
		
		return result;
	}
	
	public void copyViewTo(int index, PackedView view)
	{
		view.buffer = buffer;
		view.start = views[index].start;
		view.end = views[index].end;
	}
	
	public PackedView copyPackedView(int index)
	{
		return new PackedView(buffer, views[index].start, views[index].end);
	}
	
	public String getLineString(int index)
	{
		StringBuilder builder = new StringBuilder();
		StringBuilderTools.appendBytes(builder, buffer, views[index].start, views[index].end);
		return builder.toString();
	}
}
