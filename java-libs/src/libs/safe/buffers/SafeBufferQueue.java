package libs.safe.buffers;

import libs.buffers.immutable.BufferQueue;
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
	
	// append side functions
	
	@Override
	public void appendSide(byte[] buffer, boolean toLeft)
	{
		if (SAFE)
			SafeTools.checkNullArgument(buffer, "buffer");
		
		super.appendSide(buffer, toLeft);
	}
	
	@Override
	public void appendSide(byte[] buffer, int start, int end, boolean toLeft)
	{
		if (SAFE)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		super.appendSide(buffer, start, end, toLeft);
	}
	
	// append functions
	
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
	public void popSide(byte[] buffer, int start, int end, boolean fromLeft)
	{
		if (SAFE)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		super.popSide(buffer, start, end, fromLeft);
	}
}
