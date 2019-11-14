package tools;

import tools.bytes.BytesView;
import tools.bytes.PackedView;
import tools.bytes.UnpackedView;
import tools.exceptions.UnknownClassException;
import tools.types.ByteTest;

public class BytesTools
{
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

	public static int find(byte[] buffer, int start, int end, ByteTest test)
	{
		int index;
		
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
			if (!test.test(buffer[index]))
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
