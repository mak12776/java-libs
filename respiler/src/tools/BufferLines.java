package tools;

public class BufferLines
{
	public byte[] buffer;
	public BytesView[] lines;
	
	public BufferLines(byte[] buffer, BytesView[] lines)
	{
		this.buffer = buffer;
		this.lines = lines;
	}
	
	public String getLineString(int num)
	{
		StringBuffer builder = new StringBuffer();
		builder.append("[");
		for (int index = lines[num].start; index < lines[num].end; index += 1)
		{
			char ch = (char)buffer[index];
			
			if (ch == '\n')
			{
				builder.append("\\n");
			}
			else if (ch == '\t')
			{
				builder.append("   ~");
			}
			else
			{
				builder.append((char)buffer[index]);
			}
		}
		builder.append("]");
		return builder.toString();
	}
}
