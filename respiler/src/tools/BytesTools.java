package tools;

import java.io.IOException;
import java.io.OutputStream;

import tests.junit.BytesToolsTest;
import tools.bytes.BytesView;
import tools.bytes.PackedView;
import tools.bytes.UnpackedView;
import tools.exceptions.UnknownClassException;
import tools.types.ByteTest;

public class BytesTools
{	
	public static boolean isNull(byte b)
	{
		return (b == '\0');
	}
	
	public static boolean isBlank(byte b)
	{
		return (b == ' ') || (b == '\t');
	}
	
	public static boolean isNewline(byte b)
	{
		return (b == '\n');
	}
	
	public static boolean isCarriageReturn(byte b)
	{
		return (b == '\r');
	}

	public static boolean isLower(byte b)
	{
		return ('a' <= b) && (b <= 'z');
	}
	
	public static boolean isUpper(byte b)
	{
		return ('A' <= b) && (b <= 'Z');
	}
	
	public static boolean isDigit(byte b)
	{
		return ('0' <= b) && (b <= '9');
	}
	
	public static boolean isLetter(byte b)
	{
		return  (('a' <= b) && (b <= 'z')) ||
				(('A' <= b) && (b <= 'Z'));
	}
	
	public static boolean isLetterDigit(byte b)
	{
		return  (('a' <= b) && (b <= 'z')) ||
				(('A' <= b) && (b <= 'Z')) ||
				(('0' <= b) && (b <= '9'));
	}
	
	public static boolean isHexDigit(byte b)
	{
		return  (('0' <= b) && (b <= '9')) ||
				(('a' <= b) && (b <= 'f')) ||
				(('A' <= b) && (b <= 'F'));
	}
	
	public static final long getMask(final int size)
	{
		switch (size)
		{
		case 8:
			return 0xFFL;
		case 16:
			return 0xFFFFL;
		case 32:
			return 0xFFFFFFFFL;
		case 64:
			return 0xFFFFFFFFFFFFFFL;
		default:
			throw new IllegalArgumentException("invalid size: " + size);
		}
	}
	
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
	
	public static void writeHex(byte[] buffer, int start, int size, long value, boolean upper)
	{
		if (SafeTools.CHECK_INTEGER_BYTES)
			SafeTools.checkIntegerBytes(size);
		
		size *= 2;
		for (int index = start + size - 1; index >= start; index -= 1)
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
			
			if (test(buffer, offset, buffer.length, ByteTest.isNull))
			{
				offset += 16;
				if (offset >= buffer.length)
					break;
				
				if (test(buffer, offset, buffer.length, ByteTest.isNull))
				{
					
				}
			}
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
	
	public static long read(byte[] buffer, int offset, int size)
	{
		if (SafeTools.CHECK_INTEGER_BYTES)
			SafeTools.checkIntegerBytes(size);
		
		long value = 0;
		
		for (int index = 0; index < size; index += 1)
		{
			value <<= 8;
			value = value | ((long) buffer[offset + index] & 0xFF);
		}
		
		return value;
	}
	
	public static void write(byte[] buffer, int offset, int size, long value)
	{
		if (SafeTools.CHECK_INTEGER_BYTES)
			SafeTools.checkIntegerBytes(size);
		
		for (int index = size - 1; index >= 0; index -= 1)
		{
			buffer[offset + index] = (byte) (value & 0xFF);
			value >>>= 8;
		}
	}

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
	
	public static boolean byteIn(byte b, byte[] bytes)
	{
		for (int i = 0; i < bytes.length; i += 1)
		{
			if (bytes[i] == b)
				return true;
		}
		return false;
	}
	
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
	
	public static boolean test(byte[] buffer, int start, int end, ByteTest test)
	{
		for (int index = 0; index < end; index += 1)
		{
			if (test.test(buffer[index]))
				continue;
			return false;
		}
		return true;
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
	
	public static int sumBytesArrayLength(byte[]... bytesArray)
	{
		int length = 0;
		
		for (int i = 0; i < bytesArray.length; i += 1)
		{
			length += bytesArray[i].length;
		}
		
		return length;
	}
	
	public static byte[] joinBytes(byte[]... bytesArray)
	{
		byte[] result;
		int length;
		
		length = sumBytesArrayLength(bytesArray);
		
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
	
	private static void splitLines(byte[] buffer, int bufferStart, int bufferEnd, BytesView[] lines)
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
	
	public static BytesView[] splitLines(Class<?> c, byte[] array)
	{
		BytesView[] result;
		
		if (c.isAssignableFrom(PackedView.class))
		{
			result = PackedView.newArray(countLines(array, 0, array.length));
		}
		else if (c.isAssignableFrom(UnpackedView.class))
		{
			result = UnpackedView.newArray(countLines(array, 0, array.length));
		}
		else
		{
			throw new UnknownClassException("unknown class: " + c.getName());
		}
		
		splitLines(array, 0, array.length, result);
		
		return result;
	}
}
