package tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import exceptions.BaseException;
import exceptions.BigFileSizeException;
import exceptions.InvalidReadNumberException;
import tools.bytes.BytesView;
import tools.bytes.PackedBytesView;
import tools.bytes.UnpackedBytesView;
import tools.types.BufferLines;

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
	
	public static BytesView[] splitLines(Class<?> c, byte[] array)
	{
		BytesView[] result;
		int lnum;
		int start;
		int end;
		
		result = (BytesView[]) ArrayTools.newArray(c, countLines(array));
		for (int i = 0; i < result.length; i += 1)
		{
			result[i] = new BytesView();
		}
		
		lnum = 0;
		start = 0;
		
		while (true)
		{
			end = ByteTools.find(array, start, array.length, new byte[] {'\r', '\n'});
			
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
	
	public static BufferLines readBufferLines(FileInputStream stream) throws IOException, BaseException
	{
		BufferLines result = new BufferLines(null, null);
		
		result.buffer = readFile(stream);
		result.lines = (UnpackedBytesView[]) splitLines(UnpackedBytesView.class, result.buffer);
		return result;
	}
	
	public static BufferLines readBufferLines(String name) throws FileNotFoundException, IOException, BaseException
	{
		return readBufferLines(new FileInputStream(name));
	}
	
	public interface PackedBytesViewStream
	{
		public PackedBytesView next();
	}
	
	public static PackedBytesViewStream readLines(FileInputStream stream) throws IOException, BaseException
	{
		throw new RuntimeException("unimplemented function");
	}
}
