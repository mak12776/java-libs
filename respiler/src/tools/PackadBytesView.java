package tools;

public class PackadBytesView
{
	public byte[] buffer;
	
	public int start;
	public int end;
	
	public PackadBytesView(byte[] buffer, int start, int end)
	{
		this.buffer = buffer;
		this.start = start;
		this.end = end;
	}
}
