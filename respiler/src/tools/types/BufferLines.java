package tools.types;

import tools.StringBuilderTools;
import tools.bytes.UnpackedBytesView;

public class BufferLines
{
	public byte[] buffer;
	public UnpackedBytesView[] lines;
	
	public BufferLines(byte[] buffer, UnpackedBytesView[] lines)
	{
		this.buffer = buffer;
		this.lines = lines;
	}
	
	public String getLineString(int num)
	{
		StringBuilder builder = new StringBuilder();
		StringBuilderTools.appendBytes(builder, buffer, lines[num].start, lines[num].end);
		return builder.toString();
	}
}
