
package libs.types.buffers.immutable.safe;

import java.io.IOException;
import java.io.InputStream;

import libs.safe.SafeOptions;
import libs.safe.SafeTools;
import libs.types.buffers.immutable.Buffer;

public class SafeBuffer extends Buffer
{
	public static final boolean SAFE = SafeOptions.get(Buffer.class);

	public SafeBuffer(byte[] buffer, int length)
	{
		if (SAFE)
		{
			SafeTools.checkNullArgument(buffer, "buffer");
			SafeTools.checkInvalidIndex(length, 0, buffer.length, "length");
		}

		this.buffer = buffer;
		this.length = length;
	}

	public SafeBuffer(int size)
	{
		if (SAFE)
			SafeTools.checkNegativeIndex(size, "size");

		this.buffer = new byte[size];
		this.length = 0;
	}

	// get and set byte at index

	@Override
	public byte get(int index)
	{
		if (SAFE)
			SafeTools.checkIndexOutOfBounds(index, 0, length, "index");

		return buffer[index];
	}

	@Override
	public void set(int index, byte value)
	{
		if (SAFE)
			SafeTools.checkIndexOutOfBounds(index, 0, length, "index");

		buffer[index] = value;
	}

	// file reading functions

	@Override
	public int readFile(InputStream stream) throws IOException
	{
		if (SAFE)
			SafeTools.checkNullArgument(stream, "stream");

		return super.readFile(stream);
	}

	@Override
	public int readFileFull(InputStream stream) throws IOException
	{
		if (SAFE)
			SafeTools.checkNullArgument(stream, "stream");

		return super.readFileFull(stream);
	}

	@Override
	public int readFileMore(InputStream stream) throws IOException
	{
		if (SAFE)
			SafeTools.checkNullArgument(stream, "stream");

		return super.readFileMore(stream);
	}

	// delete functions

	@Override
	public void delete(int length, final boolean checkEnoughData, final boolean fromLeft)
	{
		if (SAFE)
			SafeTools.checkInvalidIndexMinimum(length, 0, "length");

		super.delete(length, checkEnoughData, fromLeft);
	}

	// append functions

	@Override
	public void append(byte[] buffer, int start, int end)
	{
		if (SAFE)
			SafeTools.checkBufferStartEnd(buffer, start, end);

		super.append(buffer, start, end);
	}

	@Override
	public void append(byte[] buffer)
	{
		if (SAFE)
			SafeTools.checkNullArgument(buffer, "buffer");

		super.append(buffer);
	}

	@Override
	public void append(int size, long value)
	{
		if (SAFE)
			SafeTools.checkIntegerBytes(size);

		super.append(size, value);
	}

	// pop functions

	@Override
	public void pop(byte[] buffer, int start, int end)
	{
		if (SAFE)
			SafeTools.checkBufferStartEnd(buffer, start, end);

		super.pop(buffer, start, end);
	}

	@Override
	public void pop(byte[] buffer)
	{
		if (SAFE)
			SafeTools.checkNullArgument(buffer, "buffer");

		super.pop(buffer);
	}

	@Override
	public long pop(int size)
	{
		if (SAFE)
			SafeTools.checkIntegerBytes(size);

		return super.pop(size);
	}

	// ++ append and pop integer types ++
}
