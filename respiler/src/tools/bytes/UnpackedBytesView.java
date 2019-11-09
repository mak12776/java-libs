package tools.bytes;

import java.io.IOException;
import java.io.OutputStream;

import tools.ByteTools;
import tools.types.ByteTest;

public class UnpackedBytesView implements BytesView
{
	public int start;
	public int end;
	
	@Override
	public void set(byte[] buffer, int start, int end)
	{
		this.start = start;
		this.end = end;
	}
	
	public void copyTo(UnpackedBytesView view)
	{
		view.start = start;
		view.end = end;
	}
	
	public UnpackedBytesView(int start, int end)
	{
		set(null, start, end);
	}
	
	public UnpackedBytesView()
	{
		this(0, 0);
	}
	
	public int length()
	{
		return this.end - this.start;
	}
	
	public boolean startsWith(byte[] buffer, UnpackedBytesView view, byte[] viewBytes)
	{
		return (length() >= view.length()) && ByteTools.isEqual(buffer, start, viewBytes, view.start, length());
	}
	
	public boolean endsWith(byte[] buffer, UnpackedBytesView view, byte[] viewBytes)
	{
		return (length() >= view.length()) && ByteTools.isEqual(buffer, start, viewBytes, view.start, length());
	}
	
	public boolean startsAndEndsWith(byte[] buffer, UnpackedBytesView prefixView, byte[] prefixBytes, UnpackedBytesView suffixView, byte[] suffixBytes)
	{
		return startsWith(buffer, prefixView, prefixBytes) && endsWith(buffer, suffixView, suffixBytes);
	}
	
	public int find(byte[] buffer, ByteTest test)
	{
		return ByteTools.find(buffer, start, end, test);
	}
	
	public int rfind(byte[] buffer, ByteTest test)
	{
		return ByteTools.rfind(buffer, start, end, test);
	}
	
	public int lstrip(byte[] buffer, ByteTest test)
	{
		start = find(buffer, test.not());
		return length();
	}
	
	public int rstrip(byte[] buffer, ByteTest test)
	{
		int index;
		
		index = rfind(buffer, test.not());
		end = (index != end) ? index : start;
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
