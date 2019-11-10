package tools.types;

import tools.ByteTools;
import tools.StringBuilderTools;
import tools.bytes.PackedBytesView;
import tools.bytes.UnpackedBytesView;

public class BufferViews
{
	public byte[] buffer;
	public UnpackedBytesView[] lines;
	
	public BufferViews(byte[] buffer, UnpackedBytesView[] lines)
	{
		this.buffer = buffer;
		this.lines = lines;
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
		result.lines = new UnpackedBytesView[bytesArray.length];
		
		length = 0;
		for (int i = 0; i < bytesArray.length; i += 1)
		{
			ByteTools.copy(result.buffer, length, bytesArray[i], 0, bytesArray[i].length);
			
			result.lines[i].start = length;
			length += bytesArray[i].length;
			result.lines[i].end = length;
		}
		
		return result;
	}
	
	public void copyLineTo(int lnum, PackedBytesView view)
	{
		view.buffer = buffer;
		view.start = lines[lnum].start;
		view.end = lines[lnum].end;
	}
	
	public String getLineString(int num)
	{
		StringBuilder builder = new StringBuilder();
		StringBuilderTools.appendBytes(builder, buffer, lines[num].start, lines[num].end);
		return builder.toString();
	}
}
