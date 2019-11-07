package types;

import java.io.IOException;
import java.io.OutputStream;

public class Line 
{
	public int start;
	public int end;
	
	public Line(int start, int end)
	{
		this.start = start;
		this.end = end;
	}
	
	public Line()
	{
		this.start = 0;
		this.end = 0;
	}
	
	public void copyTo(Line line)
	{
		line.start = this.start;
		line.end = this.end;
	}
	
	public int length()
	{
		return this.end - this.start;
	}
	
	public static boolean byteIn(byte b, byte[] bytes)
	{
		for (int i = 0; i < bytes.length; i += 1)
		{
			if (bytes[i] == b)
				return true;
		}
		return false;
	}
	
	public static boolean compareBytes(byte[] buffer, int offset, byte[] bytes)
	{
		for (int i = 0; i < bytes.length; i += 1)
		{
			if (buffer[offset + i] != bytes[i])
				return false;
		}
		return true;
	}
	
	public boolean startsWith(byte[] buffer, byte[] bytes)
	{
		return (length() >= bytes.length) && compareBytes(buffer, start, bytes);
	}
	
	public boolean endsWith(byte[] buffer, byte[] bytes)
	{
		return (length() >= bytes.length) && compareBytes(buffer, length() - bytes.length, bytes);
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
