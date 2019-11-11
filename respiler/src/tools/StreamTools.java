package tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import tools.bytes.BufferUnpackedViews;
import tools.bytes.BytesView;
import tools.bytes.PackedView;
import tools.bytes.UnpackedView;
import tools.exceptions.BaseException;
import tools.exceptions.BigFileSizeException;
import tools.exceptions.InvalidReadNumberException;
import tools.exceptions.UnknownClassException;
import tools.types.ByteTest;

public class StreamTools 
{	
	public static byte[] readFile(FileInputStream stream) throws IOException, BaseException
	{
		long fileSize;
		int readNumber;
		byte[] array;
		
		fileSize = stream.getChannel().size();
		if (fileSize > Integer.MAX_VALUE)
		{
			throw new BigFileSizeException("file size: " + fileSize);
		}
		
		array = new byte[(int)fileSize];
		
		readNumber = stream.read(array);
		if (readNumber != fileSize)
		{
			throw new InvalidReadNumberException("read number: " + readNumber + ", file size: " + fileSize);
		}
		return array;
	}
	
	public static byte[] readFile(String name) throws FileNotFoundException, IOException, BaseException
	{
		return readFile(new FileInputStream(name));
	}
	
	public static int countLines(byte[] array)
	{
		int index;
		int total;
		
		index = 0;
		total = 0;
		while (true)
		{
			if (array[index] == '\r')
			{
				total += 1;
				
				index += 1;
				if (index == array.length)
					break;
				
				if (array[index] == '\n')
				{
					index += 1;
					
					if (index == array.length)
						break;
				}
			}
			else if (array[index] == '\n')
			{
				total += 1;
				
				index += 1;
				if (index == array.length)
					break;
			}
			else
			{
				index += 1;
				if (index == array.length)
				{
					total += 1;
					break;
				}
			}
		}
		
		return total;
	}
	
	public static BytesView[] readLines(Class<?> c, FileInputStream stream) throws IOException, BaseException
	{
		return splitLines(c, readFile(stream));
	}
	
	public static BytesView[] splitLines(Class<?> c, byte[] array)
	{
		BytesView[] result;
		int lnum;
		int start;
		int end;
		
		if (c.isAssignableFrom(PackedView.class))
		{
			result = PackedView.newArray(countLines(array));
		}
		else if (c.isAssignableFrom(UnpackedView.class))
		{
			result = UnpackedView.newArray(countLines(array));
		}
		else
		{
			throw new UnknownClassException("unknown class: " + c.getName());
		}
		
		lnum = 0;
		start = 0;
		
		while (true)
		{
			end = ByteTools.find(array, start, array.length, ByteTest.isBlank);
			
			if (end == array.length)
			{
				result[lnum].set(array, start, end);
				break;
			}
			else if (array[end] == '\r')
			{
				array[end] = '\n';

				end += 1;
				result[lnum].set(array, start, end);
				
				if (end == array.length)
					break;
				
				if (array[end] == '\n')
				{
					end += 1;
					if (end == array.length)
						break;
				}
			}
			else
			{
				end += 1;
				result[lnum].set(array, start, end);
				
				if (end == array.length)
					break;
			}
			
			lnum += 1;
			start = end;
		}
		
		return result;
	}
	
	public static BufferUnpackedViews readLines(FileInputStream stream) throws IOException, BaseException
	{
		BufferUnpackedViews result = new BufferUnpackedViews(null, null);
		
		result.buffer = readFile(stream);
		result.views = (UnpackedView[]) splitLines(UnpackedView.class, result.buffer);
		return result;
	}
	
	public static BufferUnpackedViews readLines(String name) throws FileNotFoundException, IOException, BaseException
	{
		return readLines(new FileInputStream(name));
	}
}
