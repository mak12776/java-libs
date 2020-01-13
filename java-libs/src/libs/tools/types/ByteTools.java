package libs.tools.types;

import java.io.IOException;
import java.io.OutputStream;

import libs.bytes.ByteTest;
import libs.bytes.ByteView;
import libs.exceptions.UnknownClassException;
import libs.tools.SafeTools;
import libs.views.ByteViewInterface;
import libs.views.View;

public class ByteTools
{
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
	
	public static final long getMask(final int size)
	{
		switch (size)
		{
		case 8:	 return 0xFFL;
		case 16: return 0xFFFFL;
		case 32: return 0xFFFFFFFFL;
		case 64: return 0xFFFFFFFFFFFFFFL;
		default: throw new IllegalArgumentException("invalid size: " + size);
		}
	}
	
	public static byte toHex(int value, boolean upper)
	{
		byte first = (byte) ((upper ? 'A' : 'a') - 10);
		
		switch (value)
		{
		case 0: case 1: case 2: case 3: case 4: 
		case 5: case 6: case 7: case 8: case 9:
			return (byte) (value + '0');
			
		case 10: case 11: case 12: case 13: 
		case 14: case 15:
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
		// "00 00 00 00  " * 4
		//	0  3  6  9  c
		
		for (int i = 0; i < 4; i += 1)
		{
			for (int j = 0; j < 4; j += 1)
			{
				if (offset < bytes.length)
				{
					writeHex(buffer, start, 1, bytes[offset], upper);
					offset += 1;
				}
				else
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
		
		// "0000 0000:" + ("  00 00 00 00" * 4) + "\n"
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

	// +++ algorithms +++
	
	// test all and any
	
	public static boolean byteIn(byte b, byte[] bytes)
	{
		for (int index = 0; index < bytes.length; index += 1)
			if (bytes[index] == b)
				return true;
		return false;
	}
	
	public static boolean testAny(byte[] buffer, int start, int end, ByteTest test)
	{
		for (; start < end; start += 1)
			if (test.test(buffer[start]))
				return true;
		return false;
	}
	
	public static boolean testAny(byte[] buffer, int start, int end, byte value)
	{
		for (; start < end; start += 1)
			if (buffer[start] == value)
				return true;
		return false;
	}
	
	public static boolean testAll(byte[] buffer, int start, int end, ByteTest test)
	{
		for (; start < end; start += 1)
			if (!test.test(buffer[start]))
				return false;
		return true;
	}
	
	public static boolean testAll(byte[] buffer, int start, int end, byte value)
	{
		for (; start < end; start += 1)
			if (buffer[start] != value)
				return false;
		return true;
	}
	
	// find test functions
	
	public static int find(byte[] buffer, int start, int end, ByteTest test)
	{
		int index;
		
		if (SafeTools.CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		index = start;
		while (index < end)
		{
			if (test.test(buffer[index]))
				return index;
			index += 1;
		}
		return end;
	}
	
	public static int findNot(byte[] buffer, int start, int end, ByteTest test)
	{
		int index;
		
		if (SafeTools.CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		index = start;
		while (index < end)
		{
			if (!test.test(buffer[index]))
				return index;
			index += 1;
		}
		return end;
	}
	
	public static int rfind(byte[] buffer, int start, int end, ByteTest test)
	{
		int index;

		if (SafeTools.CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		index = end;
		while (index > start)
		{
			index -= 1;
			if (test.test(buffer[index]))
				return index;
		}
		return end;
	}
	
	public static int rfindNot(byte[] buffer, int start, int end, ByteTest test)
	{
		int index;
		
		if (SafeTools.CHECK_BUFFER_START_END)
			SafeTools.checkBufferStartEnd(buffer, start, end);
		
		index = end;
		while (index > start)
		{
			index -= 1;
			if (!test.test(buffer[index]))
				return index;
		}
		return end;
	}
	
	// comparison functions
	
	public static int compare(byte[] buffer, int bufferOffset, byte[] bytes, int bytesOffset, int length)
	{
		int diff = 0;
		for (int i = 0; i < length; i += 1)
		{
			diff = bytes[bytesOffset + i] - buffer[bufferOffset + i];
			if (diff != 0) 
				break;
		}
		return diff;
	}
	
	public static boolean isEqual(byte[] buffer, int bufferOffset, byte[] bytes, int bytesOffset, int length)
	{
		return compare(buffer, bufferOffset, bytes, bytesOffset, length) == 0;
	}
	
	public static void copy(byte[] destination, int destinationOffset, byte[] source, int sourceOffset, int length)
	{
		for (int index = 0; index < length; index += 1)
		{
			destination[destinationOffset + index] = source[sourceOffset + index];
		}
	}
	
	// starts with & ends with
	
	public static boolean startsWith(byte[] buffer, int bufferStart, int bufferEnd, byte[] bytes, int bytesStart, int bytesEnd)
	{
		int bufferLength = bufferEnd - bufferStart;
		int bytesLength = bytesEnd - bytesStart;
		return (bufferLength >= bytesLength) && isEqual(buffer, bufferStart, bytes, bytesStart, bytesLength);
	}
	
	public static boolean endsWith(byte[] buffer, int bufferStart, int bufferEnd, byte[] bytes, int bytesStart, int bytesEnd)
	{
		int bufferLength = bufferEnd - bufferStart;
		int bytesLength = bytesEnd - bytesStart;
		return (bufferLength >= bytesLength) && isEqual(buffer, bufferEnd - bytesLength, bytes, bytesStart, bytesLength);
	}
	
	// search
	
	public static int search(byte[] buffer, int bufferStart, int bufferEnd, byte[] bytes, int bytesStart, int bytesEnd)
	{
		int bufferLength = bufferEnd - bufferStart;
		int bytesLength = bytesEnd - bytesStart;
		
		if (bufferLength >= bytesLength)
		{
			for (int index = bufferStart, end = bufferEnd - bytesLength; index <= end; index += 1)
			{
				if (isEqual(buffer, index, bytes, bytesStart, bytesLength))
					return index;
			}
		}
		return bufferEnd;
	}
	
	public static int lsearch(byte[] buffer, int bufferStart, int bufferEnd, byte[] bytes, int bytesStart, int bytesEnd)
	{
		int bufferLength = bufferEnd - bufferStart;
		int bytesLength = bytesEnd - bytesStart;
		
		if (bufferLength >= bytesLength)
		{
			for (int index = bufferEnd - bytesLength; index >= 0; index -= 1)
			{
				if (isEqual(buffer, index, bytes, bytesStart, bytesLength))
					return index;
			}
		}
		return bufferEnd;
	}
	
	// join bytes array
	
	public static byte[] joinBytes(byte[]... bytesArray)
	{
		byte[] result;
		int length;
		
		length = ArrayTools.sumArrayLengths(bytesArray);
		
		if (length == 0)
			return null;
		
		result = new byte[length];
		
		length = 0;
		for (int i = 0; i < bytesArray.length; i += 1)
		{
			copy(result, length, bytesArray[i], 0, bytesArray[i].length);
			length += bytesArray[i].length;
		}
		
		return result;
	}
	
	// count lines & split lines
	
	public static int countLines(byte[] buffer, int start, int end)
	{
		int index;
		int total;
		
		total = 1;
		
		index = start;
		while (index < end)
		{
			if (buffer[index] == '\r')
			{
				total += 1;
				
				index += 1;
				if (index == end)
					break;
				
				if (buffer[index] == '\n')
					index += 1;
				
				continue;
			}
			else if (buffer[index] == '\n')
			{
				total += 1;
				
				index  += 1;
				continue;
			}
			index += 1;
		}
		
		return total;
	}
	
	private static void splitLines(byte[] buffer, int bufferStart, int bufferEnd, ByteViewInterface[] lines)
	{
		int lnum;
		int start;
		int end;
		
		lnum = 0;
		start = bufferStart;
		
		end = start;
		while (end < bufferEnd)
		{
			if (buffer[end] == '\n')
			{
				end += 1;
				lines[lnum].set(buffer, start, end);
			}
			else if (buffer[end] == '\r')
			{
				buffer[end] = '\n';
				
				end += 1;
				lines[lnum].set(buffer, start, end);
				
				if (end == bufferEnd)
					break;
				
				if (buffer[end] == '\n')
					end += 1;
			}
			else
			{
				end += 1;
				continue;
			}
			
			lnum += 1;
			start = end;
		}
		
		lines[lnum].set(buffer, start, end);
	}
	
	public static ByteViewInterface[] splitLines(Class<?> c, byte[] array)
	{
		ByteViewInterface[] result;
		
		if (c.isAssignableFrom(ByteView.class))
		{
			result = ByteView.newArray(countLines(array, 0, array.length));
		}
		else if (c.isAssignableFrom(View.class))
		{
			result = View.newArray(countLines(array, 0, array.length));
		}
		else
		{
			throw new UnknownClassException("unknown class: " + c.getName());
		}
		
		splitLines(array, 0, array.length, result);
		
		return result;
	}
}
