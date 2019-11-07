package tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import exceptions.BaseException;
import exceptions.BigFileSizeException;
import exceptions.InvalidReadNumberException;

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
	
	public static BytesView[] splitLines(byte[] array)
	{
		BytesView[] result;
		int total;
		int index;
		int lnum;
		
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
		
		result = new BytesView[total];
		for (lnum = 0; lnum < result.length; lnum += 1)
		{
			result[lnum] = new BytesView(0, 0);
		}
		
		index = 0;
		lnum = 0;
		
		result[lnum].start = index;
		while (true)
		{
			if (array[index] == '\r')
			{
				array[index] = '\n';
				
				index += 1;
				result[lnum].end = index;
				
				if (index == array.length)
					break;
				
				if (array[index] == '\n')
				{
					index += 1;
					
					if (index == array.length)
						break;
				}
				
				lnum += 1;
				result[lnum].start = index;
				
			}
			else if (array[index] == '\n')
			{
				index += 1;
				result[lnum].end = index;
				
				if (index == array.length)
					break;
				
				lnum += 1;
				result[lnum].start = index;
			}
			else
			{
				index += 1;
				
				if (index == array.length)
				{
					result[lnum].end = index;
					break;
				}
			}
		}
		
		return result;
	}
	
	public static BufferLines readLines(FileInputStream stream) throws IOException, BaseException
	{
		BufferLines bufferLines = new BufferLines(null, null);
		
		bufferLines.buffer = readFile(stream);
		bufferLines.lines = splitLines(bufferLines.buffer);
		return bufferLines;
	}
	
	public static BufferLines readLines(String name) throws FileNotFoundException, IOException, BaseException
	{
		return readLines(new FileInputStream(name));
	}
}
