package olds;

public class Line 
{
	public LinkedBuffer buffer;
	public int start;
	public int length;

	public long number;
	
	public Line next;
	
	public Line(LinkedBuffer buffer, int start, int length, long lineNumber, Line next) 
	{
		this.buffer = buffer;
		this.start = start;
		this.length = length;
		this.number = lineNumber;
		this.next = next;
	}
	
	public String getLine()
	{
		StringBuilder builder = new StringBuilder();
		int index = this.start;
		int length = this.length;
		LinkedBuffer buffer = this.buffer;
		while (length != 0)
		{
			builder.append((char) buffer.array[index]);
			
			index += 1;
			if (index == buffer.length)
			{
				buffer = buffer.next;
			}
			length -= 1;
		}
		return builder.toString();
	}
}
