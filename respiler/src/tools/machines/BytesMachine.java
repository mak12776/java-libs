package tools.machines;

import tools.bytes.PackedView;

public class BytesMachine
{
	private byte[] buffer;
	private int index;
	private int end;
	
	public BytesMachine(byte[] buffer, int start, int end)
	{
		this.buffer = buffer;
		this.index = start;
		this.end = end;
	}
	
	public BytesMachine(PackedView view)
	{
		this(view.buffer, view.start, view.end);
	}
}
