package libs.tools.others;

import java.io.IOException;
import java.io.OutputStream;

import libs.tools.SafeTools;

public class HexTools
{
	public static byte toHex(int value, boolean upper)
	{
		byte first = (byte) ((upper ? 'A' : 'a') - 10);

		switch (value)
		{
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			return (byte) (value + '0');

		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
			return (byte) (value + first);

		default:
			throw new IllegalArgumentException("invalid value: " + value);
		}
	}

	public static void writeHex(byte[] buffer, int offset, int size, long value, boolean upper)
	{
		if (SafeTools.CHECK_INTEGER_BYTES)
			SafeTools.checkIntegerBytes(size);

		size *= 2;
		for (int index = offset + size - 1; index >= offset; index -= 1)
		{
			buffer[index] = toHex((int) (value & 0x0F), upper);
			value >>>= 4;
		}
	}

	public static void writeHexRow(byte[] buffer, int start, byte[] bytes, int offset, boolean upper)
	{
		// "00 00 00 00 " * 4
		// 0 3 6 9 c

		for (int i = 0; i < 4; i += 1)
		{
			for (int j = 0; j < 4; j += 1)
			{
				if (offset < bytes.length)
				{
					writeHex(buffer, start, 1, bytes[offset], upper);
					offset += 1;
				} else
				{
					buffer[start] = ' ';
					buffer[start + 1] = ' ';
				}

				start += 3;
			}
			start += 1;
		}
	}

	public static void dumpHex(byte[] buffer, OutputStream stream) throws IOException
	{
		byte[] line;

		// "0000 0000:" + (" 00 00 00 00" * 4) + "\n"
		line = new byte[10 + (13 * 4) + 1];

		line[line.length - 1] = '\n';
		line[9] = ':';

		int offset = 0;

		while (offset < buffer.length)
		{
			writeHex(line, 0, 2, offset >>> 16, true);
			writeHex(line, 5, 2, offset & 0xFF, true);

			writeHexRow(line, 12, buffer, offset, true);
			stream.write(line);

			offset += 16;
		}
	}
	
	public static byte[] newSpaceLine(int size)
	{
		byte[] result;

		result = new byte[size];

		size -= 1;
		for (int index = 0; index < size; index += 1)
		{
			result[index] = ' ';
		}
		result[size] = '\n';

		return result;
	}
}
