package libs.buffers.safe;

import libs.buffers.BufferQueue;
import libs.safe.SafeOptions;
import libs.safe.SafeTools;

public class SafeBufferQueue extends BufferQueue
{
	public static final boolean SAFE = SafeOptions.get(SafeBufferQueue.class);
	
	public SafeBufferQueue(byte[] buffer, int start, int end)
	{
		if (SAFE)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		this.buffer = buffer;
		this.start = start;
		this.end = end;
	}
	
	public SafeBufferQueue(int size)
	{
		if (SAFE)
			SafeTools.checkInvalidIndexMinimum(size, 0, "size");
		
		this.buffer = new byte[size];
		this.start = 0;
		this.end = 0;
	}
	
	// set and get byte at index
	
	@Override
	public byte get(int index)
	{
		if (SAFE)
			SafeTools.checkIndexOutOfBounds(index, 0, end - start, "index");
		
		return super.get(index);
	}
	
	@Override
	public void set(int index, byte value)
	{
		if (SAFE)
			SafeTools.checkIndexOutOfBounds(index, 0, end - start, "index");
		
		super.set(index, value);
	}
	
	// shift functions
	
	@Override
	public void shiftRight(int shift)
	{
		if (SAFE)
			SafeTools.checkInvalidIndexMinimum(shift, 0, "shift");
		
		super.shiftRight(shift);
	}
	
	@Override
	public void shiftLeft(int shift)
	{
		if (SAFE)
			SafeTools.checkInvalidIndexMinimum(shift, 0, "shift");
		
		super.shift(shift);
	}
	
	@Override
	public void append(byte[] buffer, int start, int end, int shift, boolean toLeft)
	{
		if (SAFE)
		{
			SafeTools.checkBufferStartEnd(buffer, start, end);
			SafeTools.checkInvalidIndexMinimum(shift, 0, "shift");
		}
		
		super.append(buffer, start, end, shift, toLeft);
	}
	
	@Override
	public void append(byte[] buffer, int shift, boolean toLeft)
	{
		if (SAFE)
		{
			SafeTools.checkNullArgument(buffer, "buffer");
			SafeTools.checkInvalidIndexMinimum(shift, 0, "shift");
		}
		
		super.append(buffer, shift, toLeft);
	}
	
	// pop functions
	
	@Override
	public void popLeft(byte[] buffer, int start, int end)
	{
		if (SAFE)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		super.popLeft(buffer, start, end);
	}
	
	@Override
	public void popRight(byte[] buffer, int start, int end)
	{
		if (SAFE)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		super.popRight(buffer, start, end);
	}
}
