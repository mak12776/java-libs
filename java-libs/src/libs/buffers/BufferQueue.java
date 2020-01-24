package libs.buffers;

import libs.tools.SafeTools;

public class BufferQueue
{
	public static final boolean CHECK_BUFFER_START_END = false;
	public static final boolean CHECK_INVALID_SIZE = false;
	public static final boolean CHECK_INDEX_OUT_OF_BOUNDS = false;
	
	private byte[] buffer;
	private int start;
	private int end;
	
	// constructors
	
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
			SafeTools.checkNegativeZeroIndex(size, "size");
		
		this.buffer = new byte[size];
		this.start = 0;
		this.end = 0;
	}
	
	// fields functions
	
	public byte[] getBytes()
	{
		return buffer;
	}
	
	public int getStart()
	{
		return start;
	}
	
	public int getEnd()
	{
		return end;
	}
	
	public byte get(int index)
	{
		if (CHECK_INDEX_OUT_OF_BOUNDS)
			SafeTools.checkIndexOutOfBounds(index, 0, end - start, "index");
		
		return buffer[start + index];
	}
	
	public void set(int index, byte value)
	{
		if (CHECK_INDEX_OUT_OF_BOUNDS)
			SafeTools.checkIndexOutOfBounds(index, 0, end - start, "index");
		
		buffer[start + index] = value;
	}
	
	// properties
	
	public int length()
	{
		return end - start;
	}
	
	public int remaining()
	{
		return buffer.length - end + start; 
	}
	
	public int leftRemaining()
	{
		return start;
	}
	
	public int rightRemaining()
	{
		return buffer.length - end;
	}
	
	public int size()
	{
		return buffer.length;
	}
	
	public boolean isEmpty()
	{
		return start == end;
	}
	
	public boolean isFull()
	{
		return (start == 0) && (end == buffer.length);
	}
	
	// append methods
	
	public void append(byte[] buffer, int start, int end)
	{
		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		int bufferLength = end - start;
		
		if (bufferLength <= this.buffer.length - this.end)
		{
			System.arraycopy(buffer, start, this.buffer, this.end, bufferLength);
			this.end += bufferLength;
			return;
		}
	}
}
