package olds;

import java.io.IOException;
import java.io.InputStream;

import respiler.Main;

public class LinkedBuffer 
{
	public byte[] array;
	public int length;
	public LinkedBuffer next;
	
	public LinkedBuffer(int size, LinkedBuffer next)
	{		
		this.array = new byte[size];
		this.length = 0;
		this.next = next;
	}
	
	public static LinkedBuffer readFileFull(InputStream stream, int size) throws IOException
	{
		LinkedBuffer buffer;
		
		buffer = new LinkedBuffer(size, null);
		if (buffer.readFileFull(stream) == 0)
		{
			return null;
		}
		return buffer;
	}

	public int size()
	{
		return array.length;
	}
	
	public boolean isFull()
	{
		return length == array.length;
	}
	
	public boolean isEmpty()
	{
		return length == 0;
	}
	
	public void clear()
	{
		length = 0;
	}
	
	public void popFirst(int size)
	{
		if (Main.SAFE)
		{
			if (size < 0 || size > length)
			{
				throw new IllegalArgumentException("invalid size: " + String.valueOf(size));
			}
		}
		
		length -= size; 
		for (int i = 0; i < length; i += 1)
		{
			array[i] = array[size + i];
		}
	}
	
	public void popLast(int size)
	{
		if (Main.SAFE)
		{
			if (size < 0 || size > length)
			{
				throw new IllegalArgumentException("invalid size: " + String.valueOf(size));
			}
		}
		
		length -= size;
	}
	
	public int readFile(InputStream stream) throws IOException
	{
		length = stream.read(array);
		if (length == -1)
		{
			length = 0;
		}
		return length;
	}
	
	public int readFileFull(InputStream stream) throws IOException
	{
		int readNumber;
		
		length = 0;
		while (length != array.length)
		{
			readNumber = stream.read(array, length, array.length - length);
			if (readNumber == -1)
			{
				return length;
			}
			length += readNumber;
		}
		return length;
	}
	
	public int readFileFill(InputStream stream) throws IOException
	{
		int readNumber;
		
		if (Main.SAFE)
		{
			if (length == array.length)
			{
				throw new IllegalStateException("buffer is full.");
			}
		}
		
		readNumber = stream.read(array, length, array.length - length);
		if (readNumber == -1)
		{
			return 0;
		}
		length += readNumber;
		return readNumber;
	}
}
