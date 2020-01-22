package libs.buffers;

import libs.tools.SafeTools;

public class BufferQueue
{
	private static final boolean CHECK_BUFFER_START_END = false;
	private static final boolean CHECK_INVALID_SIZE = false;
	
	private byte[] buffer;
	private int start;
	private int end;
	
	public BufferQueue(byte[] buffer, int start, int end)
	{
		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		this.buffer = buffer;
		this.start = start;
		this.end = end;
	}
	
	public BufferQueue(int size)
	{
		if (CHECK_INVALID_SIZE)
		{ }
		
		this.buffer = new byte[size];
		this.start = 0;
		this.end = 0;
	}
	
	
}
