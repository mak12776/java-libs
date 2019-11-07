package tools;

import java.io.IOException;
import java.io.OutputStream;

public class BytesView 
{
	public int start;
	public int end;
	
	public BytesView(int start, int end)
	{
		this.start = start;
		this.end = end;
	}
	
	public void copyTo(BytesView line)
	{
		line.start = this.start;
		line.end = this.end;
	}
	
	public int length()
	{
		return this.end - this.start;
	}
	
	public boolean startsWith(byte[] buffer, byte[] bytes)
	{
		return (length() >= bytes.length) && ByteTools.isEqualBytes(buffer, start, bytes, 0, bytes.length);
	}
	
	public boolean startsWith(byte[] buffer, BytesView view, byte[] viewBytes)
	{
		return (length() >= view.length()) && ByteTools.isEqualBytes(buffer, start, viewBytes, view.start, length());
	}
	
	public boolean endsWith(byte[] buffer, byte[] bytes)
	{
		return (length() >= bytes.length) && ByteTools.isEqualBytes(buffer, length() - bytes.length, bytes, 0, bytes.length);
	}
	
	public boolean endsWith(byte[] buffer, BytesView view, byte[] viewBytes)
	{
		return (length() >= view.length()) && ByteTools.isEqualBytes(buffer, start, viewBytes, view.start, length());
	}
	
	public int find(byte[] buffer, ByteTest test)
	{
		int index;
		
		index = start;
		while (index < end)
		{
			if (test.test(buffer[index]))
				return index;
			index += 1;
		}
		return end;
	}
	
	public int findNot(byte[] buffer, ByteTest test)
	{
		int index;
		
		index = start;
		while (index < end)
		{
			if (!test.test(buffer[index]))
				return index;
			index += 1;
		}
		return end;
	}
	
	public int rfind(byte[] buffer, ByteTest test)
	{
		int index;
		
		index = end;
		while (index > start)
		{
			index -= 1;
			if (test.test(buffer[index]))
				return index;
		}
		return end;
	}
	
	public int rfindNot(byte[] buffer, ByteTest test)
	{
		int index;
		
		index = end;
		while (index > start)
		{
			index -= 1;
			if (!test.test(buffer[index]))
				return index;
		}
		return end;
	}
	
	public int lstrip(byte[] buffer, ByteTest test)
	{
		start = findNot(buffer, test);
		return length();
	}
	
	public int rstrip(byte[] buffer, ByteTest test)
	{
		int index;
		
		index = rfindNot(buffer, test);
		if (index == end)
			end = start;
		else
			end = index;
		return length();
	}
	
	public int strip(byte[] buffer, ByteTest test)
	{
		if (lstrip(buffer, test) != 0)
		{
			return rstrip(buffer, test);
		}
		return 0;
	}
	
	public void write(byte[] buffer, OutputStream stream) throws IOException
	{
		stream.write(buffer, start, end - start);
	}
}
