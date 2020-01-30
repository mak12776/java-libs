
package libs.bytes;

import libs.safe.SafeTools;

public class ByteIO
{
	public static final boolean CHECK_INTEGER_BYTES = true;
	
	// read functions

	public static long read(byte[] buffer, int offset, int size)
	{
		if (CHECK_INTEGER_BYTES)
			SafeTools.checkIntegerBytes(size);

		long value = 0;

		for (int index = 0; index < size; index += 1)
		{
			value <<= 8;
			value = value | ((long) buffer[offset + index] & 0xFF);
		}

		return value;
	}

	public static byte readByte(byte[] buffer, int offset)
	{
		byte value = 0;

		value |= buffer[offset];

		return value;
	}

	public static short readShort(byte[] buffer, int offset)
	{
		short value = 0;

		value |= (buffer[offset++] & 0xFF) << 8;
		value |= (buffer[offset] & 0xFF);

		return value;
	}

	public static int readInt(byte[] buffer, int offset)
	{
		int value = 0;

		value |= (buffer[offset++] & 0xFF) << 24;
		value |= (buffer[offset++] & 0xFF) << 16;
		value |= (buffer[offset++] & 0xFF) << 8;
		value |= (buffer[offset] & 0xFF);

		return value;
	}

	public static long readLong(byte[] buffer, int offset)
	{
		long value = 0;

		value |= ((long) buffer[offset++] & 0xFF) << 56;
		value |= ((long) buffer[offset++] & 0xFF) << 48;
		value |= ((long) buffer[offset++] & 0xFF) << 40;
		value |= ((long) buffer[offset++] & 0xFF) << 32;
		value |= ((long) buffer[offset++] & 0xFF) << 24;
		value |= ((long) buffer[offset++] & 0xFF) << 16;
		value |= ((long) buffer[offset++] & 0xFF) << 8;
		value |= ((long) buffer[offset] & 0xFF);

		return value;
	}

	// write functions

	public static void write(byte[] buffer, int offset, int size, long value)
	{
		if (CHECK_INTEGER_BYTES)
			SafeTools.checkIntegerBytes(size);

		for (int index = size - 1; index >= 0; index -= 1)
		{
			buffer[offset + index] = (byte) (value & 0xFF);
			value >>>= 8;
		}
	}

	public static void writeByte(byte[] buffer, int offset, byte value)
	{
		buffer[offset] = value;
	}

	public static void writeShort(byte[] buffer, int offset, short value)
	{
		buffer[offset++] = (byte) ((value >> 8) & 0xFF);
		buffer[offset] = (byte) (value & 0xFF);
	}

	public static void writeInt(byte[] buffer, int offset, int value)
	{
		buffer[offset++] = (byte) ((value >> 24) & 0xFF);
		buffer[offset++] = (byte) ((value >> 16) & 0xFF);
		buffer[offset++] = (byte) ((value >> 8) & 0xFF);
		buffer[offset] = (byte) (value & 0xFF);
	}

	public static void writeLong(byte[] buffer, int offset, long value)
	{
		buffer[offset++] = (byte) ((value >> 56) & 0xFF);
		buffer[offset++] = (byte) ((value >> 48) & 0xFF);
		buffer[offset++] = (byte) ((value >> 40) & 0xFF);
		buffer[offset++] = (byte) ((value >> 32) & 0xFF);
		buffer[offset++] = (byte) ((value >> 24) & 0xFF);
		buffer[offset++] = (byte) ((value >> 16) & 0xFF);
		buffer[offset] = (byte) (value & 0xFF);
	}
}
