package tools.bytes;

import java.io.IOException;
import java.io.OutputStream;

import tools.ByteTest;
import tools.ByteTools;

public class UnpackedBytesView extends BytesView
{
	public int start;
	public int end;
	
	@Override
	public void set(byte[] buffer, int start, int end)
	{
		this.start = start;
		this.end = end;
	}
	
	public UnpackedBytesView(int start, int end)
	{
		set(null, start, end);
	}
	
	public void copyTo(UnpackedBytesView line)
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
		return (length() >= bytes.length) && ByteTools.isEqual(buffer, start, bytes, 0, bytes.length);
	}
	
	public boolean startsWith(byte[] buffer, UnpackedBytesView view, byte[] viewBytes)
	{
		return (length() >= view.length()) && ByteTools.isEqual(buffer, start, viewBytes, view.start, length());
	}
	
	public boolean endsWith(byte[] buffer, byte[] bytes)
	{
		return (length() >= bytes.length) && ByteTools.isEqual(buffer, length() - bytes.length, bytes, 0, bytes.length);
	}
	
	public boolean endsWith(byte[] buffer, UnpackedBytesView view, byte[] viewBytes)
	{
		return (length() >= view.length()) && ByteTools.isEqual(buffer, start, viewBytes, view.start, length());
	}
	
	public boolean startsAndEndsWith(byte[] buffer, UnpackedBytesView startView, byte[] startViewBytes, UnpackedBytesView endView, byte[] endViewBytes)
	{
		return startsWith(buffer, startView, startViewBytes) && endsWith(buffer, endView, endViewBytes);
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
