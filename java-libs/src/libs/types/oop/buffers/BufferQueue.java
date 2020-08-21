
package libs.types.oop.buffers;

import libs.exceptions.BufferIsFullException;
import libs.exceptions.NotEnoughDataException;
import libs.tools.MathTools;
import libs.tools.bytes.ByteTools;

public class BufferQueue
{
	protected byte[] buffer;
	protected int start;
	protected int end;

	// constructors

	public BufferQueue(byte[] buffer, int start, int end)
	{
		this.buffer = buffer;
		this.start = start;
		this.end = end;
	}

	public BufferQueue(int size)
	{
		this.buffer = new byte[size];
		this.start = 0;
		this.end = 0;
	}

	protected BufferQueue()
	{
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

	// get and set byte

	public byte get(int index)
	{
		return buffer[start + index];
	}

	public void set(int index, byte value)
	{
		buffer[start + index] = value;
	}

	// length and size functions

	public int length()
	{
		return end - start;
	}

	public int size()
	{
		return buffer.length;
	}

	// remaining functions

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

	// test functions

	public boolean isEmpty()
	{
		return start == end;
	}

	public boolean isFull()
	{
		return (start == 0) && (end == buffer.length);
	}

	// private shift functions

	private void shiftEmpty(int shift)
	{
		System.arraycopy(buffer, start, buffer, start + shift, start - end);
		start += shift;
		end += shift;
	}

	// shift functions

	public static final int MAX_SHIFT = Integer.MAX_VALUE;

	public void shiftRight(int shift)
	{
		if (start == end)
			return;

		if (shift == 0)
			return;

		if (shift > buffer.length - end)
			shift = buffer.length - end;

		shiftEmpty(shift);
	}

	public void shiftLeft(int shift)
	{
		if (start == end)
			return;

		if (shift == 0)
			return;

		if (shift > start)
			shift = start;

		shiftEmpty(-shift);
	}

	public void shift(int shift)
	{
		if (start == end)
			return;

		if (shift == 0)
			return;

		if (shift > buffer.length - end)
			shift = buffer.length - end;
		else if (shift < -start)
			shift = -start;

		shiftEmpty(shift);
	}

	// fill functions

	public void fill(byte value)
	{
		ByteTools.fill(buffer, start, end, value);
	}

	public void fillAll(byte value)
	{
		ByteTools.fill(buffer, 0, buffer.length, value);
		this.start = 0;
		this.end = buffer.length;
	}

	// private append functions

	private void appendEmpty(byte[] buffer, int start, int end, int shift, final boolean toLeft)
	{
		int length = end - start;

		shift = MathTools.limitMaximum(shift, this.buffer.length - length);

		if (toLeft)
		{
			this.start = shift;
			this.end = shift + length;
		} else
		{
			this.start = this.buffer.length - length - shift;
			this.end = this.buffer.length - shift;
		}

		System.arraycopy(buffer, start, this.buffer, this.start, length);
	}

	// private append side functions

	private boolean appendSideEmpty(byte[] buffer, int start, int end, final boolean toLeft)
	{
		int length = end - start;

		if (toLeft)
		{
			if (length > this.start)
				return false;

			this.start -= length;
			System.arraycopy(buffer, start, this.buffer, this.start, length);
		} else
		{
			if (length > this.buffer.length - this.end)
				return false;

			System.arraycopy(buffer, start, this.buffer, this.end, length);
			this.end += length;
		}

		return true;
	}

	// append side functions

	public void appendSide(byte[] buffer, int start, int end, final boolean toLeft)
	{
		if (this.start == this.end)
		{
			appendEmpty(buffer, start, end, 0, toLeft);
			return;
		}

		if (!appendSideEmpty(buffer, start, end, toLeft))
			throw new BufferIsFullException();
	}

	public void appendSide(byte[] buffer, final boolean toLeft)
	{
		appendSide(buffer, 0, buffer.length, toLeft);
	}

	// append functions

	public void append(byte[] buffer, int start, int end, int shift, final boolean toLeft)
	{
		if (this.start == this.end)
		{
			appendEmpty(buffer, start, end, shift, toLeft);
			return;
		}

		int length = end - start;

		if (length > this.buffer.length - this.end + this.start)
			throw new BufferIsFullException();

		int maxShift;
		int minShift;

		if (toLeft)
		{
			minShift = (length > this.start) ? length - this.start : 0;
			maxShift = (this.buffer.length - this.end);

			shift = minShift + MathTools.limitMaximum(shift, maxShift - minShift);
		} else
		{
			minShift = (length > this.buffer.length - this.end) ? length - (this.buffer.length - this.end) : 0;
			maxShift = this.start;

			shift = -(minShift + MathTools.limitMaximum(shift, maxShift - minShift));
		}
		shiftEmpty(shift);

		appendSideEmpty(buffer, start, end, toLeft);
	}

	public void append(byte[] buffer, final int shift, final boolean toLeft)
	{
		append(buffer, 0, buffer.length, shift, toLeft);
	}

	// pop methods

	public void popSide(byte[] buffer, int start, int end, final boolean fromLeft)
	{
		int length = end - start;

		if (length > this.end - this.start)
			throw new NotEnoughDataException();

		if (fromLeft)
		{
			this.end -= length;
			System.arraycopy(this.buffer, this.end, buffer, start, length);
		} else
		{
			System.arraycopy(this.buffer, this.start, buffer, start, length);
			this.start += length;
		}
	}
}
