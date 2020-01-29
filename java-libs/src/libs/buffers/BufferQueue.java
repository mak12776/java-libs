package libs.buffers;

import libs.exceptions.BufferIsFullException;
import libs.exceptions.NotEnoughDataException;
import libs.tools.SafeTools;

public class BufferQueue
{
	public static final boolean CHECK_BUFFER_START_END = false;
	public static final boolean CHECK_INVALID_SIZE = false;
	public static final boolean CHECK_INVALID_SHIFT = false;
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
	
	// functions
	
	public static final int MAX_SHIFT = Integer.MAX_VALUE;
	
	public void shiftRight(int shift)
	{
		if (shift == 0)
			return;
		
		if (shift > buffer.length - end)
			shift = buffer.length - end;
		
		System.arraycopy(buffer, start, buffer, start + shift, end - start);
		start += shift;
		end += shift;
	}
	
	public void shiftLeft(int shift)
	{
		if (CHECK_INVALID_SHIFT)
			SafeTools.checkInvalidIndex(shift, 0, MAX_SHIFT, "shift");
		
		if (shift == 0)
			return;
		
		if (shift > start)
			shift = start;
		
		System.arraycopy(buffer, start, buffer, start - shift, end - start);
		start -= shift;
		end -= shift;
	}
	
	public void shift(int shift)
	{
		if (CHECK_INVALID_SHIFT)
			SafeTools.checkInvalidIndex(shift, 0, MAX_SHIFT, "shift");
		
		if (shift == 0)
			return;
		
		if (shift == Integer.MIN_VALUE)
			shiftLeft(MAX_SHIFT);
		
		if (shift < 0)
			shiftLeft(-shift);
		else
			shiftRight(shift);
	}
	
	// append methods
	
	public void appendLeft(byte[] buffer, int start, int end)
	{
		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		int bufferLength = end - start;
		
		if (bufferLength > this.start)
			throw new BufferIsFullException();
		
		start -= bufferLength;
		System.arraycopy(buffer, start, this.buffer, start, bufferLength);
	}
	
	public void appendRight(byte[] buffer, int start, int end)
	{
		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		int bufferLength = end - start;
		
		if (bufferLength > this.buffer.length - this.end)
			throw new BufferIsFullException();
		
		System.arraycopy(buffer, start, this.buffer, end, bufferLength);
		end += bufferLength;
	}
	
	public void append(byte[] buffer, int start, int end, final int minShift, final boolean toLeft)
	{
		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		if (CHECK_INVALID_SHIFT)
			SafeTools.checkInvalidIndex(minShift, 0, MAX_SHIFT, "shift");
		
		int bufferLength = end - start;
		
		if (toLeft)
		{
			if (bufferLength <= this.start)
			{
				start -= bufferLength;
				System.arraycopy(buffer, start, this.buffer, this.start, bufferLength);
				return;
			}
		}
		else
		{
			if (bufferLength <= this.buffer.length - this.end)
			{
				System.arraycopy(buffer, start, this.buffer, this.end, bufferLength);
				this.end += bufferLength;
				return;
			}
		}
		
		if (bufferLength > this.buffer.length - this.end + this.start)
			throw new BufferIsFullException();
		
		if (toLeft)
		{
			shiftRight(Math.min(bufferLength - this.start, minShift));
			
			start -= bufferLength;
			System.arraycopy(buffer, start, this.buffer, this.start, bufferLength);
			return;
		}
		else
		{
			shiftLeft(Math.min(this.buffer.length - this.end - bufferLength, minShift));
			
			System.arraycopy(buffer, start, this.buffer, this.end, bufferLength);
			this.end += bufferLength;
			return;
		}
	}
	
	// pop methods
	
	public void popLeft(byte[] buffer, int start, int end)
	{
		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		int bufferLength = end - start;
		
		if (bufferLength > this.end - this.start)
			throw new NotEnoughDataException();
		
		System.arraycopy(this.buffer, this.start, buffer, start, bufferLength);
		this.start += bufferLength;
	}
	
	public void popRight(byte[] buffer, int start, int end)
	{
		if (CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		int bufferLength = end - start;
		
		if (bufferLength > this.end - this.start)
			throw new NotEnoughDataException();
		
		this.end -= bufferLength;
		System.arraycopy(this.buffer, this.end, buffer, start, bufferLength);
	}
}
