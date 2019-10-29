package types;

public class BufferLines 
{
	public byte[] buffer;
	public Line[] lines;
	
	public BufferLines(byte[] buffer, Line[] lines)
	{
		this.buffer = buffer;
		this.lines = lines;
	}
	
	public BufferLines()
	{
		this.buffer = null;
		this.lines = null;
	}
	
	public String getLine(int num)
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
				builder.append("~   ");
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
