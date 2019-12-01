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
	
	public byte[] getBuffer()
	{
		return buffer;
	}
	
	private static final byte NOOP = 0;
	private static final byte COPY = 1; 
	
	public void run()
	{
		while (index < end)
		{
			switch (buffer[index])
			{
			case NOOP:
				break;
			
			case COPY:
				break;
			}
		}
	}
}
